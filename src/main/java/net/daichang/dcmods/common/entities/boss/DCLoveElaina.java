package net.daichang.dcmods.common.entities.boss;

import net.daichang.dcmods.Config;
import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.client.PacketHandler;
import net.daichang.dcmods.client.font.DCEntityFont;
import net.daichang.dcmods.client.font.DCItemFont;
import net.daichang.dcmods.client.network.*;
import net.daichang.dcmods.common.entities.BossEntity;
import net.daichang.dcmods.common.entities.projectile.DCWitherSkull;
import net.daichang.dcmods.common.item.UseCountItem;
import net.daichang.dcmods.event.DCForgeEventHandler;
import net.daichang.dcmods.inits.*;
import net.daichang.dcmods.utils.EntityActuallyHurt;
import net.daichang.dcmods.utils.ModUtil;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.helpers.MathHelper;
import net.daichang.dcmods.utils.helpers.ParticleHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.commands.arguments.EntityAnchorArgument;
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
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
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

public class DCLoveElaina extends BossEntity implements PowerableMob, RangedAttackMob {
    public static EntityDataAccessor<Integer> ATTACK_COUNT;
    public static EntityDataAccessor<Boolean> IS_RANGE_ATTACK;
    private int rangeAttackLife = 0;
    private final Difficulty difficulty = level.getDifficulty();
    private final int maxDeathTime = 2000;

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
        this.xpReward = 200000;
    }

    public DCLoveElaina(PlayMessages.SpawnEntity spawnEntity, Level world) {
        super(DCEntities.ELAINA.get(), world);
        this.xpReward = 200000;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0, true) {
            @Override
            protected double getAttackReachSqr(@NotNull LivingEntity entity) {
                return 74.0D;
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
    protected void customServerAiStep() {
        super.customServerAiStep();
    }

    @Override
    public boolean hurt(@NotNull DamageSource damageSource, float damage) {
        if (getHealth() <= 0 || isDeadOrDying()) return false;
        this.addAttackCount(1);
        Entity entity = damageSource.getEntity();
        if (entity instanceof LivingEntity living)
            EntityActuallyHurt.getInstance(living, this).actuallyHurt(damageSource, damage);
        return super.hurt(damageSource, damage);
    }


    @Override
    public @NotNull MobType getMobType() {
        return MobType.ILLAGER;
    }

    @Override
    public void die(@NotNull DamageSource p_21014_) {
        if (isDeadOrDying()) super.die(p_21014_);
    }

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
        ++this.deathTime;
        isDeadAnimationState.startIfStopped(tickCount);
        if (this.deathTime >= maxDeathTime + 100) {
            try {
                this.playSound(this.getDeathSound());
            } catch (Exception ignored){}
            DCForgeEventHandler.bossList.remove(this);
            sendPlayerMsg(Component.translatable("chat.dc_m.elaina.elaina_die"));
            this.dropExperience();
            this.die(this.damageSources().magic());
            this.remove(RemovalReason.KILLED);
            this.setRemoved(RemovalReason.KILLED);
            this.onClientRemoval();
            this.onRemovedFromWorld();
        }
    }

    @Override
    public @NotNull SoundEvent getDeathSound() {
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
        UseCountItem.setUseS(stack, Integer.MAX_VALUE);
        return stack;
    }

    @Override
    public void dropAllDeathLoot(DamageSource pDamageSource) {
        super.dropAllDeathLoot(pDamageSource);
        try {
            ItemEntity item = new ItemEntity(level, getX(), getY(), getZ(), superSword());
            if (level instanceof ServerLevel serverLevel) for (ServerPlayer serverPlayer : serverLevel.players())serverPlayer.displayClientMessage(Component.literal(DCItemFont.getString("entities.dc_mods.dc_wither_name") + " left the game").withStyle(ChatFormatting.YELLOW), false);
            level.addFreshEntity(item);
        } catch (Exception ignored) {}
    }

    @Override
    public void remove(@NotNull RemovalReason p_276115_) {
        if (this.deathTime >= maxDeathTime || isDeadOrDying()) super.remove(p_276115_);
    }

    @Override
    public @Nullable Component getCustomName() {
        if (isDeadOrDying()) return Component.translatable("entities.dc_mods.dc_wither_name_tow");
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
        if (level instanceof ServerLevel serverLevel)
            for (ServerPlayer serverPlayer : serverLevel.players()) serverPlayer.displayClientMessage(Component.literal(DCItemFont.getString("entities.dc_mods.dc_wither_name") + " join the game").withStyle(ChatFormatting.YELLOW), false);
    }

    private int currentAttackAnimation = 0;
    private int animationDelay = 0;
    private int rangeAttackTimer = 0;
    private int rangeAttackCount = 0;

    @Override
    public void tick() {
        super.tick();
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
            this.wasOnFire = false;
            this.clearFire();
            LivingEntity living = getTarget();
            double count = MathHelper.getRandomDouble(0.00D, 1.00D);
            if (living != null) {
                this.getLookControl().setLookAt(living);
                this.lookAt(EntityAnchorArgument.Anchor.EYES, living.position());
                if (getAttackValue() >= 7 || isCanRangeAttack(living)) {
                    this.setDeltaMovement(Vec3.ZERO);
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
            if (isAlive()) {
                setNoAi(false);
                if (this.tickCount % 20 == 0) {
                    float value = 0;
                    switch (difficulty) {
                        case PEACEFUL, EASY -> value = 4;
                        case NORMAL -> value = 7;
                        case HARD -> value = 200;
                    }
                    if (Config.Server.elaina_super_mode.get()) value = 2000;
                    EntityHelper.forceHeal(this, value);
                }
                if (this.getTarget() == null && this.tickCount % 100 == 0) {
                    int random = MathHelper.getRandomInt(1, 5);
                    switch (random) {
                        case 1 -> sendPlayerMsg(Component.translatable("chat.dc_m.elaina.tell_player_1"));
                        case 2 -> sendPlayerMsg(Component.translatable("chat.dc_m.elaina.tell_player_2"));
                        case 3 -> sendPlayerMsg(Component.translatable("chat.dc_m.elaina.tell_player_3"));
                        case 4 -> sendPlayerMsg(Component.translatable("chat.dc_m.elaina.tell_player_4"));
                        case 5 -> sendPlayerMsg(Component.translatable("chat.dc_m.elaina.tell_player_5"));
                    }
                }
            }
            if (isOnRangeAttack()) PacketHandler.sendToClient(new S2CElainaRangeAttack(this.getId()));
            if (this.deathTime > 0 || isDeadOrDying()) {
                this.hurtMarked = false;
                this.setTarget(null);
                this.setNoAi(true);
                this.hurtDuration = 0;
                this.hurtTime = 0;
                this.setDeltaMovement(Vec3.ZERO);
                this.setInvulnerable(true);
                setHealth(0.0F);
                EntityHelper.forceSetHealth(this, 0.0F);
                Utils.Override_DATA_HEALTH_ID(this, 0.0F);
                for (Entity entity : EntityHelper.getEntity(this.level, this.getX(), this.getY(), this.getZ(), 200)) if (entity != this) lastKill(entity);
            }
        }
    }

    void sendPlayerMsg(Component msg) {
        if (level instanceof ServerLevel serverLevel) {
            for (ServerPlayer player : serverLevel.players()) if (this.distanceTo(player) <= 40) player.displayClientMessage(Component.literal("<" + this.getCustomName().getString() + "> " + msg.getString()), false);
        }
    }

    boolean isCanRangeAttack(LivingEntity living) {
        int rangeAttackDelay = 10;
        switch (difficulty) {
            case NORMAL -> rangeAttackDelay = 40;
            case EASY, PEACEFUL -> rangeAttackDelay = 60;
            case HARD -> rangeAttackDelay = 20;
        }
        return this.distanceTo(living) > 20 && living.isAlive() & this.tickCount % rangeAttackDelay == 0;
    }

    void lastKill(Entity entity) {
        if (entity instanceof LivingEntity target && target.getHealth() > 0) {
            if (!(target instanceof Player)) {
                Utils.attackEntity(target, this);
                target.addEffect(EffectHelper.addEffect(MobEffects.DARKNESS, 20, 1));
                target.addEffect(EffectHelper.addEffect(DCEffects.Bloodshed.get(), 20, 5, true));
            }
            else if (target instanceof ServerPlayer player && player.gameMode.isSurvival() && !EffectHelper.hasEffect(player, DCEffects.EnchantressMercy.get())) {
                float value = 2.5F + player.getMaxHealth() * 0.001F;
                if (player.getMaxHealth() > 5128)
                    value = value + player.getHealth() * 0.01F;
                if (player.getMaxHealth() > 1000)
                    value = value + player.getMaxHealth() * 0.01F;
                if (ModUtil.isFELoad()) value = value + player.getMaxHealth() * 0.01F;
                EntityActuallyHurt util = EntityActuallyHurt.getInstance(player, this);
                util.dcHurt(value);
                PacketHandler.sendToClient(new S2CLastKillPlayer(player.getId(), value));
                PacketHandler.sendToClient(new S2CElainaKillAllEntity(this.getId()));
            }
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
    public boolean isDeadOrDying() {
        return getHealth() <= 0;
    }

    @Override
    public boolean shouldShowName() {
        return true;
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5200.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 49.2)
                .add(Attributes.ARMOR_TOUGHNESS, 8.9D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 32.1D)
                .add(ForgeMod.ENTITY_REACH.get(), 4.6D)
                .add(DCAttributes.DC_SUPER_DAMAGE.get(), 85.2D)
                .add(DCAttributes.OCEAN_DAMAGE.get(), 45.2D)
                .add(DCAttributes.DC_DEFENSE.get(), 10.0D)
                .add(Attributes.ARMOR, 27.3D);
    }

    @Override
    public float getMaxHealth() {
        if (Config.Server.elaina_super_mode.get()) return 5200.0F;
        return 520.0F;
    }

    @Override
    public BossEvent.BossBarColor getBossBarColor() {
        return BossEvent.BossBarColor.PINK;
    }

    @Override
    public boolean isPowered() {
        return getHealth() > getMaxHealth() / 2;
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity livingEntity, float v) {
        DCWitherSkull skull = new DCWitherSkull(DCEntities.DC_WITHER_SKULL.get(), level);
        skull.setPos(this.getX(), this.getY() + 2, this.getZ());
        level.addFreshEntity(skull);
        float yaw = this.getYRot();
        float pitch = this.getXRot();
        float f = -Mth.sin(yaw * ((float) Math.PI / 180F)) * Mth.cos(pitch * ((float) Math.PI / 180F));
        float g = -Mth.sin(pitch * ((float) Math.PI / 180F));
        float h = Mth.cos(yaw * ((float) Math.PI / 180F)) * Mth.cos(pitch * ((float) Math.PI / 180F));
        skull.shoot(f, g, h, 4, (float) 12.0);
        skull.lookAt(EntityAnchorArgument.Anchor.EYES, livingEntity.position());
        if (!(livingEntity instanceof Player) && !isCanRangeAttack(this)) {
            livingEntity.addEffect(EffectHelper.addEffect(DCEffects.Freeze.get(), 60));
            livingEntity.addEffect(EffectHelper.addEffect(DCEffects.Bloodshed.get()));
            lastKill(livingEntity);
        }
        if (livingEntity instanceof Player player) player.addEffect(EffectHelper.addEffect(MobEffects.HUNGER, 60));
        setIsRangeAttack(true);
        Vec3 thisVec = new Vec3(getX(), getY(), getZ());
        Vec3 targetVec = new Vec3(livingEntity.getX(), livingEntity.getX(), livingEntity.getZ());
        if (level instanceof ServerLevel serverLevel) for (ServerPlayer player : serverLevel.players()) PacketHandler.CHANNEL.send(PacketDistributor.PLAYER.with(()->player), new S2CSonicBoomPacket(thisVec, targetVec));
    }

    public int getAttackValue() {
        return entityData.get(ATTACK_COUNT);
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

    public boolean isOnRangeAttack() {
        return entityData.get(IS_RANGE_ATTACK);
    }

    public SoundEvent getBossMusic() {
        return DCSounds.Moog_City_2.get();
    }

    @Override
    public float getMaxDamageHurt() {
        return 10.0F;
    }

//    @Override
//    public ResourceLocation getBossBar() {
//        return DCMod.getDCEntitiesLocation("health_bar_1");
//    }
//
//    @Override
//    public ResourceLocation getBossBarOn() {
//        return DCMod.getDCEntitiesLocation("health_bar_2");
//    }
//
//    @Override
//    public boolean isHasMask() {
//        return true;
//    }

    @Override
    public ResourceLocation getBossBarOverlay() {
        return ResourceLocation.fromNamespaceAndPath(DCMod.MOD_ID, "textures/gui/elaina_bar.png");
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACK_COUNT, 0);
        this.entityData.define(IS_RANGE_ATTACK, false);
    }

    static {
        IS_RANGE_ATTACK = SynchedEntityData.defineId(DCLoveElaina.class, EntityDataSerializers.BOOLEAN);
        ATTACK_COUNT = SynchedEntityData.defineId(DCLoveElaina.class, EntityDataSerializers.INT);
    }
}
