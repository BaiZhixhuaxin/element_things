package com.example.element_things.network;

import com.example.element_things.access.AnimalInventoryAccess;
import com.example.element_things.access.FlattenManagerAccess;
import com.example.element_things.network.packet.DeleteC2SPacket;
import com.example.element_things.network.packet.EntityC2SPacket;
import com.example.element_things.network.packet.FlattenS2CPacket;
import com.example.element_things.network.packet.StealInventoryPacket;
import com.example.element_things.util.animal_inventory.AnimalInventory;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.UUID;

public class ServerPacket {
    public static void init(){
        PayloadTypeRegistry.playS2C().register(FlattenS2CPacket.PACKET_ID,FlattenS2CPacket.PACKET_CODEC);
        PayloadTypeRegistry.playC2S().register(EntityC2SPacket.PACKET_ID,EntityC2SPacket.PACKET_CODEC);
        PayloadTypeRegistry.playC2S().register(DeleteC2SPacket.PACKET_ID,DeleteC2SPacket.PACKET_CODEC);
        PayloadTypeRegistry.playC2S().register(StealInventoryPacket.PACKET_ID,StealInventoryPacket.PACKET_CODEC);
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
        ServerPlayNetworking.registerGlobalReceiver(DeleteC2SPacket.PACKET_ID,(payload, context) -> {
            UUID id = payload.uuid();
            World world = context.player().getWorld();
            PlayerEntity player = world.getPlayerByUuid(id);
            if (player != null) {
                PlayerInventory playerInventory = player.getInventory();
                ItemStack stack = playerInventory.getStack(payload.index());
                stack.decrement(stack.getCount());
            }
        });
        ServerPlayNetworking.registerGlobalReceiver(StealInventoryPacket.PACKET_ID,(payload,context)->{
            int EntityId = payload.id();
            World world = context.player().getWorld();
            LivingEntity entity = (LivingEntity) world.getEntityById(EntityId);
            if (entity != null) {
                AnimalInventory animalInventory = ((AnimalInventoryAccess)entity).getAnimalInventory();
                animalInventory.clear();
            }
        });
    }
}
