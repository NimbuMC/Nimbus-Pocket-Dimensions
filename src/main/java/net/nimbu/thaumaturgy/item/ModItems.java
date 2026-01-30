package net.nimbu.thaumaturgy.item;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.item.custom.*;
import net.nimbu.thaumaturgy.entity.ModEntities;
import net.nimbu.thaumaturgy.item.custom.*;
import net.nimbu.thaumaturgy.spell.Spells;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    //-------------------------Tools--------------------------:

    public static final Item WAND = registerItem("wand", new WandItem(new Item.Settings()
            .maxCount(1)
            .maxDamage(131)
            .rarity(Rarity.RARE)
            .component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, false))); //never show enchantment glint

    public static final Item STAFF = registerItem("staff", new SpellcasterItem(new Item.Settings()
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

    //-------------------------Resources--------------------------

    public static final Item PIXIE_DUST = registerItem("pixie_dust",
            new Item(new Item.Settings()));

    public static final Item MAGIC_MUSHROOM_STEW = registerItem("magic_mushroom_stew",
            new Item(new Item.Settings()
                    .maxCount(1)
                    .food(ModFoodComponents.MAGIC_MUSHROOM_STEW)));


    //-------------------------Spawn Egss--------------------------
    public static final Item PIXIE_SPAWN_EGG = registerItem("pixie_spawn_egg",
            new SpawnEggItem(ModEntities.PIXIE, 0x00d5ff, 0xFFFFFF, new Item.Settings()));

    //=================================================================================================
    //item models because i dont know how to register models alone lmao THIS NEEDS REMOVAL
    public static final Item STAFF_SPELL_FLASH_0 = registerItem("staff_spell_flash_0",
            new Item(new Item.Settings()));
    public static final Item STAFF_SPELL_FLASH_1 = registerItem("staff_spell_flash_1",
            new Item(new Item.Settings()));
    public static final Item STAFF_SPELL_FLASH_2 = registerItem("staff_spell_flash_2",
            new Item(new Item.Settings()));
    //=================================================================================================



    //-------------------------Grimoires--------------------------
    public static final List<Item> GRIMOIRES = new ArrayList<>();


    //Priority Grimoires
    public static final Item POCKET_DIMENSION_GRIMOIRE = registerItem("grimoire_of_dimensional_expansion",
            new GrimoireItem(Spells.POCKET_DIMENSION, new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));

    public static final Item GRIMOIRE_OF_SOARING = registerItem("grimoire_of_soaring",
            new GrimoireItem(Spells.SOARING, new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));

    //Rare Grimoires
    public static final Item GRIMOIRE_OF_AERODETONATION = registerItem("grimoire_of_aerodetonation",
            new GrimoireItem(Spells.AERODETONATION, new Item.Settings().maxCount(1).rarity(Rarity.EPIC)));

    //Uncommon/Slightly Useful Grimoires
    public static final Item EFFECT_CLEANSING_GRIMOIRE = registerItem("grimoire_of_effect_cleansing",
            new GrimoireItem(Spells.EFFECT_CLEANSING, new Item.Settings().maxCount(1).rarity(Rarity.RARE)));


    //Common/Joke Grimoires
    public static final Item GRIMOIRE_OF_AMBIENCE_PIGSTEP= registerItem("grimoire_of_ambience_pigstep",
            new GrimoireItem(Spells.AMBIENCE_PIGSTEP, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item GRIMOIRE_OF_UNBAKING_BREAD = registerItem("grimoire_of_unbaking_bread",
            new GrimoireItem(Spells.UNBAKING_BREAD, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)));

    public static void registerGrimoires(){
        GRIMOIRES.add(POCKET_DIMENSION_GRIMOIRE);
        GRIMOIRES.add(GRIMOIRE_OF_SOARING);
        GRIMOIRES.add(GRIMOIRE_OF_AERODETONATION);
        GRIMOIRES.add(EFFECT_CLEANSING_GRIMOIRE);
        GRIMOIRES.add(GRIMOIRE_OF_AMBIENCE_PIGSTEP);
        GRIMOIRES.add(GRIMOIRE_OF_UNBAKING_BREAD);
    }














    private static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, Identifier.of(Thaumaturgy.MOD_ID, name), item);
    }

    public static void registerModItems(){
        Thaumaturgy.LOGGER.info("Registering mod items for "+Thaumaturgy.MOD_ID);

        //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).re
    }

}
