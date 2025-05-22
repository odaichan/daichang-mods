package net.daichang.dcmods.utils;

import net.daichang.dcmods.inits.DCOceanDamage;
import net.daichang.dcmods.inits.DCSuperDamage;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeHooks;

public class EntityHurtUtil {
    public final LivingEntity target, attacker;
    public final Level level;
    
    public EntityHurtUtil(LivingEntity living, LivingEntity attaker) {
        this.target = living;
        this.attacker = attaker;
        if (getAttacker() == null) level = target.level();
        else level = attacker.level();
    }

    public EntityHurtUtil(LivingEntity living) {
        this(living, living);
    }
    
    public static EntityHurtUtil getInstance(LivingEntity target) {
        return new EntityHurtUtil(target);
    }

    public static EntityHurtUtil getInstance(LivingEntity target, LivingEntity attacker) {
        return new EntityHurtUtil(target, attacker);
    }

    public void die(DamageSource source) {
        target.die(source);
        target.dead = true;
        ForgeHooks.onLivingDeath(target, source);
        DataHelper.setHealthDelta(target, Float.NEGATIVE_INFINITY);
        target.invalidateCaps();
        target.setNoActionTime(0);
        DataHelper.forceSetHealth(target, Float.NEGATIVE_INFINITY);
        if (target.isAlive()) {
            target.gameEvent(GameEvent.ENTITY_DIE);
            if (target.getDeathSound() != null) target.playSound(target.getDeathSound());
        }
    }
    
    public void hurtTarget(DamageSource source, float value) {
        if (value <= 0 || target.isRemoved()) return;
        EntityHelper.noHurtDuration(target);
        ForgeHooks.onLivingAttack(target, source, value);
        ForgeHooks.onLivingHurt(target, source, value);
        target.gameEvent(GameEvent.ENTITY_DAMAGE);
        target.hurt(source, value);
        target.setHealth(target.getHealth() - value);
        target.setLastHurtByMob(attacker);
        target.walkAnimation.setSpeed(1.5F);
        target.actuallyHurt(source, value);
        target.noActionTime = 0;
        target.invalidateCaps();
        if (target.isSleeping() && !target.level().isClientSide()) target.stopSleeping();
        if (value == Float.POSITIVE_INFINITY) {
            EntityHelper.forceSetHealth(target, Float.NEGATIVE_INFINITY);
            target.gameEvent(GameEvent.ENTITY_DIE);
            target.dead = true;
            if (source.is(DCSuperDamage.SUPER_DAMAGE) || source.is(DCOceanDamage.OCEAN_DAMAGE)) DataHelper.setIsDead(target, true);
        }
        if (!target.isDeadOrDying()) target.deathTime = 0;
        if (target.isDeadOrDying() || DataHelper.isDead(target)) {
            target.gameEvent(GameEvent.ENTITY_DIE);
            target.die(source);
            target.dead = true;
        }
        try {
            if (target.isAlive()) target.playHurtSound(source);
            target.level().broadcastDamageEvent(target, source);
            if (target.isDeadOrDying() && target.getDeathSound() != null) target.playSound(target.getDeathSound());
        } catch (Exception ignored) {}
        EntityHelper.forceSetHealth(target, target.getHealth() - value);
    }
    
    public void kbHurt(DamageSource source, float value) {
        hurtTarget(source, value);
        if (getAttacker() != null) {
            if (attacker instanceof Player player) {
                for (LivingEntity livingentity : level.getEntitiesOfClass(LivingEntity.class, player.getItemInHand(InteractionHand.MAIN_HAND).getSweepHitBox(player, target))) {
                    double entityReachSq = Mth.square(player.getEntityReach());
                    if (!player.isAlliedTo(livingentity) && (!(livingentity instanceof ArmorStand) || !((ArmorStand) livingentity).isMarker()) && player.distanceToSqr(livingentity) < entityReachSq)
                        target.knockback(0.3F, Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
                }
            }
        }
    }

    public void kbDCHurt(float damage) {
        kbHurt(EntityHelper.ocean_damage(target), damage);
        DataHelper.addHealthDelta(target, -damage);
    }

    public void dcHurt(float value) {
        hurtTarget(EntityHelper.ocean_damage(target), value);
        if (getAttacker() != null) {
            if (attacker instanceof Player player) {
                double d0 = -Mth.sin(player.getYRot() * ((float) Math.PI / 180F));
                double d1 = Mth.cos(player.getYRot() * ((float) Math.PI / 180F));
                if (level instanceof ServerLevel serverLevel) serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, player.getX() + d0, player.getY(0.5D), player.getZ() + d1, 0, d0, 0.0D, d1, 0.0D);
            }
        }
        DataHelper.addHealthDelta(target, -value);
    }

    public LivingEntity getAttacker() {
        return attacker;
    }
}