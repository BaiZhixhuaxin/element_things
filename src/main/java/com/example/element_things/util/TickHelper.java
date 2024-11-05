package com.example.element_things.util;

public class TickHelper {
    public static int tick = 0;
    public static void add_tick(){
        if(tick < 179){
            tick++;
        }
        else {
            tick = 0;
        }
    }
}
