package com.example.element_things.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SoundCheckKey {
    public static final String KEY_CATEGORY_ELEMENT = "key.category.element_things";
    public static final String KEY_SOUND_CHECK = "key.element_things.sound_check";
    public static KeyBinding soundCheckKey;
    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
        });
    }
    public static void register(){
        soundCheckKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
               KEY_SOUND_CHECK,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_M,
                KEY_CATEGORY_ELEMENT
        ));
        registerKeyInputs();
    }
    public static boolean isSoundKeyPressed(){
        return soundCheckKey.isPressed();
    }
}
