package com.example.element_things.util;

import com.example.element_things.mixin.BufferBuilderAccess;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;


public class LineRenderer {
    private static VertexBuffer vertexBuffer;
    public static synchronized void render(WorldRenderContext context){
        if(vertexBuffer == null) {
            vertexBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
            if (context.camera().getFocusedEntity() instanceof PlayerEntity player && player.isSneaking()) {
                if(BlockPosList.list != null) {
                    if (BlockPosList.list.toArray().length > 0) {
                        for (BlockPos pos : BlockPosList.list) {
                            renderBlock(buffer, pos);
                            vertexBuffer.bind();
                            vertexBuffer.upload(build(buffer));
                            VertexBuffer.unbind();
                        }
                    }
                }
            }
        }
            if(vertexBuffer != null){
                Tessellator tessellator = Tessellator.getInstance();
                BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
                if (context.camera().getFocusedEntity() instanceof PlayerEntity player && player.isSneaking()) {
                    if(BlockPosList.list != null) {
                        if (BlockPosList.list.toArray().length > 0) {
                            for (BlockPos pos : BlockPosList.list) {
                                renderBlock(buffer, pos);
                            }
                            vertexBuffer.bind();
                            vertexBuffer.upload(build(buffer));
                            VertexBuffer.unbind();
                            Camera camera = context.camera();
                            Vec3d cameraPos = camera.getPos();
                            RenderSystem.depthMask(false);
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();
                            RenderSystem.applyModelViewMatrix();
                            MatrixStack stack = context.matrixStack();
                            if (stack != null) {
                                stack.push();
                            }
                            RenderSystem.setShader(GameRenderer::getPositionColorProgram);
                            RenderSystem.applyModelViewMatrix();;
                            RenderSystem.depthFunc(GL11.GL_ALWAYS);
                            context.projectionMatrix().lookAt(cameraPos.toVector3f(),cameraPos.toVector3f().add(camera.getHorizontalPlane()),camera.getVerticalPlane());
                            vertexBuffer.bind();
                            if (stack != null) {
                                vertexBuffer.draw(stack.peek().getPositionMatrix(),new Matrix4f(context.projectionMatrix()),RenderSystem.getShader());
                            }
                            VertexBuffer.unbind();
                            RenderSystem.depthFunc(GL11.GL_LEQUAL);
                            if (stack != null) {
                                stack.pop();
                            }
                            RenderSystem.applyModelViewMatrix();
                        }
                    }
                }
        }
    }
    private static void renderBlock(BufferBuilder buffer, BlockPos pos){
        final float size = 1.0f;
        final float red = 255 / 255F;
        final float green = 255 / 255F;
        final float blue = 255 / 255F;
        final float alpha = 1.0F;
        int x = pos.getX(),y = pos.getY(),z = pos.getZ();
        //顶部
        buffer.vertex(x,y + size,z).color(red,green,blue,alpha);
        buffer.vertex(x + size,y + size,z).color(red,green,blue,alpha);
        buffer.vertex(x + size,y + size,z).color(red,green,blue,alpha);
        buffer.vertex(x + size,y + size,z + size).color(red,green,blue,alpha);
        buffer.vertex(x + size,y + size,z + size).color(red,green,blue,alpha);
        buffer.vertex(x,y + size,z + size).color(red,green,blue,alpha);
        buffer.vertex(x,y + size,z + size).color(red,green,blue,alpha);
        buffer.vertex(x,y + size,z).color(red,green,blue,alpha);
        //底部
        buffer.vertex(x,y,z).color(red,green,blue,alpha);
        buffer.vertex(x + size,y,z).color(red,green,blue,alpha);
        buffer.vertex(x + size,y,z).color(red,green,blue,alpha);
        buffer.vertex(x + size,y,z + size).color(red,green,blue,alpha);
        buffer.vertex(x + size,y,z + size).color(red,green,blue,alpha);
        buffer.vertex(x,y,z + size).color(red,green,blue,alpha);
        buffer.vertex(x,y,z + size).color(red,green,blue,alpha);
        buffer.vertex(x,y,z).color(red,green,blue,alpha);
        //边缘1
        buffer.vertex(x + size,y,z + size).color(red,green,blue,alpha);
        buffer.vertex(x + size,y + size,z + size).color(red,green,blue,alpha);
        //边缘2
        buffer.vertex(x + size,y,z).color(red,green,blue,alpha);
        buffer.vertex(x + size,y + size,z).color(red,green,blue,alpha);
        //边缘3
        buffer.vertex(x,y,z + size).color(red,green,blue,alpha);
        buffer.vertex(x,y + size,z + size).color(red,green,blue,alpha);
        //边缘4
        buffer.vertex(x,y,z).color(red,green,blue,alpha);
        buffer.vertex(x,y + size,z).color(red,green,blue,alpha);
    }
    private static BuiltBuffer build(BufferBuilder buffer) {
        int vertexCount = ((BufferBuilderAccess) buffer).getVertexCount();
        BufferAllocator allocator = ((BufferBuilderAccess) buffer).getAllocator();
        VertexFormat.DrawMode drawMode = ((BufferBuilderAccess) buffer).getDrawMode();
        VertexFormat format = ((BufferBuilderAccess) buffer).getFormat();
        if (vertexCount == 0) {
            return null;
        } else {
            BufferAllocator.CloseableBuffer closeableBuffer = allocator.getAllocated();
            if (closeableBuffer == null) {
                return null;
            } else {
                int i = drawMode.getIndexCount(vertexCount);
                VertexFormat.IndexType indexType = VertexFormat.IndexType.smallestFor(vertexCount);
                return new BuiltBuffer(closeableBuffer, new BuiltBuffer.DrawParameters(format, vertexCount, i, drawMode, indexType));
            }
        }
    }
}
