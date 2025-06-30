package net.daichang.dcmods.event.dcevents;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Event;

public class DCHurtEntityEvent extends Event {
    private final LivingEntity target;
    private final Entity attacker;
    public DamageSource source;
    private float value;
    public DCHurtEntityEvent(LivingEntity target, Entity attacker, DamageSource damageSource, float amount) {
        this.target = target;
        this.attacker = attacker;
        this.source = damageSource;
        this.value = amount;
    }

    public LivingEntity getTarget() {
        return target;
    }

    public Entity getAttacker() {
        return attacker;
    }

    public float getAmount() {
        return value;
    }

    public void setAmount(float amount) {
        value = amount;
    }
}
