package net.daichang.dcmods.common.effect;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class EffectFreeze extends BaseEffect {
    public EffectFreeze() {
        super(MobEffectCategory.HARMFUL, 0xFF55FF);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
        pLivingEntity.deltaMovement = Vec3.ZERO;
        pLivingEntity.setDeltaMovement(Vec3.ZERO);
        pLivingEntity.clearFire();
        pLivingEntity.wasOnFire = false;
        pLivingEntity.setTicksFrozen(20);
    }
}
