package net.nimbu.thaumaturgy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.block.ModBlocks;
import net.nimbu.thaumaturgy.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        List<ItemConvertible> PITCH_BLACK_SMELTABLES = List.of(
                Blocks.BLACK_CONCRETE);

        offerSmelting(recipeExporter, PITCH_BLACK_SMELTABLES, RecipeCategory.MISC, ModBlocks.PITCH_BLACK_BLOCK, 5f, 50, "pitch_black");
        //offerBlasting(recipeExporter, PITCH_BLACK_SMELTABLES, RecipeCategory.MISC, ModItems.CHISEL, 5f, 20, "pitch_black");

        //allows for the combining of 9 of the same item to craft another
        offerReversibleCompactingRecipes(recipeExporter, RecipeCategory.BUILDING_BLOCKS, Items.BLACK_DYE, RecipeCategory.DECORATIONS, ModBlocks.PITCH_BLACK_BLOCK);

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.WAND)
                .pattern(" S")
                .pattern("P ")
                .input('S', Items.STICK)
                .input('P', ModItems.PIXIE_DUST)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK)) //adds criteria to learn crafting recipe
                .offerTo(recipeExporter);


        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.PITCH_BLACK_BLOCK, 5)
                .input(Items.BLACK_DYE)
                .criterion(hasItem(Items.BLACK_DYE), conditionsFromItem(Items.BLACK_DYE))
                .offerTo(recipeExporter, Identifier.of(Thaumaturgy.MOD_ID, "pitch_black_block_from_black_dye")); //the identifier prevents overlap of crafting recipes - default identifier is crafting result

    }

}
