package net.daichang.dcmods.client;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.client.network.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

@Mod.EventBusSubscriber(modid = DCMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PacketHandler {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(DCMod.MOD_ID, "particle"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static <MSG> void sendToClient(MSG msg) {
        if (!Thread.currentThread().getName().contains("Render")) {
            CHANNEL.send(PacketDistributor.ALL.noArg(), msg);
        }
    }

    public static void init() {
        DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
            int packetId = 0;
            CHANNEL.registerMessage(
                    packetId++,
                    SonicBoomPacket.class,
                    SonicBoomPacket::encode,
                    SonicBoomPacket::decode,
                    (o1, o2) -> {},
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
            CHANNEL.registerMessage(
                    packetId++,
                    ElainaPacket.class,
                    ElainaPacket::encode,
                    ElainaPacket::decode,
                    (o1, o2) -> {},
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
            CHANNEL.registerMessage(
                    packetId++,
                    ElainaCameraPacket.class,
                    ElainaCameraPacket::encode,
                    ElainaCameraPacket::decode,
                    (o1, o2) -> {},
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
        });
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            int packetId = 0;
            CHANNEL.registerMessage(
                    packetId++,
                    SonicBoomPacket.class,
                    SonicBoomPacket::encode,
                    SonicBoomPacket::decode,
                    SonicBoomPacket::handle,
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
            CHANNEL.registerMessage(
                    packetId++,
                    ElainaPacket.class,
                    ElainaPacket::encode,
                    ElainaPacket::decode,
                    ElainaPacket::handle,
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
            CHANNEL.registerMessage(
                    packetId++,
                    ElainaCameraPacket.class,
                    ElainaCameraPacket::encode,
                    ElainaCameraPacket::decode,
                    ElainaCameraPacket::handle,
                    Optional.of(NetworkDirection.PLAY_TO_CLIENT)
            );
        });
    }

    public static void register() {
        CHANNEL.messageBuilder(S2CSyncSetFloatField.class, 6, NetworkDirection.PLAY_TO_CLIENT).encoder(S2CSyncSetFloatField::encode).decoder(S2CSyncSetFloatField::new).consumerMainThread(S2CSyncSetFloatField::handle).add();
        CHANNEL.messageBuilder(S2CSyncDamageFlash.class, 2, NetworkDirection.PLAY_TO_CLIENT).encoder(S2CSyncDamageFlash::encode).decoder(S2CSyncDamageFlash::new).consumerMainThread(S2CSyncDamageFlash::handle).add();
    }
}
