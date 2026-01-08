package net.nimbu.thaumaturgy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerStateWithModelReference(ModBlocks.REVISUALISING_TABLE, Blocks.ENCHANTING_TABLE);// SimpleState(ModBlocks.REVISUALISING_TABLE);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.WAND, Models.HANDHELD);
    }
}
