package net.daichang.dcmods.common.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class DCBaseMonster extends Monster {
    private int dcHurtTime;
    private final int maxDCHurtTime = 20;
    public DCBaseMonster(EntityType<? extends DCBaseMonster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Component getDCName() {
        return Component.empty();
    }

    public void setDeathTime(int second) {
        deathTime = second * 20;
    }

    public int getDeathTime() {
        return deathTime / 20;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (dcHurtTime > 0) --dcHurtTime;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.dcHurtTime = pCompound.getShort("dcHurtTime");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putShort("dcHurtTime", (short) this.dcHurtTime);
    }

    @Override
    public void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    public boolean isInvulnerable() {
        return dcHurtTime > 0;
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        if (dcHurtTime > 0) return false;
        dcHurtTime = maxDCHurtTime;
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void animateHurt(float pYaw) {
        super.animateHurt(pYaw);
        this.dcHurtTime = maxDCHurtTime;
    }

    @Override
    public void handleEntityEvent(byte pId) {
        super.handleEntityEvent(pId);
    }

    @Override
    public void handleDamageEvent(DamageSource pDamageSource) {
        super.handleDamageEvent(pDamageSource);
        this.dcHurtTime = maxDCHurtTime;
    }

    @Override
    public boolean isOnFire() {
        return false;
    }
}
