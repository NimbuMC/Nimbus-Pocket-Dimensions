package net.nimbu.thaumaturgy.worldgen.dimension;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.minecraft.util.Identifier;

public class ModDimensions {
    public static final RegistryKey<DimensionOptions> POCKET_DIM_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            Identifier.tryParse(Thaumaturgy.MOD_ID, "pocket_dim"));
    public static final RegistryKey<World> POCKET_DIM_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            Identifier.tryParse(Thaumaturgy.MOD_ID, "pocket_dim"));
    public static final RegistryKey<DimensionType> POCKET_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            Identifier.tryParse(Thaumaturgy.MOD_ID, "pocket_dim_type"));
}
