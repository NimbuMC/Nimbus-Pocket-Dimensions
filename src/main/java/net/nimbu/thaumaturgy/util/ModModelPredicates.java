package net.nimbu.thaumaturgy.util;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.item.ModItems;

public class ModModelPredicates {
    public static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(ModItems.WAND, Identifier.of(Thaumaturgy.MOD_ID,"portal"),
                (stack, world, entity, seed) -> stack.get(ModDataComponentTypes.SPELL_FLASH_TIMER)!=null ? 1f : 0f);
        //item stack, world, entity or seed can be used


    }
}
