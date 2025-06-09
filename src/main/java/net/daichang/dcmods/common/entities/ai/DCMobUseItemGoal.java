package net.daichang.dcmods.common.entities.ai;

import net.daichang.dcmods.common.entities.BossEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Item;

public class DCMobUseItemGoal extends Goal {
    BossEntity boss;
    InteractionHand hand;
    public DCMobUseItemGoal(BossEntity boss, InteractionHand hand) {
        this.boss = boss;
        this.hand = hand;
    }

    @Override
    public boolean canUse() {
        return boss.tickCount % 100 == 0;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void tick() {
        super.tick();
        if (boss.tickCount % 100 == 0) {
            Item item = boss.getItemInHand(hand).getItem();
            if (Minecraft.getInstance().player != null) item.use(boss.level(), Minecraft.getInstance().player, hand);
        }
    }
}
