package net.daichang.dcmods.utils.helpers;

import net.daichang.dcmods.client.PacketHandler;
import net.daichang.dcmods.client.network.S2CSyncSetFloatField;
import net.daichang.dcmods.utils.ClassUtil;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DataHelper {
    public static EntityDataAccessor<Float> DC_GET_HEALTH_DATA;

    public static void setAllSyncedHealthData(LivingEntity entity, float value) {
        entity.getEntityData().itemsById.forEach((integer, dataItem) -> {
            EntityDataAccessor entityDataAccessor = dataItem.getAccessor();
            try {
                if (((Float)entity.getEntityData().get(entityDataAccessor)).floatValue() == entity.getHealth()) {
                    EntityHelper.forceSetEntityData(entity.getEntityData(), entityDataAccessor, value);
                }
            }
            catch (ClassCastException ignored) {}
        });
    }

    public static void setHealthDelta(LivingEntity entity, float value) {
        forceSetEntityData(entity.getEntityData(), DC_GET_HEALTH_DATA, value);
    }

    public static void addHealthDelta(LivingEntity entity, float value) {
        setHealthDelta(entity, entity.getEntityData().get(DC_GET_HEALTH_DATA).floatValue() + value);
    }

    public static <T> void forceSetEntityData(SynchedEntityData entityData, EntityDataAccessor<T> entityDataAccessor, T t) {
        SynchedEntityData.DataItem<T> dataitem = entityData.getItem(entityDataAccessor);
        if (ObjectUtils.notEqual(t, dataitem.getValue())) {
            dataitem.setValue(t);
            entityData.entity.onSyncedDataUpdated(entityDataAccessor);
            dataitem.setDirty(true);
            entityData.isDirty = true;
        }
    }

    public static void setAllHealthFields(LivingEntity entity, float value) {
        for (Field field : ClassUtil.getAllDeclaredFields(entity.getClass())) {
            try {
                field.setAccessible(true);
                if (field.getFloat(entity) != entity.getHealth()) continue;
                field.setFloat(entity, value);
            }
            catch (IllegalAccessException | IllegalArgumentException exception) {}
        }
        PacketHandler.sendToClient((Object)new S2CSyncSetFloatField(value, entity.getId()));
    }

    public static void runSetHealthMethods(LivingEntity entity, float value) {
        for (Method method : ClassUtil.getAllDeclaredMethods(entity.getClass())) {
            try {
                method.setAccessible(true);
                if (!method.getName().toLowerCase().contains("set") || !FormattingHelper.isHealth((String)method.getName())) continue;
                method.invoke(entity, Float.valueOf(value));
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException exception) {}
        }
    }

    public static void setAllHealthPersistentData(LivingEntity entity, float value) {
        for (String key : entity.getPersistentData().getAllKeys()) {
            if (!FormattingHelper.isHealth((String)key) && entity.getPersistentData().getTagType(key) != 5) continue;
            entity.getPersistentData().putFloat(key, value);
        }
    }

    public static void damageHealth(LivingEntity entity, float value, float getHealth, boolean bypassArmour) {
        if (!bypassArmour) {
            value = entity.getAbsorptionAmount() - value;
            entity.setAbsorptionAmount(Mth.clamp(value, 0.0f, (float)Float.POSITIVE_INFINITY));
            value = entity.getAbsorptionAmount() - value;
            value = Mth.abs(value);
        }
        forceSetHealth(entity, getHealth - value);
    }

    public static void damageHealth(LivingEntity entity, float value, float getHealth) {
        value = entity.getAbsorptionAmount() - value;
        entity.setAbsorptionAmount(Mth.clamp(value, 0.0f, (float)Float.POSITIVE_INFINITY));
        value = entity.getAbsorptionAmount() - value;
        value = Mth.abs(value);
        forceSetHealth(entity, getHealth - value);
    }

    public static void forceSetHealth(LivingEntity entity, float value) {
        entity.setHealth(value);
        if (entity.getHealth() != value) {
            forceSetEntityData(entity.getEntityData(), LivingEntity.DATA_HEALTH_ID, Float.valueOf(value));
            if (entity.getHealth() != value) {
                setAllSyncedHealthData(entity, value);
                setAllHealthFields(entity, value);
                runSetHealthMethods(entity, value);
                setAllHealthPersistentData(entity, value);
            }
        }
    }
}
