package net.nimbu.thaumaturgy.spell;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public abstract class Spell {
    private final Identifier SPRITE;
    private final int PRIMARY_COLOUR;
    private final int SECONDARY_COLOUR;

    public Spell(Identifier spriteIdentifier, int primaryColour, int secondaryColour){
        this.SPRITE = spriteIdentifier;
        this.PRIMARY_COLOUR = primaryColour;
        this.SECONDARY_COLOUR = secondaryColour;
    }

    public Identifier getSpriteIdentifier(){
        return SPRITE;
    }

    public int getPrimaryColour(){
        return PRIMARY_COLOUR;
    }
    public int getSecondaryColour(){
        return SECONDARY_COLOUR;
    }


    public abstract void OnSpellEquip();
    public abstract void OnSpellUnequip();


    public abstract void castSpell(World world, PlayerEntity user, Hand hand); //calls functions that a particular spells needs

    public void renderReticle(World world, PlayerEntity user){}
    public void cancelRenderReticle(World world, PlayerEntity user){}

    public void createProjectile(World world, PlayerEntity user){

    }

    public abstract String toString();
}

