package net.daichang.dcmods.common.entities.ai;

import net.daichang.dcmods.common.entities.BossEntity;
import net.minecraft.world.entity.ai.goal.Goal;

public class DCHurtByTargetGoal extends Goal {
    private BossEntity boss;
    public DCHurtByTargetGoal(BossEntity boss) {
        this.boss = boss;
    }

    @Override
    public boolean canUse() {
        return true;
    }


    @Override
    public void tick() {
        super.tick();
        if (boss.getDcLastHurtTarget() != null) boss.setTarget(boss.getDcLastHurtTarget());
    }
}
