package net.daichang.dcmods.common.entities.boss;

import net.daichang.dcmods.client.PacketHandler;
import net.daichang.dcmods.client.font.DCEntityFont;
import net.daichang.dcmods.client.font.DCItemFont;
import net.daichang.dcmods.client.network.S2CElainaPacket;
import net.daichang.dcmods.client.network.S2CLastKillPlayer;
import net.daichang.dcmods.client.network.S2CSonicBoomPacket;
import net.daichang.dcmods.common.entities.BossEntity;
import net.daichang.dcmods.common.entities.entity.RainbowLightingEntity;
import net.daichang.dcmods.common.entities.projectile.DCWitherSkull;
import net.daichang.dcmods.event.DCForgeEventHandler;
import net.daichang.dcmods.inits.*;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.helpers.MathHelper;
import net.daichang.dcmods.utils.helpers.ParticleHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PlayMessages;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class DCLoveElaina extends BossEntity implements PowerableMob, RangedAttackMob {
    public static EntityDataAccessor<Integer> ATTACK_COUNT;
    public static EntityDataAccessor<Boolean> IS_RANGE_ATTACK;
    private int rangeAttackLife = 0;
    private final Difficulty difficulty = level.getDifficulty();

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState attackAnimationState_1 = new AnimationState();
    public final AnimationState attackAnimationState_2 = new AnimationState();
    public final AnimationState attackAnimationState_3 = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState isDeadAnimationState = new AnimationState();
    public final AnimationState noGroundAnimationState = new AnimationState();

    public DCLoveElaina(EntityType<DCLoveElaina> p_31437_, Level p_31438_) {
        super(p_31437_, p_31438_);
        this.xpReward = 1500;
    }

    public DCLoveElaina(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super(DCEntities.DC_WITHER.get(), world);
        this.xpReward = 1500;
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag p_21450_) {
        super.readAdditionalSaveData(p_21450_);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true) {
            @Override
            protected double getAttackReachSqr(@NotNull LivingEntity entity) {
                return 64.0D;
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
                return 64.0D;
            }
        });
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.tickCount % 20 == 0) {
            float value = 0;
            switch (difficulty) {
                case PEACEFUL, EASY -> value = 4;
                case NORMAL -> value = 7;
                case HARD -> value = 1145;
            }
            EntityHelper.forceHeal(this, value);
        }
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float damage) {
        if (damage > 20) damage = 20;
        if (getHealth() <= 10 || isDeadOrDying() || isUnsafeDamage(damageSource)) return false;
        this.setDeltaMovement(Vec3.ZERO);
        Entity entity = damageSource.getEntity();
        double canTeleport = MathHelper.getRandomDouble(0.0D, 1.0D);
        if (entity instanceof LivingEntity living && !(living instanceof ServerPlayer player && player.isCreative())) {
            this.setTarget(living);
        }
        if (canTeleport == 0.2 && entity != null) doHurtTarget(entity);
        this.addAttackCount(1);
        return super.hurt(damageSource, damage);
    }

    public static boolean isUnsafeDamage(DamageSource d) {
        return d.is(DamageTypes.GENERIC)
                || d.is(DamageTypes.GENERIC_KILL)
                || d.is(DamageTypes.FELL_OUT_OF_WORLD)
                || d.is(DamageTypes.ARROW)
                || d.is(DamageTypes.WITHER)
                || d.is(DamageTypes.WITHER_SKULL)
                || d.is(DamageTypes.EXPLOSION)
                || d.is(DamageTypes.MAGIC);
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ILLAGER;
    }

    @Override
    public void die(@NotNull DamageSource p_21014_) {
        if (getHealth() <= 0) {
            super.die(p_21014_);
            DCForgeEventHandler.bossList.remove(this);
        }
    }

    @Override
    public void heal(float pHealAmount) {}

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        addAttackCount(1);
        if (target instanceof LivingEntity living && !(target instanceof Player)) {
            Utils.attackEntity(living, this);
            if (getHealth() < getMaxHealth() * 0.3) lastKill(living);
        }
        else if (target instanceof Player player){
            player.hurt(EntityHelper.mob_attack_damage(this), 5);
            player.hurtTime = 0;
            player.hurtDuration = 0;
            player.setDeltaMovement(0, 0, 0);
            player.setInvulnerable(false);
            player.invulnerableTime = 0;
        }
        if (level instanceof ServerLevel serverLevel) {
            for (ServerPlayer player : serverLevel.players()) PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(()->player), new S2CElainaPacket(new Vec3(target.getX(), target.getY(), target.getZ())));
        }
        if (getAttackValue() >= 7) {
            Vec3 jumpDirection = new Vec3(- this.getLookAngle().normalize().x, 0.2F, - this.getLookAngle().normalize().z);
            this.setDeltaMovement(jumpDirection.scale(1.5F));
        }
        if (level.isClientSide()) this.swing(InteractionHand.MAIN_HAND);
        return true;
    }

    @Override
    public void tickDeath() {
        if (getHealth() <= 0) {
            ++this.deathTime;
            isDeadAnimationState.startIfStopped(tickCount);
            if (this.deathTime >= 2000) {
                try {
                    this.playSound(this.getDeathSound());
                } catch (Exception ignored){}
                DCForgeEventHandler.bossList.remove(this);
                this.dropExperience();
                this.die(this.damageSources().magic());
                this.remove(RemovalReason.KILLED);
                this.setRemoved(RemovalReason.KILLED);
            }
        }
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.WITHER_DEATH;
    }

    ItemStack superSword() {
        ItemStack stack = new ItemStack(DCItems.SUPER_WOOD_SWORD.get());
        stack.enchant(DCEnch.SuperSharp.get(), 10);
        stack.enchant(Enchantments.SHARPNESS, 10);
        stack.enchant(Enchantments.SMITE, 10);
        stack.enchant(Enchantments.BANE_OF_ARTHROPODS, 10);
        stack.enchant(Enchantments.BLOCK_EFFICIENCY, 5);
        stack.enchant(Enchantments.UNBREAKING, 5);
        stack.enchant(Enchantments.BLOCK_FORTUNE, 5);
        stack.enchant(Enchantments.INFINITY_ARROWS, 3);
        stack.getOrCreateTag().putInt("dc_attking", Integer.MAX_VALUE);
        return stack;
    }

    @Override
    public void onRemovedFromWorld() {
        if (this.deathTime >= 1200) super.onRemovedFromWorld();
        DCForgeEventHandler.bossList.remove(this);
    }

    @Override
    public void onClientRemoval() {
        if (this.deathTime >= 1200) super.onClientRemoval();
        DCForgeEventHandler.bossList.remove(this);
    }

    @Override
    public void dropCustomDeathLoot(DamageSource pDamageSource, int pLooting, boolean pHitByPlayer) {
        super.dropCustomDeathLoot(pDamageSource, pLooting, pHitByPlayer);
    }

    @Override
    public void dropAllDeathLoot(DamageSource pDamageSource) {
        super.dropAllDeathLoot(pDamageSource);
        try {
            ItemEntity item = new ItemEntity(level, getX(), getY(), getZ(), superSword());
            if (level instanceof ServerLevel serverLevel) for (ServerPlayer serverPlayer : serverLevel.players())serverPlayer.displayClientMessage(Component.literal(DCItemFont.getString("entities.dc_mods.dc_wither_name") + " left the game").withStyle(ChatFormatting.YELLOW), false);
            level.addFreshEntity(item);
            item.getPersistentData().putInt("isDCItem", 1);
        } catch (Exception ignored) {}
    }

    @Override
    public void remove(@NotNull RemovalReason p_276115_) {
        if (this.deathTime >= 1200 && getHealth() <= 0) super.remove(p_276115_);
        DCForgeEventHandler.bossList.remove(this);
    }

    @Override
    public @Nullable Component getCustomName() {
        if (isDeadOrDying() || this.getHealth() <= 10) return Component.translatable("entities.dc_mods.dc_wither_name_tow");
        return Component.translatable("entities.dc_mods.dc_wither_name");
    }

    @Override
    public boolean shouldDropLoot() {
        return true;
    }

    @Override
    public boolean isCustomNameVisible() {
        return true;
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        if (level instanceof ServerLevel serverLevel) {
            for (ServerPlayer serverPlayer : serverLevel.players()) serverPlayer.displayClientMessage(Component.literal(DCItemFont.getString("entities.dc_mods.dc_wither_name") + " join the game").withStyle(ChatFormatting.YELLOW), false);
        }
    }

    @Override
    public @NotNull Collection<ItemEntity> captureDrops(Collection<ItemEntity> value) {
        return super.captureDrops(value);
    }

    private int currentAttackAnimation = 0;
    private int animationDelay = 0;
    private int rangeAttackTimer = 0;
    private int rangeAttackCount = 0;

    @Override
    public void tick() {
        CompoundTag tag = this.getPersistentData();
        Vec3 vec3 = this.getDeltaMovement();
        if (vec3.y > 2) this.setDeltaMovement(vec3.x, 0, vec3.y);
        super.tick();
        this.wasOnFire = false;
        this.clearFire();
        this.resetFallDistance();
        this.fallDistance = 0;
        if (level().isClientSide()) {
            idleAnimationState.startIfStopped(this.tickCount);
            if (this.swinging && animationDelay == 0) {
                if (currentAttackAnimation == 0) {
                    attackAnimationState.start(tickCount);
                    currentAttackAnimation = 1;
                } else if (currentAttackAnimation == 1) {
                    attackAnimationState_1.start(tickCount);
                    currentAttackAnimation = 2;
                } else if (currentAttackAnimation == 2) {
                    attackAnimationState_2.start(tickCount);
                    currentAttackAnimation = 0;
                }
                animationDelay = 10;
                setIsRangeAttack(false);
            }
            if (animationDelay > 0) {
                animationDelay--;
            }
            if (!onGround()) {
                noGroundAnimationState.startIfStopped(tickCount);
            } else {
                noGroundAnimationState.stop();;
            }
            if (this.getDeltaMovement().lengthSqr() > 0.01D && onGround()) {
                walkAnimationState.startIfStopped(tickCount);
                setIsRangeAttack(false);
            }
            else walkAnimationState.stop();
            if (this.isDeadOrDying() || this.getHealth() < 10) ParticleHelper.drawSixStar(this, level);
        }
        else {
            for (Entity entity : EntityHelper.getEntity(this.level, this.getX(), this.getY(), this.getZ(), 200)) {
                if (!(tag.contains("isInPower")) && isPowered()) {
                    tag.putInt("isInPower", 1);
                    for (int a = 0; a < 20; a ++) this.heal(200);
                    if (entity instanceof LivingEntity target && !(entity instanceof Player)) {
                        Utils.attackEntity(target, this);
                        Utils.attackEntity(target, target);
                        RainbowLightingEntity lighting = new RainbowLightingEntity(DCEntities.RAINBOW_LIGHTING.get(), level);
                        lighting.setPos(target.getX(), target.getY(), target.getY());
                        target.addEffect(EffectHelper.addEffect(DCEffects.Bloodshed.get()));
                    }
                }
            }
            LivingEntity living = getTarget();
            double count = MathHelper.getRandomDouble(0.00D, 1.00D);
            if (living != null) {
                this.getLookControl().setLookAt(living);
                if (getAttackValue() >= 7) {
                    rangeAttackTimer++;
                    if (rangeAttackTimer >= 2 && rangeAttackCount < 10) { // 2 ticks = 0.10 seconds
                        performRangedAttack(living, 2);
                        rangeAttackCount++;
                        rangeAttackTimer = 0; // Reset timer
                    }
                    if (rangeAttackCount >= 10) {
                        rangeAttackCount = 0;
                        rangeAttackLife++;
                        if (rangeAttackLife < 180) {
                            lastKill(living);
                            setAttackCount(0);
                            rangeAttackLife = 0;
                        }
                    }
                }
                if (getAttackValue() >= 7 || count == 0.1D) performRangedAttack(living, 2);
                setIsRangeAttack(false);
            }
            if (this.isDeadOrDying() || this.getHealth() < 10) {
                if (this.deathTime > 0) {
                    this.hurtMarked = false;
                    this.setTarget(null);
                    this.setNoAi(true);
                    this.hurtDuration = 0;
                    this.hurtTime = 0;
                    this.setDeltaMovement(0, 0, 0);
                    this.setInvulnerable(true);
                    ParticleHelper.drawSixStar(this, level);
                    setHealth(0.0F);
                    EntityHelper.forceSetHealth(this, 0.0F);
                    for (Entity entity : EntityHelper.getEntity(this.level, this.getX(), this.getY(), this.getZ(), 200)) {
                        if (entity != this) lastKill(entity);
                    }
                }
            }
            if (getHealth() > getMaxHealth()) {
                setHealth(getMaxHealth());
                EntityHelper.forceSetHealth(this, getMaxHealth());
            }
        }
    }

    void lastKill(Entity entity) {
        if (entity instanceof LivingEntity target && !(entity instanceof Player)) {
            Utils.attackEntity(target, this);
            target.addEffect(EffectHelper.addEffect(DCEffects.Freeze.get()));
        }
        else if (entity instanceof ServerPlayer player && player.gameMode.isSurvival()) {
            player.hurt(EntityHelper.dc_damage(this), 1);
            PacketHandler.sendToClient(new S2CLastKillPlayer(player.getId()));
        }
    }

    @Override
    public Font getBossBarFont() {
        return DCEntityFont.getFont();
    }

    @Override
    public boolean isInvulnerable() {
        return getHealth() <= 0;
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag p_31485_) {
        super.addAdditionalSaveData(p_31485_);
    }

    @Override
    public void setHealth(float p_21154_) {
        if (p_21154_ > this.getHealth() - 20) super.setHealth(p_21154_);
    }

    @Override
    public boolean isDeadOrDying() {
        return getHealth() <= 0;
    }

    @Override
    public boolean shouldShowName() {
        return true;
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 520.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 19.2)
                .add(Attributes.ARMOR_TOUGHNESS, 4.7D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 32.1D)
                .add(ForgeMod.ENTITY_REACH.get(), 4.6D)
                .add(DCAttributes.DC_SUPER_DAMAGE.get(), 15.2D)
                .add(DCAttributes.DC_DEFENSE.get(), 10.0D)
                .add(Attributes.ARMOR, 7.3D);
    }

    @Override
    public float getMaxHealth() {
        return 520.0F;
    }

    @Override
    public boolean isPowered() {
        return getHealth() > getMaxHealth() / 2;
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity livingEntity, float v) {
        DCWitherSkull skull = new DCWitherSkull(DCEntities.DC_WITHER_SKULL.get(), level);
        skull.setXRot(this.getXRot());
        skull.setYRot(this.getYRot());
        skull.setPos(this.getX(), this.getY() + 2, this.getZ());
        level.addFreshEntity(skull);
        float yaw = this.getYRot();
        float pitch = this.getXRot();
        float f = -Mth.sin(yaw * ((float) Math.PI / 180F)) * Mth.cos(pitch * ((float) Math.PI / 180F));
        float g = -Mth.sin(pitch * ((float) Math.PI / 180F));
        float h = Mth.cos(yaw * ((float) Math.PI / 180F)) * Mth.cos(pitch * ((float) Math.PI / 180F));
        skull.shoot(f, g, h, 4.2F, (float) 12.0);
        if (!(livingEntity instanceof Player)) {
            livingEntity.addEffect(EffectHelper.addEffect(DCEffects.Freeze.get(), 60));
            livingEntity.addEffect(EffectHelper.addEffect(DCEffects.Bloodshed.get()));
            lastKill(livingEntity);
        }
        if (livingEntity instanceof Player player) {
            player.addEffect(EffectHelper.addEffect(MobEffects.HUNGER, 60));
        }
        setIsRangeAttack(true);
        Vec3 thisVec = new Vec3(getX(), getY(), getZ());
        Vec3 targetVec = new Vec3(livingEntity.getX(), livingEntity.getX(), livingEntity.getZ());
        if (level instanceof ServerLevel serverLevel) {
            for (ServerPlayer player : serverLevel.players()) PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(()->player), new S2CSonicBoomPacket(thisVec, targetVec));
        }
    }

    public int getAttackValue() {
        return entityData.get(ATTACK_COUNT);
    }

    public boolean isOnRangeAttack() {
        return entityData.get(IS_RANGE_ATTACK);
    }

    public void setIsRangeAttack(boolean value) {
        entityData.set(IS_RANGE_ATTACK, value);
    }

    public void setAttackCount(int value) {
        entityData.set(ATTACK_COUNT, value);
    }

    public void addAttackCount(int value) {
        setAttackCount(getAttackValue() + value);
    }

    public SoundEvent getBossMusic() {
        return DCSounds.BOSS_FIGHT.get();
    }

    @Override
    public ResourceLocation getBossBar() {
        return new ResourceLocation("dc_m:textures/entities/health_bar_1.png");
    }

    @Override
    public ResourceLocation getBossBarOn() {
        return new ResourceLocation("dc_m:textures/entities/health_bar_2.png");
    }

    @Override
    public boolean isHasMask() {
        return true;
    }

    @Override
    public ResourceLocation getBossBarMask() {
        return new ResourceLocation("dc_m:textures/entities/health_bar_3.png");
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_COUNT, 0);
        this.entityData.define(IS_RANGE_ATTACK, false);
    }

    static {
        ATTACK_COUNT = SynchedEntityData.defineId(DCLoveElaina.class, EntityDataSerializers.INT);
        IS_RANGE_ATTACK = SynchedEntityData.defineId(DCLoveElaina.class, EntityDataSerializers.BOOLEAN);
    }
}
