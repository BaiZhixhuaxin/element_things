package com.example.element_things.entity.entityRnederer;

import com.example.element_things.ElementThingsMod;
import com.example.element_things.entity.BulletEntity;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class BulletRenderer extends EntityRenderer<BulletEntity> {
    public BulletRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }
    public static final Identifier pos = Identifier.of(ElementThingsMod.MOD_ID,"textures/item/bullet.png");

    @Override
    public Identifier getTexture(BulletEntity entity) {
        return pos;
    }

    @Override
    public void render(BulletEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if(entity.tickCounter < 1) return;
        RenderSystem.enableBlend();
        matrices.push();
        matrices.scale(0.1f,0.1f,0.1f);
        matrices.multiply(dispatcher.getRotation());
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(pos));
        buffer.vertex(matrix4f,-4,4,0).color(255,255,255,255).texture(0,0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(0,1,0);
        buffer.vertex(matrix4f,-4,-4,0).color(255,255,255,255).texture(0,1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(0,1,0);
        buffer.vertex(matrix4f,4,-4,0).color(255,255,255,255).texture(1,1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(0,1,0);
        buffer.vertex(matrix4f,4,4,0).color(255,255,255,255).texture(1,0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(0,1,0);
        matrices.pop();
        RenderSystem.disableBlend();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
