package exloran.luckyascension.init;

import exloran.luckyascension.LuckyAscension;
import exloran.luckyascension.item.LuckBoostItem;
import exloran.luckyascension.item.LuckEggItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(ForgeRegistries.ITEMS, LuckyAscension.MOD_ID);

    public static final RegistryObject<Item> LUCK_BOOST = ITEMS.register(
        "luck_boost",
        () -> new LuckBoostItem(new Item.Properties().stacksTo(16))
    );

    public static final RegistryObject<Item> LUCK_EGG = ITEMS.register(
        "luck_egg",
        () -> new LuckEggItem(new Item.Properties().stacksTo(16))
    );
}
