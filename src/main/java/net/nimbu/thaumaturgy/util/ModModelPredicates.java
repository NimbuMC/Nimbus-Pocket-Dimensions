package net.nimbu.thaumaturgy.util;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;
import net.nimbu.thaumaturgy.component.ModDataComponentTypes;
import net.nimbu.thaumaturgy.item.ModItems;

public class ModModelPredicates {
    public static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(ModItems.WAND, Identifier.of(Thaumaturgy.MOD_ID,"portal"),
                (stack, world, entity, seed) -> stack.get(ModDataComponentTypes.SPELL_FLASH_TIMER)!=null ? 1f : 0f);
        //item stack, world, entity or seed can be used

        registerCustomBow(ModItems.WAND_ARROW);
    }

    private static void registerCustomBow(Item item) {
        //Code for these is found in "ModelPredicateProviderRegistry" - use this for examples of how to animate items
        ModelPredicateProviderRegistry.register(item, Identifier.ofVanilla("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getActiveItem() != stack ? 0.0F : (stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft()) / 20.0F;
            }
        });
        ModelPredicateProviderRegistry.register(
                item,
                Identifier.ofVanilla("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
        );
    }
}
