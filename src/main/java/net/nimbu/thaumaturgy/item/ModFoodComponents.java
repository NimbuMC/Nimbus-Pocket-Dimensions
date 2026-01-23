package net.nimbu.thaumaturgy.item;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.nimbu.thaumaturgy.effect.ModEffects;

public class ModFoodComponents {
    public static final FoodComponent MAGIC_MUSHROOM_STEW =
            new FoodComponent.Builder()
                    .statusEffect(new StatusEffectInstance(ModEffects.MAGICAL, 5000, 1), 1.0F)
                    .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 5000, 1), 1.0F)
                    .nutrition(6)
                    .saturationModifier(0.6F)
                    .usingConvertsTo(Items.BOWL)
                    .alwaysEdible()
                    .build();
}
