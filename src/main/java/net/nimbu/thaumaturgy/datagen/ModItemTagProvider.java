package net.nimbu.thaumaturgy.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.nimbu.thaumaturgy.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        getOrCreateTagBuilder(ItemTags.SWORDS) //enchantability tags are added to these automatically
                .add(ModItems.SPIRIT_SWORD);
        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(ModItems.SPIRIT_SHOVEL);
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(ModItems.SPIRIT_PICKAXE);
        getOrCreateTagBuilder(ItemTags.AXES)
                .add(ModItems.SPIRIT_AXE);
        getOrCreateTagBuilder(ItemTags.HOES)
                .add(ModItems.SPIRIT_HOE);


    }
}




