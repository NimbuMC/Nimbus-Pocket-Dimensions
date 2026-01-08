package net.nimbu.thaumaturgy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.block.custom.Wellspring;
import net.nimbu.thaumaturgy.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        //blockStateModelGenerator.registerStateWithModelReference(ModBlocks.REVISUALISING_TABLE, Blocks.ENCHANTING_TABLE);// SimpleState(ModBlocks.REVISUALISING_TABLE);

        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.PITCH_BLACK_BLOCK);

        Identifier wellspringOffIdentifier = TexturedModel.CUBE_ALL.upload(ModBlocks.WELLSPRING, blockStateModelGenerator.modelCollector);
        Identifier wellspringOnIdentifier = blockStateModelGenerator.createSubModel(ModBlocks.WELLSPRING, "_on", Models.CUBE_ALL, TextureMap::all);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ModBlocks.WELLSPRING)
                .coordinate(BlockStateModelGenerator.createBooleanModelMap(Wellspring.CLICKED, wellspringOnIdentifier, wellspringOffIdentifier)));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.WAND, Models.HANDHELD);
    }
}
