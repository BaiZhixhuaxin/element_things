package com.example.element_things.util.bucket_training;

import net.minecraft.nbt.NbtCompound;

public class BucketTrainingManager {
    public boolean isOn = false;
    public boolean get(){
        return isOn;
    }
    public void setOn(boolean isOn){
        this.isOn = isOn;
    }
    public void readNbt(NbtCompound tag){
        if(tag.contains("isOn",99)) this.isOn = tag.getBoolean("isOn");
    }
    public void writeNbt(NbtCompound tag){
        tag.putBoolean("isOn",this.isOn);
    }
}
