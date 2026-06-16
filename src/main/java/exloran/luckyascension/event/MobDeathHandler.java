package exloran.luckyascension.event;

import exloran.luckyascension.util.LuckData;
import exloran.luckyascension.util.LuckRewards;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.List;
import java.util.Random;

public class MobDeathHandler {

    private static final Random RANDOM = new Random();

    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register(MobDeathHandler::onDeath);
    }

    private static void onDeath(LivingEntity entity, DamageSource source) {
        if (!(entity.getWorld() instanceof ServerWorld serverWorld)) return;
        if (!serverWorld.getServer().isSingleplayer()) return;
        if (!(source.getAttacker() instanceof ServerPlayerEntity player)) return;
        if (!LuckRewards.isHostile(entity)) return;

        List<ItemStack> rewards = LuckRewards.getRewards(entity, RANDOM);
        if (rewards == null || rewards.isEmpty()) return;

        int count = LuckData.getMultiplier(player);

        for (int i = 0; i < count; i++) {
            ItemStack reward = rewards.get(RANDOM.nextInt(rewards.size())).copy();
            entity.dropStack(reward);
        }

        serverWorld.spawnParticles(
            ParticleTypes.HAPPY_VILLAGER,
            entity.getX(), entity.getY() + 1, entity.getZ(),
            15, 0.5, 0.5, 0.5, 0.1
        );
    }
}
