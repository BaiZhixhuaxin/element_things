package com.example.element_things.effect;

import com.example.element_things.ElementThingsMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> BLUE_LIGHT = register("blue_light",new BlueLightEffect());
    public static final RegistryEntry<StatusEffect> RED_LIGHT = register("red_light",new RedLightEffect());

    public static RegistryEntry<StatusEffect> register(String name, StatusEffect effect){
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(ElementThingsMod.MOD_ID,name), effect);
    }
    public static void onInitialize(){}
}
