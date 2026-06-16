package exloran.luckyascension.item;

import exloran.luckyascension.capability.ILuckCapability;
import exloran.luckyascension.capability.LuckCapabilityProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LuckBoostItem extends Item {

    public LuckBoostItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.literal("§6§lŞans Artırma");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§7Bu eşya kullanıldığında"));
        tooltip.add(Component.literal("§aŞans Seviyeni artırır."));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§eHer kullanım:"));
        tooltip.add(Component.literal("§6+1 Şans"));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            ServerPlayer serverPlayer = (ServerPlayer) player;

            serverPlayer.getCapability(LuckCapabilityProvider.LUCK_CAPABILITY).ifPresent(luck -> {

                luck.increaseLuck();
                int newLevel = luck.getLuckLevel();

                player.sendSystemMessage(
                    Component.literal("§aTebrikler! Şans +1 arttı! Yeni Seviye: §6§l" + newLevel)
                );

                spawnLuckParticles(serverPlayer, level);

                level.playSound(
                    null,
                    player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PLAYER_LEVELUP,
                    SoundSource.PLAYERS,
                    1.0f, 1.0f
                );

                serverPlayer.awardStat(net.minecraft.stats.Stats.ITEM_USED.get(this));
            });

            itemStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    private void spawnLuckParticles(ServerPlayer player, Level level) {
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            serverLevel.sendParticles(
                net.minecraft.core.particles.ParticleTypes.HAPPY_VILLAGER,
                player.getX(), player.getY() + 1, player.getZ(),
                30, 0.5, 0.5, 0.5, 0.1
            );

            serverLevel.sendParticles(
                net.minecraft.core.particles.ParticleTypes.ENCHANT,
                player.getX(), player.getY() + 1, player.getZ(),
                20, 0.5, 0.5, 0.5, 0.2
            );
        }
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }
}
