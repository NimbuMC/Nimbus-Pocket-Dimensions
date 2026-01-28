package net.nimbu.thaumaturgy.item;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;
import net.nimbu.thaumaturgy.item.custom.GrimoireItem;

public class ModItemColours {
    public static void register() {
        ColorProviderRegistry.ITEM.register(
                (stack, tintIndex) -> {
                    Item item = stack.getItem();
                    //if (item instanceof GrimoireItem grimoireItem) {
                    //    return grimoireItem.getColor(tintIndex);
                    //}
                    return 0xFFFFFF; //returns white by default
                },
                ModItems.POCKET_DIMENSION_GRIMOIRE,
                ModItems.EFFECT_CLEANSING_GRIMOIRE
        );
    }
}
