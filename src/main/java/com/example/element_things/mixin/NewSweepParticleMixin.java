package com.example.element_things.mixin;

import com.example.element_things.item.ModItems;
import com.example.element_things.particle.ModParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class NewSweepParticleMixin extends LivingEntity {
    protected NewSweepParticleMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "spawnSweepAttackParticles",at=@At("HEAD"), cancellable = true)
    public void spawnSweepAttackParticles(CallbackInfo ci) {
        ci.cancel();
    }
    @Inject(method = "attack",at= @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;spawnSweepAttackParticles()V"))
    public void attack(Entity target, CallbackInfo ci) {
        if(this.getStackInHand(Hand.MAIN_HAND).isOf(ModItems.FIRE_SWORD)){
            double d = -MathHelper.sin(this.getYaw() * 0.017453292F);
            double e = MathHelper.cos(this.getYaw() * 0.017453292F);
            if (this.getWorld() instanceof ServerWorld) {
                ((ServerWorld)this.getWorld()).spawnParticles(ModParticles.FIRE_SWEEP, this.getX() + d, this.getBodyY(0.5), this.getZ() + e, 0, d, 0.0, e, 0.0);
            }
        }
        else {
            double f = -MathHelper.sin(this.getYaw() * 0.017453292F);
            double g = MathHelper.cos(this.getYaw() * 0.017453292F);
            if (this.getWorld() instanceof ServerWorld) {
                ((ServerWorld) this.getWorld()).spawnParticles(ParticleTypes.SWEEP_ATTACK, this.getX() + f, this.getBodyY(0.5), this.getZ() + g, 0, f, 0.0, g, 0.0);
            }
        }
    }
}
