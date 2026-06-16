package exloran.luckyascension.event;

import exloran.luckyascension.util.LuckData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WrittenBookContentComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.RawFilteredPair;

import java.util.List;

public class PlayerJoinHandler {

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;

            if (!server.isSingleplayer()) return;

            LuckData.loadFromPlayer(player);

            int level = LuckData.getLuckLevel(player);
            int multi = LuckData.getMultiplier(player);
            player.sendMessage(Text.literal(
                "§6[Lucky Ascension] §eSans Seviyesi: §6§l" + level + " §e| Odul: §6§lx" + multi
            ), false);

            if (LuckData.isBookGiven(player)) return;

            ItemStack book = createGuideBook();
            player.getInventory().setStack(4, book);
            LuckData.markBookGiven(player);

            player.sendMessage(Text.literal(
                "§6§lLucky Ascension'a Hos Geldin! §eRehber kitabin hotbar'inda!"
            ), false);
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            LuckData.unloadPlayer(handler.player);
        });
    }

    private static ItemStack createGuideBook() {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);

        List<RawFilteredPair<Text>> pages = List.of(
            RawFilteredPair.of(Text.literal(
                "Lucky Ascension\n\nHos Geldin!\n\nDusman yaratiklar oldurunce OP esyalar kazan ve sans seviyeni yukseltin!"
            )),
            RawFilteredPair.of(Text.literal(
                "Sans Sistemi\n\nSeviye 1-5: x1 odul\nSeviye 6-10: x2 odul\nSeviye 11-20: x3 odul\nSeviye 21-50: x4 odul\nSeviye 50+: x5 odul"
            )),
            RawFilteredPair.of(Text.literal(
                "Sans Yumurtasi\n\nCraft Tarifi:\nN E N\nE E E\nN E N\n\nN=Netherite\nE=Elmas\n\nKoy - Sans Ustasi!"
            )),
            RawFilteredPair.of(Text.literal(
                "Mob Odulleri\n\nZombi: Elmas, Altin Blok\nIskelet: Buyulu Yay\nCreeper: Elmas Blok\nOrumcek: Zumrut\nEnderman: Netherite"
            )),
            RawFilteredPair.of(Text.literal(
                "Boss Odulleri\n\nWarden:\nNetherite Set, Elytra\n\nWither:\nElytra, Nether Star\n\nEnder Dragon:\nTAM Netherite Set + Nether Star x3"
            )),
            RawFilteredPair.of(Text.literal(
                "\n\n\nBol Sans!\n\n- Sans Ustasi -"
            ))
        );

        WrittenBookContentComponent content = new WrittenBookContentComponent(
            RawFilteredPair.of("Lucky Ascension Rehberi"),
            "Sans Ustasi",
            0,
            pages,
            true
        );

        book.set(DataComponentTypes.WRITTEN_BOOK_CONTENT, content);
        return book;
    }
}
