package com.example.element_things.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ZoomKey {
    public static final String KEY_CATEGORY_ELEMENT = "key.category.element_things";
    public static final String KEY_ZOOM = "key.element_things.zoom";
    public static KeyBinding zoomKey;
    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
        });
    }
    public static void register(){
        zoomKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_ZOOM,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                KEY_CATEGORY_ELEMENT
        ));
        registerKeyInputs();
    }
    public static boolean isZoomKeyPressed(){
        return zoomKey.isPressed();
    }
}
