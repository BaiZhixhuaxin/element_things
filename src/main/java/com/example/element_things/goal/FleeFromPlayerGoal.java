package com.example.element_things.goal;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FleeFromPlayerGoal extends Goal {

    protected final PathAwareEntity mob;
    private final double slowSpeed;
    private final double fastSpeed;
    protected final float fleeDistance;
    @Nullable
    protected Path fleePath;
    private PlayerEntity player;
    public FleeFromPlayerGoal(PathAwareEntity entity,double slowSpeed,double fastSpeed,float fleeDistance){
        this.mob = entity;
        this.slowSpeed = slowSpeed;
        this.fastSpeed = fastSpeed;
        this.fleeDistance = fleeDistance;
    }

    @Override
    public boolean canStart() {
        Box box = new Box(mob.getX() - 7,mob.getY() - 7,mob.getZ() - 7,mob.getX() + 7,mob.getY() + 7, mob.getZ() + 7);
        List<LivingEntity> list = mob.getWorld().getEntitiesByClass(LivingEntity.class,box,e -> e instanceof LivingEntity);
        if(list.toArray().length > 0){
            for(LivingEntity entity : list){
                if(entity.isPlayer()) {
                    this.player = (PlayerEntity) entity;
                    Vec3d end = mob.getPos().subtract(player.getPos()).normalize().multiply(5).multiply(1.0f,0.0f,1.0f).add(mob.getPos());
                        this.fleePath = mob.getNavigation().findPathTo(end.x,end.y,end.z,0);
                    return true;
                }
            }
        }
        this.player = null;
        return false;
    }
    public void start() {
        mob.getNavigation().startMovingAlong(this.fleePath, this.slowSpeed);
    }
    public void tick() {
        if (this.mob.squaredDistanceTo(this.player) < 49.0) {
            this.mob.getNavigation().setSpeed(this.fastSpeed);
        } else {
            this.mob.getNavigation().setSpeed(this.slowSpeed);
        }

    }
    public void stop() {
        this.player = null;
    }
    public boolean shouldContinue() {
        return !mob.getNavigation().isIdle();
    }
}
