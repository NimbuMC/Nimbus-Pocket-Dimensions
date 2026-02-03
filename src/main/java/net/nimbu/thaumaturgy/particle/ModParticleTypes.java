package net.nimbu.thaumaturgy.particle;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;

import java.util.function.Function;

public class ModParticleTypes {

    public static final SimpleParticleType MAGIC_PARTICLE =
            registerParticle("magic_particle", FabricParticleTypes.simple(true)); //will always spawn, even if particles are turned off in settings

    public static final SimpleParticleType BLAST_PARTICLE =
            registerParticle("blast_particle", FabricParticleTypes.simple(true));

//    public static final ParticleType<BlastParticleEffect> BLAST_PARTICLE =
//            registerParticle("blast_particle", false, type -> BlastParticleEffect.CODEC, type -> BlastParticleEffect.PACKET_CODEC);


    private static SimpleParticleType registerParticle(String name, SimpleParticleType simpleParticleType){
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Thaumaturgy.MOD_ID, name), simpleParticleType);
    }


//    private static <T extends ParticleEffect> ParticleType<T> registerParticle(
//            String name,
//            boolean alwaysShow,
//            Function<ParticleType<T>, MapCodec<T>> codecGetter,
//            Function<ParticleType<T>, PacketCodec<? super RegistryByteBuf, T>> packetCodecGetter
//    ) {
//        return Registry.register(Registries.PARTICLE_TYPE, name, new ParticleType<T>(alwaysShow) {
//            @Override
//            public MapCodec<T> getCodec() {
//                return (MapCodec<T>)codecGetter.apply(this);
//            }
//
//           @Override
//           public PacketCodec<? super RegistryByteBuf, T> getPacketCodec() {
//                return (PacketCodec<? super RegistryByteBuf, T>)packetCodecGetter.apply(this);
//            }
//        });
//    }





    public static void registerParticles(){
        Thaumaturgy.LOGGER.info("Registering particles for "+Thaumaturgy.MOD_ID);
    }
}
