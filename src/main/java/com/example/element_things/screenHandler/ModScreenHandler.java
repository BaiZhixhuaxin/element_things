package com.example.element_things.screenHandler;

import com.example.element_things.ElementThingsMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandler {
    public static final ScreenHandlerType<StoveScreenHandler> STOVE_SCREEN_HANDLER = register("stove_block",StoveScreenHandler::new);
    public static final ScreenHandlerType<AnimalInventoryScreenHandler> ANIMAL_INVENTORY_SCREEN_HANDLER = register("animal_inventory",AnimalInventoryScreenHandler::new);

    public static <T extends ScreenHandler> ScreenHandlerType<T> register(String id,ScreenHandlerType.Factory<T> factory){
        return Registry.register(Registries.SCREEN_HANDLER, Identifier.of(ElementThingsMod.MOD_ID,id),new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }
    public static void registerScreenHandler(){

    }
}
