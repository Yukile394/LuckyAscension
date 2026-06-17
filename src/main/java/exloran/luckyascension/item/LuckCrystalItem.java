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

public class LuckCrystalItem extends Item {

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
    };

    public LuckCrystalItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.literal("§d§lSans Kristali");
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("§7Sans Ustasi'ndan kazanilan"));
        tooltip.add(Text.literal("§7mistik bir kristal."));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.literal("§eSag Tikla §7→ §6+1 Sans Seviyesi"));
        tooltip.add(Text.literal("§eSag Tikla §7→ §bMevcut Carp. Kadar OP Item"));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.literal("§8\"Sansini sonuna kadar kullan...\""));
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
                "§d§lSans Kristali kullanildi! §6+1 Sans §e| Seviye: §6§l" + newLevel +
                " §e| " + multiplier + " §bOP item kazandin!"
            ), false);

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                    ParticleTypes.END_ROD,
                    serverPlayer.getX(), serverPlayer.getY() + 1, serverPlayer.getZ(),
                    40, 0.6, 0.6, 0.6, 0.15
                );
                serverWorld.spawnParticles(
                    ParticleTypes.HAPPY_VILLAGER,
                    serverPlayer.getX(), serverPlayer.getY() + 1, serverPlayer.getZ(),
                    20, 0.5, 0.5, 0.5, 0.1
                );
            }

            world.playSound(null,
                serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                SoundEvents.ENTITY_PLAYER_LEVELUP,
                SoundCategory.PLAYERS, 1.0f, 0.8f
            );
            world.playSound(null,
                serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME,
                SoundCategory.PLAYERS, 1.0f, 1.2f
            );

            stack.decrement(1);
        }

        return TypedActionResult.success(stack, world.isClient);
    }
}
