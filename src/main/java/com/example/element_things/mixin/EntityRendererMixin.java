package com.example.element_things.mixin;

import com.example.element_things.ElementThingsMod;
import com.example.element_things.util.IconRenderer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin<T extends Entity> {
    @Unique
    private static final Identifier fly_item = Identifier.of(ElementThingsMod.MOD_ID,"textures/fly_item.png");
    @Shadow @Final protected EntityRenderDispatcher dispatcher;
    @SuppressWarnings("unchecked")
    @Inject(method = "render",at=@At("HEAD"))
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if(entity instanceof PigEntity){
            MinecraftClient client = MinecraftClient.getInstance();
            RenderSystem.enableBlend();;
            matrices.push();
            matrices.translate(0.0f,3.0f,0.0f);
            matrices.scale(0.1f,0.1f,0.1f);
            matrices.multiply(this.dispatcher.getRotation());
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(fly_item));
            buffer.vertex(matrix4f,-8,8,0).color(255,255,255,255).texture(0,0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(0,1,0);
            buffer.vertex(matrix4f,-8,-8,0).color(255,255,255,255).texture(0,1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(0,1,0);
            buffer.vertex(matrix4f,8,-8,0).color(255,255,255,255).texture(1,1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(0,1,0);
            buffer.vertex(matrix4f,8,8,0).color(255,255,255,255).texture(1,0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(0,1,0);
            matrices.pop();
            RenderSystem.disableBlend();
        }
    }
}
