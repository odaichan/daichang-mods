package net.daichang.dcmods.utils;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

public class EffectUtil {
    public static MobEffectInstance addEffect(MobEffect mobEffect) {
        return new MobEffectInstance(mobEffect);
    }

    public static MobEffectInstance addEffect(MobEffect mobEffect, int duration, int level, boolean isVis) {
        return new MobEffectInstance(mobEffect, duration, level, false, isVis);
    }

    public static MobEffectInstance addEffect(MobEffect mobEffect, int duration) {
        return new MobEffectInstance(mobEffect, duration);
    }

    public static MobEffectInstance addEffect(MobEffect mobEffect, int duration, boolean isVis) {
        return addEffect(mobEffect, duration, 1, isVis);
    }
}
