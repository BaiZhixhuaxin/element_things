package com.example.element_things.client;

import com.example.element_things.event.SoundCheckKey;
import com.example.element_things.util.SoundRMS;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;

public class RMSHud implements HudRenderCallback {
    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter renderTickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        PlayerEntity player = client.player;
        if(player != null){
            int width = client.getWindow().getScaledWidth();
            int height = client.getWindow().getScaledHeight();
            if(SoundCheckKey.isSoundKeyPressed()){
                drawContext.drawText(textRenderer, String.format("%.2f", SoundRMS.SoundRMS), width / 2, height - 50,16777215,true);
            }
        }
    }
}
