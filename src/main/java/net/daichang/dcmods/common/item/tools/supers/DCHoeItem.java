package net.daichang.dcmods.common.item.tools.supers;

import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DCHoeItem extends HoeItem {
    public DCHoeItem(Tier p_41336_, int p_41337_, float p_41338_, Properties p_41339_) {
        super(p_41336_, p_41337_, p_41338_, p_41339_);
        SuperItemList.addItem(this);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        p_41423_.add(Component.translatable("tooltip.dc_mods.minecraft"));
        p_41423_.add(Component.translatable("tooltip.dc_mods.hoe"));
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity target, LivingEntity p_40996_) {
        target.setDeltaMovement(0, 4, 0);
        return super.hurtEnemy(p_40994_, target, p_40996_);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }
}
