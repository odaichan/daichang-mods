package net.daichang.dcmods.common.item.tools;

import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.block.state.BlockState;

public class DCShears extends ShearsItem {
    public DCShears(Properties p_43074_) {
        super(p_43074_.setNoRepair());
        SuperItemList.addItem(this);
    }

    @Override
    public float getDestroySpeed(ItemStack p_43084_, BlockState p_43085_) {
        return 200F;
    }
}
