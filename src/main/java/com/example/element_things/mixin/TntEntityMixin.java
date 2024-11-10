package com.example.element_things.mixin;

import com.example.element_things.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.TntEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TntEntity.class)
public abstract class TntEntityMixin extends Entity implements Ownable {
    public TntEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Inject(method = "tick",at=@At("HEAD"))
    private void tick(CallbackInfo ci){
        World world = this.getWorld();
        BlockState state = world.getBlockState(this.getBlockPos());
        BlockState state1 = world.getBlockState(this.getBlockPos().up(1));
        BlockState state2 = world.getBlockState(this.getBlockPos().up(2));
        if (state.isIn(BlockTags.AIR)) {
            world.setBlockState(this.getBlockPos(), ModBlocks.LIGHT_AIR_BLOCK.getDefaultState());
        } else if (state1.isIn(BlockTags.AIR) && !state.isOf(ModBlocks.LIGHT_AIR_BLOCK)) {
            world.setBlockState(this.getBlockPos().up(1), ModBlocks.LIGHT_AIR_BLOCK.getDefaultState());
        } else if (state2.isIn(BlockTags.AIR) && !state.isOf(ModBlocks.LIGHT_AIR_BLOCK) && !state1.isOf(ModBlocks.LIGHT_AIR_BLOCK)) {
            world.setBlockState(this.getBlockPos().up(2), ModBlocks.LIGHT_AIR_BLOCK.getDefaultState());
        }
    }
}