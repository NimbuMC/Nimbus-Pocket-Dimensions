package net.nimbu.pocketdimensions.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.block.ModBlocks;

public class ModItemGroups {

    public static final ItemGroup POCKET_DIMENSIONS_ITEM_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(PocketDimensions.MOD_ID, "pocketdimensions_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.GATEWAY_WAND))
                    .displayName(Text.translatable("itemgroup.pocketdimensions.pocketdimensions_items"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.GATEWAY_WAND);
                        entries.add(ModBlocks.GATEWAY);
                        entries.add(ModBlocks.DIMENSION_CUSTOMIZER);

                    }).build());

    public static void registerItemGroups() {

        PocketDimensions.LOGGER.info("Registering Item Groups for " + PocketDimensions.MOD_ID);
    }
}