package net.nimbu.thaumaturgy.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.item.custom.WandItem;

public class ModItems {

    public static final Item WAND = registerItem("wand", new WandItem(new Item.Settings()
            .maxCount(1).maxDamage(131)
            .rarity(Rarity.RARE)
            .component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false))); //never show enchantment glint

    public static final Item Staff = registerItem("staff", new Item(new Item.Settings()
            .maxCount(1)
            .maxDamage(131)
            .rarity(Rarity.RARE)));


    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Thaumaturgy.MOD_ID, name), item);
    }

    public static void registerModItems(){
        Thaumaturgy.LOGGER.info("Registering mod items for "+Thaumaturgy.MOD_ID);

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).re
    }

}
