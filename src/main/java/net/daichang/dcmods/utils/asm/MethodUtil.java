package net.daichang.dcmods.utils.asm;

import net.daichang.dcmods.common.item.tools.creative.DCLoliPickaxe;
import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.daichang.dcmods.utils.lists.DeathList;
import net.daichang.dcmods.utils.lists.GetHealthList;
import net.daichang.dcmods.utils.lists.Heal2ZList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@SuppressWarnings("unused")
public final class MethodUtil extends DataHelper {
    public static float getHealth(LivingEntity entity) {
        CompoundTag tag = entity.getPersistentData();
        if (Heal2ZList.isH2Z(entity) || GetHealthList.isHealth(entity) || DeathList.isDeath(entity)) {
            return 0;
        }
        if ((EffectHelper.hasEffect(entity, DCEffects.Bloodshed.get())|| tag.getBoolean("isByDCKill")) && entity.getHealth() >= 0) return entity.getHealth() - entity.getMaxHealth() * 0.1F;
        if (DCLoliPickaxe.isHasLoliPickaxe(entity)) return entity.getMaxHealth();
        return Math.min(entity.getHealth(), entity.getMaxHealth() + DataHelper.getHealthDelta(entity));
    }

    public static float getHealth(LivingEntity entity, float value) {
        CompoundTag tag = entity.getPersistentData();
        if (Heal2ZList.isH2Z(entity) || GetHealthList.isHealth(entity) || DeathList.isDeath(entity)) {
            return 0;
        }
        if ((EffectHelper.hasEffect(entity, DCEffects.Bloodshed.get())|| tag.getBoolean("isByDCKill")) && value >= 0) return value - entity.getMaxHealth() * 0.1F;
        if (DCLoliPickaxe.isHasLoliPickaxe(entity)) return entity.getMaxHealth();
        return Math.min(value, entity.getMaxHealth() + DataHelper.getHealthDelta(entity));
    }

    public static boolean isAlive(Entity entity) {
        if (Heal2ZList.isH2Z(entity) || GetHealthList.isHealth(entity) || DeathList.isDeath(entity)) {
            return false;
        }
        if (entity instanceof LivingEntity living && living.getEntityData().get(DataHelper.DC_GET_HEALTH_DATA) <= -living.getMaxHealth()) return false;
        return entity.isAlive();
    }

    public static boolean isAlive(Entity entity, boolean value) {
        if (Heal2ZList.isH2Z(entity) || GetHealthList.isHealth(entity) || DeathList.isDeath(entity)) {
            return false;
        }
        if (entity instanceof LivingEntity living && living.getEntityData().get(DataHelper.DC_GET_HEALTH_DATA) <= -living.getMaxHealth()) return false;
        return value;
    }

    public static boolean isDeadOrDying(LivingEntity entity) {
        if (Heal2ZList.isH2Z(entity) || GetHealthList.isHealth(entity) || DeathList.isDeath(entity)) {
            return true;
        }
        if (DCLoliPickaxe.isHasLoliPickaxe(entity)) return false;
        if (entity.getEntityData().get(DataHelper.DC_GET_HEALTH_DATA) <= -entity.getMaxHealth()) return true;
        return entity.isDeadOrDying();
    }

    public static boolean isDeadOrDying(LivingEntity entity, boolean value) {
        if (Heal2ZList.isH2Z(entity) || GetHealthList.isHealth(entity) || DeathList.isDeath(entity)) {
            return true;
        }
        if (DCLoliPickaxe.isHasLoliPickaxe(entity)) return false;
        if (entity.getEntityData().get(DataHelper.DC_GET_HEALTH_DATA) <= -entity.getMaxHealth()) return true;
        return value;
    }

    public static boolean isHasDCMark(Entity entity) {
        boolean abc = false;
        CompoundTag tag = entity.getPersistentData();
        if (entity instanceof LivingEntity living && tag.contains("dcGetHealth")) {
            abc = true;
        }
        return abc;
    }
}
