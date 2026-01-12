package net.nimbu.thaumaturgy.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.item.custom.HammerItem;
import net.nimbu.thaumaturgy.item.custom.WandArrowItem;
import net.nimbu.thaumaturgy.item.custom.WandItem;

public class ModItems {

    public static final Item WAND = registerItem("wand", new WandItem(new Item.Settings()
            .maxCount(1).maxDamage(131)
            .rarity(Rarity.RARE)
            .component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false))); //never show enchantment glint

    public static final Item STAFF = registerItem("staff", new Item(new Item.Settings()
            .maxCount(1)
            .maxDamage(131)
            .rarity(Rarity.RARE)));

    public static final Item SPIRIT_SWORD = registerItem("spirit_sword",
            new SwordItem(ModToolMaterials.SPIRIT, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ModToolMaterials.SPIRIT, 3, -2.4f)))); //default sword values
    public static final Item SPIRIT_PICKAXE = registerItem("spirit_pickaxe",
            new PickaxeItem(ModToolMaterials.SPIRIT, new Item.Settings()
                    .attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.SPIRIT, 1, -2.8f))));
    public static final Item SPIRIT_AXE = registerItem("spirit_axe",
            new AxeItem(ModToolMaterials.SPIRIT, new Item.Settings()
                    .attributeModifiers(AxeItem.createAttributeModifiers(ModToolMaterials.SPIRIT, 6, -3.2f))));
    public static final Item SPIRIT_SHOVEL = registerItem("spirit_shovel",
            new ShovelItem(ModToolMaterials.SPIRIT, new Item.Settings()
                    .attributeModifiers(ShovelItem.createAttributeModifiers(ModToolMaterials.SPIRIT, 1.5f, -3.0f))));
    public static final Item SPIRIT_HOE = registerItem("spirit_hoe",
            new HoeItem(ModToolMaterials.SPIRIT, new Item.Settings()
                    .attributeModifiers(HoeItem.createAttributeModifiers(ModToolMaterials.SPIRIT, 0, -3.0f))));

    public static final Item HAMMER = registerItem("hammer",
            new HammerItem(ToolMaterials.DIAMOND, new Item.Settings()
                    .attributeModifiers(PickaxeItem.createAttributeModifiers(ModToolMaterials.SPIRIT,7, -3.4f))));

    public static final Item WAND_ARROW = registerItem("wand_arrow",
            new WandArrowItem(new Item.Settings().maxDamage(500)));

    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Thaumaturgy.MOD_ID, name), item);
    }

    public static void registerModItems(){
        Thaumaturgy.LOGGER.info("Registering mod items for "+Thaumaturgy.MOD_ID);

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).re
    }

}
