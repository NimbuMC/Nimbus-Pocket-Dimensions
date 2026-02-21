package net.nimbu.pocketdimensions.worldgen.dimension;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.nimbu.pocketdimensions.PocketDimensions;

public class ModDimensions {
    public static final RegistryKey<DimensionOptions> POCKET_DIM_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            Identifier.of(PocketDimensions.MOD_ID, "pocket_dim"));
    public static final RegistryKey<World> POCKET_DIM_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            Identifier.of(PocketDimensions.MOD_ID, "pocket_dim"));
    public static final RegistryKey<DimensionType> POCKET_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            Identifier.of(PocketDimensions.MOD_ID, "pocket_dim_type"));
}
