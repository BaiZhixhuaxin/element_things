package com.example.element_things.block;

import com.example.element_things.entity.LightAirBlockEntity;
import com.example.element_things.entity.ModBlockEntityType;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LightAirBlock extends BlockWithEntity {
    public LightAirBlock(Settings settings) {
        super(settings);
    }


    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }


    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LightAirBlockEntity(pos,state);
    }

    @Override
    public MapCodec<LightAirBlock> getCodec() {
        return createCodec(LightAirBlock::new);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type,ModBlockEntityType.LIGHT_AIR_BLOCK_ENTITY_TYPE,((world1, pos, state1, blockEntity) -> LightAirBlockEntity.tick(world,pos,state1,blockEntity)));
    }

    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }
}
