package net.nimbu.thaumaturgy.item;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.item.Item;
import net.nimbu.thaumaturgy.item.custom.GrimoireItem;

public class ModItemColours {
    public static void register() {
        ColorProviderRegistry.ITEM.register(
                (stack, tintIndex) -> {
                    Item item = stack.getItem();
                    if (item instanceof GrimoireItem grimoireItem) {
                        return 0xFF000000 | grimoireItem.getColor(tintIndex);
                    }
                    return 0xFFFFFFFF;
                },
                ModItems.GRIMOIRES.toArray(new Item[0])
        );
    }
}
