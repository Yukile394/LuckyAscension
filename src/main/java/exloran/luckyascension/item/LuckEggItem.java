package com.luckyascension.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.npc.VillagerEntity;
import net.minecraft.entity.npc.VillagerProfession;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Şans Artırma Yumurtası
 * Bir blok yüzeyine sağ tıklayınca Şans Ustası köylüsü oluşturur.
 *
 * Craft: Netherite-Elmas-Netherite / Elmas-Elmas-Elmas / Netherite-Elmas-Netherite
 */
public class LuckEggItem extends Item {

    public LuckEggItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.literal("§6§lSans Artirma Yumurtasi");
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal("§7Bu gizemli koylü,"));
        tooltip.add(Text.literal("§7sans gucunu artirabilir."));
        tooltip.add(Text.literal("§eYerlestir ve ticaret yap."));
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient) return ActionResult.SUCCESS;

        if (!(world instanceof ServerWorld serverWorld)) return ActionResult.PASS;

        // Sadece single player
        if (!serverWorld.getServer().isSingleplayer()) return ActionResult.PASS;

        BlockPos pos = context.getBlockPos().up();

        // Köylü oluştur
        VillagerEntity villager = EntityType.VILLAGER.create(serverWorld);
        if (villager == null) return ActionResult.FAIL;

        villager.refreshPositionAndAngles(
            pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0f, 0f
        );

        // Librarian olarak ayarla (en yakın vanilla meslek)
        villager.setVillagerData(
            villager.getVillagerData().withProfession(VillagerProfession.LIBRARIAN)
        );

        // İsim: Şans Ustası
        villager.setCustomName(Text.literal("§6§lSans Ustasi"));
        villager.setCustomNameVisible(true);

        // Ölümsüz yap
        villager.setInvulnerable(true);

        serverWorld.spawnEntity(villager);

        // Kullanıcıya mesaj
        PlayerEntity player = context.getPlayer();
        if (player != null) {
            player.sendMessage(Text.literal(
                "§6§lSans Ustasi cagrildi! §eOnunla ticaret yapmak icin sag tikla."
            ), false);

            if (!player.isCreative()) {
                context.getStack().decrement(1);
            }
        }

        return ActionResult.SUCCESS;
    }
}
