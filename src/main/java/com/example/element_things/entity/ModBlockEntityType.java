package com.example.element_things.entity;

import com.example.element_things.ElementThingsMod;
import com.example.element_things.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntityType {

    public static final BlockEntityType<LightAirBlockEntity> LIGHT_AIR_BLOCK_ENTITY_TYPE = register("light_air_entity_type",BlockEntityType.Builder.create(LightAirBlockEntity::new, ModBlocks.LIGHT_AIR_BLOCK).build());
    public static final BlockEntityType<StoveBlockEntity> STOVE_BLOCK_ENTITY = register("stove_block_entity",BlockEntityType.Builder.create(StoveBlockEntity::new,ModBlocks.STOVE).build());



    public static <T extends BlockEntityType<?>> T register(String id,T blockEntityType){
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(ElementThingsMod.MOD_ID,id),blockEntityType);
    }

    public static void register_block_entity(){}
}
