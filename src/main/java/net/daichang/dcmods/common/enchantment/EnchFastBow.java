package net.daichang.dcmods.common.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchFastBow extends BaseEnch {
    public EnchFastBow() {
        super(Rarity.COMMON,EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
