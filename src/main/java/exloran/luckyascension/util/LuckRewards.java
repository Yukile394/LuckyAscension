package exloran.luckyascension.util;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LuckRewards {

    public static List<ItemStack> getRewards(LivingEntity entity, Random random) {
        EntityType<?> type = entity.getType();

        if (entity instanceof EnderDragonEntity) return dragonRewards();
        if (entity instanceof WitherEntity)       return witherRewards();
        if (type == EntityType.WARDEN)            return wardenRewards();
        if (type == EntityType.PIGLIN_BRUTE)      return piglinBruteRewards();
        if (type == EntityType.ENDERMAN)          return endermanRewards();
        if (entity instanceof CreeperEntity)      return creeperRewards();
        if (entity instanceof SkeletonEntity)     return skeletonRewards();
        if (entity instanceof SpiderEntity)       return spiderRewards();
        if (entity instanceof ZombieEntity)       return zombieRewards();
        if (entity instanceof HostileEntity)      return genericRewards();

        return null;
    }

    public static boolean isHostile(LivingEntity entity) {
        return entity instanceof HostileEntity
            || entity instanceof EnderDragonEntity
            || entity instanceof WitherEntity;
    }

    private static List<ItemStack> zombieRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.DIAMOND, 3));
        r.add(new ItemStack(Items.IRON_BLOCK, 2));
        r.add(new ItemStack(Items.GOLD_BLOCK, 1));
        r.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1));
        r.add(new ItemStack(Items.DIAMOND, 5));
        r.add(new ItemStack(Items.GOLD_INGOT, 8));
        return r;
    }

    private static List<ItemStack> skeletonRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.DIAMOND, 4));
        r.add(new ItemStack(Items.ARROW, 32));
        r.add(new ItemStack(Items.BOW));
        r.add(new ItemStack(Items.DIAMOND, 6));
        r.add(new ItemStack(Items.EMERALD, 10));
        return r;
    }

    private static List<ItemStack> creeperRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.TNT, 3));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 1));
        r.add(new ItemStack(Items.NETHERITE_SCRAP, 2));
        r.add(new ItemStack(Items.DIAMOND, 8));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 2));
        return r;
    }

    private static List<ItemStack> spiderRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.EMERALD, 8));
        r.add(new ItemStack(Items.GOLDEN_APPLE, 2));
        r.add(new ItemStack(Items.BOOK, 1));
        r.add(new ItemStack(Items.DIAMOND, 5));
        r.add(new ItemStack(Items.EMERALD, 15));
        return r;
    }

    private static List<ItemStack> endermanRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.ENDER_EYE, 4));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 1));
        r.add(new ItemStack(Items.NETHERITE_SCRAP, 3));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 2));
        r.add(new ItemStack(Items.ENDER_EYE, 8));
        return r;
    }

    private static List<ItemStack> piglinBruteRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.NETHERITE_INGOT, 1));
        r.add(new ItemStack(Items.ANCIENT_DEBRIS, 1));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 2));
        r.add(new ItemStack(Items.NETHERITE_INGOT, 2));
        r.add(new ItemStack(Items.ANCIENT_DEBRIS, 2));
        return r;
    }

    private static List<ItemStack> wardenRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.NETHERITE_HELMET));
        r.add(new ItemStack(Items.NETHERITE_CHESTPLATE));
        r.add(new ItemStack(Items.NETHERITE_LEGGINGS));
        r.add(new ItemStack(Items.NETHERITE_BOOTS));
        r.add(new ItemStack(Items.TOTEM_OF_UNDYING, 2));
        r.add(new ItemStack(Items.NETHER_STAR, 1));
        r.add(new ItemStack(Items.ELYTRA, 1));
        r.add(new ItemStack(Items.NETHERITE_INGOT, 4));
        return r;
    }

    private static List<ItemStack> witherRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.ELYTRA, 1));
        r.add(new ItemStack(Items.NETHER_STAR, 2));
        r.add(new ItemStack(Items.NETHERITE_CHESTPLATE));
        r.add(new ItemStack(Items.NETHERITE_SWORD));
        r.add(new ItemStack(Items.TOTEM_OF_UNDYING, 3));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 5));
        r.add(new ItemStack(Items.NETHERITE_INGOT, 6));
        return r;
    }

    private static List<ItemStack> dragonRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.NETHERITE_HELMET));
        r.add(new ItemStack(Items.NETHERITE_CHESTPLATE));
        r.add(new ItemStack(Items.NETHERITE_LEGGINGS));
        r.add(new ItemStack(Items.NETHERITE_BOOTS));
        r.add(new ItemStack(Items.NETHERITE_SWORD));
        r.add(new ItemStack(Items.NETHERITE_PICKAXE));
        r.add(new ItemStack(Items.ELYTRA, 1));
        r.add(new ItemStack(Items.NETHER_STAR, 3));
        r.add(new ItemStack(Items.TOTEM_OF_UNDYING, 5));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 10));
        r.add(new ItemStack(Items.NETHERITE_INGOT, 8));
        return r;
    }

    private static List<ItemStack> genericRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.DIAMOND, 2));
        r.add(new ItemStack(Items.EMERALD, 5));
        r.add(new ItemStack(Items.IRON_BLOCK, 1));
        r.add(new ItemStack(Items.GOLD_INGOT, 5));
        return r;
    }
}
