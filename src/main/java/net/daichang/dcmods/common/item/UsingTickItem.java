package net.daichang.dcmods.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface UsingTickItem {
    default void addCount(ItemStack stack, int value) {
        if (value >= 0) setCount(stack, getCount(stack) + value);
        if (getCount(stack) > 11) setCount(stack, 0);
    }

    default void setCount(ItemStack stack, int value) {
        CompoundTag tag = stack.getTag();
        tag.putInt("dcUsingCount", value);
    }

    default int getCount(ItemStack stack) {
        return stack.getTag().getInt("dcUsingCount");
    }

    static void addCountS(ItemStack stack, int value) {
        if (value >= 0) setCountS(stack, getCountS(stack) + value);
        if (getCountS(stack) > 11) setCountS(stack, 0);
    }

    static void setCountS(ItemStack stack, int value) {
        CompoundTag tag = stack.getTag();
        tag.putInt("dcUsingCount", value);
    }

    static int getCountS(ItemStack stack) {
        return stack.getTag().getInt("dcUsingCount");
    }
}
