package exloran.luckyascension.capability;

import net.minecraft.nbt.CompoundTag;

/**
 * Şans Capability Implementasyonu
 * Oyuncunun şans verisini saklar ve yönetir
 */
public class LuckCapability implements ILuckCapability {

    // Şans seviyesi - başlangıç değeri 1
    private int luckLevel = 1;

    @Override
    public int getLuckLevel() {
        return luckLevel;
    }

    @Override
    public void setLuckLevel(int level) {
        // Negatif değer olmamasını sağla
        this.luckLevel = Math.max(1, level);
    }

    @Override
    public void increaseLuck() {
        this.luckLevel++;
    }

    /**
     * Şans seviyesine göre ödül çarpanını hesapla
     */
    @Override
    public int getRewardMultiplier() {
        if (luckLevel <= 5) return 1;
        if (luckLevel <= 10) return 2;
        if (luckLevel <= 20) return 3;
        if (luckLevel <= 50) return 4;
        return 5; // 50+
    }

    /**
     * NBT'ye kaydet (dünya kapanınca veri korunur)
     */
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("LuckLevel", luckLevel);
        return tag;
    }

    /**
     * NBT'den yükle (dünya açılınca devam et)
     */
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("LuckLevel")) {
            this.luckLevel = tag.getInt("LuckLevel");
        }
    }
}
