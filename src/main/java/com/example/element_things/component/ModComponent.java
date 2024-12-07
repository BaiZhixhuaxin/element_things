package com.example.element_things.component;

import com.example.element_things.ElementThingsMod;
import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponent {
    public static final ComponentType<Integer> MOD_DATA = Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(ElementThingsMod.MOD_ID,"mod_data"),ComponentType.<Integer>builder().codec(Codec.INT).build());
    public static final ComponentType<Integer> INDICATOR_X = Registry.register(Registries.DATA_COMPONENT_TYPE,Identifier.of(ElementThingsMod.MOD_ID,"indicator_x"),ComponentType.<Integer>builder().codec(Codec.INT).build());
    public static final ComponentType<Integer> INDICATOR_Y = Registry.register(Registries.DATA_COMPONENT_TYPE,Identifier.of(ElementThingsMod.MOD_ID,"indicator_y"),ComponentType.<Integer>builder().codec(Codec.INT).build());
    public static final ComponentType<Integer> INDICATOR_Z = Registry.register(Registries.DATA_COMPONENT_TYPE,Identifier.of(ElementThingsMod.MOD_ID,"indicator_z"),ComponentType.<Integer>builder().codec(Codec.INT).build());
    public static final ComponentType<Integer> ARROW_AMOUNT = Registry.register(Registries.DATA_COMPONENT_TYPE,Identifier.of(ElementThingsMod.MOD_ID,"arrow_amount"),ComponentType.<Integer>builder().codec(Codec.INT).build());
    public static final ComponentType<Integer> FOOD_SPOILAGE = Registry.register(Registries.DATA_COMPONENT_TYPE,Identifier.of(ElementThingsMod.MOD_ID,"food_spoilage"),ComponentType.<Integer>builder().codec(Codec.INT).build());


    public static void register(){
    }
}
