package exloran.luckyascension.init;

import exloran.luckyascension.LuckyAscension;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Set;

@net.minecraftforge.fml.common.Mod.EventBusSubscriber(modid = LuckyAscension.MOD_ID)
public class ModVillagers {

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
        DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, LuckyAscension.MOD_ID);

    public static final DeferredRegister<PoiType> POI_TYPES =
        DeferredRegister.create(ForgeRegistries.POI_TYPES, LuckyAscension.MOD_ID);

    public static final RegistryObject<PoiType> LUCK_MASTER_POI = POI_TYPES.register(
        "luck_master_poi",
        () -> new PoiType(
            Set.of(net.minecraft.world.level.block.Blocks.BREWING_STAND.defaultBlockState()),
            1, 1
        )
    );

    public static final RegistryObject<VillagerProfession> LUCK_MASTER = VILLAGER_PROFESSIONS.register(
        "luck_master",
        () -> new VillagerProfession(
            "luck_master",
            holder -> holder.is(ForgeRegistries.POI_TYPES.getKey(LUCK_MASTER_POI.get())),
            holder -> holder.is(ForgeRegistries.POI_TYPES.getKey(LUCK_MASTER_POI.get())),
            com.google.common.collect.ImmutableSet.of(),
            com.google.common.collect.ImmutableSet.of(),
            net.minecraft.sounds.SoundEvents.VILLAGER_WORK_LIBRARIAN
        )
    );

    public static void registerTrades() {
        LuckyAscension.LOGGER.info("[LuckyAscension] Sans Ustasi meslegi kaydedildi.");
    }

    @SubscribeEvent
    public static void onVillagerTrades(VillagerTradesEvent event) {
        if (event.getType() == LUCK_MASTER.get()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            trades.get(1).add((trader, random) -> new MerchantOffer(
                new ItemStack(Items.NETHERITE_INGOT, 1),
                new ItemStack(ModItems.LUCK_BOOST.get(), 1),
                10, 5, 0.05f
            ));
        }
    }
}
