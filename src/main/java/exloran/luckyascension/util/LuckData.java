package exloran.luckyascension.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LuckData {

    private static final String LUCK_KEY = "LuckyAscensionLevel";
    private static final String BOOK_KEY = "LuckyAscensionBookGiven";
    private static final String MOD_NAMESPACE = "luckyascension";

    private static final Map<UUID, Integer> luckCache = new HashMap<>();
    private static final Map<UUID, Boolean> bookGivenCache = new HashMap<>();

    public static int getLuckLevel(ServerPlayerEntity player) {
        return luckCache.getOrDefault(player.getUuid(), 1);
    }

    public static void setLuckLevel(ServerPlayerEntity player, int level) {
        luckCache.put(player.getUuid(), Math.max(1, level));
        saveToState(player.getServer(), player.getUuid());
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
        return bookGivenCache.getOrDefault(player.getUuid(), false);
    }

    public static void markBookGiven(ServerPlayerEntity player) {
        bookGivenCache.put(player.getUuid(), true);
        saveToState(player.getServer(), player.getUuid());
    }

    public static void loadFromPlayer(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server == null) return;

        LuckPersistentState state = getState(server);
        UUID uuid = player.getUuid();
        String uuidStr = uuid.toString();

        NbtCompound nbt = state.data;
        if (nbt.contains(uuidStr)) {
            NbtCompound playerNbt = nbt.getCompound(uuidStr);
            int level = playerNbt.contains(LUCK_KEY) ? playerNbt.getInt(LUCK_KEY) : 1;
            boolean bookGiven = playerNbt.contains(BOOK_KEY) && playerNbt.getBoolean(BOOK_KEY);
            luckCache.put(uuid, level);
            bookGivenCache.put(uuid, bookGiven);
        } else {
            luckCache.put(uuid, 1);
            bookGivenCache.put(uuid, false);
        }
    }

    public static void unloadPlayer(ServerPlayerEntity player) {
        UUID uuid = player.getUuid();
        MinecraftServer server = player.getServer();
        if (server != null) {
            saveToState(server, uuid);
        }
        luckCache.remove(uuid);
        bookGivenCache.remove(uuid);
    }

    private static void saveToState(MinecraftServer server, UUID uuid) {
        if (server == null) return;
        LuckPersistentState state = getState(server);
        NbtCompound playerNbt = new NbtCompound();
        playerNbt.putInt(LUCK_KEY, luckCache.getOrDefault(uuid, 1));
        playerNbt.putBoolean(BOOK_KEY, bookGivenCache.getOrDefault(uuid, false));
        state.data.put(uuid.toString(), playerNbt);
        state.markDirty();
    }

    private static LuckPersistentState getState(MinecraftServer server) {
        PersistentStateManager manager = server.getOverworld().getPersistentStateManager();
        return manager.getOrCreate(
            new PersistentState.Type<>(
                LuckPersistentState::new,
                LuckPersistentState::fromNbt,
                null
            ),
            MOD_NAMESPACE
        );
    }

    // Inner class for persistent state
    public static class LuckPersistentState extends PersistentState {
        public NbtCompound data = new NbtCompound();

        public static LuckPersistentState fromNbt(NbtCompound nbt) {
            LuckPersistentState state = new LuckPersistentState();
            state.data = nbt.contains("data") ? nbt.getCompound("data") : new NbtCompound();
            return state;
        }

        @Override
        public NbtCompound writeNbt(NbtCompound nbt) {
            nbt.put("data", data);
            return nbt;
        }
    }
}
