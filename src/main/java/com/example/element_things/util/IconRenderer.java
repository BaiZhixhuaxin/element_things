package com.example.element_things.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;

public class IconRenderer {
    public static <T extends Entity> void renderIcon(Identifier identifier, MatrixStack matrixStack, int x, int y, int w, int h, float u0, float u1, float v0, float v1, float alpha){
        matrixStack.push();
        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, identifier);
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        BufferBuilder builder = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        builder.vertex(matrix4f,x,y + h ,0).texture(u0,v1);
        builder.vertex(matrix4f,x + w,y + h ,0).texture(u1,v1);
        builder.vertex(matrix4f,x + w,y,0).texture(u1,v0);
        builder.vertex(matrix4f,x,y ,0).texture(u0,v0);
        BufferRenderer.drawWithGlobalProgram(builder.end());
        matrixStack.pop();
    }
}
