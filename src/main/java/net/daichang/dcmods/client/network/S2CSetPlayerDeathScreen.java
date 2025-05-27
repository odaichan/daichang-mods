package net.daichang.dcmods.client.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CSetPlayerDeathScreen {
    private final int entity;

    public S2CSetPlayerDeathScreen(int entity, float damage) {
        this.entity = entity;
    }

    public S2CSetPlayerDeathScreen(FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readFloat());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.entity);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Entity entity1;
            if (Minecraft.getInstance().level != null && (entity1 = Minecraft.getInstance().level.getEntity(this.entity)) != null && entity1 instanceof LivingEntity living) {
                Minecraft.getInstance().setScreen(new DeathScreen(Component.empty(), false));
                context.setPacketHandled(true);
            }
        });
    }
}
