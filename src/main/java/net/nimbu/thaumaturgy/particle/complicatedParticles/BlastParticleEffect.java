/*package net.nimbu.thaumaturgy.particle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.*;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector3f;

public class BlastParticleEffect extends AbstractBlastParticleEffect {
    public static final Vector3f RED = Vec3d.unpackRgb(16711680).toVector3f();
    public static final BlastParticleEffect DEFAULT = new BlastParticleEffect(RED, 1.0F);
    public static final MapCodec<BlastParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codecs.VECTOR_3F.fieldOf("color").forGetter(effect -> effect.color), SCALE_CODEC.fieldOf("scale").forGetter(AbstractBlastParticleEffect::getScale)
                    )
                    .apply(instance, BlastParticleEffect::new)
    );
    public static final PacketCodec<RegistryByteBuf, BlastParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VECTOR3F, effect -> effect.color, PacketCodecs.FLOAT, AbstractBlastParticleEffect::getScale, BlastParticleEffect::new
    );
    private final Vector3f color;

    public BlastParticleEffect(Vector3f color, float scale) {
        super(scale);
        this.color = color;

        System.out.println("BlastParticleEffect ran.");
    }

    @Override
    public ParticleType<BlastParticleEffect> getType() {
        return ModParticleTypes.BLAST_PARTICLE;
    }

    public Vector3f getColor() {
        return this.color;
    }
}




*/




































/*
package net.nimbu.thaumaturgy.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.*;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class BlastParticleEffect implements ParticleEffect {




































    /*
    public static final float MIN_SCALE = 0.01F;
    public static final float MAX_SCALE = 4.0F;
    protected static final Codec<Float> SCALE_CODEC = Codec.FLOAT
            .validate(scale -> scale >= 0.01F && scale <= 4.0F ? DataResult.success(scale) : DataResult.error(() -> "Value must be within range [0.01;4.0]: " + scale));
    private final float scale;

    public final float velocity=0;

    public static final Vector3f RED = Vec3d.unpackRgb(16711680).toVector3f();
    public static final BlastParticleEffect DEFAULT = new BlastParticleEffect(RED, 1.0F);
    public static final MapCodec<BlastParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codecs.VECTOR_3F.fieldOf("color").forGetter(effect -> effect.color), SCALE_CODEC.fieldOf("scale").forGetter(BlastParticleEffect::getScale)
                    )
                    .apply(instance, BlastParticleEffect::new)
    );
    public static final PacketCodec<RegistryByteBuf, BlastParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VECTOR3F, effect -> effect.color, PacketCodecs.FLOAT, BlastParticleEffect::getScale, BlastParticleEffect::new
    );
    private final Vector3f color;

    public BlastParticleEffect(Vector3f color, float scale) {
        this.color = color;
        this.scale = MathHelper.clamp(scale, 0.01F, 4.0F);
    }



    @Override
    public ParticleType<DustParticleEffect> getType() {
        return ParticleTypes.DUST;
    }

    public Vector3f getColor() {
        return this.color;
    }





    public float getScale() {
        return this.scale;
    }



*/









/*
    protected static final Codec<Float> FLOAT_CODEC = Codec.FLOAT
            .validate(scale -> scale >= 0.01F && scale <= 4.0F ? DataResult.success(scale) : DataResult.error(() -> "Value must be within range [0.01;4.0]: " + scale));

    public static final MapCodec<BlastParticleEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codecs.POSITIVE_FLOAT.fieldOf("velocity").forGetter(effect -> effect.velocity)
                    )
                    .apply(instance, BlastParticleEffect::new)
    );
    public static final PacketCodec<RegistryByteBuf, BlastParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, effect -> effect.velocity, PacketCodecs.FLOAT, BlastParticleEffect::getVelocity, BlastParticleEffect::new
    );
    private final float velocity;

    private float getVelocity(){
        return this.velocity;
    }

    public BlastParticleEffect(ClientWorld clientWorld, double x, double y, double z,
                               SpriteProvider spriteProvider, double xSpeed, double ySpeed, double zSpeed) {

        super(clientWorld, x, y, z, 0, 0, 0);

        this.velocityX=0;
        this.velocityY=0.2;
        this.velocityZ=0;

        this.velocity=0;


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
            return new BlastParticleEffect(world, x, y, z, this.spriteProvider, velocityX, velocityY, velocityZ);
        }
    }

 */
