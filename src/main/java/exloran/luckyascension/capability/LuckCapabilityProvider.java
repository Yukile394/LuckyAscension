package exloran.luckyascension.capability;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Luck Capability Provider
 * Capability'yi oyuncu entity'sine ekler ve NBT serialize/deserialize işlemlerini yönetir
 */
public class LuckCapabilityProvider implements ICapabilitySerializable<CompoundTag> {

    // Capability tanımı - Forge sistemi için gerekli
    public static final Capability<ILuckCapability> LUCK_CAPABILITY =
        CapabilityManager.get(new CapabilityToken<>() {});

    // Lazy optional - gereksiz yük oluşturmaz
    private final LuckCapability instance = new LuckCapability();
    private final LazyOptional<ILuckCapability> lazyOptional = LazyOptional.of(() -> instance);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        // Sadece LuckCapability isteklerine yanıt ver
        if (cap == LUCK_CAPABILITY) {
            return lazyOptional.cast();
        }
        return LazyOptional.empty();
    }

    /**
     * Capability verisini NBT'ye kaydet
     * Oyuncu verisine yazılır, dünya kapatılıp açılınca korunur
     */
    @Override
    public CompoundTag serializeNBT() {
        return instance.serializeNBT();
    }

    /**
     * NBT'den capability verisini yükle
     */
    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.deserializeNBT(nbt);
    }

    /**
     * Provider kapatılınca lazy optional'ı temizle (memory leak önleme)
     */
    public void invalidate() {
        lazyOptional.invalidate();
    }
}
