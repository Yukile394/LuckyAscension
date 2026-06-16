package exloran.luckyascension.event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class PlayerJoinEvent {

    private static final String BOOK_GIVEN_TAG = "LuckyAscensionBookGiven";

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // Sadece sunucu tarafında çalış
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        // DÜZELTME (Hata 4): player.getServer() null olabilir, NPE riski var.
        // Önceki kod ".isSingleplayer()" çağırıyordu, null kontrolü yoktu.
        // Ayrıca bu kısıtlama kaldırıldı — kitap hem singleplayer hem multiplayer'da verilmeli.
        // Null-safe kontrol: sunucu yoksa (teorik edge case) return.
        if (player.getServer() == null) return;

        // NBT'de kitabın verilip verilmediğini kontrol et (bir kez ver)
        CompoundTag persistentData = player.getPersistentData();
        if (persistentData.getBoolean(BOOK_GIVEN_TAG)) return;

        // Rehber kitabını oluştur ve ver (hotbar ortası, slot 4)
        ItemStack guidebook = createGuidebook();
        player.getInventory().setItem(4, guidebook);

        // Bir daha verilmemesi için işaretle
        persistentData.putBoolean(BOOK_GIVEN_TAG, true);

        player.sendSystemMessage(
            Component.literal("§6§lLucky Ascension'a Hoş Geldin! §eRehberi envanterinde bulabilirsin.")
        );
    }

    private ItemStack createGuidebook() {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        CompoundTag bookTag = book.getOrCreateTag();

        bookTag.putString("title", "Lucky Ascension Rehberi");
        bookTag.putString("author", "Şans Ustası");
        bookTag.putByte("resolved", (byte) 1);

        ListTag pages = new ListTag();

        String page1 = "{\"text\":\"\",\"extra\":["
            + "{\"text\":\"✦ Hoş Geldin ✦\\n\",\"color\":\"gold\",\"bold\":true},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Lucky Ascension\\n\",\"color\":\"#FFD700\",\"bold\":true},"
            + "{\"text\":\"moduna hoş geldin!\\n\",\"color\":\"gold\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Düşman yaratıkları\\n\",\"color\":\"dark_gray\"},"
            + "{\"text\":\"öldürerek güçlü\\n\",\"color\":\"dark_gray\"},"
            + "{\"text\":\"eşyalar kazan ve\\n\",\"color\":\"dark_gray\"},"
            + "{\"text\":\"şans seviyeni\\n\",\"color\":\"dark_gray\"},"
            + "{\"text\":\"yükselt!\",\"color\":\"green\"}"
            + "]}";

        String page2 = "{\"text\":\"\",\"extra\":["
            + "{\"text\":\"📊 Çarpan Tablosu\\n\",\"color\":\"gold\",\"bold\":true},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Seviye 1-5:  x1\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"Seviye 6-10: x2\\n\",\"color\":\"green\"},"
            + "{\"text\":\"Seviye 11-20: x3\\n\",\"color\":\"aqua\"},"
            + "{\"text\":\"Seviye 21-50: x4\\n\",\"color\":\"light_purple\"},"
            + "{\"text\":\"Seviye 50+: x5\",\"color\":\"gold\",\"bold\":true}"
            + "]}";

        String page3 = "{\"text\":\"\",\"extra\":["
            + "{\"text\":\"⚔ Mob Ödülleri\\n\",\"color\":\"red\",\"bold\":true},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Zombi: \",\"color\":\"green\",\"bold\":true},"
            + "{\"text\":\"Elmas, Altın\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"İskelet: \",\"color\":\"green\",\"bold\":true},"
            + "{\"text\":\"Büyülü Yay\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"Creeper: \",\"color\":\"green\",\"bold\":true},"
            + "{\"text\":\"Elmas Blok\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"Örümcek: \",\"color\":\"green\",\"bold\":true},"
            + "{\"text\":\"Büyülü Kitap\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Boss'lar çok daha\\n\",\"color\":\"dark_purple\"},"
            + "{\"text\":\"iyi ödüller verir!\",\"color\":\"dark_purple\"}"
            + "]}";

        String page4 = "{\"text\":\"\",\"extra\":["
            + "{\"text\":\"\\n\\n\\n\"},"
            + "{\"text\":\"✨ İyi Eğlenceler! ✨\\n\",\"color\":\"#FFD700\",\"bold\":true},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Bol şans dileriz!\\n\",\"color\":\"gold\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"— Şans Ustası —\",\"color\":\"gray\"}"
            + "]}";

        pages.add(StringTag.valueOf(page1));
        pages.add(StringTag.valueOf(page2));
        pages.add(StringTag.valueOf(page3));
        pages.add(StringTag.valueOf(page4));

        bookTag.put("pages", pages);
        return book;
    }
}
