# 🎲 Lucky Ascension Mod

> **Minecraft Java Edition 1.20.1 | Forge Modu**  
> Şans sistemiyle güçlen, düşmanlardan OP eşyalar kazan!

---

## 📋 İçindekiler
- [Özellikler](#-özellikler)
- [Kurulum](#-kurulum)
- [Şans Sistemi](#-şans-sistemi)
- [Mob Ödülleri](#-mob-ödülleri)
- [Şans Yumurtası](#-şans-yumurtası)
- [Derleme](#-derleme)
- [Dosya Yapısı](#-dosya-yapısı)

---

## ✨ Özellikler

| Özellik | Açıklama |
|---------|----------|
| 📖 Rehber Kitap | İlk girişte hotbar ortasına otomatik gelir |
| ⭐ Şans Sistemi | Seviye bazlı ödül çarpanı |
| ⚔️ Mob Ödülleri | Her zararlı yaratıktan özel ödül |
| 🥚 Şans Yumurtası | Craft edilebilir, Şans Ustası çağırır |
| 👨‍🌾 Şans Ustası | Netherite ile Şans Artırma satar |
| 💾 Veri Kalıcılığı | Şans seviyesi kaybolmaz |

---

## 📥 Kurulum

1. **Forge Kur** → [files.minecraftforge.net](https://files.minecraftforge.net) (1.20.1-47.x)
2. **JAR İndir** → Releases sayfasından `luckyascension-1.20.1-1.0.0.jar`
3. **Mods Klasörüne Koy** → `%appdata%/.minecraft/mods/`
4. **Oyunu Başlat** ✅

---

## ⭐ Şans Sistemi

Her oyuncunun bir **Şans Seviyesi** vardır. Başlangıçta **1** dir.

### Ödül Çarpan Tablosu

| Seviye Aralığı | Ödül Sayısı |
|---------------|-------------|
| 1 - 5         | x1 ödül     |
| 6 - 10        | x2 ödül     |
| 11 - 20       | x3 ödül     |
| 21 - 50       | x4 ödül     |
| 50+           | x5 ödül     |

### Şans Nasıl Artar?

1. **Şans Artırma Yumurtası** craft et
2. Bir yüzeye **sağ tıkla** → Şans Ustası köylüsü çıkar
3. Köylüyle ticaret yap: **1 Netherite Külçesi → 1 Şans Artırma**
4. Şans Artırma itemine **sağ tıkla** → Şans +1!

---

## ⚔️ Mob Ödülleri

### Normal Yaratıklar

| Yaratık | Ödüller |
|---------|---------|
| 🧟 Zombi | Elmas, Demir Blok, Altın Blok, Büyülü Elma |
| 💀 İskelet | Elmas, Ok Paketi, Büyülü Yay |
| 💥 Creeper | TNT, Elmas Blok, Netherite Hurda |
| 🕷️ Örümcek | Zümrüt, Altın Elma, Büyülü Kitap |
| 👁️ Enderman | Ender Gözü, Elmas Blok, Netherite Hurda |
| 🐷 Piglin Brute | Netherite Külçesi, Antik Kalıntı |

### Boss Yaratıklar

| Boss | Ödüller |
|------|---------|
| 🏛️ **Warden** | Netherite Set Parçası, Totem, Nether Star, Elytra |
| ☠️ **Wither** | Elytra, Nether Star x2, Netherite Set, Totem x3 |
| 🐉 **Ender Dragon** | TAM Netherite Set, Elytra, Nether Star x3, Totem x5 |

---

## 🥚 Şans Yumurtası

### Craft Tarifi

```
[Netherite] [Elmas]    [Netherite]
[Elmas]     [Elmas]    [Elmas]
[Netherite] [Elmas]    [Netherite]
```

→ **1 adet Şans Artırma Yumurtası**

### Kullanım

1. Yumurtayı envanterine al
2. Herhangi bir yüzeye **sağ tıkla**
3. **Şans Ustası** köylüsü oluşur
4. Köylüyle ticaret yap!

---

## 🔨 Derleme

### Gereksinimler

- Java 17+
- Git

### Adımlar

```bash
# Repoyu klonla
git clone https://github.com/yourusername/LuckyAscension.git
cd LuckyAscension

# Derle
./gradlew build

# JAR dosyası burada:
# build/libs/luckyascension-1.20.1-1.0.0.jar
```

### GitHub Actions

Her `main` branch push'unda otomatik derler.  
Her `v*` tag'ında otomatik GitHub Release oluşturur.

---

## 📁 Dosya Yapısı

```
LuckyAscension/
├── .github/workflows/build.yml        # CI/CD otomasyonu
├── src/main/java/com/luckyascension/
│   ├── LuckyAscension.java            # Ana mod sınıfı
│   ├── capability/
│   │   ├── ILuckCapability.java       # Şans arayüzü
│   │   ├── LuckCapability.java        # Şans implementasyonu
│   │   └── LuckCapabilityProvider.java # Capability provider
│   ├── init/
│   │   ├── ModItems.java              # Item kayıtları
│   │   └── ModVillagers.java          # Köylü kayıtları
│   ├── item/
│   │   ├── LuckBoostItem.java         # Şans Artırma itemi
│   │   └── LuckEggItem.java           # Şans Yumurtası itemi
│   ├── event/
│   │   ├── PlayerJoinEvent.java       # İlk giriş kitabı
│   │   ├── MobDeathEvent.java         # Mob ödül sistemi
│   │   └── LuckEvent.java             # Veri kalıcılığı
│   └── util/
│       └── LuckRewardUtil.java        # Ödül tanımları
└── src/main/resources/
    ├── META-INF/mods.toml             # Mod metadata
    ├── pack.mcmeta                    # Resource pack
    ├── assets/luckyascension/
    │   ├── lang/tr_tr.json            # Türkçe çeviri
    │   ├── lang/en_us.json            # İngilizce çeviri
    │   └── models/item/               # Item modelleri
    └── data/luckyascension/
        └── recipes/luck_egg.json      # Craft tarifi
```

---

## 📄 Lisans

MIT License - Özgürce kullanabilir, değiştirebilir ve paylaşabilirsin.

---

*Lucky Ascension — Şans yolculuğun başlasın! 🎲*
