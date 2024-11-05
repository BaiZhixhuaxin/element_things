package com.example.element_things.particle;

import com.example.element_things.ElementThingsMod;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static final SimpleParticleType FIRE_SWEEP = register("fire_sweep", FabricParticleTypes.simple());



    public static void init(){

    }
    private static SimpleParticleType register(String name, SimpleParticleType type) {
        return (SimpleParticleType) Registry.register(Registries.PARTICLE_TYPE, Identifier.of(ElementThingsMod.MOD_ID,name), type);
    }
}
