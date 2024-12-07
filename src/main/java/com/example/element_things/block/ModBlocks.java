package com.example.element_things.block;

import com.example.element_things.ElementThingsMod;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block LIGHT_AIR_BLOCK = addBlock("light_air",new LightAirBlock(AbstractBlock.Settings.copy(Blocks.AIR).luminance((state) -> 14).ticksRandomly()),false);
    public static final Block STOVE = addBlock("stove_block",new StoveBlock(AbstractBlock.Settings.copy(Blocks.COBBLESTONE)),true);


    public static Block addBlock(String id,Block block,boolean hasItem){
            if(hasItem) registerBlockItems(id,block);
        return Registry.register(Registries.BLOCK, Identifier.of(ElementThingsMod.MOD_ID, id), block);
    }
    public static void registerBlockItems(String id,Block block){
        Registry.register(Registries.ITEM,Identifier.of(ElementThingsMod.MOD_ID,id),new BlockItem(block,new Item.Settings()));
    }


    public static void registerBlock(){

    }
}
