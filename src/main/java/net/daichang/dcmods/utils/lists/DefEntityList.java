package net.daichang.dcmods.utils.lists;

import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class DefEntityList {
    private static final List<String> uuid = new ArrayList<>();

    public static boolean isList(Entity target) {
        return uuid.contains(target.getStringUUID());
    }

    public static void addList(Entity target) {
        uuid.add(target.getStringUUID());
    }

    public static void removeList(Entity target) {
        uuid.remove(target.getStringUUID());
    }
}
