package net.nimbu.pocketdimensions.dimensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.UUID;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionOptions;
import net.minecraft.world.dimension.DimensionType;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.renderer.PocketDimensionBorderRenderer;
import net.nimbu.pocketdimensions.worldgen.biome.ModBiomes;
import net.nimbu.pocketdimensions.worldgen.dimension.ModDimensions;
import qouteall.dimlib.api.DimensionAPI;

public class DimensionalInstancer {
    public static ServerWorld createInstance(MinecraftServer server, UUID ownerID) {
        Identifier instanceID = Identifier.of(PocketDimensions.MOD_ID, "pocket_dimension_" + ownerID);
        if (!DimensionAPI.dimensionExistsInRegistry(server, instanceID)) {
            RegistryEntry<DimensionType> typeEntry = server.getRegistryManager()
                    .get(RegistryKeys.DIMENSION_TYPE)
                    .getEntry(ModDimensions.POCKET_DIM_TYPE) // or NETHER / END
                    .orElseThrow(() -> new RuntimeException("Overworld DimensionType not found"));

            RegistryEntry<Biome> biome = server.getRegistryManager()
                    .get(RegistryKeys.BIOME)
                    .getEntry(ModBiomes.POCKET_DIM_BIOME)
                    .orElseThrow();

            DimensionOptions options = new DimensionOptions(typeEntry, SolidGenerator.create(biome));


            DimensionAPI.addDimensionDynamically(server, instanceID, options);

            ServerWorld world = server.getWorld(RegistryKey.of(RegistryKeys.WORLD, instanceID));
            DoWorldPreset(world);
            return world;
        }
        return server.getWorld(RegistryKey.of(RegistryKeys.WORLD, instanceID));
    }

    private static void DoWorldPreset(ServerWorld world) {
        BlockState state = Blocks.AIR.getDefaultState();
        for (int x = 0; x < PocketDimensionBorderRenderer.BorderLength; x++) {
            for (int y = 0; y < PocketDimensionBorderRenderer.BorderHeight; y++) {
                for (int z = 0; z < PocketDimensionBorderRenderer.BorderLength; z++) {
                    world.setBlockState(BlockPos.ORIGIN.add(x,147 + y,z), state);
                }
            }
        }
    }
}
