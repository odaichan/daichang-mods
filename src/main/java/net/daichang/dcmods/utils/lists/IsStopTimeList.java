package net.daichang.dcmods.utils.lists;

import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class IsStopTimeList {

    private static final List<String> uuid = new ArrayList<>();

    public static boolean notStop(Entity target) {
        return uuid.contains(target.getStringUUID());
    }

    public static void addNoStop(Entity target) {
        uuid.add(target.getStringUUID());
    }

    public static void removeNoStop(Entity target) {
        uuid.remove(target.getStringUUID());
    }
}
