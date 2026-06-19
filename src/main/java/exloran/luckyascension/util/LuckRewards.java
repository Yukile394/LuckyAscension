package exloran.luckyascension.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LuckRewards {

    // ===== ENCHANT YARDIMCI =====
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

    private static ItemStack opSword(ServerWorld world) {
        ItemStack s = new ItemStack(Items.NETHERITE_SWORD);
        enchant(world, s, Enchantments.SHARPNESS, 5);
        enchant(world, s, Enchantments.FIRE_ASPECT, 2);
        enchant(world, s, Enchantments.LOOTING, 3);
        enchant(world, s, Enchantments.UNBREAKING, 3);
        enchant(world, s, Enchantments.MENDING, 1);
        enchant(world, s, Enchantments.SWEEPING_EDGE, 3);
        return s;
    }

    private static ItemStack opBow(ServerWorld world) {
        ItemStack s = new ItemStack(Items.BOW);
        enchant(world, s, Enchantments.POWER, 5);
        enchant(world, s, Enchantments.FLAME, 1);
        enchant(world, s, Enchantments.INFINITY, 1);
        enchant(world, s, Enchantments.PUNCH, 2);
        enchant(world, s, Enchantments.UNBREAKING, 3);
        enchant(world, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opTrident(ServerWorld world) {
        ItemStack s = new ItemStack(Items.TRIDENT);
        enchant(world, s, Enchantments.LOYALTY, 3);
        enchant(world, s, Enchantments.CHANNELING, 1);
        enchant(world, s, Enchantments.IMPALING, 5);
        enchant(world, s, Enchantments.UNBREAKING, 3);
        enchant(world, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opHelmet(ServerWorld world) {
        ItemStack s = new ItemStack(Items.NETHERITE_HELMET);
        enchant(world, s, Enchantments.PROTECTION, 4);
        enchant(world, s, Enchantments.RESPIRATION, 3);
        enchant(world, s, Enchantments.AQUA_AFFINITY, 1);
        enchant(world, s, Enchantments.UNBREAKING, 3);
        enchant(world, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opChestplate(ServerWorld world) {
        ItemStack s = new ItemStack(Items.NETHERITE_CHESTPLATE);
        enchant(world, s, Enchantments.PROTECTION, 4);
        enchant(world, s, Enchantments.UNBREAKING, 3);
        enchant(world, s, Enchantments.THORNS, 3);
        enchant(world, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opLeggings(ServerWorld world) {
        ItemStack s = new ItemStack(Items.NETHERITE_LEGGINGS);
        enchant(world, s, Enchantments.PROTECTION, 4);
        enchant(world, s, Enchantments.UNBREAKING, 3);
        enchant(world, s, Enchantments.SWIFT_SNEAK, 3);
        enchant(world, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opBoots(ServerWorld world) {
        ItemStack s = new ItemStack(Items.NETHERITE_BOOTS);
        enchant(world, s, Enchantments.PROTECTION, 4);
        enchant(world, s, Enchantments.FEATHER_FALLING, 4);
        enchant(world, s, Enchantments.DEPTH_STRIDER, 3);
        enchant(world, s, Enchantments.SOUL_SPEED, 3);
        enchant(world, s, Enchantments.UNBREAKING, 3);
        enchant(world, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opPickaxe(ServerWorld world) {
        ItemStack s = new ItemStack(Items.NETHERITE_PICKAXE);
        enchant(world, s, Enchantments.EFFICIENCY, 5);
        enchant(world, s, Enchantments.FORTUNE, 3);
        enchant(world, s, Enchantments.UNBREAKING, 3);
        enchant(world, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opAxe(ServerWorld world) {
        ItemStack s = new ItemStack(Items.NETHERITE_AXE);
        enchant(world, s, Enchantments.SHARPNESS, 5);
        enchant(world, s, Enchantments.EFFICIENCY, 5);
        enchant(world, s, Enchantments.UNBREAKING, 3);
        enchant(world, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opElytra(ServerWorld world) {
        ItemStack s = new ItemStack(Items.ELYTRA);
        enchant(world, s, Enchantments.UNBREAKING, 3);
        enchant(world, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opFishingRod(ServerWorld world) {
        ItemStack s = new ItemStack(Items.FISHING_ROD);
        enchant(world, s, Enchantments.LUCK_OF_THE_SEA, 3);
        enchant(world, s, Enchantments.LURE, 3);
        enchant(world, s, Enchantments.UNBREAKING, 3);
        enchant(world, s, Enchantments.MENDING, 1);
        return s;
    }

    private static ItemStack opCrossbow(ServerWorld world) {
        ItemStack s = new ItemStack(Items.CROSSBOW);
        enchant(world, s, Enchantments.MULTISHOT, 1);
        enchant(world, s, Enchantments.QUICK_CHARGE, 3);
        enchant(world, s, Enchantments.PIERCING, 4);
        enchant(world, s, Enchantments.UNBREAKING, 3);
        return s;
    }

    // ===== ANA EŞYA HAVUZU (her şey OP) =====
    private static List<ItemStack> fullOpPool(ServerWorld world) {
        List<ItemStack> r = new ArrayList<>();
        r.add(opSword(world));
        r.add(opBow(world));
        r.add(opTrident(world));
        r.add(opHelmet(world));
        r.add(opChestplate(world));
        r.add(opLeggings(world));
        r.add(opBoots(world));
        r.add(opPickaxe(world));
        r.add(opAxe(world));
        r.add(opElytra(world));
        r.add(opFishingRod(world));
        r.add(opCrossbow(world));
        r.add(new ItemStack(Items.NETHERITE_INGOT, 4));
        r.add(new ItemStack(Items.ANCIENT_DEBRIS, 6));
        r.add(new ItemStack(Items.DIAMOND_BLOCK, 4));
        r.add(new ItemStack(Items.EMERALD_BLOCK, 4));
        r.add(new ItemStack(Items.NETHER_STAR, 1));
        r.add(new ItemStack(Items.TOTEM_OF_UNDYING, 1));
        r.add(new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, 4));
        r.add(new ItemStack(Items.FIREWORK_ROCKET, 64));
        r.add(new ItemStack(Items.EXPERIENCE_BOTTLE, 32));
        r.add(new ItemStack(Items.ENDER_EYE, 8));
        r.add(new ItemStack(Items.SHULKER_SHELL, 4));
        r.add(new ItemStack(Items.BEACON, 1));
        r.add(new ItemStack(Items.DRAGON_BREATH, 6));
        r.add(new ItemStack(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 2));
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
