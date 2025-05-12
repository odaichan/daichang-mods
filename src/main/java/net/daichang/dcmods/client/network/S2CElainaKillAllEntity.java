package net.daichang.dcmods.client.network;

import net.daichang.dcmods.utils.helpers.ParticleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CElainaKillAllEntity {
    private final int entity;

    public S2CElainaKillAllEntity(int entity) {
        this.entity = entity;
    }

    public S2CElainaKillAllEntity(FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.entity);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Entity entity1;
            if (Minecraft.getInstance().level != null && (entity1 = Minecraft.getInstance().level.getEntity(this.entity)) != null) {
                ParticleHelper.drawSixStar(entity1, Minecraft.getInstance().level);
            }
        });
    }
}
