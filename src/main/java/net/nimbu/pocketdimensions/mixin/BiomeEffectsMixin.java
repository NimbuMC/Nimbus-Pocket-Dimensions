package net.nimbu.pocketdimensions.mixin;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.BiomeParticleConfig;
import net.nimbu.pocketdimensions.network.ClientPocketDimensionPersistentState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(BiomeEffects.class)
    public class BiomeEffectsMixin {

    @Inject(method = "getFogColor", at = @At("HEAD"), cancellable = true)
    public void getDynamicBiomeFogColour(CallbackInfoReturnable<Integer> cir)
    {
        if(ClientPocketDimensionPersistentState.isClientInPocketDimension())
        {
            cir.setReturnValue(ClientPocketDimensionPersistentState.getDynamicBiomeEffects().getFogColor());
        }
    }

    @Inject(method = "getWaterColor", at = @At("HEAD"), cancellable = true)
    public void getDynamicBiomeWaterColor(CallbackInfoReturnable<Integer> cir)
    {
        if(ClientPocketDimensionPersistentState.isClientInPocketDimension())
        {
            cir.setReturnValue(ClientPocketDimensionPersistentState.getDynamicBiomeEffects().getWaterColor());
        }
    }

    @Inject(method = "getWaterFogColor", at = @At("HEAD"), cancellable = true)
    public void getDynamicBiomeWaterFogColor(CallbackInfoReturnable<Integer> cir)
    {
        if(ClientPocketDimensionPersistentState.isClientInPocketDimension())
        {
            cir.setReturnValue(ClientPocketDimensionPersistentState.getDynamicBiomeEffects().getWaterFogColor());
        }
    }

    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    public void getDynamicBiomeSkyColor(CallbackInfoReturnable<Integer> cir)
    {
        if(ClientPocketDimensionPersistentState.isClientInPocketDimension())
        {
            cir.setReturnValue(ClientPocketDimensionPersistentState.getDynamicBiomeEffects().getSkyColor());
        }
    }

    @Inject(method = "getFoliageColor", at = @At("HEAD"), cancellable = true)
    public void getDynamicBiomeFoliageColor(CallbackInfoReturnable<Optional<Integer>> cir)
    {
        if(ClientPocketDimensionPersistentState.isClientInPocketDimension())
        {
            cir.setReturnValue(ClientPocketDimensionPersistentState.getDynamicBiomeEffects().getFoliageColor());
        }
    }

    @Inject(method = "getGrassColor", at = @At("HEAD"), cancellable = true)
    public void getDynamicBiomeGrassColor(CallbackInfoReturnable<Optional<Integer>> cir)
    {
        if(ClientPocketDimensionPersistentState.isClientInPocketDimension())
        {
            cir.setReturnValue(ClientPocketDimensionPersistentState.getDynamicBiomeEffects().getGrassColor());
        }
    }

    @Inject(method = "getGrassColorModifier", at = @At("HEAD"), cancellable = true)
    public void getDynamicBiomeGrassColorModifier(CallbackInfoReturnable<BiomeEffects.GrassColorModifier> cir)
    {
        if(ClientPocketDimensionPersistentState.isClientInPocketDimension())
        {
            cir.setReturnValue(BiomeEffects.GrassColorModifier.NONE);
        }
    }

    @Inject(method = "getParticleConfig", at = @At("HEAD"), cancellable = true)
    public void getDynamicBiomeParticleConfig(CallbackInfoReturnable<Optional<BiomeParticleConfig>> cir)
    {
        if(ClientPocketDimensionPersistentState.isClientInPocketDimension())
        {
            cir.setReturnValue(ClientPocketDimensionPersistentState.getDynamicBiomeEffects().getParticleConfig());
        }
    }

    @Inject(method = "getLoopSound", at = @At("HEAD"), cancellable = true)
    public void getDynamicBiomeLoopSound(CallbackInfoReturnable<Optional<RegistryEntry<SoundEvent>>> cir)
    {
        if(ClientPocketDimensionPersistentState.isClientInPocketDimension())
        {
            cir.setReturnValue(ClientPocketDimensionPersistentState.getDynamicBiomeEffects().getLoopSound());
        }
    }

    @Inject(method = "getMoodSound", at = @At("HEAD"), cancellable = true)
    public void getDynamicBiomeMoodSound(CallbackInfoReturnable<Optional<BiomeMoodSound>> cir)
    {
        if(ClientPocketDimensionPersistentState.isClientInPocketDimension())
        {
            cir.setReturnValue(ClientPocketDimensionPersistentState.getDynamicBiomeEffects().getMoodSound());
        }
    }

    @Inject(method = "getAdditionsSound", at = @At("HEAD"), cancellable = true)
    public void getDynamicBiomeAdditionsSound(CallbackInfoReturnable<Optional<BiomeAdditionsSound>> cir)
    {
        if(ClientPocketDimensionPersistentState.isClientInPocketDimension())
        {
            cir.setReturnValue(ClientPocketDimensionPersistentState.getDynamicBiomeEffects().getAdditionsSound());
        }
    }

    @Inject(method = "getMusic", at = @At("HEAD"), cancellable = true)
    public void getDynamicBiomeMusic(CallbackInfoReturnable<Optional<MusicSound>> cir)
    {
        if(ClientPocketDimensionPersistentState.isClientInPocketDimension())
        {
            cir.setReturnValue(ClientPocketDimensionPersistentState.getDynamicBiomeEffects().getMusic());
        }
    }
}
