package com.luckyascension.event;

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
     */
    private ItemStack createGuidebook() {
        ItemStack book = new ItemStack(Items.WRITTEN_BOOK);
        CompoundTag bookTag = new CompoundTag();

        // Kitap başlığı ve yazarı
        bookTag.putString("title", "Lucky Ascension Rehberi");
        bookTag.putString("author", "Şans Ustası");

        // Sayfalar listesi
        ListTag pages = new ListTag();

        // ─── SAYFA 1: Hoş Geldin ───
        String page1 = Component.Serializer.toJson(
            Component.literal("")
                .append(Component.literal("✦ Hoş Geldin ✦\n").withStyle(s -> s.withColor(0xFFAA00).withBold(true)))
                .append(Component.literal("\n"))
                .append(Component.literal("Lucky Ascension\n").withStyle(s -> s.withColor(0xFFD700).withBold(true)))
                .append(Component.literal("moduna hoş geldin!\n").withStyle(s -> s.withColor(0xFFAA00)))
                .append(Component.literal("\n"))
                .append(Component.literal("Bu modda düşman\n").withStyle(s -> s.withColor(0x555555)))
                .append(Component.literal("yaratıkları öldürerek\n").withStyle(s -> s.withColor(0x555555)))
                .append(Component.literal("güçlü eşyalar\n").withStyle(s -> s.withColor(0x555555)))
                .append(Component.literal("kazanabilir ve\n").withStyle(s -> s.withColor(0x555555)))
                .append(Component.literal("şans seviyeni\n").withStyle(s -> s.withColor(0x555555)))
                .append(Component.literal("yükseltebilirsin!").withStyle(s -> s.withColor(0x55FF55)))
        );

        // ─── SAYFA 2: Şans Sistemi ───
        String page2 = Component.Serializer.toJson(
            Component.literal("")
                .append(Component.literal("⭐ Şans Sistemi ⭐\n").withStyle(s -> s.withColor(0xFFAA00).withBold(true)))
                .append(Component.literal("\n"))
                .append(Component.literal("Şans seviyen\n").withStyle(s -> s.withColor(0x55FF55)))
                .append(Component.literal("yükseldikçe:\n").withStyle(s -> s.withColor(0x55FF55)))
                .append(Component.literal("\n"))
                .append(Component.literal("• Daha fazla ödül\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("• Daha güçlü eşya\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("• Boss'lardan\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("  değerli ödüller\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("\n"))
                .append(Component.literal("Seviye: ").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("1").withStyle(s -> s.withColor(0xFFAA00).withBold(true)))
                .append(Component.literal(" ile başlarsın").withStyle(s -> s.withColor(0xAAAAAA)))
        );

        // ─── SAYFA 3: Ödül Çarpanları ───
        String page3 = Component.Serializer.toJson(
            Component.literal("")
                .append(Component.literal("📊 Çarpan Tablosu\n").withStyle(s -> s.withColor(0xFFAA00).withBold(true)))
                .append(Component.literal("\n"))
                .append(Component.literal("Seviye 1-5:\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("  → x1 ödül\n").withStyle(s -> s.withColor(0x55FF55)))
                .append(Component.literal("\n"))
                .append(Component.literal("Seviye 6-10:\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("  → x2 ödül\n").withStyle(s -> s.withColor(0x55FF55)))
                .append(Component.literal("\n"))
                .append(Component.literal("Seviye 11-20:\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("  → x3 ödül\n").withStyle(s -> s.withColor(0x55FF55)))
                .append(Component.literal("\n"))
                .append(Component.literal("Seviye 21-50:\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("  → x4 ödül\n").withStyle(s -> s.withColor(0x55FF55)))
                .append(Component.literal("\n"))
                .append(Component.literal("Seviye 50+:\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("  → x5 ödül").withStyle(s -> s.withColor(0xFFAA00).withBold(true)))
        );

        // ─── SAYFA 4: Şans Yumurtası ───
        String page4 = Component.Serializer.toJson(
            Component.literal("")
                .append(Component.literal("🥚 Şans Yumurtası\n").withStyle(s -> s.withColor(0xFFAA00).withBold(true)))
                .append(Component.literal("\n"))
                .append(Component.literal("Şans seviyeni artır-\n").withStyle(s -> s.withColor(0x555555)))
                .append(Component.literal("mak için Şans\n").withStyle(s -> s.withColor(0x555555)))
                .append(Component.literal("Yumurtası üret!\n").withStyle(s -> s.withColor(0x555555)))
                .append(Component.literal("\n"))
                .append(Component.literal("Tarif (3x3):\n").withStyle(s -> s.withColor(0x55FF55).withBold(true)))
                .append(Component.literal("N  E  N\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("E  E  E\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("N  E  N\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("\n"))
                .append(Component.literal("N=Netherite\n").withStyle(s -> s.withColor(0xFFAA00)))
                .append(Component.literal("E=Elmas").withStyle(s -> s.withColor(0x55FFFF)))
        );

        // ─── SAYFA 5: Mob Ödülleri ───
        String page5 = Component.Serializer.toJson(
            Component.literal("")
                .append(Component.literal("⚔ Mob Ödülleri\n").withStyle(s -> s.withColor(0xFF5555).withBold(true)))
                .append(Component.literal("\n"))
                .append(Component.literal("🧟 Zombi:\n").withStyle(s -> s.withColor(0x55FF55).withBold(true)))
                .append(Component.literal("Elmas, Altın Blok\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("\n"))
                .append(Component.literal("💀 İskelet:\n").withStyle(s -> s.withColor(0x55FF55).withBold(true)))
                .append(Component.literal("Büyülü Yay\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("\n"))
                .append(Component.literal("💥 Creeper:\n").withStyle(s -> s.withColor(0x55FF55).withBold(true)))
                .append(Component.literal("Elmas Blok\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("\n"))
                .append(Component.literal("🕷 Örümcek:\n").withStyle(s -> s.withColor(0x55FF55).withBold(true)))
                .append(Component.literal("Büyülü Kitap\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("\n"))
                .append(Component.literal("👁 Enderman:\n").withStyle(s -> s.withColor(0x55FF55).withBold(true)))
                .append(Component.literal("Netherite Hurda").withStyle(s -> s.withColor(0xAAAAAA)))
        );

        // ─── SAYFA 6: Boss Ödülleri ───
        String page6 = Component.Serializer.toJson(
            Component.literal("")
                .append(Component.literal("👑 Boss Ödülleri\n").withStyle(s -> s.withColor(0xFF5555).withBold(true)))
                .append(Component.literal("\n"))
                .append(Component.literal("🏛 Warden:\n").withStyle(s -> s.withColor(0xAA00AA).withBold(true)))
                .append(Component.literal("Netherite Set\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("Totem, Elytra\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("\n"))
                .append(Component.literal("☠ Wither:\n").withStyle(s -> s.withColor(0xAA00AA).withBold(true)))
                .append(Component.literal("Elytra, Nether Star\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("Netherite Set\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("\n"))
                .append(Component.literal("🐉 Ender Dragon:\n").withStyle(s -> s.withColor(0xFF55FF).withBold(true)))
                .append(Component.literal("TAM Netherite Set\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("Çoklu Nether Star\n").withStyle(s -> s.withColor(0xAAAAAA)))
                .append(Component.literal("Totem Paketi").withStyle(s -> s.withColor(0xAAAAAA)))
        );

        // ─── SAYFA 7: Son Sayfa ───
        String page7 = Component.Serializer.toJson(
            Component.literal("")
                .append(Component.literal("\n\n\n"))
                .append(Component.literal("✨ İyi Eğlenceler! ✨\n").withStyle(s -> s.withColor(0xFFD700).withBold(true)))
                .append(Component.literal("\n"))
                .append(Component.literal("Bol şans dileriz!\n").withStyle(s -> s.withColor(0xFFAA00)))
                .append(Component.literal("\n"))
                .append(Component.literal("— Şans Ustası —").withStyle(s -> s.withColor(0xAAAAAA)))
        );

        // Sayfaları listeye ekle
        pages.add(StringTag.valueOf(page1));
        pages.add(StringTag.valueOf(page2));
        pages.add(StringTag.valueOf(page3));
        pages.add(StringTag.valueOf(page4));
        pages.add(StringTag.valueOf(page5));
        pages.add(StringTag.valueOf(page6));
        pages.add(StringTag.valueOf(page7));

        bookTag.put("pages", pages);
        book.setTag(bookTag);

        return book;
    }
}
