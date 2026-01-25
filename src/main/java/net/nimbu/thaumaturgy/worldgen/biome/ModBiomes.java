package net.nimbu.thaumaturgy.worldgen.biome;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.nimbu.thaumaturgy.Thaumaturgy;

public class ModBiomes {
    public static final RegistryKey<Biome> POCKET_DIM_BIOME = RegistryKey.of(RegistryKeys.BIOME,
            Identifier.of(Thaumaturgy.MOD_ID, "pocket_dim_biome"));
}
