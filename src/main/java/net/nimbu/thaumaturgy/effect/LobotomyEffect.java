package net.nimbu.thaumaturgy.effect;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.nimbu.thaumaturgy.renderer.LobotomyVisualState;
import net.nimbu.thaumaturgy.sound.ModSoundEvents;

import java.util.List;
import java.util.Random;

public class LobotomyEffect extends StatusEffect {
    private int TickCounter = 0;
    protected LobotomyEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!(entity instanceof PlayerEntity player)) return true;
        if (!player.getWorld().isClient) return true;

        TickCounter++;

        if (TickCounter == 40) {
            TickCounter = 0;

            Random random = new Random();
            player.playSound(
                    ModSoundEvents.LOBOTOMY,
                    1.0f,
                    (float) (1 + (Math.sin(random.nextFloat() * Math.TAU) / 4f))
            );

            LobotomyVisualState.trigger();
        }

        return true;
    }


    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
