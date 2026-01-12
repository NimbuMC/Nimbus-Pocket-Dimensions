package net.nimbu.thaumaturgy.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.*;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.block.custom.PocketDimensionPortal;
import net.nimbu.thaumaturgy.block.custom.RevisualisingTable;
import net.nimbu.thaumaturgy.block.custom.Wellspring;

public class ModBlocks {

    public static final Block PITCH_BLACK_BLOCK = registerBlock("pitch_black_block",
            new Block(AbstractBlock.Settings.create().strength(0.2f)));

    public static final Block REVISUALISING_TABLE = registerBlock("revisualising_table",
            new RevisualisingTable(AbstractBlock.Settings.create()
                    .mapColor(MapColor.BLUE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresTool()
                    .luminance(state -> 7)
                    .strength(3.0F, 1200.0F)));


    public static final Block WELLSPRING = registerBlock("wellspring",
            new Wellspring(AbstractBlock.Settings.create()
                    .strength(2f).requiresTool().luminance(state -> state.get(Wellspring.CLICKED)? 15 : 0)));

    public static final Block POCKET_DIMENSION_PORTAL =
            Registry.register(
                    Registries.BLOCK,
                    Identifier.of("thaumaturgy", "pocket_dimension_portal"),
                    new PocketDimensionPortal(
                            AbstractBlock.Settings.copy(Blocks.END_GATEWAY)
                    )
            );





    private static Block registerBlock(String name, Block block){
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Thaumaturgy.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block){
        Registry.register(Registries.ITEM, Identifier.of(Thaumaturgy.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks(){
        Thaumaturgy.LOGGER.info("Registering Mod Blocks for " + Thaumaturgy.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(fabricItemGroupEntries -> {

        });
    }
}
//EnchantingTableBlock
//Blocks

