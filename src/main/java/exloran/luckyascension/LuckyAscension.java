package exloran.luckyascension;

import exloran.luckyascension.event.MobDeathHandler;
import exloran.luckyascension.event.PlayerJoinHandler;
import exloran.luckyascension.item.LuckBoostItem;
import exloran.luckyascension.item.LuckCrystalItem;
import exloran.luckyascension.item.LuckEggItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
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

        // Creative menü grubu
        Registry.register(Registries.ITEM_GROUP,
            Identifier.of(MOD_ID, "lucky_group"),
            FabricItemGroup.builder()
                .displayName(Text.literal("§6§lLucky Ascension"))
                .icon(() -> new ItemStack(LUCK_CRYSTAL))
                .entries((ctx, entries) -> {
                    entries.add(LUCK_EGG);
                    entries.add(LUCK_BOOST);
                    entries.add(LUCK_CRYSTAL);
                })
                .build()
        );

        PlayerJoinHandler.register();
        MobDeathHandler.register();

        LOGGER.info("[LuckyAscension] Mod yuklendi!");
    }
}
