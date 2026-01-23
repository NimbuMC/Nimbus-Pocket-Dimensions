package net.nimbu.thaumaturgy.enchantment;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.enchantment.custom.ThundercallEnchantmentEffect;

public class ModEnchantmentEffects {

    public static MapCodec<? extends EnchantmentEntityEffect> THUNDERCALL =
            registerEntityEffect("thundercall", ThundercallEnchantmentEffect.CODEC);

    private static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(String name,
                                                                                    MapCodec<? extends EnchantmentEntityEffect> codec){
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(Thaumaturgy.MOD_ID, name), codec);
    }

    public static void registerEnchantmentEffects() {
        Thaumaturgy.LOGGER.info("Registering mod enchantments effects "+Thaumaturgy.MOD_ID);
    }
}
