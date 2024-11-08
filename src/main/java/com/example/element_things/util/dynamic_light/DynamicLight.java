package com.example.element_things.util.dynamic_light;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class DynamicLight {
    public static boolean shouldBeReplaced(BlockPos pos, World world) {
        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, new Box(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1), e -> e instanceof PlayerEntity);
        if (list.toArray().length > 0) {
            for (LivingEntity entity : list) {
                return entity.getStackInHand(Hand.MAIN_HAND).isOf(Items.TORCH) || entity.getStackInHand(Hand.OFF_HAND).isOf(Items.TORCH);
            }
        }
        return false;
    }
    public static boolean isOpened(){return false;}
}
