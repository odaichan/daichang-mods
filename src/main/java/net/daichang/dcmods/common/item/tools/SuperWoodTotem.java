package net.daichang.dcmods.common.item.tools;

import net.daichang.dcmods.common.item.BaseSuperItem;
import net.daichang.dcmods.inits.DCItems;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SuperWoodTotem extends BaseSuperItem {
    public SuperWoodTotem() {
        super(new Properties().stacksTo(1).fireResistant().durability(1024));
        SuperItemList.addItem(this);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        if (pEntity instanceof LivingEntity living && living.getHealth() <= 0) {
            EntityHelper.forceHeal(living, 1.0F);
            living.heal(1.0F);
            living.playSound(SoundEvents.TOTEM_USE);
            if (living instanceof Player player) {
                player.respawn();
                player.playSound(SoundEvents.TOTEM_USE);
                Minecraft.getInstance().gameRenderer.displayItemActivation(new ItemStack(DCItems.WOOD_TOTEM.get()));
            }
        }
    }
}