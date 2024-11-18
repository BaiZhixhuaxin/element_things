package com.example.element_things.mixin;

import com.example.element_things.enchantment.Enchantments;
import com.example.element_things.entity.BulletEntity;
import com.example.element_things.util.ModEnchantmentHelper;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PersistentProjectileEntity.class)
public abstract class PersistentProjectileEntityMixin extends ProjectileEntity {
    @Shadow public abstract void setVelocity(double x, double y, double z, float power, float uncertainty);

    @Shadow @Nullable private ItemStack weapon;

    public PersistentProjectileEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }
    @Inject(method = "getGravity",at=@At("HEAD"), cancellable = true)
    protected void getGravity(CallbackInfoReturnable<Double> cir){
        cir.setReturnValue(0.0);
    }
    @Inject(method = "tick",at=@At("TAIL"))
    protected void tickTrace(CallbackInfo ci){
        if(this.getOwner() instanceof LivingEntity owner && ModEnchantmentHelper.getLevel(owner.getStackInHand(Hand.MAIN_HAND),Enchantments.TRACING) > 0){
            Box box = new Box(this.getX() - 10,this.getY() - 10,this.getZ() - 5,this.getX() + 10,this.getY() + 10,this.getZ() + 5);
            LivingEntity target = this.getWorld().getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT,null,this.getX(),this.getY(),this.getZ(),box);
            if(target != null && target.isAlive() && !target.isSpectator() && target != owner){
                if(this.age > 5){
                    double x = target.getX() - this.getX();
                    double y = target.getEyeY() - this.getY();
                    double z = target.getZ() - this.getZ();
                    Vec3d vec3d = new Vec3d(x,y,z).normalize();
                    double pre = this.getVelocity().length();
                    this.setVelocity(vec3d.normalize().multiply(pre).multiply(0.99f));
                }
            }
        }
    }
    @WrapWithCondition(method = "onBlockHit",at= @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"))
    private boolean cancel(PersistentProjectileEntity instance, SoundEvent soundEvent, float v, float n){
        PersistentProjectileEntity entity = (PersistentProjectileEntity) this.getWorld().getEntityById(this.getId());
        return !(entity instanceof BulletEntity);
    }
    @WrapWithCondition(method = "onEntityHit",at= @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;playSound(Lnet/minecraft/sound/SoundEvent;FF)V"))
    private boolean cancel1(PersistentProjectileEntity instance, SoundEvent soundEvent, float v, float n){
        PersistentProjectileEntity entity = (PersistentProjectileEntity) this.getWorld().getEntityById(this.getId());
        return !(entity instanceof BulletEntity);
    }
//   @WrapWithCondition(method = "tick",at= @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
 //   private boolean condition(PersistentProjectileEntity instance, Vec3d vec3d){
 //      PersistentProjectileEntity entity = (PersistentProjectileEntity) this.getWorld().getEntityById(this.getId());
 //       return !(entity instanceof BulletEntity);
 //  }
 //  @WrapWithCondition(method = "onEntityHit",at= @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage//(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
 //   private boolean should(Entity instance, DamageSource source, float amount){
  //     PersistentProjectileEntity entity = (PersistentProjectileEntity) this.getWorld().getEntityById(this.getId());
  //     return !(entity instanceof BulletEntity);
   }
