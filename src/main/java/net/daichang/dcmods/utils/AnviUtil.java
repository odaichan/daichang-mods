package net.daichang.dcmods.utils;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;

public class AnviUtil {
    public static void addAnviUpdate(AnvilUpdateEvent event, Item leftItem, Item rightItem, Item outPut) {
        if (event.getLeft().is(leftItem) && event.getRight().is(rightItem)) event.setOutput(new ItemStack(outPut));
    }
}