package net.nimbu.thaumaturgy.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;

public class ModEffects {

    public static final RegistryEntry<StatusEffect> SLIMEY = registerStatusEffect("slimey",
            new SlimeyEffect(StatusEffectCategory.NEUTRAL, 0x36ebab)
                    .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            Identifier.of(Thaumaturgy.MOD_ID, "slimey"), -0.25f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final RegistryEntry<StatusEffect> REVERSE_VELOCITY = registerStatusEffect("block_hop",
            new BlockHopEffect(StatusEffectCategory.NEUTRAL, 0x0c1f0c)
                    .addAttributeModifier(EntityAttributes.GENERIC_STEP_HEIGHT,
                            Identifier.of(Thaumaturgy.MOD_ID, "block_hop"), 0.5f,
                            EntityAttributeModifier.Operation.ADD_VALUE));

    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect){
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(Thaumaturgy.MOD_ID, name), statusEffect);
    }

    public static void registerEffects(){
        Thaumaturgy.LOGGER.info("Registering mod effects for "+Thaumaturgy.MOD_ID);


    }
}
