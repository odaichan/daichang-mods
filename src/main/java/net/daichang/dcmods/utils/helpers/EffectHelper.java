package net.daichang.dcmods.utils.helpers;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class EffectHelper {
    public static MobEffectInstance addEffect(MobEffect mobEffect) {
        return new MobEffectInstance(mobEffect);
    }

    public static MobEffectInstance addEffect(MobEffect mobEffect, int duration, int level, boolean isVis) {
        return new MobEffectInstance(mobEffect, duration, level, false, isVis);
    }

    public static MobEffectInstance addEffect(MobEffect mobEffect, int duration) {
        return new MobEffectInstance(mobEffect, duration);
    }


    public static MobEffectInstance addEffect(MobEffect mobEffect, int duration, int level) {
        return new MobEffectInstance(mobEffect, duration, level, false, false);
    }

    public static MobEffectInstance addEffect(MobEffect mobEffect, int duration, boolean isVis) {
        return addEffect(mobEffect, duration, 1, isVis);
    }

    public static boolean hasEffect(LivingEntity entity, MobEffect mobEffect) {
        return entity.hasEffect(mobEffect);
    }
}
