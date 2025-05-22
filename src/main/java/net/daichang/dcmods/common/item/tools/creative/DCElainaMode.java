package net.daichang.dcmods.common.item.tools.creative;

import net.daichang.dcmods.common.item.BaseSuperItem;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DCElainaMode extends BaseSuperItem {
    public DCElainaMode() {
        super(new Properties().stacksTo(1));
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        DataHelper.setIsDead(pTarget, true);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof LivingEntity living) DataHelper.setIsDead(living, true);
        return super.onLeftClickEntity(stack, player, entity);
    }
}
