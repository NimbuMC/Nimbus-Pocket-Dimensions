package net.nimbu.thaumaturgy.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.nimbu.thaumaturgy.renderer.LobotomyVisualState;

public class LobotomyHudRenderer {

    public static void register() {
        HudRenderCallback.EVENT.register(LobotomyHudRenderer::render);
    }

    private static void render(DrawContext ctx, RenderTickCounter tickDelta) {
        if (LobotomyVisualState.ticksRemaining <= 0) return;
        if (LobotomyVisualState.currentImage == null) return;

        MinecraftClient client = MinecraftClient.getInstance();
        int w = client.getWindow().getScaledWidth();
        int h = client.getWindow().getScaledHeight();

        float alpha = (float) LobotomyVisualState.ticksRemaining
                / LobotomyVisualState.maxTicks;

        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, alpha);

        ctx.drawTexture(
                LobotomyVisualState.currentImage,
                0, 0,
                0, 0,
                w, h,
                w, h
        );

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();

        LobotomyVisualState.ticksRemaining--;
    }
}
