package net.daichang.dcmods.utils.lists.items;

import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuperItemList {
    private static final List<Item> clazz = new ArrayList<>();

    public static boolean getItem(Item target) {
        return clazz.contains(target);
    }

    public static void addItem(Item... target) {
        clazz.addAll(Arrays.asList(target));
    }
}
