package com.example.element_things.mixin;

import com.example.element_things.enchantment.Enchantments;
import com.example.element_things.util.ModEnchantmentHelper;
import com.google.common.collect.Sets;
import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.block.BlockState;
import net.minecraft.component.ComponentHolder;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.LinkedList;
import java.util.Set;

@Mixin(ItemStack.class)
public abstract class PostMineMixin implements ComponentHolder, FabricItemStack {
    @Unique
    private static final int max_block = 125;
    @Unique
    @Inject(method = "postMine",at=@At("RETURN"))
    private void mining(World world, BlockState state, BlockPos pos, PlayerEntity miner, CallbackInfo ci){
        if(!world.isClient){
            ItemStack stack = miner.getStackInHand(Hand.MAIN_HAND);
            if(ModEnchantmentHelper.getLevel(stack, Enchantments.CHAIN_MINING) > 0){
                if(stack.isSuitableFor(state) || !state.isToolRequired()){
                    int blocks = 1;
                    Set<BlockPos> visited = Sets.newHashSet(pos);
                    LinkedList<BlockPos> candidates = new LinkedList<>();
                    addNeighbors(candidates,pos);
                    while (blocks < max_block && !candidates.isEmpty()){
                        BlockPos blockPos = candidates.poll();
                        BlockState state1 = world.getBlockState(blockPos);
                        if(state1.equals(state) && visited.add(blockPos) && stack.getMaxDamage() - stack.getDamage() > 1){
                            blocks++;
                            world.breakBlock(blockPos,true,miner);
                            addNeighbors(candidates,blockPos);
                            stack.damage(1,miner, EquipmentSlot.MAINHAND);
                        }
                        else if(stack.getMaxDamage() - stack.getDamage() <= 1) break;
                    }
                    System.out.println(blocks);
                }
            }
        }
    }
    @Unique
    private static void addNeighbors(LinkedList<BlockPos> candidates,BlockPos source){
        BlockPos up = source.up();
        BlockPos down = source.down();
        candidates.add(up);
        candidates.add(down);
        BlockPos[] blockPositions = new BlockPos[]{up,source,down};
        for(BlockPos pos : blockPositions){
            candidates.add(pos.west());
            candidates.add(pos.east());
            candidates.add(pos.north());
            candidates.add(pos.south());
            candidates.add(pos.north().west());
            candidates.add(pos.north().east());
            candidates.add(pos.south().west());
            candidates.add(pos.south().east());
        }
    }
}
