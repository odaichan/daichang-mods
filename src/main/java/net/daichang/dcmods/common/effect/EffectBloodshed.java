package net.daichang.dcmods.common.effect;

import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
        if (p_19467_ instanceof Player player) player.setHealth(player.getHealth() - (1 + p_19468_));
        else killLiving(p_19467_);
    }

    void killLiving(LivingEntity living) {
        living.hurt(EntityHelper.generic_kill_damage(living, living), living.getMaxHealth() * 0.1F);
        living.setHealth(living.getHealth() - living.getMaxHealth() * 0.1F);
        living.entityData.set(LivingEntity.DATA_HEALTH_ID, living.getHealth() - living.getMaxHealth() * 0.1F);
        EntityHelper.forceSetHealth(living, living.getHealth() - living.getMaxHealth() * 0.1F);
        living.setInvulnerable(false);
        living.invulnerableTime = 0;
        living.hurtTime = 0;
        living.hurtDuration = 0;
        living.setDeltaMovement(0 ,0, 0);
    }
}
