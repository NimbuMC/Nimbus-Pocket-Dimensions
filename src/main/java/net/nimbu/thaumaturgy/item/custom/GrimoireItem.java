package net.nimbu.thaumaturgy.item.custom;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.nimbu.thaumaturgy.spell.Spell;
import net.nimbu.thaumaturgy.spell.Spells;

import java.util.Map;

public class GrimoireItem extends Item {
    //private static final Map<Spell, GrimoireItem> GRIMOIRES = Maps.<Spell, GrimoireItem>newIdentityHashMap();
    private final int primaryColor;
    private final int secondaryColor;
    private final Spell spell;

    public GrimoireItem(Spell spell, Settings settings) {
        super(settings);
        this.primaryColor=spell.getPrimaryColour();
        this.secondaryColor=spell.getSecondaryColour();
        this.spell=spell;
    }

    public int getColor(int tintIndex) {
        return switch (tintIndex) {
            case 1 -> primaryColor;
            case 2 -> secondaryColor;
            default -> 0xFFFFFFFF;
        };
    }
}
