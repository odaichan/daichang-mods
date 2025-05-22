package net.daichang.dcmods.common.item.tools.supers;

import net.daichang.dcmods.utils.EntityHurtUtil;
import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DCAxeItem extends AxeItem {
    public DCAxeItem(Tier p_40521_, float p_40522_, float p_40523_, Properties p_40524_) {
        super(p_40521_, p_40522_, p_40523_, p_40524_);
        SuperItemList.addItem(this);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.translatable("tooltip.dc_mods.minecraft"));
        list.add(Component.translatable("tooltip.dc_mods.axe"));
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.wasOnFire = true;
        pTarget.setRemainingFireTicks(300);
        EntityHurtUtil util = EntityHurtUtil.getInstance(pTarget, pAttacker);
        util.kbDCHurt(30 + pTarget.getMaxHealth() * 0.001F);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }
}
