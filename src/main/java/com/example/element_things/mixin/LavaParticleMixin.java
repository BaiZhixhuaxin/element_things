package com.example.element_things.mixin;

import net.minecraft.client.particle.LavaEmberParticle;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LavaEmberParticle.class)
public abstract class LavaParticleMixin extends SpriteBillboardParticle {
    protected LavaParticleMixin(ClientWorld clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
    }
    @Inject(method = "<init>",at=@At("RETURN"))
    private void set(ClientWorld clientWorld, double d, double e, double f, CallbackInfo ci){
        this.gravityStrength = 0;
    }
}
