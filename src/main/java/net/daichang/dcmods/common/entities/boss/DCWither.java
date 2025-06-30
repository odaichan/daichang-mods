package net.daichang.dcmods.common.entities.boss;

import com.google.common.collect.ImmutableList;
import net.daichang.dcmods.common.entities.BossEntity;
import net.daichang.dcmods.common.entities.ai.DCRangeAttackGoal;
import net.daichang.dcmods.common.entities.projectile.DCWitherSkull;
import net.daichang.dcmods.common.item.UseCountItem;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.inits.DCEntities;
import net.daichang.dcmods.inits.DCItems;
import net.daichang.dcmods.inits.DCSounds;
import net.daichang.dcmods.utils.helpers.ExplodeHelper;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class DCWither extends BossEntity implements PowerableMob, RangedAttackMob  {
    private static final EntityDataAccessor<Integer> DATA_TARGET_A;
    private static final EntityDataAccessor<Integer> DATA_TARGET_B;
    private static final EntityDataAccessor<Integer> DATA_TARGET_C;
    private static final EntityDataAccessor<Integer> DEATH_TIME_BOOM;
    private static final List<EntityDataAccessor<Integer>> DATA_TARGETS;
    private static final EntityDataAccessor<Integer> DATA_ID_INV;
    private final float[] xRotHeads = new float[2];
    private final float[] yRotHeads = new float[2];
    private final float[] xRotOHeads = new float[2];
    private final float[] yRotOHeads = new float[2];
    private final int[] nextHeadUpdate = new int[2];
    private final int[] idleHeadUpdates = new int[2];
    private int destroyBlocksTick;
    private static final Predicate<LivingEntity> LIVING_ENTITY_SELECTOR;
    private static final TargetingConditions TARGETING_CONDITIONS;

    public DCWither(EntityType<DCWither> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        setDeathSecond(50);
    }

    public DCWither(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super(DCEntities.DC_WITHER.get(), world);
        this.xpReward = 200905;
        setDeathSecond(50);
    }

    @Override
    public void tickDeath() {
        ++this.deathTime;
        if (this.getDeathTime() > 20) {
            ExplodeHelper.boom(level, this.position(), this, 2);
            this.playSound(this.getDeathSound());
            this.remove(RemovalReason.KILLED);
            this.dropAllDeathLoot(this.damageSources().magic());
        }
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    public void baseTick() {
        super.baseTick();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new DCWither.WitherDoNothingGoal());
        this.goalSelector.addGoal(2, new DCRangeAttackGoal(this, 1.0F, 40, 120, 128.0F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomFlyingGoal(this, 1.0F));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, false, false, LIVING_ENTITY_SELECTOR));
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_TARGET_A, 0);
        this.entityData.define(DATA_TARGET_B, 0);
        this.entityData.define(DATA_TARGET_C, 0);
        this.entityData.define(DATA_ID_INV, 0);
        this.entityData.define(DEATH_TIME_BOOM, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Invul", this.getInvulnerableTicks());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setInvulnerableTicks(pCompound.getInt("Invul"));
        this.bossEvent.setName(this.getDisplayName());
    }

    @Override
    public void setCustomName(@Nullable Component pName) {
        super.setCustomName(pName);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.WITHER_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.WITHER_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.WITHER_DEATH;
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity living = this.getTarget();
        if (!this.isDeadOrDying() && living != null) this.lookAt(EntityAnchorArgument.Anchor.FEET, living.position());
    }

    @Override
    public void aiStep() {
        Vec3 vec3 = this.getDeltaMovement().multiply(1.0F, 0.6, 1.0F);
        if (!this.level().isClientSide && this.getAlternativeTarget(0) > 0) {
            Entity entity = this.level().getEntity(this.getAlternativeTarget(0));
            if (entity != null) {
                double d0 = vec3.y;
                if (this.getY() < entity.getY() || !this.isPowered() && this.getY() < entity.getY() + (double)5.0F) {
                    d0 = Math.max((double)0.0F, d0);
                    d0 += 0.3 - d0 * (double)0.6F;
                }

                vec3 = new Vec3(vec3.x, d0, vec3.z);
                Vec3 vec31 = new Vec3(entity.getX() - this.getX(), (double)0.0F, entity.getZ() - this.getZ());
                if (vec31.horizontalDistanceSqr() > (double)9.0F) {
                    Vec3 vec32 = vec31.normalize();
                    vec3 = vec3.add(vec32.x * 0.3 - vec3.x * 0.6, (double)0.0F, vec32.z * 0.3 - vec3.z * 0.6);
                }
            }
        }

        this.setDeltaMovement(vec3);
        if (vec3.horizontalDistanceSqr() > 0.05) {
            this.setYRot((float) Mth.atan2(vec3.z, vec3.x) * (180F / (float)Math.PI) - 90.0F);
        }

        super.aiStep();

        for(int i = 0; i < 2; ++i) {
            this.yRotOHeads[i] = this.yRotHeads[i];
            this.xRotOHeads[i] = this.xRotHeads[i];
        }

        for(int j = 0; j < 2; ++j) {
            int k = this.getAlternativeTarget(j + 1);
            Entity entity1 = null;
            if (k > 0) {
                entity1 = this.level().getEntity(k);
            }

            if (entity1 != null) {
                double d9 = this.getHeadX(j + 1);
                double d1 = this.getHeadY(j + 1);
                double d3 = this.getHeadZ(j + 1);
                double d4 = entity1.getX() - d9;
                double d5 = entity1.getEyeY() - d1;
                double d6 = entity1.getZ() - d3;
                double d7 = Math.sqrt(d4 * d4 + d6 * d6);
                float f = (float)(Mth.atan2(d6, d4) * (double)(180F / (float)Math.PI)) - 90.0F;
                float f1 = (float)(-(Mth.atan2(d5, d7) * (double)(180F / (float)Math.PI)));
                this.xRotHeads[j] = this.rotlerp(this.xRotHeads[j], f1, 40.0F);
                this.yRotHeads[j] = this.rotlerp(this.yRotHeads[j], f, 10.0F);
            } else {
                this.yRotHeads[j] = this.rotlerp(this.yRotHeads[j], this.yBodyRot, 10.0F);
            }
        }

        boolean flag = this.isPowered();

        for(int l = 0; l < 3; ++l) {
            double d8 = this.getHeadX(l);
            double d10 = this.getHeadY(l);
            double d2 = this.getHeadZ(l);
            this.level().addParticle(ParticleTypes.SMOKE, d8 + this.random.nextGaussian() * (double)0.3F, d10 + this.random.nextGaussian() * (double)0.3F, d2 + this.random.nextGaussian() * (double)0.3F, (double)0.0F, (double)0.0F, (double)0.0F);
            if (flag && this.level().random.nextInt(4) == 0) {
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, d8 + this.random.nextGaussian() * (double)0.3F, d10 + this.random.nextGaussian() * (double)0.3F, d2 + this.random.nextGaussian() * (double)0.3F, (double)0.7F, (double)0.7F, (double)0.5F);
            }
        }

        if (this.getInvulnerableTicks() > 0) {
            for(int i1 = 0; i1 < 3; ++i1) {
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + this.random.nextGaussian(), this.getY() + (double)(this.random.nextFloat() * 3.3F), this.getZ() + this.random.nextGaussian(), (double)0.7F, (double)0.7F, (double)0.9F);
            }
        }

    }

    @Override
    protected void customServerAiStep() {
        if (this.getInvulnerableTicks() > 0) {
            int k1 = this.getInvulnerableTicks() - 1;
            this.bossEvent.setProgress(1.0F - (float)k1 / 220.0F);
            if (k1 <= 0) {
                this.level().explode(this, this.getX(), this.getEyeY(), this.getZ(), 7.0F, false, Level.ExplosionInteraction.MOB);
                if (!this.isSilent()) {
                    this.level().globalLevelEvent(1023, this.blockPosition(), 0);
                }
            }

            this.setInvulnerableTicks(k1);
            if (this.tickCount % 10 == 0) {
                this.heal(20.0F);
            }
        } else {
            super.customServerAiStep();

            for(int i = 1; i < 3; ++i) {
                if (this.tickCount >= this.nextHeadUpdate[i - 1]) {
                    this.nextHeadUpdate[i - 1] = this.tickCount + 10 + this.random.nextInt(10);
                    if (this.level().getDifficulty() == Difficulty.NORMAL || this.level().getDifficulty() == Difficulty.HARD) {
                        int i3 = i - 1;
                        int j3 = this.idleHeadUpdates[i - 1];
                        this.idleHeadUpdates[i3] = this.idleHeadUpdates[i - 1] + 1;
                        if (j3 > 15) {
                            float f = 10.0F;
                            float f1 = 5.0F;
                            double d0 = Mth.nextDouble(this.random, this.getX() - (double)10.0F, this.getX() + (double)10.0F);
                            double d1 = Mth.nextDouble(this.random, this.getY() - (double)5.0F, this.getY() + (double)5.0F);
                            double d2 = Mth.nextDouble(this.random, this.getZ() - (double)10.0F, this.getZ() + (double)10.0F);
                            this.performRangedAttack(i + 1, d0, d1, d2, true);
                            this.idleHeadUpdates[i - 1] = 0;
                        }
                    }

                    int l1 = this.getAlternativeTarget(i);
                    if (l1 > 0) {
                        LivingEntity livingentity = (LivingEntity)this.level().getEntity(l1);
                        if (livingentity != null && this.canAttack(livingentity) && !(this.distanceToSqr(livingentity) > (double)900.0F) && this.hasLineOfSight(livingentity)) {
                            this.performRangedAttack(i + 1, livingentity);
                            this.nextHeadUpdate[i - 1] = this.tickCount + 40 + this.random.nextInt(20);
                            this.idleHeadUpdates[i - 1] = 0;
                        } else {
                            this.setAlternativeTarget(i, 0);
                        }
                    } else {
                        List<LivingEntity> list = this.level().getNearbyEntities(LivingEntity.class, TARGETING_CONDITIONS, this, this.getBoundingBox().inflate((double)20.0F, (double)8.0F, (double)20.0F));
                        if (!list.isEmpty()) {
                            LivingEntity livingentity1 = list.get(this.random.nextInt(list.size()));
                            this.setAlternativeTarget(i, livingentity1.getId());
                        }
                    }
                }
            }

            if (this.getTarget() != null) {
                this.setAlternativeTarget(0, this.getTarget().getId());
            } else {
                this.setAlternativeTarget(0, 0);
            }

            if (this.destroyBlocksTick > 0) {
                --this.destroyBlocksTick;
                if (this.destroyBlocksTick == 0 && ForgeEventFactory.getMobGriefingEvent(this.level(), this)) {
                    int j1 = Mth.floor(this.getY());
                    int i2 = Mth.floor(this.getX());
                    int j2 = Mth.floor(this.getZ());
                    boolean flag = false;

                    for(int j = -1; j <= 1; ++j) {
                        for(int k2 = -1; k2 <= 1; ++k2) {
                            for(int k = 0; k <= 3; ++k) {
                                int l2 = i2 + j;
                                int l = j1 + k;
                                int i1 = j2 + k2;
                                BlockPos blockpos = new BlockPos(l2, l, i1);
                                BlockState blockstate = this.level().getBlockState(blockpos);
                                if (blockstate.canEntityDestroy(this.level(), blockpos, this) && ForgeEventFactory.onEntityDestroyBlock(this, blockpos, blockstate)) {
                                    flag = this.level().destroyBlock(blockpos, true, this) || flag;
                                }
                            }
                        }
                    }

                    if (flag) {
                        this.level().levelEvent((Player)null, 1022, this.blockPosition(), 0);
                    }
                }
            }

            if (this.tickCount % 20 == 0) {
                this.heal(1.0F);
            }

            this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        }

    }

    /** @deprecated */
    @Deprecated
    public static boolean canDestroy(BlockState pState) {
        return !pState.isAir() && !pState.is(BlockTags.WITHER_IMMUNE);
    }

    public void makeInvulnerable() {
        this.setInvulnerableTicks(220);
        this.bossEvent.setProgress(0.0F);
        this.setHealth(this.getMaxHealth() / 3.0F);
    }

    @Override
    public void makeStuckInBlock(BlockState pState, Vec3 pMotionMultiplier) {
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer pPlayer) {
        super.startSeenByPlayer(pPlayer);
        this.bossEvent.addPlayer(pPlayer);
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer pPlayer) {
        super.stopSeenByPlayer(pPlayer);
        this.bossEvent.removePlayer(pPlayer);
    }

    private double getHeadX(int pHead) {
        if (pHead <= 0) {
            return this.getX();
        } else {
            float f = (this.yBodyRot + (float)(180 * (pHead - 1))) * ((float)Math.PI / 180F);
            float f1 = Mth.cos(f);
            return this.getX() + (double)f1 * 1.3;
        }
    }

    private double getHeadY(int pHead) {
        return pHead <= 0 ? this.getY() + (double)3.0F : this.getY() + 2.2;
    }

    private double getHeadZ(int pHead) {
        if (pHead <= 0) {
            return this.getZ();
        } else {
            float f = (this.yBodyRot + (float)(180 * (pHead - 1))) * ((float)Math.PI / 180F);
            float f1 = Mth.sin(f);
            return this.getZ() + (double)f1 * 1.3;
        }
    }

    private float rotlerp(float pAngle, float p_31444_, float p_31445_) {
        float f = Mth.wrapDegrees(p_31444_ - pAngle);
        if (f > p_31445_) {
            f = p_31445_;
        }

        if (f < -p_31445_) {
            f = -p_31445_;
        }

        return pAngle + f;
    }

    private void performRangedAttack(int pHead, LivingEntity pTarget) {
        this.performRangedAttack(pHead, pTarget.getX(), pTarget.getY() + (double)pTarget.getEyeHeight() * (double)0.5F, pTarget.getZ(), pHead == 0 && this.random.nextFloat() < 0.001F);
    }

    private void performRangedAttack(int pHead, double pX, double pY, double pZ, boolean pIsDangerous) {
        if (!this.isSilent()) {
            this.level().levelEvent(null, 1024, this.blockPosition(), 0);
        }

        double d0 = this.getHeadX(pHead);
        double d1 = this.getHeadY(pHead);
        double d2 = this.getHeadZ(pHead);
        double d3 = pX - d0;
        double d4 = pY - d1;
        double d5 = pZ - d2;
        float yaw = this.getYRot();
        float pitch = this.getXRot();
        float f = -Mth.sin(yaw * ((float) Math.PI / 180F)) * Mth.cos(pitch * ((float) Math.PI / 180F));
        float g = -Mth.sin(pitch * ((float) Math.PI / 180F));
        float h = Mth.cos(yaw * ((float) Math.PI / 180F)) * Mth.cos(pitch * ((float) Math.PI / 180F));
        DCWitherSkull witherskull = new DCWitherSkull(DCEntities.DC_WITHER_SKULL.get(), level);
        witherskull.setShootEntity(this);
        witherskull.shoot(f, g, h, 4, (float) 12.0);
        witherskull.setOwner(this);
        if (pIsDangerous) witherskull.setDangerous(true);
        witherskull.setPosRaw(d0, d1, d2);
        level.addFreshEntity(witherskull);
    }

    @Override
    public void performRangedAttack(LivingEntity pTarget, float pDistanceFactor) {
        this.performRangedAttack(0, pTarget);
    }

    @Override
    public void dropCustomDeathLoot(@NotNull DamageSource pDamageSource, int pLooting, boolean pHitByPlayer) {
        super.dropCustomDeathLoot(pDamageSource, pLooting, pHitByPlayer);
        this.spawnAtLocation(getStack());
    }

    ItemStack getStack() {
        ItemStack stack = new ItemStack(DCItems.OCEAN_SCYTHE.get());
        UseCountItem.setUseS(stack, Integer.MAX_VALUE);
        return stack;
    }


    @Override
    public void checkDespawn() {
        if (this.level().getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.discard();
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    public float getMaxDamageHurt() {
        return 20;
    }

    @Override
    public BossEvent.BossBarColor getBossBarColor() {
        return BossEvent.BossBarColor.PURPLE;
    }

    @Override
    public SoundEvent getBossMusic() {
        return DCSounds.Moog_City_2.get();
    }

    public boolean addEffect(MobEffectInstance pEffectInstance, @Nullable Entity pEntity) {
        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 300.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.6F)
                .add(Attributes.FLYING_SPEED, 0.6F)
                .add(Attributes.FOLLOW_RANGE, 40.0F)
                .add(DCAttributes.DC_DEFENSE.get(), 2.3D)
                .add(Attributes.ARMOR, 4.0F);
    }

    public float getHeadYRot(int pHead) {
        return this.yRotHeads[pHead];
    }

    public float getHeadXRot(int pHead) {
        return this.xRotHeads[pHead];
    }

    public int getInvulnerableTicks() {
        return this.entityData.get(DATA_ID_INV);
    }

    public void setInvulnerableTicks(int pInvulnerableTicks) {
        this.entityData.set(DATA_ID_INV, pInvulnerableTicks);
    }

    public int getAlternativeTarget(int pHead) {
        return this.entityData.get(DATA_TARGETS.get(pHead));
    }

    public void setAlternativeTarget(int pTargetOffset, int pNewId) {
        this.entityData.set(DATA_TARGETS.get(pTargetOffset), pNewId);
    }

    @Override
    public boolean isPowered() {
        return this.getHealth() <= this.getMaxHealth() / 2.0F;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    protected boolean canRide(Entity pEntity) {
        return false;
    }

    public boolean canChangeDimensions() {
        return false;
    }

    public boolean canBeAffected(MobEffectInstance pPotioneffect) {
        return pPotioneffect.getEffect() != MobEffects.WITHER && super.canBeAffected(pPotioneffect);
    }

    @Override
    public float getMaxHealth() {
        return 333.3F;
    }

    public int getDeathTimeBoom() {
        return entityData.get(DEATH_TIME_BOOM);
    }

    public void setDeathTimeBoom(int value) {
        entityData.set(DEATH_TIME_BOOM, value);
    }

    public void setDeathSecond(int value) {
        setDeathTimeBoom(value * 20);
    }

    public void removeDeathTimeBoom(int value) {
        if (value > 0) setDeathTimeBoom(getDeathTimeBoom() - value);
    }

    static {
        DATA_TARGET_A = SynchedEntityData.defineId(DCWither.class, EntityDataSerializers.INT);
        DATA_TARGET_B = SynchedEntityData.defineId(DCWither.class, EntityDataSerializers.INT);
        DATA_TARGET_C = SynchedEntityData.defineId(DCWither.class, EntityDataSerializers.INT);
        DEATH_TIME_BOOM = SynchedEntityData.defineId(DCWither.class, EntityDataSerializers.INT);
        DATA_TARGETS = ImmutableList.of(DATA_TARGET_A, DATA_TARGET_B, DATA_TARGET_C);
        DATA_ID_INV = SynchedEntityData.defineId(DCWither.class, EntityDataSerializers.INT);
        LIVING_ENTITY_SELECTOR = (p_31504_) -> p_31504_.getMobType() != MobType.UNDEAD && p_31504_.attackable();
        TARGETING_CONDITIONS = TargetingConditions.forCombat().range((double)20.0F).selector(LIVING_ENTITY_SELECTOR);
    }

    class WitherDoNothingGoal extends Goal {
        public WitherDoNothingGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP, Flag.LOOK));
        }

        public boolean canUse() {
            return DCWither.this.getInvulnerableTicks() > 0;
        }
    }
}
