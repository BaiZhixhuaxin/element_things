package com.example.element_things.entity;

import com.example.element_things.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class BulletEntity extends PersistentProjectileEntity {
    public BulletEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    public static final double MAX_DISTANCE = 50;
    public static final int MAX_AGE = 50;
    public int tickCounter;
    public double distanceTravelled;

    @Override
    protected ItemStack getDefaultItemStack() {
        return ModItems.BULLET.getDefaultStack();
    }

    @Override
    public void tick() {
        super.tick();
        if(++tickCounter >= MAX_AGE || distanceTravelled > MAX_DISTANCE){
            this.discard();
            return;
        }
        Vec3d motion = getMovement();
        double posX = getX() + motion.x;
        double posY  = getY() + motion.y;
        double posZ = getZ() + motion.z;
        distanceTravelled += motion.length();
   //     setPos(posX,posY,posZ);
        checkBlockCollision();
        Box box = new Box(this.getX() - 0.25,this.getY() - 0.25,this.getZ() + 0.25,this.getX() + 0.25,this.getY() - 0.25,this.getZ() + 0.25);
        Entity entity = this.getWorld().getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT,null,this.getX() - 0.25,this.getY() - 0.25,this.getZ() - 0.25,box);
        if(entity instanceof LivingEntity livingEntity && livingEntity.isAlive()){
        //    entity.damage(entity.getDamageSources().magic(), 20.0f);
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        if(blockHitResult .getType().equals(HitResult.Type.BLOCK) && !this.getWorld().getBlockState(blockHitResult.getBlockPos()).isIn(BlockTags.AIR)) this.discard();
    }

    @Override
    protected boolean canHit(Entity entity) {
        return !entity.equals(getOwner());
    }


}
