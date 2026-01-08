package net.nimbu.thaumaturgy.worldgen.dimension;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.minecraft.util.Identifier;

import java.util.OptionalLong;

public class ModDimensions {
    public static final RegistryKey<DimensionOptions> POCKET_DIM_KEY = RegistryKey.of(RegistryKeys.DIMENSION,
            Identifier.tryParse(Thaumaturgy.MOD_ID, "pocket_dim"));
    public static final RegistryKey<World> POCKET_DIM_LEVEL_KEY = RegistryKey.of(RegistryKeys.WORLD,
            Identifier.tryParse(Thaumaturgy.MOD_ID, "pocket_dim"));
    public static final RegistryKey<DimensionType> POCKET_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            Identifier.tryParse(Thaumaturgy.MOD_ID, "pocket_dim_type"));

    public static void bootstrapType(Registerable<DimensionType> context)
    {
        context.register(POCKET_DIM_TYPE, new DimensionType(
                OptionalLong.of(12000), //fixedTime
                false, //hasSkyLight
                false, //hasCeiling
                false, //ultraWarm
                false, //natural
                1.0, //coordinateScale
                true, //bedWorks
                true, //respawnAnchorWorks
                0, //minY
                256, //height
                256, //logicalHeight these fucking psycho devs
                BlockTags.INFINIBURN_OVERWORLD, // infiniburn
                DimensionTypes.OVERWORLD_ID, // effectsLocation
                1.0f, // ambientLight
                new DimensionType.MonsterSettings(false, false, UniformIntProvider.create(0, 0),0)));
    }
}
