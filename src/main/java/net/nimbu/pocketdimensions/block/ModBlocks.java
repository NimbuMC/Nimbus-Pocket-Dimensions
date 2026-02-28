package net.nimbu.pocketdimensions.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.block.custom.*;

public class ModBlocks {


    public static final Block GATEWAY = registerBlock("dark_oak_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque()));

    public static final Block TEST_S_GATEWAY = registerBlock("spruce_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque()));

    public static final Block TEST_O_GATEWAY = registerBlock("oak_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque()));

    public static final Block TEST_B_GATEWAY = registerBlock("birch_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque()));

    public static final Block DIMENSION_CUSTOMIZER = registerBlock("dimension_customizer",
            new PocketDimensionCustomizerBlock(AbstractBlock.Settings.create().nonOpaque().luminance(state -> 10)));



    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(PocketDimensions.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, Identifier.of(PocketDimensions.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks(){
        PocketDimensions.LOGGER.info("Registering Mod Blocks for " + PocketDimensions.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> {

        });
    }
}

