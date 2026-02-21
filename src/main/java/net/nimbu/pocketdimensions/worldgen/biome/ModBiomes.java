package net.nimbu.pocketdimensions.worldgen.biome;

import net.minecraft.registry.*;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.nimbu.pocketdimensions.PocketDimensions;

public class ModBiomes {
    public static final RegistryKey<Biome> POCKET_DIM_BIOME = RegistryKey.of(RegistryKeys.BIOME,
            Identifier.of(PocketDimensions.MOD_ID, "pocket_dim_biome"));
}
