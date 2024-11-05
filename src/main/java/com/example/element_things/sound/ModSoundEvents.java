package com.example.element_things.sound;

import com.example.element_things.ElementThingsMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSoundEvents {
    public static final SoundEvent HIT_SOUND = register("hit_sound");
    public static final SoundEvent TRANSFORM = register("transform");
    public static final SoundEvent RED_LIGHT = register("red_light");

    private static SoundEvent register(String name){
        return Registry.register(Registries.SOUND_EVENT, Identifier.of(ElementThingsMod.MOD_ID,name),SoundEvent.of(Identifier.of(ElementThingsMod.MOD_ID,name)));
    }
    public static void registerSoundEvents(){}
}
