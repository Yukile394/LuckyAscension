package exloran.luckyascension;

import exloran.luckyascension.capability.LuckCapabilityProvider;
import exloran.luckyascension.event.LuckEvent;
import exloran.luckyascension.event.MobDeathEvent;
import exloran.luckyascension.event.PlayerJoinEvent;
import exloran.luckyascension.init.ModCreativeTabs;
import exloran.luckyascension.init.ModItems;
import exloran.luckyascension.init.ModVillagers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(LuckyAscension.MOD_ID)
public class LuckyAscension {

    public static final String MOD_ID = "luckyascension";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public LuckyAscension() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModVillagers.VILLAGER_PROFESSIONS.register(modEventBus);
        ModVillagers.POI_TYPES.register(modEventBus);
        ModCreativeTabs.CREATIVE_TABS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        // DÜZELTME: onAttachCapabilities static inner class üzerinden register ediliyor
        // Bu sayede Forge event dispatch doğru çalışır
        MinecraftForge.EVENT_BUS.register(new PlayerJoinEvent());
        MinecraftForge.EVENT_BUS.register(new MobDeathEvent());
        MinecraftForge.EVENT_BUS.register(new LuckEvent());
        MinecraftForge.EVENT_BUS.register(CapabilityHandler.class);

        LOGGER.info("[LuckyAscension] Mod yuklendi!");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModVillagers.registerTrades();
        });
    }

    /**
     * DÜZELTME (Hata 1): onAttachCapabilities static bir nested class içinde olmalı.
     * Instance metod olarak MinecraftForge.EVENT_BUS.register(this) ile kayıt edilince
     * Forge, generic type Entity olan event'i doğru dispatch etmiyor.
     * Static @Mod.EventBusSubscriber veya static inner class ile çözülür.
     */
    @Mod.EventBusSubscriber(modid = LuckyAscension.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class CapabilityHandler {

        @SubscribeEvent
        public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
            if (event.getObject() instanceof Player) {
                ResourceLocation capKey = new ResourceLocation(MOD_ID, "luck_capability");
                // Duplicate ekleme kontrolü - capability zaten eklenmediyse ekle
                if (!event.getCapabilities().containsKey(capKey)) {
                    event.addCapability(capKey, new LuckCapabilityProvider());
                }
            }
        }
    }
}
