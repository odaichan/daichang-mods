package net.daichang.dcmods.utils.lists;

import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class GetHealthList {
    private static final List<String> uuid = new ArrayList<>();

    public static boolean isHealth(Entity target) {
        return uuid.contains(target.getStringUUID());
    }

    public static void addHealth(Entity target) {
        uuid.add(target.getStringUUID());
    }

    public static void removeHealth(Entity target) {
        uuid.remove(target.getStringUUID());
    }
}