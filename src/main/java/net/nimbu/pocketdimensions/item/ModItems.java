package net.nimbu.pocketdimensions.item;

import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;
import net.nimbu.pocketdimensions.item.custom.*;

public class ModItems {


    public static final Item GATEWAY_WAND = registerItem("gateway_wand",
            new GatewayWandItem(new Item.Settings()
                    .maxCount(1)
                    .maxDamage(131)));
    public static final Item EXPANSION_GEM = registerItem("expansion_gem",
            new DimensionExpanderItem(new Item.Settings()));


    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(PocketDimensions.MOD_ID, name), item);
    }
    public static void registerModItems(){
        PocketDimensions.LOGGER.info("Registering mod items for "+ PocketDimensions.MOD_ID);
    }

}
