package net.daichang.dcmods.common.entities;

import net.daichang.dcmods.Config;
import net.daichang.dcmods.event.DCForgeEventHandler;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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

public class BossEntity extends Monster {
    private final BossMusic music = new BossMusic(this);

    public BossEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(2, new RandomStrollGoal(this, 1));
    }

    public ResourceLocation getBossBar() {
        return null;
    }

    public ResourceLocation getBossBarOn() {
        return null;
    }

    public ResourceLocation getBossBarMask() {
        return null;
    }

    public SoundEvent getBossMusic() {
        return null;
    }

    public boolean isHasMask() {
        return false;
    }

    public Font getBossBarFont() {
        return Minecraft.getInstance().font;
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
            if (!(pEntity instanceof Player) && pEntity instanceof LivingEntity living) EntityHelper.forceOceanHurt(living, damage);
        }
        return super.doHurtTarget(pEntity);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pAmount > getMaxDamageHurt()) pAmount = getMaxDamageHurt();
        if (isUnsafeDamage(pSource)) return false;
        this.setDeltaMovement(Vec3.ZERO);
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
        DCForgeEventHandler.bossList.remove(this);
    }

    @Override
    public void heal(float pHealAmount) {
        super.heal(pHealAmount);
    }

    @Override
    public void remove(RemovalReason pReason) {
        super.remove(pReason);
        DCForgeEventHandler.bossList.remove(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide() && Config.Client.boss_music.get()) BossMusic.playMusic(music ,this);
        this.resetFallDistance();
        this.fallDistance = 0;
    }

    @Override
    public void setRemoved(RemovalReason pRemovalReason) {
        super.setRemoved(pRemovalReason);
        DCForgeEventHandler.bossList.remove(this);
    }

    @Override
    public void onClientRemoval() {
        super.onClientRemoval();
        DCForgeEventHandler.bossList.remove(this);
    }

    @Override
    public void onRemovedFromWorld() {
        super.onRemovedFromWorld();
        DCForgeEventHandler.bossList.remove(this);
    }
}
