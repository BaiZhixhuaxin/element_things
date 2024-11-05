package com.example.element_things.mixin;

import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.particle.SweepAttackParticle;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SweepAttackParticle.class)
public abstract class SweepParticleMixin extends SpriteBillboardParticle {
    protected SweepParticleMixin(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }
    @Inject(method = "<init>",at=@At("RETURN"))
    private void setMax(ClientWorld world, double x, double y, double z, double d, SpriteProvider spriteProvider, CallbackInfo ci){
        this.maxAge = 100;
        this.gravityStrength = 10.0f;
    }
}
