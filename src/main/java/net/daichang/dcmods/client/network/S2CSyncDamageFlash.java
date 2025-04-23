package net.daichang.dcmods.client.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CSyncDamageFlash {
    private final int damageTime;
    private final int entity;

    public S2CSyncDamageFlash(int damageTime, int entity) {
        this.damageTime = damageTime;
        this.entity = entity;
    }

    public S2CSyncDamageFlash(FriendlyByteBuf buffer) {
        this(buffer.readInt(), buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.damageTime);
        buffer.writeInt(this.entity);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Entity entity1;
            if (Minecraft.getInstance().level != null && (entity1 = Minecraft.getInstance().level.getEntity(this.entity)) != null) {
                entity1.animateHurt(this.damageTime);
                context.setPacketHandled(true);
            }
        });
    }
}
