package exloran.luckyascension.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LuckRewards {

    private static ItemStack enchant(ServerWorld world, ItemStack stack, RegistryKey<Enchantment> key, int level) {
        Registry<Enchantment> registry = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT);
        RegistryEntry<Enchantment> entry = registry.getEntry(key).orElse(null);
        if (entry == null) return stack;

        ItemEnchantmentsComponent existing = stack.getOrDefault(
            DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT
        );
        ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(existing);
        builder.set(entry, level);
        stack.set(DataComponentTypes.ENCHANTMENTS, builder.build());
        return stack;
    }

    private static ItemStack opSword(ServerWorld w) {
        ItemStack s = new ItemStack(Items.NETHERITE_SWORD);
        enchant(w, s, Enchantments.SHARPNESS, 5);
        enchant(w, s, Enchantments.FIRE_ASPECT, 2);
        enchant(w, s, Enchantments.LOOTING, 3);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        enchant(w, s, Enchantments.SWEEPING_EDGE, 3);
        return s;
    }

    private static ItemStack opBow(ServerWorld w) {
        ItemStack s = new ItemStack(Items.BOW);
        enchant(w, s, Enchantments.POWER, 5);
        enchant(w, s, Enchantments.FLAME, 1);
        enchant(w, s, Enchantments.INFINITY, 1);
        enchant(w, s, Enchantments.PUNCH, 2);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opTrident(ServerWorld w) {
        ItemStack s = new ItemStack(Items.TRIDENT);
        enchant(w, s, Enchantments.LOYALTY, 3);
        enchant(w, s, Enchantments.CHANNELING, 1);
        enchant(w, s, Enchantments.IMPALING, 5);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opHelmet(ServerWorld w) {
        ItemStack s = new ItemStack(Items.NETHERITE_HELMET);
        enchant(w, s, Enchantments.PROTECTION, 4);
        enchant(w, s, Enchantments.RESPIRATION, 3);
        enchant(w, s, Enchantments.AQUA_AFFINITY, 1);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opChestplate(ServerWorld w) {
        ItemStack s = new ItemStack(Items.NETHERITE_CHESTPLATE);
        enchant(w, s, Enchantments.PROTECTION, 4);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.THORNS, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opLeggings(ServerWorld w) {
        ItemStack s = new ItemStack(Items.NETHERITE_LEGGINGS);
        enchant(w, s, Enchantments.PROTECTION, 4);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.SWIFT_SNEAK, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opBoots(ServerWorld w) {
        ItemStack s = new ItemStack(Items.NETHERITE_BOOTS);
        enchant(w, s, Enchantments.PROTECTION, 4);
        enchant(w, s, Enchantments.FEATHER_FALLING, 4);
        enchant(w, s, Enchantments.DEPTH_STRIDER, 3);
        enchant(w, s, Enchantments.SOUL_SPEED, 3);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opPickaxe(ServerWorld w) {
        ItemStack s = new ItemStack(Items.NETHERITE_PICKAXE);
        enchant(w, s, Enchantments.EFFICIENCY, 5);
        enchant(w, s, Enchantments.FORTUNE, 3);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opAxe(ServerWorld w) {
        ItemStack s = new ItemStack(Items.NETHERITE_AXE);
        enchant(w, s, Enchantments.SHARPNESS, 5);
        enchant(w, s, Enchantments.EFFICIENCY, 5);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opShovel(ServerWorld w) {
        ItemStack s = new ItemStack(Items.NETHERITE_SHOVEL);
        enchant(w, s, Enchantments.EFFICIENCY, 5);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opHoe(ServerWorld w) {
        ItemStack s = new ItemStack(Items.NETHERITE_HOE);
        enchant(w, s, Enchantments.EFFICIENCY, 5);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opElytra(ServerWorld w) {
        ItemStack s = new ItemStack(Items.ELYTRA);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opFishingRod(ServerWorld w) {
        ItemStack s = new ItemStack(Items.FISHING_ROD);
        enchant(w, s, Enchantments.LUCK_OF_THE_SEA, 3);
        enchant(w, s, Enchantments.LURE, 3);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opCrossbow(ServerWorld w) {
        ItemStack s = new ItemStack(Items.CROSSBOW);
        enchant(w, s, Enchantments.MULTISHOT, 1);
        enchant(w, s, Enchantments.QUICK_CHARGE, 3);
        enchant(w, s, Enchantments.PIERCING, 4);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        return s;
    }

    private static ItemStack opMace(ServerWorld w) {
        ItemStack s = new ItemStack(Items.MACE);
        enchant(w, s, Enchantments.DENSITY, 5);
        enchant(w, s, Enchantments.WIND_BURST, 3);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opShield(ServerWorld w) {
        ItemStack s = new ItemStack(Items.SHIELD);
        enchant(w, s, Enchantments.UNBREAKING, 3);
        enchant(w, s, Enchantments.MENDING, 1);
        return s;
    }

    // ===== GENİŞ HAVUZ — her ölümde buradan rastgele seçilir =====
    private static List<ItemStack> fullOpPool(ServerWorld world) {
        List<ItemStack> r = new ArrayList<>();

        // Enchantlı silah/aletler
        r.add(opSword(world));
        r.add(opBow(world));
        r.add(opTrident(world));
        r.add(opCrossbow(world));
        r.add(opMace(world));
        r.add(opHelmet(world));
        r.add(opChestplate(world));
        r.add(opLeggings(world));
        r.add(opBoots(world));
        r.add(opPickaxe(world));
        r.add(opAxe(world));
        r.add(opShovel(world));
        r.add(opHoe(world));
        r.add(opElytra(world));
        r.add(opFishingRod(world));
        r.add(opShield(world));

        // Değerli kaynaklar (farklı miktarlarda)
        r.add(new ItemStack(Items.NETHERITE_INGOT, 2));
        r.add(new ItemStack(Items.NETHERITE_INGOT, 4));
        r.add(new ItemStack(Items.ANCIENT_DEBRIS, 3));
        r.add(new ItemStack(Items.ANCIENT_DEBRIS, 6));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 2));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 5));
        r.add(new ItemStack(Items.EMERALD_BLOCK, 3));
        r.add(new ItemStack(Items.GOLD_BLOCK, 5));
        r.add(new ItemStack(Items.IRON_BLOCK, 5));
        r.add(new ItemStack(Items.NETHER_STAR, 1));
        r.add(new ItemStack(Items.NETHER_STAR, 2));

        // Özel / nadir
        r.add(new ItemStack(Items.TOTEM_OF_UNDYING, 1));
        r.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 2));
        r.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 4));
        r.add(new ItemStack(Items.BEACON, 1));
        r.add(new ItemStack(Items.SHULKER_SHELL, 2));
        r.add(new ItemStack(Items.SHULKER_SHELL, 4));
        r.add(new ItemStack(Items.DRAGON_BREATH, 4));
        r.add(new ItemStack(Items.DRAGON_BREATH, 8));
        r.add(new ItemStack(Items.ENDER_EYE, 6));
        r.add(new ItemStack(Items.ENDER_EYE, 12));
        r.add(new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1));
        r.add(new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 3));

        // Tüketilebilir / eğlence
        r.add(new ItemStack(Items.EXPERIENCE_BOTTLE, 16));
        r.add(new ItemStack(Items.EXPERIENCE_BOTTLE, 32));
        r.add(new ItemStack(Items.FIREWORK_ROCKET, 16));
        r.add(new ItemStack(Items.FIREWORK_ROCKET, 32));
        r.add(new ItemStack(Items.FIREWORK_ROCKET, 64));
        r.add(new ItemStack(Items.GOLDEN_CARROT, 32));
        r.add(new ItemStack(Items.HONEY_BOTTLE, 16));

        return r;
    }

    public static List<ItemStack> getRewards(LivingEntity entity, Random random) {
        if (!(entity.getWorld() instanceof ServerWorld world)) return null;
        if (!isHostile(entity)) return null;
        return fullOpPool(world);
    }

    public static boolean isHostile(LivingEntity entity) {
        return entity instanceof HostileEntity
            || entity instanceof EnderDragonEntity
            || entity instanceof WitherEntity;
    }
}
