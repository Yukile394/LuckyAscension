package exloran.luckyascension.item;

import com.luckyascension.capability.ILuckCapability;
import com.luckyascension.capability.LuckCapabilityProvider;
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

/**
 * Şans Artırma İtemi
 *
 * Dragon Breath görünümünde özel bir item.
 * Sağ tıklandığında oyuncunun şans seviyesini 1 artırır.
 * Köylü takasından elde edilir (1 Netherite Külçesi karşılığı).
 *
 * Görsel efektler:
 * - Altın renkli parçacık efekti
 * - Seviye atlama sesi
 * - Sohbet mesajı
 */
public class LuckBoostItem extends Item {

    public LuckBoostItem(Properties properties) {
        super(properties);
    }

    /**
     * Özel item adı - altın renkli ve kalın
     */
    @Override
    public Component getName(ItemStack stack) {
        return Component.literal("§6§lŞans Artırma");
    }

    /**
     * Item tooltip/lore bilgisi
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§7Bu eşya kullanıldığında"));
        tooltip.add(Component.literal("§aŞans Seviyeni artırır."));
        tooltip.add(Component.literal(""));
        tooltip.add(Component.literal("§eHer kullanım:"));
        tooltip.add(Component.literal("§6+1 Şans"));
    }

    /**
     * Sağ tık ile kullan - şans artırma işlemi
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        // Sadece sunucu tarafında işlem yap (multiplayer uyumluluğu için)
        if (!level.isClientSide()) {
            ServerPlayer serverPlayer = (ServerPlayer) player;

            // Oyuncunun luck capability'sini al
            serverPlayer.getCapability(LuckCapabilityProvider.LUCK_CAPABILITY).ifPresent(luck -> {

                // Şans seviyesini artır
                luck.increaseLuck();
                int newLevel = luck.getLuckLevel();

                // Başarı mesajı gönder - yeşil renk
                player.sendSystemMessage(
                    Component.literal("§aTebrikler! Şans +1 arttı! Yeni Seviye: §6§l" + newLevel)
                );

                // Altın parçacık efekti oynat
                spawnLuckParticles(serverPlayer, level);

                // Seviye atlama sesi çal
                level.playSound(
                    null,
                    player.getX(), player.getY(), player.getZ(),
                    SoundEvents.PLAYER_LEVELUP,
                    SoundSource.PLAYERS,
                    1.0f, 1.0f
                );

                // Başarı bildirimi
                serverPlayer.awardStat(net.minecraft.stats.Stats.ITEM_USED.get(this));
            });

            // Itemi 1 azalt (sarf malzeme)
            itemStack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    /**
     * Altın renkli parçacık efekti oluştur
     */
    private void spawnLuckParticles(ServerPlayer player, Level level) {
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            // Oyuncunun etrafında altın parçacıklar fırlat
            serverLevel.sendParticles(
                net.minecraft.core.particles.ParticleTypes.HAPPY_VILLAGER,
                player.getX(), player.getY() + 1, player.getZ(),
                30,    // Parçacık sayısı
                0.5,   // X yayılımı
                0.5,   // Y yayılımı
                0.5,   // Z yayılımı
                0.1    // Hız
            );

            // Enchant parçacıkları ekle (altın görünüm için)
            serverLevel.sendParticles(
                net.minecraft.core.particles.ParticleTypes.ENCHANT,
                player.getX(), player.getY() + 1, player.getZ(),
                20,
                0.5, 0.5, 0.5,
                0.2
            );
        }
    }

    /**
     * Item görünümü - Dragon Breath olarak render et
     * Bu item Dragon Breath'in modelini kullanır
     */
    @Override
    public boolean isFoil(ItemStack stack) {
        // Büyülü parıltı efekti ekle
        return true;
    }
}
