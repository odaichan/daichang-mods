package net.daichang.dcmods.common.entities;

import net.daichang.dcmods.Config;
import net.daichang.dcmods.event.DCForgeEventHandler;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.utils.EntityHurtUtil;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BossEntity extends Monster {
    public final DCServerBossEvent bossEvent;

    private final BossMusic music = new BossMusic(this);

    public BossEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.bossEvent = new DCServerBossEvent(this, getBossBarColor());
        if (this.level().isClientSide()) DCForgeEventHandler.BOSSES.add(this);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
    }

    @Override
    public void startSeenByPlayer(ServerPlayer pServerPlayer) {
        super.startSeenByPlayer(pServerPlayer);
        this.bossEvent.addPlayer(pServerPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer pServerPlayer) {
        super.stopSeenByPlayer(pServerPlayer);
        this.bossEvent.removePlayer(pServerPlayer);
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
        if (Config.Server.boss_super_hurt.get()) {
            float damage = 0;
            damage = (float) (damage + this.getAttributeValue(Attributes.ATTACK_DAMAGE));
            damage = (float) (damage + this.getAttributeValue(DCAttributes.DC_SUPER_DAMAGE.get()));
            if (!(pEntity instanceof Player) && pEntity instanceof LivingEntity living) {
                EntityHurtUtil util = EntityHurtUtil.getInstance(living, this);
                util.dcHurt(damage);
                if (living.getHealth() < 2) {
                    DataHelper.setIsDead(living, true);
                }
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
        DCForgeEventHandler.BOSSES.add(this);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pAmount > getMaxDamageHurt()) pAmount = getMaxDamageHurt();
        if (isUnsafeDamage(pSource)) return false;
        this.setDeltaMovement(Vec3.ZERO);
        Entity entity = pSource.getEntity();
        if (entity instanceof LivingEntity living && !(living instanceof BossEntity)) this.setTarget(living);
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
    public void readAdditionalSaveData(CompoundTag pCompound) {
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
    public void die(DamageSource pDamageSource) {
        super.die(pDamageSource);
    }

    @Override
    public void heal(float pHealAmount) {
        super.heal(pHealAmount);
    }

    @Override
    public void remove(RemovalReason pReason) {
        super.remove(pReason);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide() && Config.Client.boss_music.get()) BossMusic.playMusic(music ,this);
        this.resetFallDistance();
        this.fallDistance = 0;
        bossEvent.setName(this.getDisplayName());
        bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }

    @Override
    public void setRemoved(@NotNull RemovalReason pRemovalReason) {
        super.setRemoved(pRemovalReason);
    }

    @Override
    public void onClientRemoval() {
        super.onClientRemoval();
    }

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();
    }

    public List<ServerPlayer> getAllPlayer() {
        List<ServerPlayer> list = new ArrayList<>();
        if (level instanceof ServerLevel serverLevel) {
            MinecraftServer server = serverLevel.getServer();
            list.addAll(server.getPlayerList().getPlayers());
        }
        return list;
    }
}
