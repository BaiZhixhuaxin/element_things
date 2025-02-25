package com.example.element_things.entity.client;

import com.example.element_things.ElementThingsMod;
import com.example.element_things.entity.custom.IceEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

import net.minecraft.util.Identifier;

public class IceEntityRenderer extends MobEntityRenderer<IceEntity,IceEntityModel<IceEntity>> {
    public static final Identifier TEXTURE = Identifier.of(ElementThingsMod.MOD_ID,"textures/entity/ice_entity.png");

    public IceEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new IceEntityModel<>(ctx.getPart(ModModelLayers.ICE_ENTITY)),0.5f);
    }


    @Override
    public Identifier getTexture(IceEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(IceEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
