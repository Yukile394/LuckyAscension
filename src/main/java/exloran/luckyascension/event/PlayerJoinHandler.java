package exloran.luckyascension.event;

import exloran.luckyascension.util.LuckData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.WrittenBookContentComponent;
import net.minecraft.component.type.WritableBookContentComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.RawFilteredPair;

import java.util.ArrayList;
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

        List<RawFilteredPair<Text>> pages = new ArrayList<>();

        pages.add(RawFilteredPair.of(Text.Serialization.fromJsonString(
            "{\"text\":\"\",\"extra\":[" +
            "{\"text\":\"Lucky Ascension\\n\",\"color\":\"gold\",\"bold\":true}," +
            "{\"text\":\"\\nHos Geldin!\\n\",\"color\":\"yellow\"}," +
            "{\"text\":\"\\nDusman yaratiklar oldurunce OP esyalar kazan ve sans seviyeni yukseltin!\",\"color\":\"gray\"}" +
            "]}", null)));

        pages.add(RawFilteredPair.of(Text.Serialization.fromJsonString(
            "{\"text\":\"\",\"extra\":[" +
            "{\"text\":\"Sans Sistemi\\n\",\"color\":\"gold\",\"bold\":true}," +
            "{\"text\":\"\\nSeviye 1-5:\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"  x1 odul\\n\",\"color\":\"green\"}," +
            "{\"text\":\"Seviye 6-10:\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"  x2 odul\\n\",\"color\":\"green\"}," +
            "{\"text\":\"Seviye 11-20:\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"  x3 odul\\n\",\"color\":\"green\"}," +
            "{\"text\":\"Seviye 21-50:\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"  x4 odul\\n\",\"color\":\"green\"}," +
            "{\"text\":\"Seviye 50+:\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"  x5 odul\",\"color\":\"gold\",\"bold\":true}" +
            "]}", null)));

        pages.add(RawFilteredPair.of(Text.Serialization.fromJsonString(
            "{\"text\":\"\",\"extra\":[" +
            "{\"text\":\"Sans Yumurtasi\\n\",\"color\":\"gold\",\"bold\":true}," +
            "{\"text\":\"\\nCraft Tarifi:\\n\",\"color\":\"green\",\"bold\":true}," +
            "{\"text\":\"N  E  N\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"E  E  E\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"N  E  N\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"\\nN=Netherite\\n\",\"color\":\"dark_purple\"}," +
            "{\"text\":\"E=Elmas\\n\",\"color\":\"aqua\"}," +
            "{\"text\":\"\\nKoy - Sans Ustasi!\",\"color\":\"yellow\"}" +
            "]}", null)));

        pages.add(RawFilteredPair.of(Text.Serialization.fromJsonString(
            "{\"text\":\"\",\"extra\":[" +
            "{\"text\":\"Mob Odulleri\\n\",\"color\":\"red\",\"bold\":true}," +
            "{\"text\":\"\\nZombi: \",\"color\":\"green\"}," +
            "{\"text\":\"Elmas, Altin Blok\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"Iskelet: \",\"color\":\"green\"}," +
            "{\"text\":\"Buyulu Yay\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"Creeper: \",\"color\":\"green\"}," +
            "{\"text\":\"Elmas Blok\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"Orumcek: \",\"color\":\"green\"}," +
            "{\"text\":\"Zumrut\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"Enderman: \",\"color\":\"green\"}," +
            "{\"text\":\"Netherite\",\"color\":\"gray\"}" +
            "]}", null)));

        pages.add(RawFilteredPair.of(Text.Serialization.fromJsonString(
            "{\"text\":\"\",\"extra\":[" +
            "{\"text\":\"Boss Odulleri\\n\",\"color\":\"red\",\"bold\":true}," +
            "{\"text\":\"\\nWarden:\\n\",\"color\":\"dark_purple\",\"bold\":true}," +
            "{\"text\":\"Netherite Set, Elytra\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"\\nWither:\\n\",\"color\":\"dark_purple\",\"bold\":true}," +
            "{\"text\":\"Elytra, Nether Star\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"\\nEnder Dragon:\\n\",\"color\":\"dark_purple\",\"bold\":true}," +
            "{\"text\":\"TAM Netherite Set + Nether Star x3\",\"color\":\"gray\"}" +
            "]}", null)));

        pages.add(RawFilteredPair.of(Text.Serialization.fromJsonString(
            "{\"text\":\"\",\"extra\":[" +
            "{\"text\":\"\\n\\n\\nBol Sans!\\n\",\"color\":\"gold\",\"bold\":true}," +
            "{\"text\":\"\\n- Sans Ustasi -\",\"color\":\"gray\"}" +
            "]}", null)));

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
