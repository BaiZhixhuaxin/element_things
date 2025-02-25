package com.example.element_things.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class FireSweepAttackParticle extends SpriteBillboardParticle {
    public static final Quaternionf QUATERNION = new Quaternionf(0f,-0.7f,0.7f,0.7f);
    private final SpriteProvider spriteProvider;
    protected FireSweepAttackParticle(ClientWorld clientWorld, double x, double y, double z,double d,SpriteProvider spriteProvider) {
        super(clientWorld, x, y ,z,0.0,0.0,0.0);
        this.velocityX = 0;
        this.velocityY = 0;
        this.velocityZ = 0;
        this.velocityMultiplier = 0;
        this.spriteProvider = spriteProvider;
        this.maxAge = 100;
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

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1,-1,0),new Vector3f(-1,1,0),new Vector3f(1,1,0),new Vector3f(-1,-1,0)};
        for(int i = 0;i < 4;i++){
            Vector3f vector3f = vector3fs[i];
            vector3f.rotate(QUATERNION);
        }
        float f7 = this.getMinU();
        float f8 = this.getMaxU();
        float f5 = this.getMinV();
        float f6 = this.getMaxV();
        vertexConsumer.vertex(vector3fs[0].x(),vector3fs[0].y(),vector3fs[0].z()).texture(f8,f6).color(this.red,this.green,this.blue,this.alpha).light(15728880);
        vertexConsumer.vertex(vector3fs[1].x(),vector3fs[1].y(),vector3fs[1].z()).texture(f8,f5).color(this.red,this.green,this.blue,this.alpha).light(15728880);
        vertexConsumer.vertex(vector3fs[2].x(),vector3fs[2].y(),vector3fs[2].z()).texture(f7,f5).color(this.red,this.green,this.blue,this.alpha).light(15728880);
        vertexConsumer.vertex(vector3fs[3].x(),vector3fs[3].y(),vector3fs[3].z()).texture(f7,f6).color(this.red,this.green,this.blue,this.alpha).light(15728880);
        super.buildGeometry(vertexConsumer,camera,tickDelta);
    }
}
