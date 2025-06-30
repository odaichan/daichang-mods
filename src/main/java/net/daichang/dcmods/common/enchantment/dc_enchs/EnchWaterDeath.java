package net.daichang.dcmods.common.enchantment.dc_enchs;

import net.daichang.dcmods.common.enchantment.BaseEnch;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EnchWaterDeath extends BaseEnch {
    public EnchWaterDeath() {
        super(Rarity.VERY_RARE,EnchantmentCategory.ARMOR_HEAD, new EquipmentSlot[]{EquipmentSlot.HEAD});
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void tick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        ItemStack head = player.getInventory().getArmor(3);
        if (head.hasFoil() && head.getEnchantmentLevel(this) > 0 && player.isUnderWater() && player.tickCount % 20 == 0) {
            float playerHealth = player.getMaxHealth() - (1 + player.getMaxHealth() * 0.001F + player.getHealth() * 0.001F);
            EntityHelper.forceSetHealth(player, playerHealth);
        }
    }

    @Override
    public boolean isCurse() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public void doPostAttack(LivingEntity pAttacker, Entity pTarget, int pLevel) {
        super.doPostAttack(pAttacker, pTarget, pLevel);
    }

    @Override
    public void doPostHurt(LivingEntity pTarget, Entity pAttacker, int pLevel) {
        super.doPostHurt(pTarget, pAttacker, pLevel);
    }
}
