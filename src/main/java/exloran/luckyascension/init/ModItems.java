package com.luckyascension.init;

import com.luckyascension.LuckyAscension;
import com.luckyascension.item.LuckBoostItem;
import com.luckyascension.item.LuckEggItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Mod Item Kayıt Sınıfı
 * Tüm özel itemler buraya kaydedilir
 */
public class ModItems {

    // Deferred register - item kayıtları için
    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, LuckyAscension.MOD_ID);

    /**
     * Şans Artırma İtemi
     * Dragon Breath görünümünde, sağ tıklayınca şans verir
     * Köylü tarafından satılır (1 Netherite Külçesi karşılığı)
     */
    public static final RegistryObject<Item> LUCK_BOOST = ITEMS.register(
        "luck_boost",
        () -> new LuckBoostItem(new Item.Properties().stacksTo(16))
    );

    /**
     * Şans Artırma Yumurtası
     * Craft edilebilir, yerleştirilince Şans Ustası köylüsü oluşturur
     * Tarif: Netherite-Elmas-Netherite / Elmas-Elmas-Elmas / Netherite-Elmas-Netherite
     */
    public static final RegistryObject<Item> LUCK_EGG = ITEMS.register(
        "luck_egg",
        () -> new LuckEggItem(new Item.Properties().stacksTo(16))
    );
}
