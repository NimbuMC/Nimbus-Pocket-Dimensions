package net.nimbu.pocketdimensions.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.block.custom.*;

public class ModBlocks {

    public static final Block OAK_GATEWAY = registerBlock("oak_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque(), BlockSetType.OAK), new Item.Settings());

    public static final Block SPRUCE_GATEWAY = registerBlock("spruce_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque(), BlockSetType.SPRUCE), new Item.Settings());

    public static final Block BIRCH_GATEWAY = registerBlock("birch_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque(), BlockSetType.BIRCH), new Item.Settings());

    public static final Block JUNGLE_GATEWAY = registerBlock("jungle_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque(), BlockSetType.JUNGLE), new Item.Settings());

    public static final Block ACACIA_GATEWAY = registerBlock("acacia_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque(), BlockSetType.ACACIA), new Item.Settings());

    public static final Block DARK_OAK_GATEWAY = registerBlock("dark_oak_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque(), BlockSetType.DARK_OAK), new Item.Settings());

    public static final Block MANGROVE_GATEWAY = registerBlock("mangrove_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque(), BlockSetType.MANGROVE), new Item.Settings());

    public static final Block CHERRY_GATEWAY = registerBlock("cherry_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque(), BlockSetType.CHERRY), new Item.Settings());

    //Pale Oak
    //-------

    public static final Block CRIMSON_GATEWAY = registerBlock("crimson_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque(), BlockSetType.CRIMSON), new Item.Settings());

    public static final Block WARPED_GATEWAY = registerBlock("warped_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque(), BlockSetType.WARPED), new Item.Settings());

    public static final Block BAMBOO_GATEWAY = registerBlock("bamboo_gateway",
            new GatewayBlock(AbstractBlock.Settings.create().nonOpaque(), BlockSetType.BAMBOO), new Item.Settings());

    public static final Block DIMENSION_CUSTOMIZER = registerBlock("dimension_customizer",
            new PocketDimensionCustomizerBlock(AbstractBlock.Settings.create()
                    .nonOpaque().luminance(state -> 10).mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(1.5F, 6.0F)),
            new Item.Settings().rarity(Rarity.RARE));

    public static final Block GUI_OAK_LEAVES = registerGuiBlock("gui_oak_leaves",
            new Block(AbstractBlock.Settings.create().nonOpaque()));
    public static final Block GUI_WATER = registerGuiBlock("gui_water",
            new Block(AbstractBlock.Settings.create().nonOpaque()));
    public static final Block GUI_GRASS = registerGuiBlock("gui_grass",
            new Block(AbstractBlock.Settings.create().nonOpaque()));


    private static Block registerBlock(String name, Block block, Item.Settings settings){
        registerBlockItem(name, block, settings);
        return Registry.register(Registries.BLOCK, Identifier.of(PocketDimensions.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block, Item.Settings settings){
        Registry.register(Registries.ITEM, Identifier.of(PocketDimensions.MOD_ID, name),
                new BlockItem(block, settings));
    }

    private static Block registerGuiBlock(String name, Block block){
        return Registry.register(Registries.BLOCK, Identifier.of(PocketDimensions.MOD_ID, name), block);
    }

    public static void registerModBlocks(){
        PocketDimensions.LOGGER.info("Registering Mod Blocks for " + PocketDimensions.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> {

        });
    }
}

