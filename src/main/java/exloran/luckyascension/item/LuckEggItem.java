package exloran.luckyascension.item;

import exloran.luckyascension.init.ModVillagers;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LuckEggItem extends Item {

    public LuckEggItem(Properties properties) {
        super(properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.literal("§6§lŞans Artırma Yumurtası");
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§7Bu gizemli köylü,"));
        tooltip.add(Component.literal("§7şans gücünü artırabilir."));
        tooltip.add(Component.literal("§eYerleştir ve ticaret yap."));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            BlockPos clickedPos = context.getClickedPos().above();

            Villager villager = EntityType.VILLAGER.create(serverLevel);

            if (villager != null) {
                villager.setPos(
                    clickedPos.getX() + 0.5,
                    clickedPos.getY(),
                    clickedPos.getZ() + 0.5
                );

                VillagerData villagerData = new VillagerData(
                    VillagerType.PLAINS,
                    ModVillagers.LUCK_MASTER.get(),
                    1
                );
                villager.setVillagerData(villagerData);

                villager.setCustomName(Component.literal("§6§lŞans Ustası"));
                villager.setCustomNameVisible(true);

                serverLevel.addFreshEntity(villager);

                if (context.getPlayer() != null) {
                    context.getPlayer().sendSystemMessage(
                        Component.literal("§6§lŞans Ustası çağrıldı! Onunla ticaret yapabilirsin.")
                    );
                }

                if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                    context.getItemInHand().shrink(1);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }
}
