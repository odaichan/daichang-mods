package net.daichang.dcmods.common.effect;

import net.daichang.dcmods.utils.helpers.DataHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class EffectBloodshed extends MobEffect {
    public EffectBloodshed() {
        super(MobEffectCategory.HARMFUL, 0xFF55FF);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration % 20 == 0;
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity p_19467_, int p_19468_) {
        super.applyEffectTick(p_19467_, p_19468_);
        float value = 1 + p_19468_;
        DataHelper.addHealthDelta(p_19467_, -value);
    }
}
