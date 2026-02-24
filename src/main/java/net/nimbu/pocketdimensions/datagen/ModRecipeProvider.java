package net.nimbu.pocketdimensions.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.nimbu.pocketdimensions.block.ModBlocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.DIMENSION_CUSTOMIZER)
                .pattern("BEB")
                .pattern("BCB")
                .input('E', Items.END_CRYSTAL)
                .input('B', Blocks.STONE_BRICKS)
                .input('C', Blocks.CHISELED_STONE_BRICKS)
                .criterion(hasItem(Items.END_CRYSTAL), conditionsFromItem(Items.END_CRYSTAL)) //adds criteria to learn crafting recipe
                .offerTo(recipeExporter);



//        //========================================
//        List<ItemConvertible> PITCH_BLACK_SMELTABLES = List.of(
//                Blocks.BLACK_CONCRETE);
//
//        offerSmelting(recipeExporter, PITCH_BLACK_SMELTABLES, RecipeCategory.MISC, ModBlocks.PITCH_BLACK_BLOCK, 5f, 50, "pitch_black");
//        //offerBlasting(recipeExporter, PITCH_BLACK_SMELTABLES, RecipeCategory.MISC, ModItems.CHISEL, 5f, 20, "pitch_black");
//
//        //allows for the combining of 9 of the same item to craft another
//        offerReversibleCompactingRecipes(recipeExporter, RecipeCategory.BUILDING_BLOCKS, Items.BLACK_DYE, RecipeCategory.DECORATIONS, ModBlocks.PITCH_BLACK_BLOCK);
//        //========================================
//
//
//
//
//
//
//        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.WAND)
//                .pattern(" S")
//                .pattern("P ")
//                .input('S', Items.STICK)
//                .input('P', ModItems.PIXIE_DUST)
//                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK)) //adds criteria to learn crafting recipe
//                .offerTo(recipeExporter);
//        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.STAFF)
//                .pattern("  S")
//                .pattern(" S ")
//                .pattern("P  ")
//                .input('S', Items.STICK)
//                .input('P', ModItems.PIXIE_DUST)
//                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK)) //adds criteria to learn crafting recipe
//                .offerTo(recipeExporter);
//        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.SLAYER_STAFF)
//                .pattern("  G")
//                .pattern(" B ")
//                .pattern("P  ")
//                .input('G', Items.GOLD_INGOT)
//                .input('B', Items.BLAZE_ROD)
//                .input('P', ModItems.PIXIE_DUST)
//                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK)) //adds criteria to learn crafting recipe
//                .offerTo(recipeExporter);
//        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.BUTTERFLY_STAFF)
//                .pattern("  S")
//                .pattern(" C ")
//                .pattern("P  ")
//                .input('S', Items.STICK)
//                .input('C', Items.PURPLE_CARPET)
//                .input('P', ModItems.PIXIE_DUST)
//                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK)) //adds criteria to learn crafting recipe
//                .offerTo(recipeExporter);
//
//        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.PURPLE_MAGIC_MUSHROOM)
//                .input(Items.BROWN_MUSHROOM)
//                .input(ModItems.PIXIE_DUST)
//                .criterion(hasItem(ModItems.PIXIE_DUST), conditionsFromItem(ModItems.PIXIE_DUST))
//                .offerTo(recipeExporter);
//        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.MAGENTA_MAGIC_MUSHROOM)
//                .input(Items.RED_MUSHROOM)
//                .input(ModItems.PIXIE_DUST)
//                .criterion(hasItem(ModItems.PIXIE_DUST), conditionsFromItem(ModItems.PIXIE_DUST))
//                .offerTo(recipeExporter);
//
//        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.MAGIC_MUSHROOM_STEW)
//                .input(Items.BOWL)
//                .input(ModBlocks.PURPLE_MAGIC_MUSHROOM)
//                .input(ModBlocks.MAGENTA_MAGIC_MUSHROOM)
//                .criterion(hasItem(ModBlocks.PURPLE_MAGIC_MUSHROOM), conditionsFromItem(ModBlocks.PURPLE_MAGIC_MUSHROOM))
//                .criterion(hasItem(ModBlocks.MAGENTA_MAGIC_MUSHROOM), conditionsFromItem(ModBlocks.MAGENTA_MAGIC_MUSHROOM))
//                .offerTo(recipeExporter);
//
//
//        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.PITCH_BLACK_BLOCK, 5)
//                .input(Items.BLACK_DYE)
//                .criterion(hasItem(Items.BLACK_DYE), conditionsFromItem(Items.BLACK_DYE))
//                .offerTo(recipeExporter, Identifier.of(Thaumaturgy.MOD_ID, "pitch_black_block_from_black_dye")); //the identifier prevents overlap of crafting recipes - default identifier is crafting result

    }

}
