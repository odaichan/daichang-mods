package net.daichang.dcmods.client.network;

import net.daichang.dcmods.utils.EntityActuallyHurt;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CLastKillPlayer {
    private final int entity;
    private final float damage;

    public S2CLastKillPlayer(int entity, float damage) {
        this.entity = entity;
        this.damage = damage;
    }

    public S2CLastKillPlayer(FriendlyByteBuf buffer) {
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
                EntityActuallyHurt util = EntityActuallyHurt.getInstance(living, living);
                util.dcHurt(damage);
                context.setPacketHandled(true);
            }
        });
    }
}
