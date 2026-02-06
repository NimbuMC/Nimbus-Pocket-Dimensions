package net.nimbu.thaumaturgy.mixin;

import net.fabricmc.fabric.mixin.client.rendering.LivingEntityRendererAccessor;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
//import net.nimbu.thaumaturgy.renderer.FlightWindFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {

    @Inject(
            method = "<init>",
            at = @At("TAIL")
    )
    private void addFlightWindFeature(
            EntityRendererFactory.Context context,
            boolean slim,
            CallbackInfo ci
    ) {
        LivingEntityRendererAccessor accessor =
                (LivingEntityRendererAccessor) this;

//        accessor.callAddFeature(
//                new FlightWindFeatureRenderer(
//                        (PlayerEntityRenderer) (Object) this,
//                        context.getModelLoader()
//                )
//        );
    }
}