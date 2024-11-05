package com.example.element_things.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_ELEMENT = "key.category.element_things";
    public static final String KEY_OPEN_SCREEN = "key.element_things.open_screen";
    public static KeyBinding openScreenKey;
    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
        });
    }
    public static void register(){
        openScreenKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_SCREEN,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Z,
                KEY_CATEGORY_ELEMENT
        ));
        registerKeyInputs();
    }
    public static boolean screenKeyBeenPressed(){
        return openScreenKey.isPressed();
    }
}
