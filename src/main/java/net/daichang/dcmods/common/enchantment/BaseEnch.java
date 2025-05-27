package net.daichang.dcmods.common.enchantment;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

public class BaseEnch extends Enchantment {
    public BaseEnch(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    public void onLeftClickEntity(LivingEntity target, Entity attack, int level, LivingAttackEvent event) {
        target = event.getEntity();
        attack = event.getSource().getEntity();
    }
}
