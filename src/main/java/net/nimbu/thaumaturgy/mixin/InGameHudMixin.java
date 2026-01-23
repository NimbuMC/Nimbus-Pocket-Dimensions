/*package net.nimbu.thaumaturgy.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LayeredDrawer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
/*
    @Inject(method = "render", at = @At("TAIL"))
    private void renderMixin(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        LayeredDrawer layeredDrawer = new LayeredDrawer()
                .addLayer(this::renderTest);

        //RenderSystem.enableDepthTest();
        layeredDrawer.render(context, tickCounter);
        //RenderSystem.disableDepthTest();


    }

    //\assets\minecraft\textures\gui\sprites\hud\hotbar_selection.png
    //\assets\minecraft\textures\mob_effect\poison.png

    //Identifier.of(Thaumaturgy.MOD_ID, "textures/entity/pixie/pixie.png");
    //private static final Identifier MAGICAL_FILTER = Identifier.of(Thaumaturgy.MOD_ID, "textures/entity/pixie/pixie.png");
    /*private static final Identifier MAGICAL_FILTER = Identifier.of(Thaumaturgy.MOD_ID, "hud/magical_filter");


    private void renderTest(DrawContext context, RenderTickCounter tickCounter) {

        context.drawGuiTexture(MAGICAL_FILTER, 0,  0, context.getScaledWindowWidth(), context.getScaledWindowHeight());

        int i = this.client.player.experienceLevel;
        if (this.shouldRenderExperience() && i > 0) {
            this.client.getProfiler().push("expLevel");
            String string = i + "";
            int j = (context.getScaledWindowWidth() - this.getTextRenderer().getWidth(string)) / 2;
            int k = context.getScaledWindowHeight() - 31 - 4;
            context.drawText(this.getTextRenderer(), string, j + 1, k, 0, false);
            context.drawText(this.getTextRenderer(), string, j - 1, k, 0, false);
            context.drawText(this.getTextRenderer(), string, j, k + 1, 0, false);
            context.drawText(this.getTextRenderer(), string, j, k - 1, 0, false);
            context.drawText(this.getTextRenderer(), string, j, k, 8453920, false);
            this.client.getProfiler().pop();
        }


//}
}*/

