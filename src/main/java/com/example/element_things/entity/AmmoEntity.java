package com.example.element_things.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AmmoEntity extends Entity implements FlyingItemEntity {
    private int tick;

    public AmmoEntity(EntityType<? extends Entity> type, World world) {
        super(type, world);
    }



    @Override
    protected void initDataTracker(DataTracker.Builder builder) {

    }


    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public ItemStack getStack() {
        return new ItemStack(Items.DIAMOND);
    }

    @Override
    public void tick() {
        tick++;
        if(tick > 100) this.discard();
        super.tick();
        Box box = new Box(this.getX() - 0.5,this.getY() - 0.5,this.getZ() + 0.5,this.getX() + 0.5,this.getY() - 0.5,this.getZ() + 0.5);
        Entity entity = this.getWorld().getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT,null,this.getX() - 0.5,this.getY() - 0.5,this.getZ() - 0.5,box);
        if(entity instanceof LivingEntity livingEntity && livingEntity.isAlive() && !entity.isPlayer()){
            entity.damage(entity.getDamageSources().magic(), 20.0f);
            this.discard();
        }
        Vec3d motion = getMovement();
        double posX = getX() + motion.x;
        double posY  = getY() + motion.y;
        double posZ = getZ() + motion.z;
     //   setPos(posX,posY,posZ);
        this.updatePosition(posX,posY,posZ);
    }

    @Override
    public void setVelocity(Vec3d velocity) {
        super.setVelocity(velocity);
    }

    @Override
    protected MoveEffect getMoveEffect() {
        return MoveEffect.NONE;
    }

    @Override
    public boolean canHit() {
        return true;
    }
}
