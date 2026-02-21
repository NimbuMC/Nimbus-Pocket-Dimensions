package net.nimbu.pocketdimensions.dimensions;
import net.minecraft.block.Blocks;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.FlatChunkGenerator;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.FlatChunkGeneratorLayer;

import java.util.List;
import java.util.Optional;

public class SolidGenerator {

    public static FlatChunkGenerator create(RegistryEntry<Biome> biome) {
        FlatChunkGeneratorConfig config = new FlatChunkGeneratorConfig(
                Optional.empty(), // no structures
                biome,
                List.of() // no features
        );

        // ENTIRE WORLD = BARRIER
        config.getLayers().add(
                new FlatChunkGeneratorLayer(
                        DimensionType.MAX_HEIGHT,
                        Blocks.BARRIER
                )
        );

        config.updateLayerBlocks();

        return new FlatChunkGenerator(config);
    }
}
