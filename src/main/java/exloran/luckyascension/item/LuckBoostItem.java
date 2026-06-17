package exloran.luckyascension.item;

import exloran.luckyascension.util.LuckData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

public class LuckBoostItem extends Item {

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
        tooltip.add(Text.literal("§6+1 Sans"));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient && user instanceof ServerPlayerEntity serverPlayer) {
            int newLevel = LuckData.increaseLuck(serverPlayer);
            int newMulti = LuckData.getMultiplier(serverPlayer);

            serverPlayer.sendMessage(Text.literal(
                "§aTebrikler! Sans +1 artti! §6§lSeviye: " + newLevel +
                " §e| Odul: §6§lx" + newMulti
            ), false);

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                    ParticleTypes.HAPPY_VILLAGER,
                    serverPlayer.getX(), serverPlayer.getY() + 1, serverPlayer.getZ(),
                    30, 0.5, 0.5, 0.5, 0.1
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
