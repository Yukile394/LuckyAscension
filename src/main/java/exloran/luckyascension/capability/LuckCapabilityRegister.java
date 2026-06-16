package exloran.luckyascension.capability;

import com.luckyascension.LuckyAscension;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Capability Kayıt Sınıfı
 *
 * Forge'a ILuckCapability tipini tanıtır.
 * Bu olmadan capability sistemi çalışmaz.
 */
@Mod.EventBusSubscriber(modid = LuckyAscension.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class LuckCapabilityRegister {

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        // ILuckCapability tipini Forge'a kaydet
        event.register(ILuckCapability.class);
        LuckyAscension.LOGGER.info("[LuckyAscension] Luck Capability kaydedildi.");
    }
}
