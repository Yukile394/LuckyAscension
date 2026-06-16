package com.luckyascension.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Oyuncu şans verisini yöneten sınıf.
 * Fabric'te veri NBT ile PlayerEntity'ye kaydedilir.
 * Dünya kapansa bile korunur.
 */
public class LuckData {

    private static final String LUCK_KEY = "LuckyAscensionLevel";
    private static final String BOOK_KEY = "LuckyAscensionBookGiven";

    // Sunucu tarafı önbellek
    private static final Map<UUID, Integer> luckCache = new HashMap<>();

    /**
     * Oyuncunun şans seviyesini al
     */
    public static int getLuckLevel(ServerPlayerEntity player) {
        NbtCompound data = player.writeNbt(new NbtCompound());
        // Custom data için persistent data kullan
        if (luckCache.containsKey(player.getUuid())) {
            return luckCache.get(player.getUuid());
        }
        return 1; // Varsayılan
    }

    /**
     * Şans seviyesini ayarla
     */
    public static void setLuckLevel(ServerPlayerEntity player, int level) {
        luckCache.put(player.getUuid(), Math.max(1, level));
        saveToPersistentData(player, level);
    }

    /**
     * Şans seviyesini 1 artır
     */
    public static int increaseLuck(ServerPlayerEntity player) {
        int current = getLuckLevel(player);
        int newLevel = current + 1;
        setLuckLevel(player, newLevel);
        return newLevel;
    }

    /**
     * Ödül çarpanı hesapla
     */
    public static int getMultiplier(ServerPlayerEntity player) {
        int level = getLuckLevel(player);
        if (level <= 5)  return 1;
        if (level <= 10) return 2;
        if (level <= 20) return 3;
        if (level <= 50) return 4;
        return 5;
    }

    /**
     * Kitabın verilip verilmediğini kontrol et
     */
    public static boolean isBookGiven(ServerPlayerEntity player) {
        NbtCompound nbt = getOrCreateCustomData(player);
        return nbt.getBoolean(BOOK_KEY);
    }

    /**
     * Kitabın verildiğini işaretle
     */
    public static void markBookGiven(ServerPlayerEntity player) {
        NbtCompound nbt = getOrCreateCustomData(player);
        nbt.putBoolean(BOOK_KEY, true);
        saveCustomData(player, nbt);
    }

    /**
     * Oyuncu giriş yaptığında önbelleği yükle
     */
    public static void loadFromPlayer(ServerPlayerEntity player) {
        NbtCompound nbt = getOrCreateCustomData(player);
        int level = nbt.contains(LUCK_KEY) ? nbt.getInt(LUCK_KEY) : 1;
        luckCache.put(player.getUuid(), level);
    }

    /**
     * Oyuncu çıkış yaptığında önbelleği temizle
     */
    public static void unloadPlayer(ServerPlayerEntity player) {
        luckCache.remove(player.getUuid());
    }

    // ── Private helpers ──────────────────────────────────────────────────────

    private static void saveToPersistentData(ServerPlayerEntity player, int level) {
        NbtCompound nbt = getOrCreateCustomData(player);
        nbt.putInt(LUCK_KEY, level);
        saveCustomData(player, nbt);
    }

    private static NbtCompound getOrCreateCustomData(ServerPlayerEntity player) {
        // Fabric'te custom persistent data için playerdata NBT'sini kullan
        NbtCompound playerNbt = new NbtCompound();
        player.writeNbt(playerNbt);
        if (playerNbt.contains("ForgeData")) {
            return playerNbt.getCompound("ForgeData");
        }
        // Fabric için kendi namespace'imizle sakla
        NbtCompound customData = player.getCustomData();
        if (!customData.contains(MOD_NAMESPACE)) {
            customData.put(MOD_NAMESPACE, new NbtCompound());
        }
        return customData.getCompound(MOD_NAMESPACE);
    }

    private static void saveCustomData(ServerPlayerEntity player, NbtCompound data) {
        NbtCompound customData = player.getCustomData();
        customData.put(MOD_NAMESPACE, data);
    }

    private static final String MOD_NAMESPACE = "luckyascension";
}
