package net.nimbu.thaumaturgy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.block.custom.Wellspring;
import net.nimbu.thaumaturgy.item.ModItems;

import java.util.Optional;

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

        //blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.DOORWAY_BOTTOM);
        //blockStateModelGenerator.registerNorthDefaultHorizontalRotation(ModBlocks.DOORWAY_TOP);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        // Tools:

        //itemModelGenerator.register(ModItems.WAND, Models.HANDHELD);

        itemModelGenerator.register(ModItems.SPIRIT_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SPIRIT_SHOVEL, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SPIRIT_PICKAXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SPIRIT_AXE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SPIRIT_HOE, Models.HANDHELD);

        itemModelGenerator.register(ModItems.HAMMER, Models.HANDHELD);

        // Resources:
        itemModelGenerator.register(ModItems.PIXIE_DUST, Models.GENERATED);
        itemModelGenerator.register(ModItems.MAGIC_MUSHROOM_STEW, Models.GENERATED);

        // Spawn eggs:
        itemModelGenerator.register(ModItems.PIXIE_SPAWN_EGG,
                new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));

        Model sharedModel = new Model(
                Optional.of(Identifier.of(Thaumaturgy.MOD_ID, "item/grimoire")),
                Optional.empty()
        );

        ModItems.GRIMOIRES.forEach(item -> {
            itemModelGenerator.register(item, sharedModel);
        });
    }
}
