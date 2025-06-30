package net.daichang.dcmods.common.enchantment.dc_enchs;

import net.daichang.dcmods.common.enchantment.BaseEnch;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchSuperProtection extends BaseEnch {
    public EnchSuperProtection() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getDamageProtection(int pLevel, DamageSource pSource) {
        if (pSource.is(DamageTypes.GENERIC_KILL)) return 0;
        return super.getDamageProtection(pLevel, pSource);
    }

    @Override
    public int getMaxLevel() {
        return super.getMaxLevel();
    }
}
