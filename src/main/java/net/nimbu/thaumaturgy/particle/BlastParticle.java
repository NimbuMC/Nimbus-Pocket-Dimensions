

package net.nimbu.thaumaturgy.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ShriekParticleEffect;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class BlastParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;

    public BlastParticle(ClientWorld clientWorld, double x, double y, double z,
                         SpriteProvider spriteProvider) {

        super(clientWorld, x, y, z);
        this.spriteProvider = spriteProvider;
        this.setSpriteForAge(spriteProvider);
        this.maxAge = 12 + this.random.nextInt(4);
        this.scale = 1.5F;
        //this.setBoundingBoxSpacing(1.0F, 1.0F);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }

    @Override
    public int getBrightness(float tint) {
        return 15728880;
    }

    @Override
    public void tick() {
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.setSpriteForAge(this.spriteProvider);
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new BlastParticle(clientWorld, d, e, f, this.spriteProvider);
        }
    }
}





































/*
package net.nimbu.thaumaturgy.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class BlastParticle extends SpriteBillboardParticle {
    private final float velocity=0;

    protected BlastParticle(
            ClientWorld clientWorld,
            double x, double y, double z,
            float velocity
    ) {
        super(clientWorld, x, y, z);

        this.velocityMultiplier=0.8f;
        this.maxAge=5;
        this.red=0.5f;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    protected int getBrightness(float tint) {
        return 255;
    }

    public static class Factory
            implements ParticleFactory<BlastParticleEffect> {

        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(
                BlastParticleEffect effect,
                ClientWorld world,
                double x, double y, double z,
                double vx, double vy, double vz
        ) {
            BlastParticle particle =
                    new BlastParticle(world, x, y, z, effect.velocity);
            particle.setSprite(this.spriteProvider);
            return particle;
        }
    }
}
*/