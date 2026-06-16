package com.luckyascension.event;

import com.luckyascension.util.LuckData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

/**
 * Oyuncu ilk girişinde hotbar 5. slota rehber kitap verir.
 * Sadece tek oyunculu dünyalarda çalışır.
 */
public class PlayerJoinHandler {

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;

            // Sadece single player
            if (!server.isSingleplayer()) return;

            // Önbelleğe yükle
            LuckData.loadFromPlayer(player);

            // Şans seviyesini bildir
            int level = LuckData.getLuckLevel(player);
            int multi = LuckData.getMultiplier(player);
            player.sendMessage(Text.literal(
                "§6[Lucky Ascension] §eSans Seviyesi: §6§l" + level + " §e| Ödül: §6§lx" + multi
            ), false);

            // Kitap daha önce verilmişse çık
            if (LuckData.isBookGiven(player)) return;

            // Rehber kitabı oluştur ve ver
            ItemStack book = createGuideBook();

            // Hotbar 5. slot = index 4
            player.getInventory().setStack(4, book);

            LuckData.markBookGiven(player);

            player.sendMessage(Text.literal(
                "§6§l✦ Lucky Ascension'a Hoş Geldin! §eRehber kitabın hotbar'ında!"
            ), false);
        });

        // Çıkışta önbelleği temizle
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            LuckData.unloadPlayer(handler.player);
        });
    }

    /**
     * Yazılmış rehber kitap oluştur - Fabric 1.21.x NBT formatı
     */
    private static ItemStack createGuideBook() {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        NbtCompound tag = book.getOrCreateNbt();

        tag.putString("title", "Lucky Ascension Rehberi");
        tag.putString("author", "Sans Ustasi");
        tag.putBoolean("resolved", true);

        NbtList pages = new NbtList();

        // Sayfa 1 - Hos Geldin
        pages.add(NbtString.of(Text.Serialization.toJsonString(
            Text.literal("")
        )));

        // Fabric 1.21'de sayfalar düz JSON string olarak eklenir
        pages.add(NbtString.of(
            "{\"text\":\"\",\"extra\":[" +
            "{\"text\":\"✦ Lucky Ascension ✦\\n\",\"color\":\"gold\",\"bold\":true}," +
            "{\"text\":\"\\nHos Geldin!\\n\",\"color\":\"yellow\"}," +
            "{\"text\":\"\\nDusman yaratiklar oldurunce OP esyalar kazan ve sans seviyeni yukseltin!\",\"color\":\"gray\"}" +
            "]}"
        ));

        // Sayfa 2 - Sans Sistemi
        pages.add(NbtString.of(
            "{\"text\":\"\",\"extra\":[" +
            "{\"text\":\"⭐ Sans Sistemi\\n\",\"color\":\"gold\",\"bold\":true}," +
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
            "]}"
        ));

        // Sayfa 3 - Yumurta Tarifi
        pages.add(NbtString.of(
            "{\"text\":\"\",\"extra\":[" +
            "{\"text\":\"🥚 Sans Yumurtasi\\n\",\"color\":\"gold\",\"bold\":true}," +
            "{\"text\":\"\\nCraft Tarifi:\\n\",\"color\":\"green\",\"bold\":true}," +
            "{\"text\":\"N  E  N\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"E  E  E\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"N  E  N\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"\\nN=Netherite\\n\",\"color\":\"dark_purple\"}," +
            "{\"text\":\"E=Elmas\\n\",\"color\":\"aqua\"}," +
            "{\"text\":\"\\nKoy → Sans Ustasi!\",\"color\":\"yellow\"}" +
            "]}"
        ));

        // Sayfa 4 - Mob Odulleri
        pages.add(NbtString.of(
            "{\"text\":\"\",\"extra\":[" +
            "{\"text\":\"⚔ Mob Odulleri\\n\",\"color\":\"red\",\"bold\":true}," +
            "{\"text\":\"\\nZombi: \",\"color\":\"green\"}," +
            "{\"text\":\"Elmas, Altin Blok\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"Iskelet: \",\"color\":\"green\"}," +
            "{\"text\":\"Buyulu Yay\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"Creeper: \",\"color\":\"green\"}," +
            "{\"text\":\"Elmas Blok\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"Orumcek: \",\"color\":\"green\"}," +
            "{\"text\":\"Zumrut\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"Enderman: \",\"color\":\"green\"}," +
            "{\"text\":\"Netherite\\n\",\"color\":\"gray\"}" +
            "]}"
        ));

        // Sayfa 5 - Boss Odulleri
        pages.add(NbtString.of(
            "{\"text\":\"\",\"extra\":[" +
            "{\"text\":\"👑 Boss Odulleri\\n\",\"color\":\"red\",\"bold\":true}," +
            "{\"text\":\"\\nWarden:\\n\",\"color\":\"dark_purple\",\"bold\":true}," +
            "{\"text\":\"Netherite Set, Elytra\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"\\nWither:\\n\",\"color\":\"dark_purple\",\"bold\":true}," +
            "{\"text\":\"Elytra, Nether Star\\n\",\"color\":\"gray\"}," +
            "{\"text\":\"\\nEnder Dragon:\\n\",\"color\":\"dark_purple\",\"bold\":true}," +
            "{\"text\":\"TAM Netherite Set\\nNether Star x3\",\"color\":\"gray\"}" +
            "]}"
        ));

        // Sayfa 6 - Son
        pages.add(NbtString.of(
            "{\"text\":\"\",\"extra\":[" +
            "{\"text\":\"\\n\\n\\n✨ Bol Sans! ✨\\n\",\"color\":\"gold\",\"bold\":true}," +
            "{\"text\":\"\\n— Sans Ustasi —\",\"color\":\"gray\"}" +
            "]}"
        ));

        tag.put("pages", pages);
        return book;
    }
}
