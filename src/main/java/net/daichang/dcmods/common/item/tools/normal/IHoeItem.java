package net.daichang.dcmods.common.item.tools.normal;

import net.daichang.dcmods.utils.EntityActuallyHurt;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IHoeItem extends HoeItem {
    public IHoeItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
        list.add(Component.translatable("tool_tip.iaxe_item"));
        super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pTarget.getArmorValue() > 0)
            EntityActuallyHurt.getInstance(pTarget, pAttacker).kbActuallyHurt(pAttacker.damageSources().magic(), 4.0F);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
