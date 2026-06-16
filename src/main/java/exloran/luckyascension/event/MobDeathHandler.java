package com.luckyascension.event;

import com.luckyascension.util.LuckData;
import com.luckyascension.util.LuckRewards;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Random;

/**
 * Zararlı mob öldürüldüğünde özel ödül düşürür.
 * Şans seviyesine göre ödül sayısı artar.
 */
public class MobDeathHandler {

    private static final Random RANDOM = new Random();

    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register(MobDeathHandler::onDeath);
    }

    private static void onDeath(LivingEntity entity, DamageSource source) {
        // Sadece sunucu tarafı
        if (!(entity.getWorld() instanceof ServerWorld serverWorld)) return;

        // Sadece single player
        if (!serverWorld.getServer().isSingleplayer()) return;

        // Öldüren oyuncu mu?
        if (!(source.getAttacker() instanceof ServerPlayerEntity player)) return;

        // Hostile mob mu?
        if (!LuckRewards.isHostile(entity)) return;

        // Ödül listesi al
        List<ItemStack> rewards = LuckRewards.getRewards(entity, RANDOM);
        if (rewards == null || rewards.isEmpty()) return;

        // Kaç ödül düşeceğini belirle
        int count = LuckData.getMultiplier(player);

        // Ödülleri düşür
        for (int i = 0; i < count; i++) {
            ItemStack reward = rewards.get(RANDOM.nextInt(rewards.size())).copy();
            entity.dropStack(reward);
        }

        // Parçacık efekti
        serverWorld.spawnParticles(
            ParticleTypes.HAPPY_VILLAGER,
            entity.getX(), entity.getY() + 1, entity.getZ(),
            15, 0.5, 0.5, 0.5, 0.1
        );
    }
}
