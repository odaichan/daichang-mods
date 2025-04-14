package net.daichang.dcmods.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnchSuperSharp extends Enchantment {
    public EnchSuperSharp() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinLevel() {
        return 1;
    }

    @Override
    public int getMaxLevel() {
        return 6;
    }

    @Override
    public boolean isAllowedOnBooks() {
        return true;
    }

    @Override
    public boolean isCurse() {
        return false;
    }

    @Override
    public boolean isTradeable() {
        return true;
    }

    @Override
    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        float damage = 0.1F;
        if (level <= 10 && level > 0) {
            damage = 32.9F * level;
        } else if (level > 10 && level <= 100) {
            damage = damage * level + damage * 50000 * level + damage / 0.01F * 0.09F * level + 30912.1F * level;
        } else if (level > 100 && level < Integer.MAX_VALUE) {
            damage = damage * level * 12 + damage * 84123 * level + damage / 0.01F * 0.09F * level + 309122.1F * level;
        } else if (level == Integer.MAX_VALUE) {
            damage = Float.POSITIVE_INFINITY;
        } else if (enchantedItem.getItem() == Items.COMMAND_BLOCK) {
            damage = Float.POSITIVE_INFINITY;
        }
        return damage;
    }
}
