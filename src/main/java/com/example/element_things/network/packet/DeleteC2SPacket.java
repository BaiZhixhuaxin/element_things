package com.example.element_things.network.packet;

import com.example.element_things.ElementThingsMod;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.UUID;

public record DeleteC2SPacket(int index, UUID uuid) implements CustomPayload{
    public static final CustomPayload.Id<DeleteC2SPacket> PACKET_ID = new CustomPayload.Id<>(Identifier.of(ElementThingsMod.MOD_ID,"delete_packet"));
    public static final PacketCodec<RegistryByteBuf,DeleteC2SPacket> PACKET_CODEC = PacketCodec.of((value, buf) -> {
        buf.writeInt(value.index);
        buf.writeUuid(value.uuid);
    },buf -> new DeleteC2SPacket(buf.readInt(),buf.readUuid()));




    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }
}
