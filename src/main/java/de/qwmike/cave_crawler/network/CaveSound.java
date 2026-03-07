package de.qwmike.cave_crawler.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class CaveSound {
    public final ResourceLocation soundResource;
    public final BlockPos playerPosition;
    public final float volume;
    public final float pitch;

    public CaveSound(final ResourceLocation soundResource, final BlockPos playerPosition, float volume, float pitch) {
        this.soundResource = soundResource;
        this.playerPosition = playerPosition;
        this.volume = volume;
        this.pitch = pitch;
    }

    public static void encode(final CaveSound packet, final FriendlyByteBuf buf) {
        buf.writeResourceLocation(packet.soundResource);
        buf.writeBlockPos(packet.playerPosition);
        buf.writeFloat(packet.volume);
        buf.writeFloat(packet.pitch);
    }

    public static CaveSound decode(final FriendlyByteBuf buf) {
        ResourceLocation soundResource = buf.readResourceLocation();
        BlockPos playerPosition = buf.readBlockPos();
        float volume = buf.readFloat();
        float pitch = buf.readFloat();
        return new CaveSound(soundResource, playerPosition, volume, pitch);
    }

    public static void handle(final CaveSound packet, final Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context ctx = ctxSupplier.get();
        ctx.enqueueWork(() -> de.qwmike.cave_crawler.client.HandleCaveSound.handle(packet));
        ctx.setPacketHandled(true);
    }
}
