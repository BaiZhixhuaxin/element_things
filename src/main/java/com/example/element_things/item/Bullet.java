package com.example.element_things.item;

import com.example.element_things.ElementThingsMod;
import com.example.element_things.entity.AmmoEntity;
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
            user.setNoGravity(false);
        }
        return super.use(world, user, hand);
    }
}
