package com.example.element_things.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

public class ModEnchantmentHelper {
    public static int getLevel(ItemStack stack, RegistryKey<Enchantment> enchantment){
        for(RegistryEntry<Enchantment> entry : stack.getEnchantments().getEnchantments()){
            if(entry.matchesKey(enchantment)){
                return EnchantmentHelper.getLevel(entry,stack);
            }
        }
        return 0;
    }
}
