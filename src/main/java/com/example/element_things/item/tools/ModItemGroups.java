package com.example.element_things.item.tools;

import com.example.element_things.ElementThingsMod;
import com.example.element_things.block.ModBlocks;
import com.example.element_things.item.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> ELEMENT_GROUP = register1("element_group");

    public static void registerItemGroups(){
        Registry.register(Registries.ITEM_GROUP,ELEMENT_GROUP,ItemGroup.create(ItemGroup.Row.TOP,-1).displayName(Text.translatable("itemGroup.element_things.element_group")).icon(() -> new ItemStack(ModItems.INDICATOR))
                .entries((displayContext, entries) -> {
                    entries.add(ModItems.FIRE_GEM);
                    entries.add(ModItems.FIRE_SWORD);
                    entries.add(ModItems.INDICATOR);
                    entries.add(ModItems.BULLET);
              //      entries.add(ModBlocks.STOVE.asItem());
                }).build());
    }


    public static RegistryKey<ItemGroup> register1(String id){
        return RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(ElementThingsMod.MOD_ID,id));
    }
}
