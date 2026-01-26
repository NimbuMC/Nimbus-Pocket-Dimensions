package net.nimbu.thaumaturgy.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.nimbu.thaumaturgy.screen.custom.SpellScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {

    //used for reapplying inputs when the spell screen is up

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(
            method = "onKey",
            at = @At("HEAD")
    )
    private void allowMovementInGui(
            long window,
            int key,
            int scancode,
            int action,
            int modifiers,
            CallbackInfo ci
    ) {
        if (client.player == null) return;
        if (client.currentScreen == null) return;

        if (!(client.currentScreen instanceof SpellScreen)) return;

        boolean pressed = action != GLFW.GLFW_RELEASE;

        handleKey(client.options.forwardKey, key, pressed);
        handleKey(client.options.backKey, key, pressed);
        handleKey(client.options.leftKey, key, pressed);
        handleKey(client.options.rightKey, key, pressed);
        handleKey(client.options.jumpKey, key, pressed);
        handleKey(client.options.sneakKey, key, pressed);
        handleKey(client.options.sprintKey, key, pressed);
        handleKey(client.options.useKey, key,pressed);
    }

    private void handleKey(KeyBinding binding, int key, boolean pressed) {
        if (binding.matchesKey(key, 0)) {
            binding.setPressed(pressed);
        }
    }
}