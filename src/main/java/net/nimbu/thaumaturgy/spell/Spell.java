package net.nimbu.thaumaturgy.spell;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.entity.ModEntities;
import net.nimbu.thaumaturgy.entity.custom.SpellPortalEntity;

public abstract class Spell{
    private final Identifier SPRITE;

    public Spell(Identifier spriteIdentifier){
        this.SPRITE=spriteIdentifier;
    }

    public Identifier getSpriteIdentifier(){
        return SPRITE;
    }

    public void castSpell(World world, PlayerEntity user, Hand hand){
        //calls functions that a particular spells needs
    }

    public void createProjectile(World world, PlayerEntity user){

    }
}

