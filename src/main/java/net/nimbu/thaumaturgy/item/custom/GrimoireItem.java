package net.nimbu.thaumaturgy.item.custom;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.nimbu.thaumaturgy.spell.Spell;

import java.util.Map;

public class GrimoireItem extends Item {
    private static final Map<Spell, GrimoireItem> GRIMOIRES = Maps.<Spell, GrimoireItem>newIdentityHashMap();
    private final int primaryColor;
    private final int secondaryColor;

    public GrimoireItem(Spell spell, Settings settings) {
        super(settings);
        this.primaryColor=spell.getPrimaryColour();
        this.secondaryColor=spell.getSecondaryColour();
        GRIMOIRES.put(spell, this);
    }

    public int getColor(int tintIndex) {
        return tintIndex == 0 ? this.primaryColor : this.secondaryColor;
    }

    public static Iterable<GrimoireItem> getAll() {
        return Iterables.unmodifiableIterable(GRIMOIRES.values());
    }
}
