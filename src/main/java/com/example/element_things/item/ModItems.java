package com.example.element_things.item;

import com.example.element_things.ElementThingsMod;
import com.example.element_things.component.ModComponent;
import com.example.element_things.item.tools.FireGemToolMaterial;
import com.example.element_things.item.tools.FireSword;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item FIRE_GEM = registerItem("fire_gem",new FireGem(new Item.Settings().maxCount(64)));
    public static final Item FIRE_SWORD = registerItem("fire_sword",new FireSword(new FireGemToolMaterial(),new Item.Settings().maxDamage(512).attributeModifiers(SwordItem.createAttributeModifiers(new FireGemToolMaterial(),9,-2.0f))));
    public static final Item INDICATOR = registerItem("indicator",new Indicator(new Item.Settings().component(ModComponent.INDICATOR_X,0).component(ModComponent.INDICATOR_Y,0).component(ModComponent.INDICATOR_Z,0)));
    public static final Item QUIVER = registerItem("quiver",new Quiver(new Item.Settings().maxCount(1).component(ModComponent.ARROW_AMOUNT,0)));
    public static final Item BULLET  = registerItem("bullet",new Bullet(new Item.Settings().maxCount(64)));
    public static final Item ENTITY_ITEM = registerItem("entity_item",new EntityItem(new Item.Settings().maxCount(1).component(ModComponent.ENTITY_MESSAGE, NbtComponent.DEFAULT)));







    private static Item registerItem(String id, Item item){
        return  Registry.register(Registries.ITEM,Identifier.of(ElementThingsMod.MOD_ID,id),item);
    }


    public static void registerModItems(){

    }
}
