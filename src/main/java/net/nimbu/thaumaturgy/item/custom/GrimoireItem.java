package net.nimbu.thaumaturgy.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.spell.Spell;

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

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
/*
        if (!world.isClient) {
            int spellIndex=0;

            SpellEquipHandler data = (SpellEquipHandler) user;
            boolean equipped = data.getSpellEquipFlags(spellIndex);

            if(!equipped){
                data.setSpellEquipFlags(spellIndex,true);
                itemStack.decrement(1);
                return TypedActionResult.consume(itemStack);
            }
        }*/
        return TypedActionResult.success(itemStack);
    }

    public int getColor(int tintIndex) {
        return switch (tintIndex) {
            case 1 -> primaryColor;
            case 2 -> secondaryColor;
            default -> 0xFFFFFFFF;
        };
    }
}
