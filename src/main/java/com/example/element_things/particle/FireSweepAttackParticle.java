package com.example.element_things.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

public class FireSweepAttackParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    protected FireSweepAttackParticle(ClientWorld clientWorld, double x, double y, double z,double d,SpriteProvider spriteProvider) {
        super(clientWorld, x, y ,z,0.0,0.0,0.0);
        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;
        this.velocityMultiplier = 0;
        this.spriteProvider = spriteProvider;
        this.maxAge = 20;
        float f = this.random.nextFloat() * 0.6F + 0.4F;
        this.red = f;
        this.green = f;
        this.blue = f;
        this.scale = 1.0F - (float)d * 0.5F;
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_LIT;
    }
    public int getBrightness(float tint) {
        return 15728880;
    }

    @Override
    public void tick() {
        super.tick();
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.setSpriteForAge(this.spriteProvider);
        }
    }
    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new FireSweepAttackParticle(clientWorld, d, e, f, g, this.spriteProvider);
        }
    }
}
