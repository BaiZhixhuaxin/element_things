package com.example.element_things.util.animation;

import net.minecraft.client.model.ModelPart;

import javax.swing.plaf.IconUIResource;
import java.util.Timer;
import java.util.TimerTask;

public class Animation {
    public  ModelPart modelPart1;
    public int tick = 0;
    public Animation(){
    }
    public void applyAnimation(){
        tick = 0;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(tick < 20){
                    tick++;
                    System.out.println(tick);
                }else {
                    timer.cancel();
                }
            }
        };
        timer.schedule(task,0,50);
    }
}
