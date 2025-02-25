package com.example.element_things.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class DeleteItemKey {
    public static final String KEY_CATEGORY_ELEMENT = "key.category.element_things";
    public static final String KEY_DELETE = "key.element_things.delete";
    public static KeyBinding deleteKey;
    public static int key;
    public static void registerKeyInputs(){
        ClientTickEvents.END_CLIENT_TICK.register(minecraftClient -> {
        });
    }
    public static void register(){
        deleteKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_DELETE,
                InputUtil.Type.KEYSYM,
                key = GLFW.GLFW_KEY_H,
                KEY_CATEGORY_ELEMENT
        ));
        registerKeyInputs();
    }
    public static boolean isDeleteKeyPressed(){
        return deleteKey.isPressed();
    }
}
