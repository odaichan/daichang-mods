package net.daichang.dcmods.addons.fantasy_ending.items;

import net.daichang.dcmods.common.item.DCTier;
import net.daichang.dcmods.common.item.DCTierItem;
import net.minecraft.world.item.ItemStack;

public class LifeAndDeathBook extends DCTierItem {
    public LifeAndDeathBook() {
        super(DCTier.OCEAN_HEART, new Properties());
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return super.isBarVisible(pStack);
    }
}
