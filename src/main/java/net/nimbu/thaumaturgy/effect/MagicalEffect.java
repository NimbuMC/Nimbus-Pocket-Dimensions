package net.nimbu.thaumaturgy.effect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.nimbu.thaumaturgy.sound.ModSoundEvents;

import java.util.List;
import java.util.Random;

public class MagicalEffect extends StatusEffect {
    private int TickCounter = 0;
    protected MagicalEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) { //called every tick

        if (MinecraftClient.getInstance() == null) return super.applyUpdateEffect(entity, amplifier);
        if(entity instanceof PlayerEntity player) {
            TickCounter++;
            Random random = new Random();
            if(TickCounter == 40)
            {
                TickCounter = 0;
                player.playSound(ModSoundEvents.LOBOTOMY, 1.0f, (float) (1 + (Math.sin(random.nextFloat() * Math.TAU)/4f)));
            }
            //if (random.nextInt(3) == 0) {
            //    List<SoundEvent> sounds = Registries.SOUND_EVENT.stream().filter(se -> !se.getId().getPath().contains("music")).toList();
            //    SoundEvent randomSound = sounds.get(player.getWorld().random.nextInt(sounds.size()));
            //    player.playSound(
            //            randomSound,
            //            1.0f,  // volume
            //            random.nextFloat() * 2.0F   // pitch
            //    );
            //}

        }

        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
