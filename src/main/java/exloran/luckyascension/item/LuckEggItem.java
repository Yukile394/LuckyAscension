package exloran.luckyascension.item;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
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
        tooltip.add(Text.literal("§7Bu gizemli koylü,"));
        tooltip.add(Text.literal("§7sans gucunu artirabilir."));
        tooltip.add(Text.literal("§eYerlestir ve ticaret yap."));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient) return ActionResult.SUCCESS;
        if (!(world instanceof ServerWorld serverWorld)) return ActionResult.PASS;

        BlockPos pos = context.getBlockPos().up();

        VillagerEntity villager = EntityType.VILLAGER.create(serverWorld);
        if (villager == null) return ActionResult.FAIL;

        villager.refreshPositionAndAngles(
            pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0f, 0f
        );

        villager.setVillagerData(
            villager.getVillagerData().withProfession(VillagerProfession.LIBRARIAN)
        );

        villager.setCustomName(Text.literal("§6§lSans Ustasi"));
        villager.setCustomNameVisible(true);
        villager.setInvulnerable(true);

        serverWorld.spawnEntity(villager);

        PlayerEntity player = context.getPlayer();
        if (player != null) {
            player.sendMessage(Text.literal(
                "§6§lSans Ustasi cagrildi! §eOnunla ticaret yap."
            ), false);
            if (!player.isCreative()) {
                context.getStack().decrement(1);
            }
        }

        return ActionResult.SUCCESS;
    }
}
