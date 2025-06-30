package net.daichang.dcmods.common.enchantment.dc_enchs;

import net.daichang.dcmods.common.enchantment.BaseEnch;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchLiquidBlock extends BaseEnch {
    public EnchLiquidBlock() {
        super(Rarity.VERY_RARE,EnchantmentCategory.ARMOR_FEET, new EquipmentSlot[]{EquipmentSlot.FEET});
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinLevel() {
        return 1;
    }
}