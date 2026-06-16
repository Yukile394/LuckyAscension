package exloran.luckyascension.capability;

/**
 * Şans Capability Arayüzü
 * Her oyuncunun şans seviyesi ve çarpanını tanımlar
 */
public interface ILuckCapability {

    /**
     * Mevcut şans seviyesini döndür
     */
    int getLuckLevel();

    /**
     * Şans seviyesini ayarla
     */
    void setLuckLevel(int level);

    /**
     * Şans seviyesini 1 artır
     */
    void increaseLuck();

    /**
     * Şans seviyesine göre ödül çarpanını hesapla
     * Level 1-5:   x1 (1 ödül)
     * Level 6-10:  x2 (2 ödül)
     * Level 11-20: x3 (3 ödül)
     * Level 21-50: x4 (4 ödül)
     * Level 50+:   x5 (5 ödül)
     */
    int getRewardMultiplier();
}
