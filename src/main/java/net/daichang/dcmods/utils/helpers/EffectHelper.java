package net.daichang.dcmods.utils.helpers;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class EffectHelper {
    public static MobEffectInstance addEffect(MobEffect mobEffect) {
        return new MobEffectInstance(mobEffect);
    }

    public static MobEffectInstance addEffect(MobEffect mobEffect, float second, int level, boolean isVis) {
        return new MobEffectInstance(mobEffect, (int) (second * 20), level, false, isVis);
    }

    public static MobEffectInstance addEffect(MobEffect mobEffect, float second) {
        return new MobEffectInstance(mobEffect, (int) (second * 20));
    }


    public static MobEffectInstance addEffect(MobEffect mobEffect, float second, int level) {
        return new MobEffectInstance(mobEffect, (int) (second * 20), level, false, false);
    }

    public static MobEffectInstance addEffect(MobEffect mobEffect, float second, boolean isVis) {
        return addEffect(mobEffect, second * 20, 1, isVis);
    }

    public static boolean hasEffect(LivingEntity entity, MobEffect mobEffect) {
        return entity.hasEffect(mobEffect);
    }

    public static int getEffectLevel(LivingEntity entity, MobEffect mobEffect) {
        if (hasEffect(entity, mobEffect)) return Objects.requireNonNull(entity.getEffect(mobEffect)).getAmplifier();
        return 0;
    }
}
