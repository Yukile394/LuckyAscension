package exloran.luckyascension.item;

import exloran.luckyascension.LuckyAscension;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradedItem;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;

import java.util.List;

public class LuckEggItem extends Item {

    public LuckEggItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.literal("§6§lSans Artirma Yumurtasi");
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.literal("§7Bu gizemli yumurtadan"));
        tooltip.add(Text.literal("§7Sans Ustasi dogacak."));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.literal("§eYerlestir §7→ Koylü spawn olur"));
        tooltip.add(Text.literal("§aTicaret §7→ §d1 Netherite = 1 Sans Kristali"));
        tooltip.add(Text.literal(""));
        tooltip.add(Text.literal("§2\"Sansin kapisi burada aciliyor...\""));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient) return ActionResult.SUCCESS;
        if (!(world instanceof ServerWorld serverWorld)) return ActionResult.PASS;

        BlockPos pos = context.getBlockPos().up();

        VillagerEntity villager = EntityType.VILLAGER.create(serverWorld);
        if (villager == null) return ActionResult.FAIL;

        // 1. Konum
        villager.refreshPositionAndAngles(
            pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0f, 0f
        );

        // 2. Önce dünyaya ekle (spawnEntity), SONRA tüm ayarları yap
        // Bu sıralama despawn race condition'ını engeller
        boolean spawned = serverWorld.spawnEntity(villager);
        if (!spawned) return ActionResult.FAIL;

        // 3. Tüm kalıcılık ve trade ayarlarını spawn SONRASI yap
        villager.setVillagerData(
            villager.getVillagerData().withProfession(VillagerProfession.CLERIC)
        );
        villager.setCustomName(Text.literal("§d§lSans Ustasi"));
        villager.setCustomNameVisible(true);
        villager.setInvulnerable(true);
        villager.setPersistent();
        villager.setAiDisabled(false);
        villager.setHealth(villager.getMaxHealth());

        TradeOfferList offers = new TradeOfferList();
        offers.add(new TradeOffer(
            new TradedItem(Items.NETHERITE_INGOT, 1),
            new ItemStack(LuckyAscension.LUCK_CRYSTAL, 1),
            999,
            10,
            0.05f
        ));
        villager.setOffers(offers);

        PlayerEntity player = context.getPlayer();
        if (player != null) {
            player.sendMessage(Text.literal(
                "§d§lSans Ustasi cagrildi! §e1 Netherite = 1 Sans Kristali"
            ), false);
            if (!player.isCreative()) {
                context.getStack().decrement(1);
            }
        }

        return ActionResult.SUCCESS;
    }
}
