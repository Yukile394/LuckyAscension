package com.luckyascension.init;

import com.luckyascension.LuckyAscension;
import com.luckyascension.item.LuckBoostItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

/**
 * Özel Köylü Sistemi
 * Şans Ustası köylüsü ve takas sistemi
 */
public class ModVillagers {

    // Köylü meslekleri için deferred register
    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
        DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, LuckyAscension.MOD_ID);

    // POI tipleri için deferred register (köylünün çalışma noktası)
    public static final DeferredRegister<PoiType> POI_TYPES =
        DeferredRegister.create(ForgeRegistries.POI_TYPES, LuckyAscension.MOD_ID);

    /**
     * Şans Ustası POI - Brewing Stand'i iş yeri olarak kullanır
     */
    public static final RegistryObject<PoiType> LUCK_MASTER_POI = POI_TYPES.register(
        "luck_master_poi",
        () -> new PoiType(
            java.util.Set.of(
                net.minecraft.world.level.block.Blocks.BREWING_STAND.defaultBlockState()
            ),
            1, // max tickets
            1  // valid range
        )
    );

    /**
     * Şans Ustası mesleği
     */
    public static final RegistryObject<VillagerProfession> LUCK_MASTER = VILLAGER_PROFESSIONS.register(
        "luck_master",
        () -> new VillagerProfession(
            "luck_master",
            holder -> holder.is(net.minecraftforge.registries.ForgeRegistries.POI_TYPES.getKey(LUCK_MASTER_POI.get())),
            holder -> holder.is(net.minecraftforge.registries.ForgeRegistries.POI_TYPES.getKey(LUCK_MASTER_POI.get())),
            com.google.common.collect.ImmutableSet.of(),
            com.google.common.collect.ImmutableSet.of(),
            net.minecraft.sounds.SoundEvents.VILLAGER_WORK_LIBRARIAN
        )
    );

    /**
     * Takas kayıtlarını ekle
     * VillagerTradesEvent üzerinden çalışır
     */
    public static void registerTrades() {
        // Trade kayıtları VillagerTradesEvent'te yapılır
        // Aşağıda onVillagerTrades metodu bu eventi dinler
        LuckyAscension.LOGGER.info("[LuckyAscension] Şans Ustası mesleği kaydedildi.");
    }

    /**
     * Köylü ticaretlerini kaydet
     * Bu metod MinecraftForge.EVENT_BUS üzerinden çağrılır
     */
    @net.minecraftforge.eventbus.api.SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        // Sadece Şans Ustası köylüsüne takas ekle
        if (event.getType() == LUCK_MASTER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            // Seviye 1 takas: 1 Netherite Külçesi → 1 Şans Artırma
            trades.get(1).add((trader, random) -> new net.minecraft.world.entity.npc.MerchantOffer(
                new ItemStack(Items.NETHERITE_INGOT, 1),  // Ödeme
                new ItemStack(ModItems.LUCK_BOOST.get(), 1), // Ürün
                10,   // Maksimum kullanım
                5,    // XP kazancı
                0.05f // Fiyat artış çarpanı
            ));
        }
    }
}
