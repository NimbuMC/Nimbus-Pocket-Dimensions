package net.nimbu.thaumaturgy.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;

import net.nimbu.thaumaturgy.block.ModBlocks;

public class ModItemGroups {

    public static final ItemGroup THAUMATURGY_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Thaumaturgy.MOD_ID, "thaumaturgy_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.WAND))
                    .displayName(Text.translatable("itemgroup.thaumaturgy.thaumaturgy_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.WAND);
                        entries.add(ModItems.WAND_ARROW);
                        entries.add(ModItems.WAND_PORTAL);
                        entries.add(ModItems.STAFF);
                        entries.add(ModItems.SPIRIT_SWORD);
                        entries.add(ModItems.SPIRIT_SHOVEL);
                        entries.add(ModItems.SPIRIT_PICKAXE);
                        entries.add(ModItems.SPIRIT_AXE);
                        entries.add(ModItems.SPIRIT_HOE);
                        entries.add(ModItems.HAMMER);
                        entries.add(ModBlocks.REVISUALISING_TABLE);
                        entries.add(ModBlocks.PITCH_BLACK_BLOCK);
                        entries.add(ModBlocks.WELLSPRING);
                        entries.add(ModItems.PIXIE_DUST);
                        entries.add(ModItems.INVISIBLE_HELMET);
                        entries.add(ModItems.INVISIBLE_CHESTPLATE);
                        entries.add(ModItems.INVISIBLE_LEGGINGS);
                        entries.add(ModItems.INVISIBLE_BOOTS);
                        entries.add(ModItems.TOME_OF_EXPANSION);
                        entries.add(ModItems.TOME_OF_SKIES);
                        entries.add(ModItems.PIXIE_SPAWN_EGG);
                        entries.add(ModBlocks.DOORWAY);
                        entries.add(ModBlocks.PURPLE_MAGIC_MUSHROOM);
                        entries.add(ModBlocks.MAGENTA_MAGIC_MUSHROOM);
                        entries.add(ModItems.MAGIC_MUSHROOM_STEW);

                    }).build());


    public static void registerItemGroups() {

        Thaumaturgy.LOGGER.info("Registering Item Groups for " + Thaumaturgy.MOD_ID);
    }
}