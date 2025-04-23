package net.daichang.dcmods.utils.helpers;

import net.daichang.dcmods.client.PacketHandler;
import net.daichang.dcmods.client.network.S2CSyncSetFloatField;
import net.daichang.dcmods.common.entity.DCLoveElaina;
import net.daichang.dcmods.event.DCForgeEventHandler;
import net.daichang.dcmods.inits.DCDamageTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EntityHelper {
    public static EntityDataAccessor<Float> DATA_MODIFY_GET_HEALTH_DELTA;

    public static void noHurtDuration(LivingEntity living) {
        living.hurtTime = 0;
        living.hurtDuration = 0;
        living.setInvulnerable(false);
        living.invulnerableTime = 0;
        living.invulnerableDuration = 0;
    }

    public static List<Entity> getEntity(Level level, double x, double y, double z, double range) {
        AABB aabb = new AABB(x - range, y - range, z - range, x + range, y + range, z + range);
        return new ArrayList<>(level.getEntitiesOfClass(Entity.class, aabb));
    }

    public static DamageSource damageSource(Entity target,Entity attacked ,ResourceKey<DamageType> damageType) {
        return new DamageSource(target.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType), attacked);
    }

    public static DamageSource void_damage(Entity target, Entity attacked) {
        return damageSource(target, attacked, DamageTypes.FELL_OUT_OF_WORLD);
    }

    public static DamageSource generic_damage(Entity target, Entity attack) {
        return damageSource(target, attack, DamageTypes.GENERIC);
    }

    public static DamageSource generic_kill_damage(Entity target, Entity attack) {
        return damageSource(target, attack, DamageTypes.GENERIC_KILL);
    }

    public static DamageSource dc_damage(Entity target, Entity attack) {
        return damageSource(target, attack, DCDamageTypes.SUPER_DAMAGE);
    }

    public static void spawnEntity(Level level, Entity entity, EntityType<?> spawn) {
        if (level instanceof ServerLevel serverLevel) {
            double x = entity.getX();
            double y = entity.getY() + 30;
            double z = entity.getZ();

            double radius = 15.0D;

            for (int i = 0; i < 100; i++) {
                double randomX = Math.round(x - radius + Math.random() * (2 * radius));
                double randomZ = Math.round(z - radius + Math.random() * (2 * radius));

                Entity entityToSpawn = spawn.spawn(serverLevel, BlockPos.containing(randomX, y, randomZ), MobSpawnType.COMMAND);
                entityToSpawn.getPersistentData().putBoolean("isDCArrowR", true);
                entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(randomX, y, randomZ)));
                serverLevel.addFreshEntity(entityToSpawn);
            }
        }
    }

    public static void spawnEntity2(Level level, Entity entity, EntityType<?> spawn) {
        if (level instanceof ServerLevel serverLevel) {
            double x = entity.getX();
            double y = entity.getY();
            double z = entity.getZ();

            double radius = 50.0D;

            for (int i = 0; i < 30; i++) {
                double randomX = Math.round(x - radius + Math.random() * (2 * radius));
                double randomZ = Math.round(z - radius + Math.random() * (2 * radius));

                Entity entityToSpawn = spawn.spawn(serverLevel, BlockPos.containing(randomX, y, randomZ), MobSpawnType.EVENT);
                entityToSpawn.moveTo(Vec3.atBottomCenterOf(BlockPos.containing(randomX, y, randomZ)));
                serverLevel.addFreshEntity(entityToSpawn);
            }
        }
    }

    public static boolean hasElaina(Level level) {
        if (level == null) {
            return false;
        }
        boolean found = false;
        if (level instanceof ClientLevel clientLevel) {
            for (Entity entity : clientLevel.getEntities().getAll()) {
                if (entity instanceof DCLoveElaina elaina) {
                    if (!DCForgeEventHandler.livingEntities.contains(elaina))
                        DCForgeEventHandler.livingEntities.add(elaina);
                    found = true;
                }
            }
        }
        if (level instanceof ServerLevel serverLevel) {
            for (Entity entity : serverLevel.getAllEntities()) {
                if (entity instanceof DCLoveElaina elaina) {
                    if (!DCForgeEventHandler.livingEntities.contains(elaina))
                        DCForgeEventHandler.livingEntities.add(elaina);
                    found = true;
                }
            }
        }
        return found;
    }

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
        forceSetEntityData(entity.getEntityData(), DATA_MODIFY_GET_HEALTH_DELTA, value);
    }

    public static void addHealthDelta(LivingEntity entity, float value) {
        setHealthDelta(entity, entity.getEntityData().get(DATA_MODIFY_GET_HEALTH_DELTA).floatValue() + value);
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

    public static void forceKnockBack(LivingEntity target, LivingEntity attacker, float value) {
        Vec3 direction = target.position().subtract(attacker.position()).normalize();
        Vec3 knockbackVec = direction.scale(value);
        target.setDeltaMovement(target.getDeltaMovement().add(knockbackVec));
    }

    public static void forceHeal(LivingEntity living, float value) {
        if (value >= 0 && living.getHealth() < living.getMaxHealth()) {
            forceSetHealth(living, living.getHealth() + value);
        }
    }

}
