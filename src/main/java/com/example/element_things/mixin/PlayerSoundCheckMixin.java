package com.example.element_things.mixin;

import com.example.element_things.event.SoundCheckKey;
import com.example.element_things.util.SoundRMS;
import com.example.element_things.util.SoundRMSCalculate;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

@Mixin(PlayerEntity.class)
public abstract class PlayerSoundCheckMixin extends LivingEntity {
    @Shadow public abstract void sendMessage(Text message, boolean overlay);

    protected PlayerSoundCheckMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Unique
    final int bufferSize = 1024;
    @Unique
    final AudioFormat format = new AudioFormat(44100,16,1,true,true);
    @Unique
    final DataLine.Info info = new DataLine.Info(TargetDataLine.class,format);
    @Unique
    private static TargetDataLine line = null;
    @Unique
    private static int time = 0;
    @Unique
    private static byte[] buffer;
    @Unique
    private static ByteArrayOutputStream out;
    @Inject(method = "tick",at=@At("HEAD"))
    private void sound(CallbackInfo ci) {
        if (!this.getWorld().isClient) {
            if (SoundCheckKey.isSoundKeyPressed()) {
                if(time == 0){
                    try{
                        line = (TargetDataLine) AudioSystem.getLine(info);
                        line.open(format);
                        line.start();
                        buffer = new byte[bufferSize];
                        out = new ByteArrayOutputStream();
                    }catch (LineUnavailableException e){
                        e.printStackTrace();
                    }
                }
                time++;
                int byteRead = line.read(buffer,0,buffer.length);
                out.write(buffer,0,byteRead);
                SoundRMS.SoundRMS = SoundRMSCalculate.value(buffer);
            }
            else if(time > 0 && !SoundCheckKey.isSoundKeyPressed()){
                time = 0;
                SoundRMS.SoundRMS = 0;
                if(line != null) {
                    line.stop();
                    line.close();
                }
            }
        }
    }
}
