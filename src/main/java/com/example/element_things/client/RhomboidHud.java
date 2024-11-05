package com.example.element_things.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

public class RhomboidHud implements HudRenderCallback {
    private static float totalTickDelta;
    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {

        totalTickDelta += renderTickCounter.getTickDelta(true);
        MatrixStack matrixStack = drawContext.getMatrices();
        matrixStack.push();
        float rotation = totalTickDelta / 50f % 360;
        matrixStack.multiply(RotationAxis.NEGATIVE_Z.rotation(rotation),20f,40f,5f);
        Matrix4f matrix4f = drawContext.getMatrices().peek().getPositionMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder builder = tessellator.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
        builder.vertex(matrix4f,20,20,5).color(0xFF414141);
        builder.vertex(matrix4f,5,40,5).color(0xFFFF0000);
        builder.vertex(matrix4f,35,40,5).color(0xFFFF0000);
        builder.vertex(matrix4f,20,60,5).color(0xFF414141);
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.setShaderColor(1.0f,1.0f,1.0f,1.0f);
        BufferRenderer.drawWithGlobalProgram(builder.end());
        matrixStack.pop();
    }
}
