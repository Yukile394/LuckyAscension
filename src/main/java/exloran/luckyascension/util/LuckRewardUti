package com.luckyascension.util;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Şans Ödül Yardımcı Sınıfı
 *
 * Her yaratık türü için ödül listelerini tanımlar.
 * Yaratığın gücüne göre ödüller farklılaşır:
 *
 * Zayıf (Zombie, Skeleton, Spider):  Elmas, Altın Blok seviyesi
 * Orta (Creeper, Enderman):          Elmas Blok, Netherite Hurda
 * Güçlü (PiglinBrute):               Netherite Külçesi, Antik Kalıntı
 * Boss (Warden, Wither, Dragon):     Netherite Set, Elytra, Nether Star
 */
public class LuckRewardUtil {

    /**
     * Yaratık türüne göre ödül listesi döndür
     * Null dönerse bu yaratıktan ödül düşmez
     */
    public static List<ItemStack> getRewardsForMob(LivingEntity entity, Random random) {

        // ─── ENDER DRAGON ───────────────────────────────────────────────────────
        if (entity instanceof EnderDragon) {
            return getEnderDragonRewards();
        }

        // ─── WITHER ─────────────────────────────────────────────────────────────
        if (entity instanceof WitherBoss) {
            return getWitherRewards();
        }

        // ─── WARDEN ─────────────────────────────────────────────────────────────
        if (entity.getType() == EntityType.WARDEN) {
            return getWardenRewards();
        }

        // ─── PİGLİN BRUTE ───────────────────────────────────────────────────────
        if (entity instanceof PiglinBrute) {
            return getPiglinBruteRewards();
        }

        // ─── ENDERMAN ───────────────────────────────────────────────────────────
        if (entity instanceof Enderman) {
            return getEndermanRewards();
        }

        // ─── CREEPER ────────────────────────────────────────────────────────────
        if (entity instanceof Creeper) {
            return getCreeperRewards();
        }

        // ─── İSKELET ────────────────────────────────────────────────────────────
        if (entity instanceof Skeleton) {
            return getSkeletonRewards();
        }

        // ─── ÖRÜMCEK ────────────────────────────────────────────────────────────
        if (entity instanceof Spider) {
            return getSpiderRewards();
        }

        // ─── ZOMBİ ──────────────────────────────────────────────────────────────
        if (entity instanceof Zombie) {
            return getZombieRewards();
        }

        // Diğer hostile moblar için genel ödüller
        if (entity instanceof Monster) {
            return getGenericRewards();
        }

        // Bu yaratıktan ödül düşmez
        return null;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // ZOMBİ ÖDÜLLERİ
    // ═══════════════════════════════════════════════════════════════════════════
    private static List<ItemStack> getZombieRewards() {
        List<ItemStack> rewards = new ArrayList<>();

        rewards.add(new ItemStack(Items.DIAMOND, 3));
        rewards.add(new ItemStack(Items.IRON_BLOCK, 2));
        rewards.add(new ItemStack(Items.GOLD_BLOCK, 1));
        rewards.add(createEnchantedGoldenApple());
        rewards.add(new ItemStack(Items.DIAMOND, 5));
        rewards.add(new ItemStack(Items.GOLD_INGOT, 8));

        return rewards;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // İSKELET ÖDÜLLERİ
    // ═══════════════════════════════════════════════════════════════════════════
    private static List<ItemStack> getSkeletonRewards() {
        List<ItemStack> rewards = new ArrayList<>();

        rewards.add(new ItemStack(Items.DIAMOND, 4));
        rewards.add(new ItemStack(Items.ARROW, 32));
        rewards.add(createEnchantedBow());
        rewards.add(new ItemStack(Items.DIAMOND, 6));
        rewards.add(new ItemStack(Items.EMERALD, 10));

        return rewards;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // CREEPER ÖDÜLLERİ
    // ═══════════════════════════════════════════════════════════════════════════
    private static List<ItemStack> getCreeperRewards() {
        List<ItemStack> rewards = new ArrayList<>();

        rewards.add(new ItemStack(Items.TNT, 3));
        rewards.add(new ItemStack(Items.DIAMOND_BLOCK, 1));
        rewards.add(new ItemStack(Items.NETHERITE_SCRAP, 2));
        rewards.add(new ItemStack(Items.DIAMOND, 8));
        rewards.add(new ItemStack(Items.DIAMOND_BLOCK, 2));

        return rewards;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // ÖRÜMCEK ÖDÜLLERİ
    // ═══════════════════════════════════════════════════════════════════════════
    private static List<ItemStack> getSpiderRewards() {
        List<ItemStack> rewards = new ArrayList<>();

        rewards.add(new ItemStack(Items.EMERALD, 8));
        rewards.add(new ItemStack(Items.GOLDEN_APPLE, 2));
        rewards.add(createEnchantedBook());
        rewards.add(new ItemStack(Items.DIAMOND, 5));
        rewards.add(new ItemStack(Items.EMERALD, 15));

        return rewards;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // ENDERMAN ÖDÜLLERİ
    // ═══════════════════════════════════════════════════════════════════════════
    private static List<ItemStack> getEndermanRewards() {
        List<ItemStack> rewards = new ArrayList<>();

        rewards.add(new ItemStack(Items.ENDER_EYE, 4));
        rewards.add(new ItemStack(Items.DIAMOND_BLOCK, 1));
        rewards.add(new ItemStack(Items.NETHERITE_SCRAP, 3));
        rewards.add(new ItemStack(Items.DIAMOND_BLOCK, 2));
        rewards.add(new ItemStack(Items.ENDER_EYE, 8));

        return rewards;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // PİGLİN BRUTE ÖDÜLLERİ
    // ═══════════════════════════════════════════════════════════════════════════
    private static List<ItemStack> getPiglinBruteRewards() {
        List<ItemStack> rewards = new ArrayList<>();

        rewards.add(new ItemStack(Items.NETHERITE_INGOT, 1));
        rewards.add(new ItemStack(Items.ANCIENT_DEBRIS, 1));
        rewards.add(new ItemStack(Items.DIAMOND_BLOCK, 2));
        rewards.add(new ItemStack(Items.NETHERITE_INGOT, 2));
        rewards.add(new ItemStack(Items.ANCIENT_DEBRIS, 2));

        return rewards;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // WARDEN ÖDÜLLERİ (BOSS)
    // ═══════════════════════════════════════════════════════════════════════════
    private static List<ItemStack> getWardenRewards() {
        List<ItemStack> rewards = new ArrayList<>();

        // Netherite Set parçaları
        rewards.add(createEnchantedItem(Items.NETHERITE_HELMET));
        rewards.add(createEnchantedItem(Items.NETHERITE_CHESTPLATE));
        rewards.add(createEnchantedItem(Items.NETHERITE_LEGGINGS));
        rewards.add(createEnchantedItem(Items.NETHERITE_BOOTS));
        rewards.add(new ItemStack(Items.TOTEM_OF_UNDYING, 2));
        rewards.add(new ItemStack(Items.NETHER_STAR, 1));
        rewards.add(new ItemStack(Items.ELYTRA, 1));
        rewards.add(new ItemStack(Items.NETHERITE_INGOT, 4));

        return rewards;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // WITHER ÖDÜLLERİ (BOSS)
    // ═══════════════════════════════════════════════════════════════════════════
    private static List<ItemStack> getWitherRewards() {
        List<ItemStack> rewards = new ArrayList<>();

        rewards.add(new ItemStack(Items.ELYTRA, 1));
        rewards.add(new ItemStack(Items.NETHER_STAR, 2));
        rewards.add(createEnchantedItem(Items.NETHERITE_CHESTPLATE));
        rewards.add(createEnchantedItem(Items.NETHERITE_SWORD));
        rewards.add(new ItemStack(Items.TOTEM_OF_UNDYING, 3));
        rewards.add(new ItemStack(Items.DIAMOND_BLOCK, 5));
        rewards.add(createEnchantedItem(Items.NETHERITE_HELMET));
        rewards.add(new ItemStack(Items.NETHERITE_INGOT, 6));

        return rewards;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // ENDER DRAGON ÖDÜLLERİ (BOSS)
    // ═══════════════════════════════════════════════════════════════════════════
    private static List<ItemStack> getEnderDragonRewards() {
        List<ItemStack> rewards = new ArrayList<>();

        // TAM Netherite Set (büyülü)
        rewards.add(createEnchantedItem(Items.NETHERITE_HELMET));
        rewards.add(createEnchantedItem(Items.NETHERITE_CHESTPLATE));
        rewards.add(createEnchantedItem(Items.NETHERITE_LEGGINGS));
        rewards.add(createEnchantedItem(Items.NETHERITE_BOOTS));
        rewards.add(createEnchantedItem(Items.NETHERITE_SWORD));
        rewards.add(createEnchantedItem(Items.NETHERITE_PICKAXE));

        // Özel ödüller
        rewards.add(new ItemStack(Items.ELYTRA, 1));
        rewards.add(new ItemStack(Items.NETHER_STAR, 3));
        rewards.add(new ItemStack(Items.TOTEM_OF_UNDYING, 5));
        rewards.add(new ItemStack(Items.DIAMOND_BLOCK, 10));
        rewards.add(new ItemStack(Items.NETHERITE_INGOT, 8));

        return rewards;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // GENEL ÖDÜLLER (Diğer hostile moblar)
    // ═══════════════════════════════════════════════════════════════════════════
    private static List<ItemStack> getGenericRewards() {
        List<ItemStack> rewards = new ArrayList<>();

        rewards.add(new ItemStack(Items.DIAMOND, 2));
        rewards.add(new ItemStack(Items.EMERALD, 5));
        rewards.add(new ItemStack(Items.IRON_BLOCK, 1));
        rewards.add(new ItemStack(Items.GOLD_INGOT, 5));

        return rewards;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // YARDIMCI METODLAR
    // ═══════════════════════════════════════════════════════════════════════════

    /**
     * Büyülü altın elma oluştur
     */
    private static ItemStack createEnchantedGoldenApple() {
        return new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1);
    }

    /**
     * Büyülü yay oluştur (Power V, Infinity)
     */
    private static ItemStack createEnchantedBow() {
        ItemStack bow = new ItemStack(Items.BOW);
        bow.enchant(Enchantments.POWER_ARROWS, 5);
        bow.enchant(Enchantments.INFINITY_ARROWS, 1);
        bow.enchant(Enchantments.UNBREAKING, 3);
        return bow;
    }

    /**
     * Rastgele büyülü kitap oluştur
     */
    private static ItemStack createEnchantedBook() {
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        net.minecraft.world.item.EnchantedBookItem.addEnchantment(
            book,
            new net.minecraft.world.item.enchantment.EnchantmentInstance(Enchantments.ALL_DAMAGE_PROTECTION, 4)
        );
        return book;
    }

    /**
     * Büyülü Netherite/Diamond item oluştur
     */
    private static ItemStack createEnchantedItem(net.minecraft.world.item.Item item) {
        ItemStack stack = new ItemStack(item);
        stack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, 4);
        stack.enchant(Enchantments.UNBREAKING, 3);
        stack.enchant(Enchantments.MENDING, 1);
        return stack;
    }
}
