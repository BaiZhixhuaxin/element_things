package com.example.element_things.network.packet;

import com.example.element_things.ElementThingsMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record FlattenS2CPacket(boolean shouldBeFlattened) implements CustomPayload {
    public static final CustomPayload.Id<FlattenS2CPacket> PACKET_ID = new Id<>(Identifier.of(ElementThingsMod.MOD_ID,"flatten_packet"));
    public static final PacketCodec<RegistryByteBuf,FlattenS2CPacket> PACKET_CODEC = PacketCodec.of((value, buf) -> {
        buf.writeBoolean(value.shouldBeFlattened);
    },buf -> new FlattenS2CPacket(buf.readBoolean()));




    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
