/*package net.nimbu.thaumaturgy.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.MathHelper;

public abstract class AbstractBlastParticleEffect implements ParticleEffect {
    public static final float MIN_SCALE = 0.01F;
    public static final float MAX_SCALE = 4.0F;
    protected static final Codec<Float> SCALE_CODEC = Codec.FLOAT
            .validate(scale -> scale >= 0.01F && scale <= 4.0F ? DataResult.success(scale) : DataResult.error(() -> "Value must be within range [0.01;4.0]: " + scale));
    private final float scale;

    public AbstractBlastParticleEffect(float scale) {
        this.scale = MathHelper.clamp(scale, 0.01F, 4.0F);
    }

    public float getScale() {
        return this.scale;
    }
}
*/