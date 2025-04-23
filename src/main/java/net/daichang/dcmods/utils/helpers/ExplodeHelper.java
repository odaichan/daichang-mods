package net.daichang.dcmods.utils.helpers;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public class ExplodeHelper {
    public static void boom(Level level, double x, double y,double z , Entity target, float boom) {
        level.explode(target, x, y,z, boom, false, Level.ExplosionInteraction.NONE);
    }
}
