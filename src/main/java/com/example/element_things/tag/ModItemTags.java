package com.example.element_things.tag;

import com.example.element_things.ElementThingsMod;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModItemTags {
    public static final TagKey<Item> GLOWING_ITEM = of("glowing_item");


    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(ElementThingsMod.MOD_ID,id));
    }
    public static void registerItemTags(){}
}
