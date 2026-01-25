package net.nimbu.thaumaturgy.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.item.custom.*;
import net.nimbu.thaumaturgy.entity.ModEntities;

public class ModItems {

    //tools:

    public static final Item WAND = registerItem("wand", new WandItem(new Item.Settings()
            .maxCount(1)
            .maxDamage(131)
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
            new WandArrowItem(new Item.Settings()
                    .maxCount(1)
                    .maxDamage(131)));

    public static final Item WAND_PORTAL = registerItem("wand_portal",
            new WandPortalItem(new Item.Settings()
                    .maxCount(1)
                    .maxDamage(131)));

    public static final Item INVISIBLE_HELMET = registerItem("invisible_helmet",
            new ModArmorItem(ModArmorMaterials.INVISIBLE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));
    public static final Item INVISIBLE_CHESTPLATE = registerItem("invisible_chestplate",
            new ModArmorItem(ModArmorMaterials.INVISIBLE_ARMOR, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(15))));
    public static final Item INVISIBLE_LEGGINGS = registerItem("invisible_leggings",
            new ModArmorItem(ModArmorMaterials.INVISIBLE_ARMOR, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(15))));
    public static final Item INVISIBLE_BOOTS = registerItem("invisible_boots",
            new ModArmorItem(ModArmorMaterials.INVISIBLE_ARMOR, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(15))));
    /*public static final Item INVISIBLE_BODY = registerItem("invisible_helmet",
            new ArmorItem(ModArmourMaterials.INVISIBLE_ARMOR, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(15))));*/

    public static final Item TOME_OF_EXPANSION = registerItem("tome_of_expansion",
            new TomeItem(new Item.Settings()
                    .maxCount(1),0));
    public static final Item TOME_OF_SKIES = registerItem("tome_of_skies",
            new TomeItem(new Item.Settings()
                    .maxCount(1),1));


    //resources:

    public static final Item PIXIE_DUST = registerItem("pixie_dust",
            new Item(new Item.Settings()));

    public static final Item MAGIC_MUSHROOM_STEW = registerItem("magic_mushroom_stew",
            new Item(new Item.Settings()
                    .maxCount(1)
                    .food(ModFoodComponents.MAGIC_MUSHROOM_STEW)));


    //spawn eggs:
    public static final Item PIXIE_SPAWN_EGG = registerItem("pixie_spawn_egg",
            new SpawnEggItem(ModEntities.PIXIE, 0x00d5ff, 0xFFFFFF, new Item.Settings()));


    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Thaumaturgy.MOD_ID, name), item);
    }

    public static void registerModItems(){
        Thaumaturgy.LOGGER.info("Registering mod items for "+Thaumaturgy.MOD_ID);

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).re
    }

}
