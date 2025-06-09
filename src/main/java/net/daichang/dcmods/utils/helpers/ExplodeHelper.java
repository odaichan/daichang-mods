package net.daichang.dcmods.utils.helpers;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ExplodeHelper {
    public static void boom(Level level, double x, double y,double z , Entity target, float value) {
        level.explode(target, x, y,z, value, false, Level.ExplosionInteraction.NONE);
    }

    public static void boom(Level level, Vec3 vec3, Entity target, float value) {
        double x = vec3.x();
        double y = vec3.y();
        double z = vec3.z();
        level.explode(target, x, y,z, value, false, Level.ExplosionInteraction.NONE);
    }

}
