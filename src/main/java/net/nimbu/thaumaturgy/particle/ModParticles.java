package net.nimbu.thaumaturgy.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.thaumaturgy.Thaumaturgy;

public class ModParticles {

    public static final SimpleParticleType MAGIC_PARTICLE =
            registerParticle("magic_particle", FabricParticleTypes.simple(true)); //will always spawn, even if particles are turned off in settings

    private static SimpleParticleType registerParticle(String name, SimpleParticleType simpleParticleType){
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Thaumaturgy.MOD_ID, name), simpleParticleType);
    }

    public static void registerParticles(){
        Thaumaturgy.LOGGER.info("Registering particles for "+Thaumaturgy.MOD_ID);
    }
}
