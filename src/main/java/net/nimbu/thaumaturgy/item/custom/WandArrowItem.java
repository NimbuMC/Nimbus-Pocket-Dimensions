package net.nimbu.thaumaturgy.item.custom;

import net.minecraft.item.BowItem;

public class WandArrowItem extends BowItem {
    public WandArrowItem(Settings settings) {
        super(settings);
    }

    @Override
    public int getRange() {
        return 1;
    }
}
