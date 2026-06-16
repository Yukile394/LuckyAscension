package exloran.luckyascension.event;

import exloran.luckyascension.LuckyAscension;
import exloran.luckyascension.capability.ILuckCapability;
import exloran.luckyascension.capability.LuckCapabilityProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class LuckEvent {

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        Player original = event.getOriginal();
        Player clone = event.getEntity();

        original.reviveCaps();

        original.getCapability(LuckCapabilityProvider.LUCK_CAPABILITY).ifPresent(originalLuck -> {
            clone.getCapability(LuckCapabilityProvider.LUCK_CAPABILITY).ifPresent(cloneLuck -> {
                cloneLuck.setLuckLevel(originalLuck.getLuckLevel());
            });
        });

        original.invalidateCaps();
    }

    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;

        serverPlayer.getCapability(LuckCapabilityProvider.LUCK_CAPABILITY).ifPresent(luck -> {
            LuckyAscension.LOGGER.debug(
                "[LuckyAscension] {} boyut değiştirdi. Şans Seviyesi: {}",
                serverPlayer.getName().getString(),
                luck.getLuckLevel()
            );
        });
    }

    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;

        serverPlayer.getCapability(LuckCapabilityProvider.LUCK_CAPABILITY).ifPresent(luck -> {
            int level = luck.getLuckLevel();
            int multiplier = luck.getRewardMultiplier();

            serverPlayer.sendSystemMessage(
                net.minecraft.network.chat.Component.literal(
                    "§6[Lucky Ascension] §eMevcut Şans Seviyesi: §6§l" + level +
                    " §e| Ödül Çarpanı: §6§lx" + multiplier
                )
            );
        });
    }
}
