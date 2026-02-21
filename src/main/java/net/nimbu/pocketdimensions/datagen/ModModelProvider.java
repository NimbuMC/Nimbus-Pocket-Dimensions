package net.nimbu.pocketdimensions.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        //blockStateModelGenerator.registerStateWithModelReference(ModBlocks.REVISUALISING_TABLE, Blocks.ENCHANTING_TABLE);// SimpleState(ModBlocks.REVISUALISING_TABLE);

        //blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.DOORWAY_BOTTOM);
        //blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.DOORWAY_TOP);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        //itemModelGenerator.register(ModItems.SPIRIT_SWORD, Models.HANDHELD);
    }
}
