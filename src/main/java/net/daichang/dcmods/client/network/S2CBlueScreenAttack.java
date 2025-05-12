package net.daichang.dcmods.client.network;

import net.daichang.dcmods.library.BlueScreenAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CBlueScreenAttack {
    private final int entity;

    public S2CBlueScreenAttack(int entity) {
        this.entity = entity;
    }

    public S2CBlueScreenAttack(FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.entity);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level != null && Minecraft.getInstance().level.getEntity(this.entity) instanceof LocalPlayer player) {
                player.displayClientMessage(Component.literal("BlueScreen Attack"), false);
                BlueScreenAPI.API.BlueScreen(true);
            }
        });
    }
}