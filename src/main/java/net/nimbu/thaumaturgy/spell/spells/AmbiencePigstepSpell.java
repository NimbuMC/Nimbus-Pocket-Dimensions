package net.nimbu.thaumaturgy.spell.spells;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.nimbu.thaumaturgy.spell.Spell;

public class AmbiencePigstepSpell extends Spell {
    public AmbiencePigstepSpell() {
        super(Identifier.ofVanilla("textures/item/music_disc_pigstep.png"),
                0xFF723232,
                0xFFfdf55f);
    }

    @Override
    public void castSpell(World world, PlayerEntity user, Hand hand) {

        //Issues:
        // - may be in wrong sound category?
        // - directional audio is a bit weird, though this is just a minecraft music disc issue in general


        if (!world.isClient) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.MUSIC_DISC_PIGSTEP, SoundCategory.PLAYERS, 10.0f, 1.0f);
        }
    }
}
