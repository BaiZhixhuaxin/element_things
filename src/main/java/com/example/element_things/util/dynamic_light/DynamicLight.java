package com.example.element_things.util.dynamic_light;

import com.example.element_things.tag.ModItemTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class DynamicLight {
    public static boolean shouldBeReplaced(BlockPos pos, World world) {
        List<Entity> list = world.getEntitiesByClass(Entity.class, new Box(pos.getX() - 1.5, pos.getY() - 1.5, pos.getZ() - 1.5, pos.getX() + 1.5, pos.getY() + 1.5, pos.getZ() + 1.5), e -> e instanceof Entity);
        if (list.toArray().length > 0) {
            for (Entity entity : list) {
                if(entity instanceof LivingEntity || entity instanceof ItemEntity || entity instanceof TntEntity || entity instanceof ArrowEntity){
                    if(entity instanceof LivingEntity livingEntity){
                        if(!(livingEntity.getStackInHand(Hand.MAIN_HAND).isIn(ModItemTags.GLOWING_ITEM) || livingEntity.getStackInHand(Hand.OFF_HAND).isIn(ModItemTags.GLOWING_ITEM) || (livingEntity instanceof CreeperEntity creeper && creeper.isIgnited()))){
                            continue;
                        }
                        return true;
                    }
                    else if(entity instanceof ItemEntity itemEntity){
                        if(!itemEntity.getStack().isIn(ModItemTags.GLOWING_ITEM)){
                            continue;
                        }
                        return true;
                    }
                    else if(entity instanceof TntEntity) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public static boolean isOpened(){
        return true;
    }
}
