package net.nimbu.thaumaturgy.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public class BlastParticle extends SpriteBillboardParticle {
    public BlastParticle(ClientWorld clientWorld, double x, double y, double z,
                         SpriteProvider spriteProvider, double xSpeed, double ySpeed, double zSpeed) {

        super(clientWorld, x, y, z, 0, 0.2, 0);

        //this.velocityMultiplier=0.8f; //deceleration?
        this.maxAge=20;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider){
            this.spriteProvider=spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientWorld world,
                                       double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ) {
            return new MagicParticle(world, x, y, z, this.spriteProvider, velocityX, velocityY, velocityZ);
        }
    }
}