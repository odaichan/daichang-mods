package net.daichang.dcmods.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface UseCountItem {
    default void addUse(ItemStack stack, int value) {
        if (value >= 0) setUse(stack, getUse(stack) + value);
    }

    default void setUse(ItemStack stack, int value) {
        CompoundTag tag = stack.getTag();
        tag.putInt("dc_attking", value);
    }

    default int getUse(ItemStack stack) {
        return stack.getTag().getInt("dc_attking");
    }

    static void addUseS(ItemStack stack, int value) {
        if (value >= 0) setUseS(stack, getUseS(stack) + value);
    }

    static void setUseS(ItemStack stack, int value) {
        CompoundTag tag = stack.getTag();
        tag.putInt("dc_attking", value);
    }

    static int getUseS(ItemStack stack) {
        return stack.getTag().getInt("dc_attking");
    }
}
