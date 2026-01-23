package net.nimbu.thaumaturgy.mixin;/*package net.nimbu.thaumaturgy.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {









    @Inject(
            method = "tiltViewWhenHurt",
            at = @At("HEAD"),
            cancellable = true
    )
    private void tiltViewWhenHurtMixin(
            MatrixStack matrices, float tickDelta, CallbackInfo ci
    ) {
        //matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90));
        //matrices.translate(0,1.0f,0);

    }








   /*@Inject(
            method = "getFov",
            at = @At("RETURN"),
            cancellable = true
    )
    private void modifyFov(
            Camera camera,
            float tickDelta,
            boolean changingFov,
            CallbackInfoReturnable<Double> cir
    ) {
        //double fov = cir.getReturnValue();
        /double fov = 90.0F;

        cir.setReturnValue(fov);
    }
    /*

    @ModifyVariable(
            method = "renderWithZoom",
            at = @At(value = "HEAD"),
            ordinal = 1,
            argsOnly = true
    )
    public float renderWithZoomMixin(float zoom, float zoomX, float zoomY) {

        zoomX=0.5f;

        return zoomX;
    }*/
//}
