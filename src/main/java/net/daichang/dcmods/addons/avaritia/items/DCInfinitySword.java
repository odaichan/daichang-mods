package net.daichang.dcmods.addons.avaritia.items;

import committee.nova.mods.avaritia.common.item.tools.infinity.InfinitySwordItem;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class DCInfinitySword extends InfinitySwordItem {
    public DCInfinitySword() {
        SuperItemList.addItem(this);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity victim) {
        if (victim instanceof LivingEntity living) DataHelper.setIsDead(living, true);
        return super.onLeftClickEntity(stack, player, victim);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        DataHelper.setIsDead(pAttacker, true);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return Utils.getUseAnim();
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.startUsingItem(pUsedHand);
        pPlayer.cooldowns.removeCooldown(this);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        DataHelper.restHealthDelta(pLivingEntity);
        EntityHelper.forceSetHealth(pLivingEntity, pLivingEntity.getMaxHealth());
        if (pLivingEntity instanceof Player player) player.cooldowns.removeCooldown(this);
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }
}
