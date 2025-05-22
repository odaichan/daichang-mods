package net.daichang.dcmods.common.item.tools.supers;

import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DCSuperPickaxeItem extends PickaxeItem {
    public DCSuperPickaxeItem(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
        SuperItemList.addItem(this);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.translatable("tooltip.dc_mods.minecraft"));
        list.add(Component.translatable("tooltip.dc_mods.pickaxe_1"));
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity target, LivingEntity player) {
        float value = 3.0F;
        target.knockback(value,  Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
        EntityHelper.forceKnockBack(target, player, value, 0.12F);
        return super.hurtEnemy(p_40994_, target, player);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }
}
