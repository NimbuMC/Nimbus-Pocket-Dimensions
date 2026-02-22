package net.nimbu.pocketdimensions.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.nimbu.pocketdimensions.PocketDimensions;

public class ModParticleTypes {

    public static final SimpleParticleType GATEWAY_PROJECTILE_PARTICLE =
            registerParticle("gateway_projectile_particle", FabricParticleTypes.simple(true)); //will always spawn, even if particles are turned off in settings




    private static SimpleParticleType registerParticle(String name, SimpleParticleType simpleParticleType){
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(PocketDimensions.MOD_ID, name), simpleParticleType);
    }
    public static void registerParticles(){
        PocketDimensions.LOGGER.info("Registering particles for "+ PocketDimensions.MOD_ID);
    }
}
