package net.daichang.dcmods.common.blocks;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface DCBaseBlock {
     void appHoveList(ItemStack stack, List<Component> list);
}
