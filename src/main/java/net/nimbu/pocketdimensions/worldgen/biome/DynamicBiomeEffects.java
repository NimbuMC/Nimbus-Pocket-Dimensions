package net.nimbu.pocketdimensions.worldgen.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BiomeAdditionsSound;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.biome.BiomeParticleConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.OptionalInt;

public class DynamicBiomeEffects {
    /*
    CARBON COPY of the BiomeEffects class, required to avoid recursive calling in the BiomeEffectsMixin
     */



    public static final Codec<DynamicBiomeEffects> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codec.INT.fieldOf("fog_color").forGetter(effects -> effects.fogColor),
                            Codec.INT.fieldOf("water_color").forGetter(effects -> effects.waterColor),
                            Codec.INT.fieldOf("water_fog_color").forGetter(effects -> effects.waterFogColor),
                            Codec.INT.fieldOf("sky_color").forGetter(effects -> effects.skyColor),
                            Codec.INT.optionalFieldOf("foliage_color").forGetter(effects -> effects.foliageColor),
                            Codec.INT.optionalFieldOf("grass_color").forGetter(effects -> effects.grassColor),
                            BiomeParticleConfig.CODEC.optionalFieldOf("particle").forGetter(effects -> effects.particleConfig),
                            SoundEvent.ENTRY_CODEC.optionalFieldOf("ambient_sound").forGetter(effects -> effects.loopSound),
                            BiomeMoodSound.CODEC.optionalFieldOf("mood_sound").forGetter(effects -> effects.moodSound),
                            BiomeAdditionsSound.CODEC.optionalFieldOf("additions_sound").forGetter(effects -> effects.additionsSound),
                            MusicSound.CODEC.optionalFieldOf("music").forGetter(effects -> effects.music)
                    )
                    .apply(instance, DynamicBiomeEffects::new)
    );
    private int fogColor;
    private int waterColor;
    private int waterFogColor;
    private int skyColor;
    private Optional<Integer> foliageColor;
    private Optional<Integer> grassColor;
    private Optional<BiomeParticleConfig> particleConfig;
    private Optional<RegistryEntry<SoundEvent>> loopSound;
    private Optional<BiomeMoodSound> moodSound;
    private Optional<BiomeAdditionsSound> additionsSound;
    private Optional<MusicSound> music;

    DynamicBiomeEffects(
            int fogColor,
            int waterColor,
            int waterFogColor,
            int skyColor,
            Optional<Integer> foliageColor,
            Optional<Integer> grassColor,
            Optional<BiomeParticleConfig> particleConfig,
            Optional<RegistryEntry<SoundEvent>> loopSound,
            Optional<BiomeMoodSound> moodSound,
            Optional<BiomeAdditionsSound> additionsSound,
            Optional<MusicSound> music
    ) {
        this.fogColor = fogColor;
        this.waterColor = waterColor;
        this.waterFogColor = waterFogColor;
        this.skyColor = skyColor;
        this.foliageColor = foliageColor;
        this.grassColor = grassColor;
        this.particleConfig = particleConfig;
        this.loopSound = loopSound;
        this.moodSound = moodSound;
        this.additionsSound = additionsSound;
        this.music = music;
    }

    public int getFogColor() {
        return this.fogColor;
    }

    public void setFogColor(int fogColor){
        this.fogColor = fogColor;
    }

    public int getWaterColor() {
        return this.waterColor;
    }

    public void setWaterColor(int waterColor) {
        this.waterColor = waterColor;
    }

    public int getWaterFogColor() {
        return this.waterFogColor;
    }

    public void setWaterFogColor(int waterFogColor) {
        this.waterFogColor = waterFogColor;
    }

    public int getSkyColor() {
        return this.skyColor;
    }

    public void setSkyColor(int skyColor) {
        this.skyColor = skyColor;
    }

    public Optional<Integer> getFoliageColor() {
        return this.foliageColor;
    }

    public void setFoliageColor(Optional<Integer> foliageColor) {
        this.foliageColor = foliageColor;
    }

    public Optional<Integer> getGrassColor() {
        return this.grassColor;
    }

    public void setGrassColor(Optional<Integer> grassColor) {
        this.grassColor = grassColor;
    }

    public Optional<BiomeParticleConfig> getParticleConfig() {
        return this.particleConfig;
    }

    public void setParticleConfig(Optional<BiomeParticleConfig> particleConfig) {
        this.particleConfig = particleConfig;
    }

    /**
     * Returns the loop sound.
     *
     * <p>A loop sound is played continuously as an ambient sound whenever the
     * player is in the biome with this effect.
     */
    public Optional<RegistryEntry<SoundEvent>> getLoopSound() {
        return this.loopSound;
    }

    public void setLoopSound(Optional<RegistryEntry<SoundEvent>> loopSound) {
        this.loopSound = loopSound;
    }

    /**
     * Returns the mood sound.
     *
     * <p>A mood sound is played once every 6000 to 17999 ticks as an ambient
     * sound whenever the player is in the biome with this effect and near a
     * position that has 0 sky light and less than 7 combined light.
     *
     * <p>Overworld biomes have the regular cave sound as their mood sound,
     * while three nether biomes in 20w10a have their dedicated mood sounds.
     */
    public Optional<BiomeMoodSound> getMoodSound() {
        return this.moodSound;
    }

    public void setMoodSound(Optional<BiomeMoodSound> moodSound) {
        this.moodSound = moodSound;
    }

    /**
     * Returns the additions sound.
     *
     * <p>An additions sound is played at 1.1% chance every tick as an ambient
     * sound whenever the player is in the biome with this effect.
     */
    public Optional<BiomeAdditionsSound> getAdditionsSound() {
        return this.additionsSound;
    }

    public void setAdditionsSound(Optional<BiomeAdditionsSound> additionsSound) {
        this.additionsSound = additionsSound;
    }

    public Optional<MusicSound> getMusic() {
        return this.music;
    }

    public void setMusic(Optional<MusicSound> music) {
        this.music = music;
    }

    public static class Builder {
        private OptionalInt fogColor = OptionalInt.empty();
        private OptionalInt waterColor = OptionalInt.empty();
        private OptionalInt waterFogColor = OptionalInt.empty();
        private OptionalInt skyColor = OptionalInt.empty();
        private Optional<Integer> foliageColor = Optional.empty();
        private Optional<Integer> grassColor = Optional.empty();
        private Optional<BiomeParticleConfig> particleConfig = Optional.empty();
        private Optional<RegistryEntry<SoundEvent>> loopSound = Optional.empty();
        private Optional<BiomeMoodSound> moodSound = Optional.empty();
        private Optional<BiomeAdditionsSound> additionsSound = Optional.empty();
        private Optional<MusicSound> musicSound = Optional.empty();

        public DynamicBiomeEffects.Builder fogColor(int fogColor) {
            this.fogColor = OptionalInt.of(fogColor);
            return this;
        }

        public DynamicBiomeEffects.Builder waterColor(int waterColor) {
            this.waterColor = OptionalInt.of(waterColor);
            return this;
        }

        public DynamicBiomeEffects.Builder waterFogColor(int waterFogColor) {
            this.waterFogColor = OptionalInt.of(waterFogColor);
            return this;
        }

        public DynamicBiomeEffects.Builder skyColor(int skyColor) {
            this.skyColor = OptionalInt.of(skyColor);
            return this;
        }

        public DynamicBiomeEffects.Builder foliageColor(int foliageColor) {
            this.foliageColor = Optional.of(foliageColor);
            return this;
        }

        public DynamicBiomeEffects.Builder grassColor(int grassColor) {
            this.grassColor = Optional.of(grassColor);
            return this;
        }

        public DynamicBiomeEffects.Builder particleConfig(BiomeParticleConfig particleConfig) {
            this.particleConfig = Optional.of(particleConfig);
            return this;
        }

        public DynamicBiomeEffects.Builder loopSound(RegistryEntry<SoundEvent> loopSound) {
            this.loopSound = Optional.of(loopSound);
            return this;
        }

        public DynamicBiomeEffects.Builder moodSound(BiomeMoodSound moodSound) {
            this.moodSound = Optional.of(moodSound);
            return this;
        }

        public DynamicBiomeEffects.Builder additionsSound(BiomeAdditionsSound additionsSound) {
            this.additionsSound = Optional.of(additionsSound);
            return this;
        }

        public DynamicBiomeEffects.Builder music(@Nullable MusicSound music) {
            this.musicSound = Optional.ofNullable(music);
            return this;
        }

        public DynamicBiomeEffects build() {
            return new DynamicBiomeEffects(
                    this.fogColor.orElseThrow(() -> new IllegalStateException("Missing 'fog' color.")),
                    this.waterColor.orElseThrow(() -> new IllegalStateException("Missing 'water' color.")),
                    this.waterFogColor.orElseThrow(() -> new IllegalStateException("Missing 'water fog' color.")),
                    this.skyColor.orElseThrow(() -> new IllegalStateException("Missing 'sky' color.")),
                    this.foliageColor,
                    this.grassColor,
                    this.particleConfig,
                    this.loopSound,
                    this.moodSound,
                    this.additionsSound,
                    this.musicSound
            );
        }
    }
}
