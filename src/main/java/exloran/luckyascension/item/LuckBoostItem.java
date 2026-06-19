package exloran.luckyascension.item;

import exloran.luckyascension.util.LuckData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class LuckBoostItem extends Item {

    private static final Random RANDOM = new Random();

    private static final ItemStack[] OP_REWARDS = {
        new ItemStack(Items.NETHERITE_SWORD),
        new ItemStack(Items.NETHERITE_PICKAXE),
        new ItemStack(Items.NETHERITE_HELMET),
        new ItemStack(Items.NETHERITE_CHESTPLATE),
        new ItemStack(Items.NETHERITE_LEGGINGS),
        new ItemStack(Items.NETHERITE_BOOTS),
        new ItemStack(Items.ELYTRA),
        new ItemStack(Items.TOTEM_OF_UNDYING),
        new ItemStack(Items.NETHER_STAR),
        new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 3),
        new ItemStack(Items.DIAMOND_BLOCK, 5),
        new ItemStack(Items.NETHERITE_INGOT, 3),
        new ItemStack(Items.ANCIENT_DEBRIS, 4),
        new ItemStack(Items.ENDER_EYE, 8),
        new ItemStack(Items.EXPERIENCE_BOTTLE, 16),
        new ItemStack(Items.BEACON, 1),
        new ItemStack(Items.SHULKER_SHELL, 4),
        new ItemStack(Items.DRAGON_BREATH, 6),
        new ItemStack(Items.FIREWORK_ROCKET, 64),
    };

    public LuckBoostItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.literal("§6§lSans Artirma");
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("§7Bu esya kullanildiginda"));
        tooltip.add(Text.literal("§aSans Seviyeni arttirir."));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.literal("§eHer kullanim:"));
        tooltip.add(Text.literal("§6+1 Sans §7| §bMevcut Carp. Kadar OP Item"));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient && user instanceof ServerPlayerEntity serverPlayer) {
            int newLevel = LuckData.increaseLuck(serverPlayer);
            int multiplier = LuckData.getMultiplier(serverPlayer);

            for (int i = 0; i < multiplier; i++) {
                ItemStack reward = OP_REWARDS[RANDOM.nextInt(OP_REWARDS.length)].copy();
                serverPlayer.getInventory().offerOrDrop(reward);
            }

            serverPlayer.sendMessage(Text.literal(
                "§aTebrikler! Sans +1 artti! §6§lSeviye: " + newLevel +
                " §e| " + multiplier + " §bOP item kazandin!"
            ), false);

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                    ParticleTypes.HAPPY_VILLAGER,
                    serverPlayer.getX(), serverPlayer.getY() + 1, serverPlayer.getZ(),
                    30, 0.5, 0.5, 0.5, 0.1
                );
                serverWorld.spawnParticles(
                    ParticleTypes.END_ROD,
                    serverPlayer.getX(), serverPlayer.getY() + 1, serverPlayer.getZ(),
                    20, 0.5, 0.5, 0.5, 0.1
                );
            }

            world.playSound(null,
                serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                SoundEvents.ENTITY_PLAYER_LEVELUP,
                SoundCategory.PLAYERS, 1.0f, 1.0f
            );

            stack.decrement(1);
        }

        return TypedActionResult.success(stack, world.isClient);
    }
}
