package net.daichang.dcmods.utils.asm;

import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.daichang.dcmods.utils.helpers.FileHelper;
import net.daichang.dcmods.utils.lists.DeathList;
import net.daichang.dcmods.utils.lists.GetHealthList;
import net.daichang.dcmods.utils.lists.Heal2ZList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

@SuppressWarnings("unused")
public final class MethodUtil {
    public static float getHealth(LivingEntity entity) {
        CompoundTag tag = entity.getPersistentData();
        if (Heal2ZList.isH2Z(entity) || GetHealthList.isHealth(entity) || DeathList.isDeath(entity)) {
            entity.setPose(Pose.DYING);
            return 0;
        }
        if ((EffectHelper.hasEffect(entity, DCEffects.Bloodshed.get())|| tag.getBoolean("isByDCKill")) && entity.getHealth() >= 0) return entity.getHealth() - entity.getMaxHealth() * 0.1F;
        if (FileHelper.defaultHasTarget(entity)) return entity.getMaxHealth();
        return entity.getHealth();
    }

    public static float getHealth(LivingEntity entity, float value) {
        CompoundTag tag = entity.getPersistentData();
        if (Heal2ZList.isH2Z(entity) || GetHealthList.isHealth(entity) || DeathList.isDeath(entity)) {
            entity.setPose(Pose.DYING);
            return 0;
        }
        if ((EffectHelper.hasEffect(entity, DCEffects.Bloodshed.get())|| tag.getBoolean("isByDCKill")) && value >= 0) return value - entity.getMaxHealth() * 0.1F;
        if (FileHelper.defaultHasTarget(entity)) return entity.getMaxHealth();
        return value;
    }

    public static boolean isAlive(Entity entity) {
        if (Heal2ZList.isH2Z(entity) || GetHealthList.isHealth(entity) || DeathList.isDeath(entity)) {
            return false;
        }
        if (FileHelper.defaultHasTarget(entity)) return true;
        return entity.isAlive();
    }

    public static boolean isAlive(Entity entity, boolean value) {
        if (Heal2ZList.isH2Z(entity) || GetHealthList.isHealth(entity) || DeathList.isDeath(entity)) {
            return false;
        }
        if (FileHelper.defaultHasTarget(entity)) return true;
        return value;
    }

    public static boolean isDeadOrDying(LivingEntity entity) {
        if (Heal2ZList.isH2Z(entity) || GetHealthList.isHealth(entity) || DeathList.isDeath(entity)) {
            return true;
        }
        if (FileHelper.defaultHasTarget(entity)) return false;
        return entity.isDeadOrDying();
    }

    public static boolean isDeadOrDying(LivingEntity entity, boolean value) {
        if (Heal2ZList.isH2Z(entity) || GetHealthList.isHealth(entity) || DeathList.isDeath(entity)) {
            return true;
        }
        if (FileHelper.defaultHasTarget(entity)) return false;
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
