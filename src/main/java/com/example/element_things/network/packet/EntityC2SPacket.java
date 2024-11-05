package com.example.element_things.network.packet;

import com.example.element_things.ElementThingsMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record EntityC2SPacket(int entityId) implements CustomPayload {
    public static final CustomPayload.Id<EntityC2SPacket> PACKET_ID = new Id<>(Identifier.of(ElementThingsMod.MOD_ID,"entity_packet"));
    public static final PacketCodec<RegistryByteBuf,EntityC2SPacket> PACKET_CODEC = PacketCodec.of((value, buf) -> {
        buf.writeInt(value.entityId);
    },buf -> new EntityC2SPacket(buf.readInt()));




    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
