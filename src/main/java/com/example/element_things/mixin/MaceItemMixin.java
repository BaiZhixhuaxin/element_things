package com.example.element_things.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MaceItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MaceItem.class)
public abstract class MaceItemMixin extends Item {
    public MaceItemMixin(Settings settings) {
        super(settings);
    }
    @Inject(method = "postHit",at= @At(value = "INVOKE", target = "Lnet/minecraft/item/MaceItem;knockbackNearbyEntities(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)V"))
    private void addFallingBlock(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfoReturnable<Boolean> cir){
        World world = attacker.getWorld();
        BlockPos pos = target.getBlockPos().down(1);
        for(int i = -1;i <= 1;i++){
            for(int j = -1;j <= 1;j++){
                if(i == 0 && j == 0) continue;
                BlockPos check = pos.east(i).north(j);
                BlockState state = world.getBlockState(check);
                if(state.isIn(BlockTags.AIR) || state.isOf(Blocks.LAVA) || state.isOf(Blocks.WATER)) continue;
                world.setBlockState(check,Blocks.AIR.getDefaultState());
                FallingBlockEntity.spawnFromBlock(world,check,state).setVelocity(0,1,0);
            }
        }
        System.out.println(true);
    }
}
