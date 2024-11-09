package com.example.element_things.block;

import com.example.element_things.ElementThingsMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block LIGHT_AIR_BLOCK = addBlock("light_air",new LightAirBlock(AbstractBlock.Settings.copy(Blocks.AIR).luminance((state) -> 14).ticksRandomly()));


    public static Block addBlock(String id,Block block){
        return Registry.register(Registries.BLOCK, Identifier.of(ElementThingsMod.MOD_ID, id), block);
    }


    public static void registerBlock(){

    }
}
