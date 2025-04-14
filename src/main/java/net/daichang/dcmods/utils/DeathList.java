package net.daichang.dcmods.utils;

import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class DeathList {
    private static final List<String> uuid = new ArrayList<>();

    public static boolean isDeath(Entity target) {
        return uuid.contains(target.getStringUUID());
    }

    public static void addDeath(Entity target) {
        uuid.add(target.getStringUUID());
    }

    public static void remove(Entity target) {
        uuid.remove(target.getStringUUID());
    }
}
