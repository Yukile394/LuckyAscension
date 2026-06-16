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

/**
 * Oyuncu Giriş Eventi
 *
 * Oyuncu dünyaya ilk kez girdiğinde:
 * - Hotbar'ın 5. slotuna (orta) Lucky Ascension Rehberi yerleştirir
 * - Sadece bir kez tetiklenir (NBT etiketi ile kontrol)
 */
public class PlayerJoinEvent {

    // NBT etiketi - kitabın verilip verilmediğini kontrol etmek için
    private static final String BOOK_GIVEN_TAG = "LuckyAscensionBookGiven";

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        // Sadece sunucu tarafında çalış
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        // Sadece tek oyunculu modda çalış
        if (!player.getServer().isSingleplayer()) return;

        // NBT verisinde kitabın verilip verilmediğini kontrol et
        CompoundTag persistentData = player.getPersistentData();
        if (persistentData.getBoolean(BOOK_GIVEN_TAG)) return;

        // Rehber kitabını oluştur ve ver
        ItemStack guidebook = createGuidebook();

        // Hotbar'ın 5. slotu = index 4 (0-8 arası, 4 = orta)
        player.getInventory().setItem(4, guidebook);

        // Kitabın verildiğini işaretle (tekrar verilmesini engelle)
        persistentData.putBoolean(BOOK_GIVEN_TAG, true);

        // Karşılama mesajı
        player.sendSystemMessage(
            Component.literal("§6§lLucky Ascension'a Hoş Geldin! §eRehberi envanterinde bulabilirsin.")
        );
    }

    /**
     * Lucky Ascension Rehber Kitabını Oluştur
     * Yazılmış kitap formatında, renkli ve detaylı içerik
     * Not: Written book sayfaları JSON string olarak saklanır.
     * Component.Serializer.toJson() Forge 1.20+ için kaldırıldığından
     * sayfalar doğrudan JSON string olarak yazılmıştır.
     */
    private ItemStack createGuidebook() {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        CompoundTag bookTag = book.getOrCreateTag();

        // Kitap başlığı ve yazarı
        bookTag.putString("title", "Lucky Ascension Rehberi");
        bookTag.putString("author", "Şans Ustası");
        bookTag.putByte("resolved", (byte) 1);

        // Sayfalar listesi
        ListTag pages = new ListTag();

        // ─── SAYFA 1: Hoş Geldin ───
        String page1 = "{\"text\":\"\",\"extra\":["
            + "{\"text\":\"✦ Hoş Geldin ✦\\n\",\"color\":\"gold\",\"bold\":true},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Lucky Ascension\\n\",\"color\":\"#FFD700\",\"bold\":true},"
            + "{\"text\":\"moduna hoş geldin!\\n\",\"color\":\"gold\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Bu modda düşman\\n\",\"color\":\"dark_gray\"},"
            + "{\"text\":\"yaratıkları öldürerek\\n\",\"color\":\"dark_gray\"},"
            + "{\"text\":\"güçlü eşyalar\\n\",\"color\":\"dark_gray\"},"
            + "{\"text\":\"kazanabilir ve\\n\",\"color\":\"dark_gray\"},"
            + "{\"text\":\"şans seviyeni\\n\",\"color\":\"dark_gray\"},"
            + "{\"text\":\"yükseltebilirsin!\",\"color\":\"green\"}"
            + "]}";

        // ─── SAYFA 2: Şans Sistemi ───
        String page2 = "{\"text\":\"\",\"extra\":["
            + "{\"text\":\"⭐ Şans Sistemi ⭐\\n\",\"color\":\"gold\",\"bold\":true},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Şans seviyen\\n\",\"color\":\"green\"},"
            + "{\"text\":\"yükseldikçe:\\n\",\"color\":\"green\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"• Daha fazla ödül\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"• Daha güçlü eşya\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"• Boss'lardan\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"  değerli ödüller\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Seviye: \",\"color\":\"gray\"},"
            + "{\"text\":\"1\",\"color\":\"gold\",\"bold\":true},"
            + "{\"text\":\" ile başlarsın\",\"color\":\"gray\"}"
            + "]}";

        // ─── SAYFA 3: Ödül Çarpanları ───
        String page3 = "{\"text\":\"\",\"extra\":["
            + "{\"text\":\"📊 Çarpan Tablosu\\n\",\"color\":\"gold\",\"bold\":true},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Seviye 1-5:\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"  → x1 ödül\\n\",\"color\":\"green\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Seviye 6-10:\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"  → x2 ödül\\n\",\"color\":\"green\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Seviye 11-20:\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"  → x3 ödül\\n\",\"color\":\"green\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Seviye 21-50:\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"  → x4 ödül\\n\",\"color\":\"green\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Seviye 50+:\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"  → x5 ödül\",\"color\":\"gold\",\"bold\":true}"
            + "]}";

        // ─── SAYFA 4: Şans Yumurtası ───
        String page4 = "{\"text\":\"\",\"extra\":["
            + "{\"text\":\"🥚 Şans Yumurtası\\n\",\"color\":\"gold\",\"bold\":true},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Şans seviyeni artır-\\n\",\"color\":\"dark_gray\"},"
            + "{\"text\":\"mak için Şans\\n\",\"color\":\"dark_gray\"},"
            + "{\"text\":\"Yumurtası üret!\\n\",\"color\":\"dark_gray\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Tarif (3x3):\\n\",\"color\":\"green\",\"bold\":true},"
            + "{\"text\":\"N  E  N\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"E  E  E\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"N  E  N\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"N=Netherite\\n\",\"color\":\"gold\"},"
            + "{\"text\":\"E=Elmas\",\"color\":\"aqua\"}"
            + "]}";

        // ─── SAYFA 5: Mob Ödülleri ───
        String page5 = "{\"text\":\"\",\"extra\":["
            + "{\"text\":\"⚔ Mob Ödülleri\\n\",\"color\":\"red\",\"bold\":true},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"🧟 Zombi:\\n\",\"color\":\"green\",\"bold\":true},"
            + "{\"text\":\"Elmas, Altın Blok\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"💀 İskelet:\\n\",\"color\":\"green\",\"bold\":true},"
            + "{\"text\":\"Büyülü Yay\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"💥 Creeper:\\n\",\"color\":\"green\",\"bold\":true},"
            + "{\"text\":\"Elmas Blok\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"🕷 Örümcek:\\n\",\"color\":\"green\",\"bold\":true},"
            + "{\"text\":\"Büyülü Kitap\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"👁 Enderman:\\n\",\"color\":\"green\",\"bold\":true},"
            + "{\"text\":\"Netherite Hurda\",\"color\":\"gray\"}"
            + "]}";

        // ─── SAYFA 6: Boss Ödülleri ───
        String page6 = "{\"text\":\"\",\"extra\":["
            + "{\"text\":\"👑 Boss Ödülleri\\n\",\"color\":\"red\",\"bold\":true},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"🏛 Warden:\\n\",\"color\":\"dark_purple\",\"bold\":true},"
            + "{\"text\":\"Netherite Set\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"Totem, Elytra\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"☠ Wither:\\n\",\"color\":\"dark_purple\",\"bold\":true},"
            + "{\"text\":\"Elytra, Nether Star\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"Netherite Set\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"🐉 Ender Dragon:\\n\",\"color\":\"light_purple\",\"bold\":true},"
            + "{\"text\":\"TAM Netherite Set\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"Çoklu Nether Star\\n\",\"color\":\"gray\"},"
            + "{\"text\":\"Totem Paketi\",\"color\":\"gray\"}"
            + "]}";

        // ─── SAYFA 7: Son Sayfa ───
        String page7 = "{\"text\":\"\",\"extra\":["
            + "{\"text\":\"\\n\\n\\n\"},"
            + "{\"text\":\"✨ İyi Eğlenceler! ✨\\n\",\"color\":\"#FFD700\",\"bold\":true},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"Bol şans dileriz!\\n\",\"color\":\"gold\"},"
            + "{\"text\":\"\\n\"},"
            + "{\"text\":\"— Şans Ustası —\",\"color\":\"gray\"}"
            + "]}";

        // Sayfaları listeye ekle
        pages.add(StringTag.valueOf(page1));
        pages.add(StringTag.valueOf(page2));
        pages.add(StringTag.valueOf(page3));
        pages.add(StringTag.valueOf(page4));
        pages.add(StringTag.valueOf(page5));
        pages.add(StringTag.valueOf(page6));
        pages.add(StringTag.valueOf(page7));

        bookTag.put("pages", pages);

        return book;
    }
}
