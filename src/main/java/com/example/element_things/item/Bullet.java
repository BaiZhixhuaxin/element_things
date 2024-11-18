package com.example.element_things.item;

import com.example.element_things.ElementThingsMod;
import com.example.element_things.entity.BulletEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Bullet extends Item {
    public Bullet(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient){
            BulletEntity bulletEntity = new BulletEntity(ElementThingsMod.BULLET_ENTITY_TYPE,world);
            Vec3d eye = user.getRotationVec(0.0f).normalize().multiply(0.3f);
            Vec3d pos = user.getEyePos().add(eye);
            bulletEntity.setPos(pos.getX(),pos.getY(),pos.getZ());
            bulletEntity.setVelocity(eye.multiply(15));
            world.spawnEntity(bulletEntity);
        }
        return super.use(world, user, hand);
    }
}
