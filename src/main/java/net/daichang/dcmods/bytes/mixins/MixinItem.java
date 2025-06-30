package net.daichang.dcmods.bytes.mixins;

import net.daichang.dcmods.client.tool_tip.DCItemTip;
import net.daichang.dcmods.common.enchantment.BaseEnch;
import net.daichang.dcmods.common.item.UseCountItem;
import net.daichang.dcmods.utils.EntityActuallyHurt;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Item.class)
public abstract class MixinItem implements UseCountItem {
    @Inject(method = "appendHoverText", at = @At("HEAD"))
    private void tooltip(ItemStack pStack, Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced, CallbackInfo ci) {
        if (isUseCountItem(pStack)) DCItemTip.addAttackCount(pTooltipComponents, pStack);
    }

    @Inject(method = "hurtEnemy", at = @At("RETURN"))
    private void hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker, CallbackInfoReturnable<Boolean> cir) {
        if (pStack.hasFoil()) {
            float damage = 0.0F;
            for (Enchantment enchantment : pStack.getAllEnchantments().keySet()) {
                if (enchantment instanceof BaseEnch ench) {
                    int level = EnchantmentHelper.getEnchantmentLevel(ench, pAttacker);
                    if (daichangmod$isDCEnch(ench, level, pStack)) {
                        damage = (float) (damage + ench.getSnowDamageBounce(level, pStack) + ench.getOceanDamageBounce(level, pStack)) + damage;
                        EntityActuallyHurt.getInstance(pTarget, pAttacker).actuallyHurt(EntityHelper.ocean_damage(pAttacker), level * 1.5F);
                    }
                }
            }
        }
    }

    @Unique
    private boolean daichangmod$isDCEnch(BaseEnch baseEnch, int level, ItemStack stack) {
        return baseEnch.getOceanDamageBounce(level, stack) > 0 || baseEnch.getSnowDamageBounce(level, stack) > 0;
    }
}
