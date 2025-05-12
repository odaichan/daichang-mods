package net.daichang.dcmods.client.network;

import net.daichang.dcmods.utils.helpers.ParticleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CElainaRangeAttack {
    private final int entity;

    public S2CElainaRangeAttack(int entity) {
        this.entity = entity;
    }

    public S2CElainaRangeAttack(FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeInt(this.entity);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().level != null && Minecraft.getInstance().level.getEntity(this.entity) instanceof LivingEntity living) {
                ParticleHelper.realStar(living, Minecraft.getInstance().level);
            }
        });
    }
}
