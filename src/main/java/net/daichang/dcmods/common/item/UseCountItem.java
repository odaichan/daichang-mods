package net.daichang.dcmods.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface UseCountItem {
    default boolean isUseCountItem(ItemStack stack) {
        return stack.getOrCreateTag().contains("dc_attking");
    }

    default void setUseCountItem(ItemStack stack) {
        stack.getOrCreateTag().putInt("dc_attking", 0);
    }

    default void addUse(ItemStack stack, int value) {
        if (value >= 0 && !isMaxUse(stack)) setUse(stack, getUse(stack) + value);
        if (getUse(stack) < 0) setUse(stack, 0);
    }

    default boolean isMaxUse(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag.getInt("dc_attking") == Integer.MAX_VALUE;
    }

    default void setUse(ItemStack stack, int value) {
        CompoundTag tag = stack.getTag();
        tag.putInt("dc_attking", value);
    }

    default int getUse(ItemStack stack) {
        return stack.getTag().getInt("dc_attking");
    }


    static void addUseS(ItemStack stack, int value) {
        if (value >= 0 && !isMaxUseS(stack)) setUseS(stack, getUseS(stack) + value);
        if (getUseS(stack) < 0) setUseS(stack, 0);
    }

    static void setUseS(ItemStack stack, int value) {
        CompoundTag tag = stack.getTag();
        tag.putInt("dc_attking", value);
    }

    static int getUseS(ItemStack stack) {
        return stack.getTag().getInt("dc_attking");
    }

    static boolean isMaxUseS(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag.getInt("dc_attking") == Integer.MAX_VALUE;
    }
}
