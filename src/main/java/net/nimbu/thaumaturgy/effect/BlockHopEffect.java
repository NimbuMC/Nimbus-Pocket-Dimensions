package net.nimbu.thaumaturgy.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class BlockHopEffect extends StatusEffect {
    protected BlockHopEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) { //called every tick
        //Vec3d freezeVec = new Vec3d(0, 0, 0);
        //entity.setVelocity(freezeVec);

        return super.applyUpdateEffect(entity, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
