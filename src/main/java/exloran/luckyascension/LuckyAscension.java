package exloran.luckyascension;

import exloran.luckyascension.event.MobDeathHandler;
import exloran.luckyascension.event.PlayerJoinHandler;
import exloran.luckyascension.item.LuckBoostItem;
import exloran.luckyascension.item.LuckCrystalItem;
import exloran.luckyascension.item.LuckEggItem;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuckyAscension implements ModInitializer {

    public static final String MOD_ID = "luckyascension";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Item LUCK_BOOST;
    public static Item LUCK_EGG;
    public static Item LUCK_CRYSTAL;

    @Override
    public void onInitialize() {
        LOGGER.info("[LuckyAscension] Mod baslatiliyor...");

        LUCK_BOOST = Registry.register(
            Registries.ITEM,
            Identifier.of(MOD_ID, "luck_boost"),
            new LuckBoostItem(new Item.Settings().maxCount(16))
        );

        LUCK_EGG = Registry.register(
            Registries.ITEM,
            Identifier.of(MOD_ID, "luck_egg"),
            new LuckEggItem(new Item.Settings().maxCount(16))
        );

        LUCK_CRYSTAL = Registry.register(
            Registries.ITEM,
            Identifier.of(MOD_ID, "luck_crystal"),
            new LuckCrystalItem(new Item.Settings().maxCount(16))
        );

        PlayerJoinHandler.register();
        MobDeathHandler.register();

        LOGGER.info("[LuckyAscension] Mod yuklendi!");
    }
}
