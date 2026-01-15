package net.nimbu.thaumaturgy.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;

public class ModSoundEvents {

    public static final SoundEvent MAGIC_CHARGE = registerSoundEvent("magic_charge");
    public static final SoundEvent MAGIC_CHIME = registerSoundEvent("magic_chime");

    /*
    BLOCK SOUND GROUP TEMPLATE:

    public static final SoundEvent MAGIC_BLOCK_BREAK = registerSoundEvent("magic_block_break");
    public static final SoundEvent MAGIC_BLOCK_STEP = registerSoundEvent("magic_block_step");
    public static final SoundEvent MAGIC_BLOCK_PLACE = registerSoundEvent("magic_block_place");
    public static final SoundEvent MAGIC_BLOCK_HIT = registerSoundEvent("magic_block_hit");
    public static final SoundEvent MAGIC_BLOCK_FALL = registerSoundEvent("magic_block_fall");

    public static final BlockSoundGroup MAGIC_BLOCK_SOUNDS = new BlockSoundGroup(1f, 1f,
            MAGIC_BLOCK_BREAK, MAGIC_BLOCK_STEP, MAGIC_BLOCK_PLACE, MAGIC_BLOCK_HIT, MAGIC_BLOCK_FALL);
     */

    private static SoundEvent registerSoundEvent(String name){
        Identifier id = Identifier.of(Thaumaturgy.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds(){
        Thaumaturgy.LOGGER.info("Registering mod sounds for "+Thaumaturgy.MOD_ID);
    }
}
