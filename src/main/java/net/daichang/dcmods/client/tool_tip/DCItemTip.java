package net.daichang.dcmods.client.tool_tip;

import net.daichang.dcmods.common.item.UseCountItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class DCItemTip {
    public static void addAttackCount(List<Component> list, ItemStack pStack) {
        int count = UseCountItem.getUseS(pStack);
        list.add(Component.translatable("tooltip.dc_mods.hurts", count).withStyle(ChatFormatting.AQUA));
    }
}
