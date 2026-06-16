package exloran.luckyascension.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LuckData {

    private static final String LUCK_KEY = "LuckyAscensionLevel";
    private static final String BOOK_KEY = "LuckyAscensionBookGiven";
    private static final String MOD_NAMESPACE = "luckyascension";

    private static final Map<UUID, Integer> luckCache = new HashMap<>();

    public static int getLuckLevel(ServerPlayerEntity player) {
        if (luckCache.containsKey(player.getUuid())) {
            return luckCache.get(player.getUuid());
        }
        return 1;
    }

    public static void setLuckLevel(ServerPlayerEntity player, int level) {
        luckCache.put(player.getUuid(), Math.max(1, level));
        NbtCompound nbt = getModData(player);
        nbt.putInt(LUCK_KEY, level);
        saveModData(player, nbt);
    }

    public static int increaseLuck(ServerPlayerEntity player) {
        int newLevel = getLuckLevel(player) + 1;
        setLuckLevel(player, newLevel);
        return newLevel;
    }

    public static int getMultiplier(ServerPlayerEntity player) {
        int level = getLuckLevel(player);
        if (level <= 5)  return 1;
        if (level <= 10) return 2;
        if (level <= 20) return 3;
        if (level <= 50) return 4;
        return 5;
    }

    public static boolean isBookGiven(ServerPlayerEntity player) {
        return getModData(player).getBoolean(BOOK_KEY);
    }

    public static void markBookGiven(ServerPlayerEntity player) {
        NbtCompound nbt = getModData(player);
        nbt.putBoolean(BOOK_KEY, true);
        saveModData(player, nbt);
    }

    public static void loadFromPlayer(ServerPlayerEntity player) {
        NbtCompound nbt = getModData(player);
        int level = nbt.contains(LUCK_KEY) ? nbt.getInt(LUCK_KEY) : 1;
        luckCache.put(player.getUuid(), level);
    }

    public static void unloadPlayer(ServerPlayerEntity player) {
        luckCache.remove(player.getUuid());
    }

    private static NbtCompound getModData(ServerPlayerEntity player) {
        NbtCompound custom = player.getCustomData();
        if (!custom.contains(MOD_NAMESPACE)) {
            custom.put(MOD_NAMESPACE, new NbtCompound());
        }
        return custom.getCompound(MOD_NAMESPACE);
    }

    private static void saveModData(ServerPlayerEntity player, NbtCompound data) {
        player.getCustomData().put(MOD_NAMESPACE, data);
    }
}
