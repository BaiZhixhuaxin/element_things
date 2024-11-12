package com.example.element_things.util.zoom;

import com.example.element_things.event.ZoomKey;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

public enum Zoom {
    INSTANCE;
    private final double defaultLevel = 3;
    private Double currentLevel;
    public void onMouseScroll(double amount){
        if(!(ZoomKey.isZoomKeyPressed() && MinecraftClient.getInstance().options.getPerspective().isFirstPerson()))
            return;
        if(currentLevel == null)
            currentLevel = defaultLevel;
        if(amount > 0){
            currentLevel *= 1.1;
        }
        else if(amount < 0){
            currentLevel *= 0.9;
        }
        currentLevel = MathHelper.clamp(currentLevel,1,50);
    }
    public double getCurrentLevel(){
        if(currentLevel == null) return defaultLevel;
        return currentLevel;
    }
}
