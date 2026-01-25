package net.nimbu.thaumaturgy.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.nimbu.thaumaturgy.item.SpellUnlockHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements SpellUnlockHandler {

    private int spellUnlockFlags;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeCustomDataMixin(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("Thaumaturgy:SpellUnlocks", spellUnlockFlags);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readCustomDataMixin(NbtCompound nbt, CallbackInfo ci) {
        spellUnlockFlags = nbt.getInt("Thaumaturgy:SpellUnlocks");
    }

    public boolean getSpellUnlockFlags(int index) {
        return (spellUnlockFlags & (1 << index)) != 0; //return flag truth at masked position
    }

    public void setSpellUnlockFlags(int index, boolean value) {
        if (value) {
            spellUnlockFlags |= (1 << index);
        } else {
            spellUnlockFlags &= ~(1 << index);
        }
    }
}