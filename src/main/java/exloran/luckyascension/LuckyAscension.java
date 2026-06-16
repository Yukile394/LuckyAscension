package com.luckyascension;

import com.luckyascension.event.MobDeathHandler;
import com.luckyascension.event.PlayerJoinHandler;
import com.luckyascension.item.LuckBoostItem;
import com.luckyascension.item.LuckEggItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuckyAscension implements ModInitializer {

    public static final String MOD_ID = "luckyascension";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Item tanımları
    public static Item LUCK_BOOST;
    public static Item LUCK_EGG;

    @Override
    public void onInitialize() {
        LOGGER.info("[LuckyAscension] Mod baslatiliyor...");

        // Item'ları kaydet
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

        // Event handler'ları kaydet
        PlayerJoinHandler.register();
        MobDeathHandler.register();

        LOGGER.info("[LuckyAscension] Mod yuklendi! Sans yolculugun baslasın!");
    }
}
