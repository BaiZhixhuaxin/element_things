package com.example.element_things.mixin;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.util.BufferAllocator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BufferBuilder.class)
public interface BufferBuilderAccess {
    @Accessor("vertexCount")
    int getVertexCount();
    @Accessor("allocator")
    BufferAllocator getAllocator();
    @Accessor("drawMode")
    VertexFormat.DrawMode getDrawMode();
    @Accessor("format")
    VertexFormat getFormat();
}
