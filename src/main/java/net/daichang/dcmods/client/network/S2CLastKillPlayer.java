package net.daichang.dcmods.client.network;

import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CLastKillPlayer {
    private final int entity;

    public S2CLastKillPlayer(int entity) {
        this.entity = entity;
    }

    public S2CLastKillPlayer(FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.entity);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Entity entity1;
            if (Minecraft.getInstance().level != null && (entity1 = Minecraft.getInstance().level.getEntity(this.entity)) != null && entity1 instanceof LivingEntity living) {
                living.hurt(EntityHelper.void_damage(entity1), 10);
                living.hurtTime = 0;
                living.hurtDuration = 0;
                living.setDeltaMovement(0, 0, 0);
                living.setInvulnerable(false);
                living.invulnerableTime = 0;
                context.setPacketHandled(true);
            }
        });
    }
}
