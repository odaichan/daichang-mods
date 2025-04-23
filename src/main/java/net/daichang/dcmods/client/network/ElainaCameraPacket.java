package net.daichang.dcmods.client.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ElainaCameraPacket(Player player, boolean isLoad) {
    public static void encode(ElainaCameraPacket packge, FriendlyByteBuf buf) {
        buf.writeBoolean(packge.isLoad);
    }

    public static ElainaCameraPacket decode(FriendlyByteBuf buf) {
        boolean isload = buf.readBoolean();
        return new ElainaCameraPacket(Minecraft.getInstance().player, isload);
    }

    @OnlyIn(Dist.CLIENT)
    public static void handle(ElainaCameraPacket packet, Supplier<NetworkEvent.Context> network) {
        network.get().enqueueWork(() -> {
            if (packet.isLoad()) Minecraft.getInstance().gameRenderer.loadEffect(new ResourceLocation("shaders/post/art.json"));
            else Minecraft.getInstance().gameRenderer.shutdownEffect();
        });
        network.get().setPacketHandled(true);
    }
}
