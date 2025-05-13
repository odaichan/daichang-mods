package net.daichang.dcmods.common.item;

import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BaseSuperItem extends Item {
    public BaseSuperItem(Properties pProperties) {
        super(pProperties);
        SuperItemList.addItem(this);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }
}
