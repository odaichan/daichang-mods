package net.daichang.dcmods.client.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.NetworkEvent;

import java.lang.reflect.Field;
import java.util.function.Supplier;

public class S2CSyncSetFloatField {
    private final float value;
    private final int entity;

    public S2CSyncSetFloatField(float value, int entity) {
        this.value = value;
        this.entity = entity;
    }

    public S2CSyncSetFloatField(FriendlyByteBuf buffer) {
        this(buffer.readFloat(), buffer.readInt());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeFloat(this.value);
        buffer.writeInt(this.entity);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            Entity entity;
            if (Minecraft.getInstance().level != null && (entity = Minecraft.getInstance().level.getEntity(this.entity)) instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)((Object)entity);
                for (Field field : livingEntity.getClass().getFields()) {
                    try {
                        field.setAccessible(true);
                        if (field.getFloat(livingEntity) != livingEntity.getHealth()) continue;
                        field.setFloat(livingEntity, this.value);
                        context.setPacketHandled(true);
                    }
                    catch (IllegalAccessException | IllegalArgumentException exception) {}
                }
            }
        });
    }
}
