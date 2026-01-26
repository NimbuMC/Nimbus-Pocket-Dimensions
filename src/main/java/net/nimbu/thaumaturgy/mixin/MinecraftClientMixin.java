package net.nimbu.thaumaturgy.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.nimbu.thaumaturgy.screen.custom.SpellScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    //retains inputs when the spell menu is displayed, allowing for continuous movement

    @Inject(
            method = "setScreen",
            at = @At("TAIL")
    )
    private void allowMovementInGui(Screen screen, CallbackInfo ci) {
        if (screen == null) return;
        if (!(screen instanceof SpellScreen)) return;

        KeyBinding.updatePressedStates();
    }
}