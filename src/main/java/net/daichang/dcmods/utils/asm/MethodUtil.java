package net.daichang.dcmods.utils.asm;

import net.daichang.dcmods.common.entities.creative.EntityLoli;
import net.daichang.dcmods.common.item.tools.creative.DCLoliPickaxe;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;

@SuppressWarnings("unused")
public final class MethodUtil extends DataHelper {
    public static float getHealth(LivingEntity entity) {
        if (DCLoliPickaxe.isHasLoliPickaxe(entity)) return entity.getMaxHealth();
        if (DataHelper.isDead(entity)) return Float.NEGATIVE_INFINITY;
        if (entity instanceof EntityLoli loli && !DataHelper.isDead(loli)) return 20.0F;
        return Math.min(entity.getHealth(), entity.getMaxHealth() + DataHelper.getHealthDelta(entity));
    }

    public static boolean isNoAi(Mob mob) {
        if (mob instanceof EntityLoli loli && !loli.isDeadOrDying()) return false;
        if (DataHelper.isDead(mob)) return true;
        return mob.isNoAi();
    }

    public static boolean isNoAi(Mob mob, boolean value) {
        if (mob instanceof EntityLoli loli && !loli.isDeadOrDying()) return false;
        if (DataHelper.isDead(mob)) return true;
        return value;
    }

    public static float getHealth(LivingEntity entity, float value) {
        if (DCLoliPickaxe.isHasLoliPickaxe(entity)) return entity.getMaxHealth();
        if (DataHelper.isDead(entity)) return Float.NEGATIVE_INFINITY;
        if (entity instanceof EntityLoli loli && !DataHelper.isDead(loli)) return 20.0F;
        return Math.min(value, entity.getMaxHealth() + DataHelper.getHealthDelta(entity));
    }

    public static boolean isAlive(Entity entity) {
        if (entity instanceof LivingEntity living && DataHelper.getHealthDelta(living) < 0 && living.getHealth() <= 0) return false;
        if (entity instanceof LivingEntity living && DataHelper.isDead(living)) return false;
        if (entity instanceof EntityLoli loli && !DataHelper.isDead(loli)) return true;
        return entity.isAlive();
    }

    public static boolean isAlive(Entity entity, boolean value) {
        if (entity instanceof LivingEntity living && DataHelper.getHealthDelta(living) < 0 && living.getHealth() <= 0) return false;
        if (entity instanceof LivingEntity living && DataHelper.isDead(living)) return false;
        if (entity instanceof EntityLoli loli && !DataHelper.isDead(loli)) return true;
        return value;
    }

    public static boolean isDeadOrDying(LivingEntity entity) {
        if (DCLoliPickaxe.isHasLoliPickaxe(entity)) return false;
        if (DataHelper.getHealthDelta(entity) < 0 && entity.getHealth() <= 0) return true;
        if (DataHelper.isDead(entity) || -getHealthDelta(entity) >= entity.getMaxHealth()) return true;
        if (entity instanceof EntityLoli loli && !DataHelper.isDead(loli)) return false;
        return entity.isDeadOrDying();
    }

    public static boolean isDeadOrDying(LivingEntity entity, boolean value) {
        if (DCLoliPickaxe.isHasLoliPickaxe(entity)) return false;
        if (DataHelper.getHealthDelta(entity) < 0 && entity.getHealth() <= 0) return true;
        if (DataHelper.isDead(entity) || -getHealthDelta(entity) >= entity.getMaxHealth()) return true;
        if (entity instanceof EntityLoli loli && !DataHelper.isDead(loli)) return false;
        return value;
    }
}
