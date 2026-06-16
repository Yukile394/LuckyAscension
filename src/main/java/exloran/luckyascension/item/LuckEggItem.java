package com.luckyascension.item;

import com.luckyascension.init.ModVillagers;
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

/**
 * Şans Artırma Yumurtası
 *
 * Craft edilebilen özel bir spawn egg.
 * Bir yüzeye sağ tıklanınca Şans Ustası köylüsü oluşturur.
 *
 * Craft Tarifi (3x3):
 * [Netherite] [Elmas]    [Netherite]
 * [Elmas]     [Elmas]    [Elmas]
 * [Netherite] [Elmas]    [Netherite]
 *
 * Oluşturulan köylü:
 * - İsim: §6§lŞans Ustası
 * - Meslek: Şans Ustası (özel)
 * - Seviye 1'den başlar
 * - Takas: 1 Netherite → 1 Şans Artırma
 */
public class LuckEggItem extends Item {

    public LuckEggItem(Properties properties) {
        super(properties);
    }

    /**
     * Özel item adı - altın renkli ve kalın
     */
    @Override
    public Component getName(ItemStack stack) {
        return Component.literal("§6§lŞans Artırma Yumurtası");
    }

    /**
     * Tooltip/lore bilgisi
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("§7Bu gizemli köylü,"));
        tooltip.add(Component.literal("§7şans gücünü artırabilir."));
        tooltip.add(Component.literal("§eYerleştir ve ticaret yap."));
    }

    /**
     * Bir blok yüzeyine sağ tıklanınca Şans Ustası köylüsü oluştur
     */
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        // Sadece sunucu tarafında entity oluştur
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
            // Tıklanan bloğun üstüne köylü oluştur
            BlockPos clickedPos = context.getClickedPos().above();

            // Villager entity oluştur
            Villager villager = EntityType.VILLAGER.create(serverLevel);

            if (villager != null) {
                // Köylü pozisyonunu ayarla
                villager.setPos(
                    clickedPos.getX() + 0.5,
                    clickedPos.getY(),
                    clickedPos.getZ() + 0.5
                );

                // Şans Ustası mesleğini ve seviyesini ayarla
                VillagerData villagerData = new VillagerData(
                    VillagerType.PLAINS,
                    ModVillagers.LUCK_MASTER.get(),
                    1 // Başlangıç seviyesi
                );
                villager.setVillagerData(villagerData);

                // Özel isim: §6§lŞans Ustası
                villager.setCustomName(Component.literal("§6§lŞans Ustası"));
                villager.setCustomNameVisible(true);

                // Köylünün ölmemesi için invulnerable yap (opsiyonel)
                // villager.setInvulnerable(true);

                // Köylüyü dünyaya ekle
                serverLevel.addFreshEntity(villager);

                // Kullanıcıya mesaj ver
                if (context.getPlayer() != null) {
                    context.getPlayer().sendSystemMessage(
                        Component.literal("§6§lŞans Ustası çağrıldı! Onunla ticaret yapabilirsin.")
                    );
                }

                // Itemi 1 azalt
                if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                    context.getItemInHand().shrink(1);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    /**
     * Yaratıcı menüde parıltı efekti (spawn egg görünümü için)
     */
    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }
}
