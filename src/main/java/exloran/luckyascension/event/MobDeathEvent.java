package exloran.luckyascension.event;

import exloran.luckyascension.capability.LuckCapabilityProvider;
import exloran.luckyascension.util.LuckRewardUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Random;

public class MobDeathEvent {

    private final Random random = new Random();

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();

        // DÜZELTME (Hata 4): Sadece client-side kontrolü yap.
        // Önceki kod "!isSingleplayer()" ile multiplayer'da HİÇ ödül vermiyordu.
        // Hem singleplayer hem multiplayer sunucuda çalışması için bu satır kaldırıldı.
        if (entity.level().isClientSide()) return;

        // Öldüren oyuncu kontrolü
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        if (!(player instanceof ServerPlayer serverPlayer)) return;

        // Sadece düşman moblar için ödül ver
        if (!isHostileMob(entity)) return;

        serverPlayer.getCapability(LuckCapabilityProvider.LUCK_CAPABILITY).ifPresent(luck -> {
            int rewardCount = luck.getRewardMultiplier();
            List<ItemStack> possibleRewards = LuckRewardUtil.getRewardsForMob(entity, random);
            if (possibleRewards == null || possibleRewards.isEmpty()) return;

            for (int i = 0; i < rewardCount; i++) {
                ItemStack reward = possibleRewards.get(random.nextInt(possibleRewards.size())).copy();
                entity.spawnAtLocation(reward);
            }
        });
    }

    private boolean isHostileMob(LivingEntity entity) {
        EntityType<?> type = entity.getType();
        return entity instanceof Zombie
            || entity instanceof Skeleton
            || entity instanceof Creeper
            || entity instanceof Spider
            || entity instanceof WitherBoss
            || entity instanceof EnderDragon
            || type == EntityType.ENDERMAN
            || type == EntityType.PIGLIN_BRUTE
            || type == EntityType.WARDEN
            || entity instanceof Monster;
    }
}
