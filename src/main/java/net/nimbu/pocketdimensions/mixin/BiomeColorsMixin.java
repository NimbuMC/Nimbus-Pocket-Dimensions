package net.nimbu.pocketdimensions.mixin;

import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.biome.Biome;
import net.nimbu.pocketdimensions.network.ClientPocketDimensionPersistentState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeColors.class)
public class BiomeColorsMixin {

    @Inject(method = "getGrassColor", at = @At("HEAD"), cancellable = true)
    private static void getDynamicGrassColor(
            BlockRenderView world,
            BlockPos pos,
            CallbackInfoReturnable<Integer> cir
    ) {
        if (ClientPocketDimensionPersistentState.isClientInPocketDimension()) {
            cir.setReturnValue(ClientPocketDimensionPersistentState.getDynamicBiomeEffects().getGrassColor().orElse(0x00FF00));
        }
    }

    @Inject(method = "getFoliageColor", at = @At("HEAD"), cancellable = true)
        private static void getDynamicFoliageColor(
                BlockRenderView world,
                BlockPos pos,
                CallbackInfoReturnable<Integer> cir
    ) {
        if (ClientPocketDimensionPersistentState.isClientInPocketDimension()) {
            cir.setReturnValue(ClientPocketDimensionPersistentState.getDynamicBiomeEffects().getFoliageColor().orElse(0x00FF00));
        }
    }
}