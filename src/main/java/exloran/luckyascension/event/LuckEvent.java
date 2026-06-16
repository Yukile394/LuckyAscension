package exloran.luckyascension.event;

import com.luckyascension.capability.ILuckCapability;
import com.luckyascension.capability.LuckCapabilityProvider;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Şans Veri Yönetimi Olayları
 *
 * Oyuncu ölüp respawn olduğunda şans verisinin korunması
 * ve capability'nin düzgün kopyalanması için gerekli.
 *
 * Minecraft'ta ölünce yeni bir Player entity oluşturulur,
 * bu yüzden capability'yi eski entity'den yenisine kopyalamamız gerekir.
 */
public class LuckEvent {

    /**
     * Oyuncu ölüp yeniden doğduğunda şans verisini koru
     */
    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event) {
        Player original = event.getOriginal();
        Player clone = event.getEntity();

        // Orijinal entity'nin capability'sini revive et (veri okumak için)
        original.reviveCaps();

        // Orijinalden şans verisini al ve klona kopyala
        original.getCapability(LuckCapabilityProvider.LUCK_CAPABILITY).ifPresent(originalLuck -> {
            clone.getCapability(LuckCapabilityProvider.LUCK_CAPABILITY).ifPresent(cloneLuck -> {
                // Şans seviyesini kopyala
                cloneLuck.setLuckLevel(originalLuck.getLuckLevel());
            });
        });

        // Orijinal entity'nin capability'sini tekrar invalidate et
        original.invalidateCaps();
    }

    /**
     * Boyut (dimension) değiştirildiğinde şans verisini koru
     * End/Nether'e geçişte veri kaybolmasını engeller
     */
    @SubscribeEvent
    public void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;

        // Şans verisini log'a yaz (debug için)
        serverPlayer.getCapability(LuckCapabilityProvider.LUCK_CAPABILITY).ifPresent(luck -> {
            com.luckyascension.LuckyAscension.LOGGER.debug(
                "[LuckyAscension] {} boyut değiştirdi. Şans Seviyesi: {}",
                serverPlayer.getName().getString(),
                luck.getLuckLevel()
            );
        });
    }

    /**
     * Oyuncu sunucuya bağlandığında şans seviyesini bildir
     */
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer serverPlayer)) return;

        // Kısa bir gecikme sonrası mesaj gönder (spawn efektlerinden sonra)
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
