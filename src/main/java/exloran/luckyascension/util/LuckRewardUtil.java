package exloran.luckyascension.util;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LuckRewardUtil {

    public static List<ItemStack> getRewardsForMob(LivingEntity entity, Random random) {
        EntityType<?> type = entity.getType();

        if (entity instanceof EnderDragon)       return getEnderDragonRewards();
        if (entity instanceof WitherBoss)         return getWitherRewards();
        if (type == EntityType.WARDEN)            return getWardenRewards();
        if (type == EntityType.PIGLIN_BRUTE)      return getPiglinBruteRewards();
        if (type == EntityType.ENDERMAN)          return getEndermanRewards();
        if (entity instanceof Creeper)            return getCreeperRewards();
        if (entity instanceof Skeleton)           return getSkeletonRewards();
        if (entity instanceof Spider)             return getSpiderRewards();
        if (entity instanceof Zombie)             return getZombieRewards();

        return getGenericRewards();
    }

    // ─── Mob Ödülleri ───────────────────────────────────────────

    private static List<ItemStack> getZombieRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.DIAMOND, 3));
        r.add(new ItemStack(Items.IRON_BLOCK, 2));
        r.add(new ItemStack(Items.GOLD_BLOCK, 1));
        r.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 1));
        r.add(new ItemStack(Items.DIAMOND, 5));
        r.add(new ItemStack(Items.GOLD_INGOT, 8));
        return r;
    }

    private static List<ItemStack> getSkeletonRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.DIAMOND, 4));
        r.add(new ItemStack(Items.ARROW, 32));
        r.add(createEnchantedBow());
        r.add(new ItemStack(Items.DIAMOND, 6));
        r.add(new ItemStack(Items.EMERALD, 10));
        return r;
    }

    private static List<ItemStack> getCreeperRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.TNT, 3));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 1));
        r.add(new ItemStack(Items.NETHERITE_SCRAP, 2));
        r.add(new ItemStack(Items.DIAMOND, 8));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 2));
        return r;
    }

    private static List<ItemStack> getSpiderRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.EMERALD, 8));
        r.add(new ItemStack(Items.GOLDEN_APPLE, 2));
        r.add(createEnchantedBook());
        r.add(new ItemStack(Items.DIAMOND, 5));
        r.add(new ItemStack(Items.EMERALD, 15));
        return r;
    }

    private static List<ItemStack> getEndermanRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.ENDER_EYE, 4));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 1));
        r.add(new ItemStack(Items.NETHERITE_SCRAP, 3));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 2));
        r.add(new ItemStack(Items.ENDER_EYE, 8));
        return r;
    }

    private static List<ItemStack> getPiglinBruteRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.NETHERITE_INGOT, 1));
        r.add(new ItemStack(Items.ANCIENT_DEBRIS, 1));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 2));
        r.add(new ItemStack(Items.NETHERITE_INGOT, 2));
        r.add(new ItemStack(Items.ANCIENT_DEBRIS, 2));
        return r;
    }

    private static List<ItemStack> getWardenRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(createEnchantedArmor(Items.NETHERITE_HELMET));
        r.add(createEnchantedArmor(Items.NETHERITE_CHESTPLATE));
        r.add(createEnchantedArmor(Items.NETHERITE_LEGGINGS));
        r.add(createEnchantedArmor(Items.NETHERITE_BOOTS));
        r.add(new ItemStack(Items.TOTEM_OF_UNDYING, 2));
        r.add(new ItemStack(Items.NETHER_STAR, 1));
        r.add(new ItemStack(Items.ELYTRA, 1));
        r.add(new ItemStack(Items.NETHERITE_INGOT, 4));
        return r;
    }

    private static List<ItemStack> getWitherRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.ELYTRA, 1));
        r.add(new ItemStack(Items.NETHER_STAR, 2));
        r.add(createEnchantedArmor(Items.NETHERITE_CHESTPLATE));
        r.add(createEnchantedWeapon(Items.NETHERITE_SWORD));
        r.add(new ItemStack(Items.TOTEM_OF_UNDYING, 3));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 5));
        r.add(new ItemStack(Items.NETHERITE_INGOT, 6));
        return r;
    }

    private static List<ItemStack> getEnderDragonRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(createEnchantedArmor(Items.NETHERITE_HELMET));
        r.add(createEnchantedArmor(Items.NETHERITE_CHESTPLATE));
        r.add(createEnchantedArmor(Items.NETHERITE_LEGGINGS));
        r.add(createEnchantedArmor(Items.NETHERITE_BOOTS));
        r.add(createEnchantedWeapon(Items.NETHERITE_SWORD));
        r.add(createEnchantedTool(Items.NETHERITE_PICKAXE));
        r.add(new ItemStack(Items.ELYTRA, 1));
        r.add(new ItemStack(Items.NETHER_STAR, 3));
        r.add(new ItemStack(Items.TOTEM_OF_UNDYING, 5));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 10));
        r.add(new ItemStack(Items.NETHERITE_INGOT, 8));
        return r;
    }

    private static List<ItemStack> getGenericRewards() {
        List<ItemStack> r = new ArrayList<>();
        r.add(new ItemStack(Items.DIAMOND, 2));
        r.add(new ItemStack(Items.EMERALD, 5));
        r.add(new ItemStack(Items.IRON_BLOCK, 1));
        r.add(new ItemStack(Items.GOLD_INGOT, 5));
        return r;
    }

    // ─── Büyülü Eşya Oluşturucular ──────────────────────────────

    /**
     * DÜZELTME (Hata 3): stack.enchant() 1.20.1'de deprecated, bazı ortamlarda çalışmıyor.
     * EnchantmentHelper.enchantItem() yerine EnchantmentInstance + EnchantedBookItem
     * pattern'i ile doğrudan tag'e yazıyoruz — en güvenli yöntem.
     */
    private static ItemStack createEnchantedBow() {
        ItemStack bow = new ItemStack(Items.BOW);
        addEnchantment(bow, Enchantments.POWER_ARROWS, 5);
        addEnchantment(bow, Enchantments.INFINITY_ARROWS, 1);
        addEnchantment(bow, Enchantments.UNBREAKING, 3);
        return bow;
    }

    private static ItemStack createEnchantedBook() {
        ItemStack book = new ItemStack(Items.ENCHANTED_BOOK);
        net.minecraft.world.item.EnchantedBookItem.addEnchantment(
            book,
            new EnchantmentInstance(Enchantments.ALL_DAMAGE_PROTECTION, 4)
        );
        net.minecraft.world.item.EnchantedBookItem.addEnchantment(
            book,
            new EnchantmentInstance(Enchantments.UNBREAKING, 3)
        );
        return book;
    }

    private static ItemStack createEnchantedArmor(net.minecraft.world.item.Item item) {
        ItemStack stack = new ItemStack(item);
        addEnchantment(stack, Enchantments.ALL_DAMAGE_PROTECTION, 4);
        addEnchantment(stack, Enchantments.UNBREAKING, 3);
        addEnchantment(stack, Enchantments.MENDING, 1);
        return stack;
    }

    private static ItemStack createEnchantedWeapon(net.minecraft.world.item.Item item) {
        ItemStack stack = new ItemStack(item);
        addEnchantment(stack, Enchantments.SHARPNESS, 5);
        addEnchantment(stack, Enchantments.UNBREAKING, 3);
        addEnchantment(stack, Enchantments.MENDING, 1);
        addEnchantment(stack, Enchantments.LOOTING, 3);
        return stack;
    }

    private static ItemStack createEnchantedTool(net.minecraft.world.item.Item item) {
        ItemStack stack = new ItemStack(item);
        addEnchantment(stack, Enchantments.BLOCK_EFFICIENCY, 5);
        addEnchantment(stack, Enchantments.UNBREAKING, 3);
        addEnchantment(stack, Enchantments.MENDING, 1);
        addEnchantment(stack, Enchantments.BLOCK_FORTUNE, 3);
        return stack;
    }

    /**
     * 1.20.1 Forge için güvenli enchantment ekleme.
     * getOrCreateTag + Enchantments listesi üzerinden yazar.
     * stack.enchant() deprecated olduğundan bu helper kullanılıyor.
     */
    private static void addEnchantment(ItemStack stack,
                                        net.minecraft.world.item.enchantment.Enchantment enchantment,
                                        int level) {
        net.minecraft.nbt.CompoundTag tag = stack.getOrCreateTag();
        net.minecraft.nbt.ListTag enchList = tag.getList("Enchantments", net.minecraft.nbt.Tag.TAG_COMPOUND);

        // Aynı enchantment zaten varsa güncelle
        net.minecraft.resources.ResourceLocation enchId =
            net.minecraftforge.registries.ForgeRegistries.ENCHANTMENTS.getKey(enchantment);
        if (enchId == null) return;

        String enchIdStr = enchId.toString();
        for (int i = 0; i < enchList.size(); i++) {
            net.minecraft.nbt.CompoundTag existing = enchList.getCompound(i);
            if (enchIdStr.equals(existing.getString("id"))) {
                existing.putShort("lvl", (short) level);
                tag.put("Enchantments", enchList);
                stack.setTag(tag);
                return;
            }
        }

        // Yeni enchantment ekle
        net.minecraft.nbt.CompoundTag enchTag = new net.minecraft.nbt.CompoundTag();
        enchTag.putString("id", enchIdStr);
        enchTag.putShort("lvl", (short) level);
        enchList.add(enchTag);
        tag.put("Enchantments", enchList);
        stack.setTag(tag);
    }
}
