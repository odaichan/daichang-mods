package net.daichang.dcmods.common.effect;

import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class EffectHeal extends BaseEffect {
    public EffectHeal() {
        super(MobEffectCategory.HARMFUL, 0xFF55FF);
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 5 == 0;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity p_19467_, int p_19468_) {
        super.applyEffectTick(p_19467_, p_19468_);
        heal(p_19467_, p_19468_);
    }

    void heal(LivingEntity living, int level) {
        float newHealth = living.getHealth() + level * 7;
        living.setHealth(newHealth);
        living.entityData.set(LivingEntity.DATA_HEALTH_ID, newHealth);
        EntityHelper.forceSetHealth(living, newHealth);
    }
}
