package net.daichang.dcmods.client.network;

import net.daichang.dcmods.inits.DCItems;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class S2CUseWoodTotem {
    private final int entity;

    public S2CUseWoodTotem(int entity) {
        this.entity = entity;
    }

    public S2CUseWoodTotem(FriendlyByteBuf buffer) {
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
                Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(DCItems.HEART_OF_THE_OCEAN.get()));
                context.setPacketHandled(true);
            }
        });
    }
}
