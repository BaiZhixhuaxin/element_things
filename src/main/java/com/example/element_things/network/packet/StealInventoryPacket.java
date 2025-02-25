package com.example.element_things.network.packet;

import com.example.element_things.ElementThingsMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record StealInventoryPacket(int id) implements CustomPayload{
    public static final CustomPayload.Id<StealInventoryPacket> PACKET_ID = new CustomPayload.Id<>(Identifier.of(ElementThingsMod.MOD_ID,"steal_packet"));
    public static final PacketCodec<RegistryByteBuf,StealInventoryPacket> PACKET_CODEC = PacketCodec.of((value, buf) -> {
        buf.writeInt(value.id);
    },buf -> new StealInventoryPacket(buf.readInt()));




    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
