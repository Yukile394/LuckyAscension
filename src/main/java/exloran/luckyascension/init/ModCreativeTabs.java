package com.luckyascension.init;

import com.luckyascension.LuckyAscension;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Yaratıcı Mod Sekmesi
 * Lucky Ascension itemlerinin göründüğü özel sekme
 */
public class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LuckyAscension.MOD_ID);

    /**
     * Lucky Ascension yaratıcı sekme
     * İkon: Şans Artırma Yumurtası
     */
    public static final RegistryObject<CreativeModeTab> LUCKY_ASCENSION_TAB = CREATIVE_TABS.register(
        "lucky_ascension_tab",
        () -> CreativeModeTab.builder()
            .title(Component.literal("§6§lLucky Ascension"))
            .icon(() -> new ItemStack(ModItems.LUCK_EGG.get()))
            .displayItems((parameters, output) -> {
                // Tüm mod itemlerini sekmeye ekle
                output.accept(ModItems.LUCK_EGG.get());
                output.accept(ModItems.LUCK_BOOST.get());
            })
            .build()
    );
}
