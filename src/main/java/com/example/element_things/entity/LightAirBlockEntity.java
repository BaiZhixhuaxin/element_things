package com.example.element_things.entity;

import com.example.element_things.util.dynamic_light.DynamicLight;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LightAirBlockEntity extends BlockEntity {
    public LightAirBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityType.LIGHT_AIR_BLOCK_ENTITY_TYPE, pos, state);
    }
    public static void tick(World world,BlockPos pos,BlockState state,LightAirBlockEntity be){
        if(!DynamicLight.shouldBeReplaced(pos,world)){
            world.setBlockState(pos, Blocks.AIR.getDefaultState());
        }
    }
}
