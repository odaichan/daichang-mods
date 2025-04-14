package net.daichang.dcmods.item.tools;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.block.state.BlockState;

public class DCShears extends ShearsItem {
    public DCShears(Properties p_43074_) {
        super(p_43074_.setNoRepair());
    }

    @Override
    public float getDestroySpeed(ItemStack p_43084_, BlockState p_43085_) {
        return 200F;
    }
}
