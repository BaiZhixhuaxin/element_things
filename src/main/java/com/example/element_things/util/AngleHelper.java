package com.example.element_things.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class AngleHelper {
    public static void setRotation(PlayerEntity player, Vec3d pos){
        Vec3d direction = pos.multiply(player.getEyePos()).normalize();
        Vec3d vec_xz = new Vec3d(direction.getX(),0,direction.getZ()).normalize();
        float yaw;
        double degrees = Math.toDegrees(Math.asin(-vec_xz.x));
        if(vec_xz.z>= 0) yaw = (float) degrees;
        else if(vec_xz.z < 0 && vec_xz.x >=0) yaw = (float) -(degrees + 180 );
        else yaw = (float)(180 - degrees);
        float pitch = (float) -Math.toDegrees(Math.asin(direction.y));
        player.setAngles(yaw,pitch);
    }
    public static double getAngle(Vec3d vec3d1,Vec3d vec3d2){
        double length1 = vec3d1.length();
        double length2 = vec3d2.length();
        double a = vec3d1.x * vec3d2.x + vec3d1.y * vec3d2.y + vec3d1.z * vec3d2.z;
        double cos_angle = a / (length1 * length2);
      //  double sita1 = Math.atan2(vec3d1.x, vec3d1.z);
      //  double sita2 = Math.atan2(vec3d2.x, vec3d2.z);
     //   double b = Math.PI;
     //   if(sita1 < 0) sita1 += 2 * b;
     //   if(sita2 < 0) sita2 += 2 * b;
        return Math.acos(cos_angle);
    }
}
