package net.daichang.dcmods.utils.lists;

import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class Heal2ZList {
    private static final List<String> uuid = new ArrayList<>();

    public static boolean isH2Z(Entity target) {
        return uuid.contains(target.getStringUUID());
    }

    public static void addUUID(Entity target) {
        uuid.add(target.getStringUUID());
    }

    public static void removeUUID(Entity target) {
        uuid.remove(target.getStringUUID());
    }
}
