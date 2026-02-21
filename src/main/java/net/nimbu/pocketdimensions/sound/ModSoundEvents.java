package net.nimbu.pocketdimensions.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;

public class ModSoundEvents {

    public static final SoundEvent MAGIC_CHARGE = registerSoundEvent("magic_charge");
    public static final SoundEvent MAGIC_CHIME = registerSoundEvent("magic_chime");

    private static SoundEvent registerSoundEvent(String name){
        Identifier id = Identifier.of(PocketDimensions.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds(){
        PocketDimensions.LOGGER.info("Registering mod sounds for "+ PocketDimensions.MOD_ID);
    }
}
