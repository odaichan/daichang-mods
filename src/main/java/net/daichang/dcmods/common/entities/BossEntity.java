package net.daichang.dcmods.common.entities;

import net.daichang.dcmods.Config;
import net.daichang.dcmods.event.DCForgeEventHandler;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.utils.EntityActuallyHurt;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BossEntity extends DCBaseMonster {
    public final DCServerBossEvent bossEvent;
    private LivingEntity dcLastHurtTarget;
    private final BossMusic music = new BossMusic(this);

    public BossEntity(EntityType<? extends DCBaseMonster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.bossEvent = new DCServerBossEvent(this, getBossBarColor());
        if (this.level().isClientSide()) DCForgeEventHandler.BOSSES.add(this);
    }


    public LivingEntity getDcLastHurtTarget() {
        return dcLastHurtTarget;
    }

    public void setDcLastHurtTarget(LivingEntity dcLastHurtTarget) {
        this.dcLastHurtTarget = dcLastHurtTarget;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
    }

    @Override
    public void startSeenByPlayer(@NotNull ServerPlayer pServerPlayer) {
        super.startSeenByPlayer(pServerPlayer);
        this.bossEvent.addPlayer(pServerPlayer);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (dcLastHurtTarget != null) {
            if (!dcLastHurtTarget.isAlive() && dcLastHurtTarget.isDeadOrDying())
                setDcLastHurtTarget(null);
        }
    }

    @Override
    public void stopSeenByPlayer(@NotNull ServerPlayer pServerPlayer) {
        super.stopSeenByPlayer(pServerPlayer);
        this.bossEvent.removePlayer(pServerPlayer);
    }

    @Override
    public void heal(float pHealAmount) {
        super.heal(pHealAmount);
        if (pHealAmount > 0) EntityHelper.forceHeal(this ,pHealAmount);
    }

    public ResourceLocation getBossBarOverlay() {
        return null;
    }

    public Font getBossBarFont() {
        return Minecraft.getInstance().font;
    }

    public SoundEvent getBossMusic() {
        return null;
    }

    public float getMaxDamageHurt() {
        return Float.MAX_VALUE;
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity pEntity) {
        if (Config.Common.boss_super_hurt.get()) {
            float damage = 0;
            damage = (float) (damage + this.getAttributeValue(Attributes.ATTACK_DAMAGE));
            damage = (float) (damage + this.getAttributeValue(DCAttributes.DC_SUPER_DAMAGE.get()));
            if (!(pEntity instanceof Player) && pEntity instanceof LivingEntity living) {
                EntityActuallyHurt util = EntityActuallyHurt.getInstance(living, this);
                util.dcHurt(damage);
                if (living.getHealth() < 2) DataHelper.setIsDead(living, true);
            }
        }
        return super.doHurtTarget(pEntity);
    }

    public BossEvent.BossBarColor getBossBarColor() {
        return BossEvent.BossBarColor.WHITE;
    }

    @Override
    public boolean addEffect(MobEffectInstance pEffectInstance, @Nullable Entity pEntity) {
        return false;
    }

    @Override
    public boolean addEffect(MobEffectInstance pEffectInstance) {
        return false;
    }

    @Override
    public void forceAddEffect(MobEffectInstance pInstance, @Nullable Entity pEntity) {
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        if (isUnsafeDamage(pSource)) return false;
        this.setDeltaMovement(Vec3.ZERO);
        Entity entity = pSource.getEntity();
        if (entity instanceof LivingEntity living && !(living instanceof BossEntity) && !(living instanceof Player)) this.setTarget(living);
        if (entity instanceof ServerPlayer player && !player.isCreative()) this.setTarget(player);
        if (entity instanceof LivingEntity living) setDcLastHurtTarget(living);
        return super.hurt(pSource, pAmount);
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
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        bossEvent.setID(this.getUUID());
    }

    @Override
    public void setHealth(float pHealth) {
        if (pHealth < getHealth() - getMaxDamageHurt()) pHealth = getHealth() - getMaxDamageHurt();
        super.setHealth(pHealth);
    }

    @Override
    public void kill() {
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide() && Config.Client.boss_music.get()) BossMusic.playMusic(music, this);
        this.resetFallDistance();
        this.fallDistance = 0;
        bossEvent.setName(this.getDisplayName());
        bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
        DataHelper.restHealthDelta(this);
    }

    @Override
    public void handleEntityEvent(byte pId) {
        super.handleEntityEvent(pId);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
    }
}
