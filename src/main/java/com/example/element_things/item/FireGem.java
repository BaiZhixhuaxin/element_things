package com.example.element_things.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class FireGem extends Item {
    public FireGem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class,new Box(user.getX()-4,user.getY()-4,user.getZ()-4,user.getX()+4,user.getY()+4,user.getZ()+4), e -> !e.isPlayer());
        if(list.toArray().length > 0) {
            LivingEntity livingEntity = list.get(0);
            Vec3d vec3d = livingEntity.getEyePos().subtract(user.getEyePos()).normalize();
            Vec3d vec3d1 = new Vec3d(vec3d.getX(),0,vec3d.getZ()).normalize();
            float yaw;
            double degrees = Math.toDegrees(Math.asin(-vec3d1.x));
            if(vec3d1.z>= 0) yaw = (float) degrees;
            else if(vec3d1.z < 0 && vec3d1.x >=0) yaw = (float) -(degrees + 180 );
            else yaw = (float)(180 - degrees);
            float pitch = (float) -Math.toDegrees(Math.asin(vec3d.y));
            user.setAngles(yaw,pitch);
        }
       return super.use(world, user, hand);
    }
}
