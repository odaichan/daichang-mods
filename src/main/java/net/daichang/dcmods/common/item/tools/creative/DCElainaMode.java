package net.daichang.dcmods.common.item.tools.creative;

import net.daichang.dcmods.common.item.BaseSuperItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class DCElainaMode extends BaseSuperItem {
    public DCElainaMode() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
