package com.example.element_things.network;

import com.example.element_things.access.FlattenManagerAccess;
import com.example.element_things.network.packet.EntityC2SPacket;
import com.example.element_things.network.packet.FlattenS2CPacket;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class ServerPacket {
    public static void init(){
        PayloadTypeRegistry.playS2C().register(FlattenS2CPacket.PACKET_ID,FlattenS2CPacket.PACKET_CODEC);
        PayloadTypeRegistry.playC2S().register(EntityC2SPacket.PACKET_ID,EntityC2SPacket.PACKET_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(EntityC2SPacket.PACKET_ID,(payload, context) ->{
            int EntityId = payload.entityId();
            World world = context.player().getWorld();
            LivingEntity entity = (LivingEntity) world.getEntityById(EntityId);
            context.server().execute(() -> {
                if (entity != null) {
                    ServerPlayNetworking.send(context.player(),new FlattenS2CPacket(((FlattenManagerAccess) entity).getFlattenManager().beenFlattened));
                }
            });
        } );
    }
}
