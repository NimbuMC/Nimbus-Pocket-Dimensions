package net.nimbu.thaumaturgy.enchantment;

import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EnchantmentTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.enchantment.custom.ThundercallEnchantmentEffect;

public class ModEnchantments {

    public static final RegistryKey<Enchantment> THUNDERCALL =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Thaumaturgy.MOD_ID, "thundercall"));

    public static void bootstrap(Registerable<Enchantment> registerable){
        var enchantments = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        var items = registerable.getRegistryLookup(RegistryKeys.ITEM);

        //see definition for parameters
        register(registerable, THUNDERCALL, Enchantment.builder(Enchantment.definition(
                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                items.getOrThrow(ItemTags.SWORD_ENCHANTABLE),
                5,
                2,
                Enchantment.leveledCost(5,7),
                Enchantment.leveledCost(25,9),
                2,
                AttributeModifierSlot.MAINHAND))
                .exclusiveSet(enchantments.getOrThrow(EnchantmentTags.DAMAGE_EXCLUSIVE_SET))
                .addEffect(EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.ATTACKER, EnchantmentEffectTarget.VICTIM,
                        new ThundercallEnchantmentEffect())); //incompatible with other damage enchantments
    }

    private static void register(Registerable<Enchantment> registry,
                                 RegistryKey<Enchantment> key, Enchantment.Builder builder){
        registry.register(key, builder.build(key.getValue()));
    }
}
