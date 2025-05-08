package net.daichang.dcmods.common.entities.ai;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class DCRangeAttackGoal extends RangedAttackGoal {
    public DCRangeAttackGoal(RangedAttackMob pRangedAttackMob, double pSpeedModifier, int pAttackIntervalMin, int pAttackIntervalMax, float pAttackRadius) {
        super(pRangedAttackMob, pSpeedModifier, pAttackIntervalMin, pAttackIntervalMax, pAttackRadius);
    }

    @Override
    public void tick() {
        double $$0 = this.mob.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
        boolean $$1 = this.mob.getSensing().hasLineOfSight(this.target);
        if ($$1) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }

        this.mob.getLookControl().setLookAt(this.target, 30.0F, 30.0F);
        if (--this.attackTime == 0) {
            if (!$$1) {
                return;
            }

            float $$2 = (float)Math.sqrt($$0) / this.attackRadius;
            float $$3 = Mth.clamp($$2, 0.1F, 1.0F);
            this.rangedAttackMob.performRangedAttack(this.target, $$3);
            this.attackTime = Mth.floor($$2 * (float)(this.attackIntervalMax - this.attackIntervalMin) + (float)this.attackIntervalMin);
        } else if (this.attackTime < 0) {
            this.attackTime = Mth.floor(Mth.lerp(Math.sqrt($$0) / (double)this.attackRadius, (double)this.attackIntervalMin, (double)this.attackIntervalMax));
        }
    }
}
