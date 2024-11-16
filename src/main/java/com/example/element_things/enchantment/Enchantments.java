package com.example.element_things.enchantment;

import com.example.element_things.ElementThingsMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class Enchantments {

    public static final  RegistryKey<Enchantment> FROST = registerEnchantment("frost");
    public static final RegistryKey<Enchantment> ACCELERATION = registerEnchantment("acceleration");
    public static final RegistryKey<Enchantment> TRACING  = registerEnchantment("tracing");

    private static RegistryKey<Enchantment> registerEnchantment(String name){
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(ElementThingsMod.MOD_ID,name));
    }
    public static void register(){}
}
