package com.example.element_things.mixin;

import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WorldRenderer.class)
public interface BufferBuilderStorageMixin{
    @Accessor("bufferBuilders")
    BufferBuilderStorage get();
}
