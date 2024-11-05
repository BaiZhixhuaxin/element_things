package com.example.element_things.util.flatten;

import net.minecraft.nbt.NbtCompound;

public class FlattenManager {
    public boolean beenFlattened = false;
    public boolean getFlattened(){
        return this.beenFlattened;
    }
    public void set(){
        this.beenFlattened = true;
    }
    public void readNbt(NbtCompound tag){
        if(tag.contains("beenFlattened",99)) this.beenFlattened = tag.getBoolean("beenFlattened");
    }
    public void writeNbt(NbtCompound tag){
        tag.putBoolean("beenFlattened",this.beenFlattened);
    }
}
