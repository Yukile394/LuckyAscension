package com.luckyascension.event;

import com.luckyascension.capability.ILuckCapability;
import com.luckyascension.capability.LuckCapabilityProvider;
import com.luckyascension.util.LuckRewardUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Random;

/**
 * Mob Ölüm Olay İşleyicisi
 *
 * Zararlı yaratıklar öldürüldüğünde:
 * 1. Normal lootlarını düşürmeye devam ederler
 * 2. Ek olarak şans sistemine göre özel ödüller düşer
 * 3. Ödül miktarı: oyuncunun şans çarpanına göre belirlenir
 * 4. Ödül tipi: yaratık türüne göre belirlenir
 *
 * Desteklenen yaratıklar:
 * Zombie, Skeleton, Creeper, Spider, Enderman,
 * PiglinBrute, Warden, WitherBoss, EnderDragon
 */
public class MobDeathEvent {

    private final Random random = new Random();

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();

        // Sadece sunucu tarafında işlem yap
        if (entity.level().isClientSide()) return;

        // Sadece tek oyunculu dünyalarda çalış
        if (entity.getServer() != null && !entity.getServer().isSingleplayer()) return;

        // Öldüren oyuncu mı? Değilse işlem yapma
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        // Bu yaratık zararlı mı? Kontrol et
        if (!isHostileMob(entity)) return;

        // Oyuncunun şans capability'sini al
        serverPlayer.getCapability(LuckCapabilityProvider.LUCK_CAPABILITY).ifPresent(luck -> {
            // Ödül çarpanı (şans seviyesine göre)
            int rewardCount = luck.getRewardMultiplier();

            // Yaratığa göre ödül listesini al
            List<ItemStack> possibleRewards = LuckRewardUtil.getRewardsForMob(entity, random);

            if (possibleRewards == null || possibleRewards.isEmpty()) return;

            ServerLevel serverLevel = (ServerLevel) entity.level();

            // Çarpan kadar ödül düşür
            for (int i = 0; i < rewardCount; i++) {
                // Rastgele bir ödül seç
                ItemStack reward = possibleRewards.get(random.nextInt(possibleRewards.size())).copy();

                // Ödülü yaratığın pozisyonuna düşür
                entity.spawnAtLocation(reward);
            }

            // Şans seviyesini ve ödül sayısını logla
            com.luckyascension.LuckyAscension.LOGGER.debug(
                "[LuckyAscension] {} öldürüldü, {} ödül düştü (Şans: {})",
                entity.getType().getDescriptionId(),
                rewardCount,
                luck.getLuckLevel()
            );
        });
    }

    /**
     * Verilen entity'nin zararlı bir yaratık olup olmadığını kontrol et
     */
    private boolean isHostileMob(LivingEntity entity) {
        return entity instanceof Zombie
            || entity instanceof Skeleton
            || entity instanceof Creeper
            || entity instanceof Spider
            || entity instanceof Enderman
            || entity instanceof PiglinBrute
            || entity instanceof net.minecraft.world.entity.boss.wither.WitherBoss
            || entity instanceof net.minecraft.world.entity.boss.enderdragon.EnderDragon
            || entity.getType() == EntityType.WARDEN
            // Genel zararlı mob kontrolü (diğer hostile moblar için)
            || entity instanceof net.minecraft.world.entity.monster.Monster;
    }
}
