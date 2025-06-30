package net.daichang.dcmods.common.enchantment.dc_enchs;

import net.daichang.dcmods.common.enchantment.BaseEnch;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchNightVision extends BaseEnch {
    public EnchNightVision() {
        super(Rarity.COMMON, EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
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
