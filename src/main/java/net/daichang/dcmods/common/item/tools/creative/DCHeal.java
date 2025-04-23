package net.daichang.dcmods.common.item.tools.creative;

import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.lists.items.CreativeItemList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DCHeal extends Item {
    public DCHeal() {
        super(new Properties());
        CreativeItemList.addItem(this);
    }

    void heal(LivingEntity living) {
        living.setHealth(living.getMaxHealth());
        EntityHelper.forceSetHealth(living, living.getMaxHealth());
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pTarget.isMultipartEntity()) {
            heal(pTarget);
            return true;
        }

        return false;
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity.isMultipartEntity()) return false;
        if (entity instanceof LivingEntity living) heal(living);
        return super.onLeftClickEntity(stack, player, entity);
    }
}
