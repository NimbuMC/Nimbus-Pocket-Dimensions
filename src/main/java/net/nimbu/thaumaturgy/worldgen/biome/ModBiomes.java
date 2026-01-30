package net.nimbu.thaumaturgy.worldgen.biome;

import net.minecraft.registry.*;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.nimbu.thaumaturgy.Thaumaturgy;

public class ModBiomes {
    public static final RegistryKey<Biome> POCKET_DIM_BIOME = RegistryKey.of(RegistryKeys.BIOME,
            Identifier.of(Thaumaturgy.MOD_ID, "pocket_dim_biome"));

    public static final TagKey<Biome> DYNAMIC =
            TagKey.of(
                    RegistryKeys.BIOME,
                    Identifier.of(Thaumaturgy.MOD_ID, "dynamic")
            );


}
