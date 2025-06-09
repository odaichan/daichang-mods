package net.daichang.dcmods.common.entities.creative;

import net.daichang.dcmods.common.entities.DCBaseMonster;
import net.daichang.dcmods.common.item.tools.creative.DCLoliPickaxe;
import net.daichang.dcmods.inits.DCEntities;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.Set;

public class EntityLoli extends DCBaseMonster {
    public EntityLoli(EntityType<EntityLoli> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    public EntityLoli(PlayMessages.SpawnEntity spawnEntity, Level level) {
        this(DCEntities.LOLI.get(), level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 3.0, true) {
            @Override
            protected double getAttackReachSqr(@NotNull LivingEntity entity) {
                return 0.1D;
            }

            @Override
            protected void resetAttackCooldown() {
                super.resetAttackCooldown();
            }
        });
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.goalSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, false){
            @Override
            protected double getFollowDistance() {
                return 74.0D;
            }
        });
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    public float getHealth() {
        return 20.0F;
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public void setHealth(float pHealth) {
        super.setHealth(20.0F);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (pEntity instanceof LivingEntity living) DataHelper.setIsDead(living, true);
        return super.doHurtTarget(pEntity);
    }

    @Override
    public boolean isCustomNameVisible() {
        return false;
    }

    void safeLoli() {
        Level level = this.level();
        Utils.Override_DATA_HEALTH_ID(this, 20.0F);
        this.fallDistance = 0;
        this.hurtTime = 0;
        this.hurtDuration = 0;
        this.wasOnFire = false;
        this.setRemainingFireTicks(0);
        this.unsetRemoved();
        this.setAirSupply(20);
        this.setNoAi(false);
        this.hurtMarked = false;
        this.invulnerableTime = Integer.MAX_VALUE;
        this.invulnerableDuration = Integer.MAX_VALUE;
        double a = new Random().nextDouble(0.1, 0.5);
        for (Entity entity : level.getEntities(this, this.getBoundingBox().inflate(1.0D))) {
            if (entity instanceof LivingEntity livingEntity && !(entity instanceof EntityLoli) && !(entity instanceof Player) && livingEntity.isAlive()) {
                DataHelper.setIsDead(livingEntity, true);
                this.setPos(entity.getX() + a, entity.getY() + a, entity.getZ() + a);
            }
            if (entity instanceof Player player && !DCLoliPickaxe.isHasLoliPickaxe(player)) {
                DataHelper.addHealthDelta(player, Float.NEGATIVE_INFINITY);
            }
        }
        this.deathTime = -2;
        Utils.Override_DATA_HEALTH_ID(this, 20.0f);
        this.setHealth(20.0f);
        this.canUpdate(true);
        LivingEntity living =  this.getTarget();
        if (living != null) {
            this.lookAt(EntityAnchorArgument.Anchor.EYES, living.position());
            this.lookAt(EntityAnchorArgument.Anchor.FEET, living.position());
        }
    }

    @Override
    public void setNoAi(boolean pNoAi) {
        super.setNoAi(false);
    }

    @Override
    public void tick() {
        super.tick();
        safeLoli();
    }

    @Override
    public boolean isAlive() {
        return !DataHelper.isDead(this);
    }

    @Override
    public boolean isDeadOrDying() {
        return DataHelper.isDead(this);
    }

    @Override
    public void remove(RemovalReason pReason) {
        if (DataHelper.isDead(this)) super.remove(pReason);
    }

    @Override
    public void teleportTo(double pX, double pY, double pZ) {
    }

    @Override
    public boolean teleportTo(ServerLevel pLevel, double pX, double pY, double pZ, Set<RelativeMovement> pRelativeMovements, float pYRot, float pXRot) {
        return false;
    }

    @Override
    public float getMaxHealth() {
        return 20.0F;
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0F)
                .add(Attributes.ATTACK_DAMAGE, 1.0F)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 30.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }


    @Override
    public @Nullable RemovalReason getRemovalReason() {
        if (DataHelper.isDead(this)) return super.getRemovalReason();
        return null;
    }

    @Override
    public boolean isInWater() {
        return false;
    }

    @Override
    public boolean isInLava() {
        return false;
    }

    @Override
    public void setIsInPowderSnow(boolean pIsInPowderSnow) {
        super.setIsInPowderSnow(false);
    }

    @Override
    public boolean isOnFire() {
        return false;
    }

    @Override
    public boolean isFreezing() {
        return false;
    }

    @Override
    public void kill() {}

    @Override
    public void setTicksFrozen(int pTicksFrozen) {
        super.setTicksFrozen(0);
    }

    @Override
    public boolean addEffect(MobEffectInstance pEffectInstance) {
        return false;
    }

    @Override
    public boolean addEffect(MobEffectInstance pEffectInstance, @Nullable Entity pEntity) {
        return false;
    }

    @Override
    public void addEatEffect(ItemStack pFood, Level pLevel, LivingEntity pLivingEntity) {

    }

    @Override
    public void forceAddEffect(MobEffectInstance pInstance, @Nullable Entity pEntity) {

    }

    @Override
    public void die(DamageSource pDamageSource) {
        if (DataHelper.isDead(this)) super.die(pDamageSource);
    }

    @Override
    public void tickDeath() {
        if (DataHelper.isDead(this)) super.tickDeath();
    }

    @Override
    public boolean canSprint() {
        return true;
    }
}
