package com.luckyascension;

import com.luckyascension.capability.LuckCapabilityProvider;
import com.luckyascension.event.LuckEvent;
import com.luckyascension.event.MobDeathEvent;
import com.luckyascension.event.PlayerJoinEvent;
import com.luckyascension.init.ModItems;
import com.luckyascension.init.ModVillagers;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Lucky Ascension - Ana Mod Sınıfı
 * Minecraft 1.21.x Forge Modu
 *
 * Bu mod tek oyunculu dünyalarda özel şans sistemi,
 * mob ödülleri ve özel köylü takasları ekler.
 */
@Mod(LuckyAscension.MOD_ID)
public class LuckyAscension {

    // Mod kimliği - tüm kayıtlarda kullanılır
    public static final String MOD_ID = "luckyascension";

    // Loglama için logger
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public LuckyAscension() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Modül kayıtlarını başlat
        ModItems.ITEMS.register(modEventBus);
        ModVillagers.VILLAGER_PROFESSIONS.register(modEventBus);
        ModVillagers.POI_TYPES.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);

        // Ortak kurulum eventi
        modEventBus.addListener(this::commonSetup);

        // Forge eventlerini kaydet
        MinecraftForge.EVENT_BUS.register(new PlayerJoinEvent());
        MinecraftForge.EVENT_BUS.register(new MobDeathEvent());
        MinecraftForge.EVENT_BUS.register(new LuckEvent());
        MinecraftForge.EVENT_BUS.register(this);

        LOGGER.info("[LuckyAscension] Mod yüklendi! Şans yolculuğun başlasın.");
    }

    /**
     * Ortak kurulum - capability ve trade kayıtları
     */
    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModVillagers.registerTrades();
            LOGGER.info("[LuckyAscension] Köylü takasları kaydedildi.");
        });
    }

    /**
     * Oyuncu entity'sine Luck Capability ekle
     * Bu sayede şans seviyesi oyuncu verisine kaydedilir
     */
    @SubscribeEvent
    public void onAttachCapabilities(AttachCapabilitiesEvent<net.minecraft.world.entity.Entity> event) {
        if (event.getObject() instanceof Player) {
            // Capability henüz eklenmemişse ekle
            if (!event.getObject().getCapability(LuckCapabilityProvider.LUCK_CAPABILITY).isPresent()) {
                event.addCapability(
                    new ResourceLocation(MOD_ID, "luck_capability"),
                    new LuckCapabilityProvider()
                );
            }
        }
    }
}
