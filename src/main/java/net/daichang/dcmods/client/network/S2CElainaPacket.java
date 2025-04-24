package net.daichang.dcmods.client.network;

import net.daichang.dcmods.utils.helpers.ParticleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record S2CElainaPacket(Vec3 targets_position) {
    public static void encode(S2CElainaPacket packet, FriendlyByteBuf buf) {
        buf.writeDouble(packet.targets_position.x);
        buf.writeDouble(packet.targets_position.y);
        buf.writeDouble(packet.targets_position.z);
    }

    public static S2CElainaPacket decode(FriendlyByteBuf buf) {
        var targetpos = new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
        return new S2CElainaPacket(targetpos);
    }

    @OnlyIn(Dist.CLIENT)
    public static void handle(S2CElainaPacket packet, Supplier<NetworkEvent.Context> network) {
        network.get().enqueueWork(() -> {
            Level level = Minecraft.getInstance().level;
            if (level == null) return;
            ParticleHelper.spawnExaggeratedWaterSplash(level, packet.targets_position, 40);
        });
        network.get().setPacketHandled(true);
    }
}
