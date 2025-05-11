package net.daichang.dcmods.utils.helpers;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.stream.Stream;

import static java.lang.Math.*;
import static net.minecraft.util.Mth.square;

public class ParticleHelper {
    public static void drawLine(double interval, double tox, double toy, double toz, double X, double Y, double Z, SimpleParticleType type, Level level){
        //自动连接任意两点
        double deltax = tox-X, deltay = toy-Y, deltaz = toz-Z;
        double length = sqrt(square(deltax) + square(deltay) + square(deltaz));
        int amount = (int)(length/interval);
        for (int i = 0 ; i <= amount; i++) {
            level.addParticle(type,X+deltax*i/amount, Y+deltay*i/amount, Z+deltaz*i/amount,0,0,0);
        }
    }

    static double rs = 0.0D;

    public static void drawSixStar(Entity player, Level level) {
        double X = player.getX();
        double Z = player.getZ();
        double y = player.getY() + 0.1D;
        double r = sqrt(12);
        SimpleParticleType type = ParticleTypes.SOUL_FIRE_FLAME.getType();

        //二维空间内距离原点长度为r且角度为a的点p坐标是：r*(cosa,sina)

        double[] x1 = new double[5];
        double[] y1 = new double[5];

        for (int i = 0; i < 5; i++) {
            double rad = (2 * Math.PI / 5) * i + rs;
            x1[i] = (r * cos(rad) + player.getX());
            y1[i] = (r * sin(rad) + player.getZ());
            //现场计算顶点
        }


        for (int i = 0; i < 5; i++) {
            drawLine(0.05, x1[i], y, y1[i], x1[(i + 7) % 5], y, y1[(i + 7) % 5], type, level);
        }

        rs = rs + 0.0015;


        for (int i = 0; i <= 360; i++) {
            double rad = i * 0.017453292519943295;
            double x = r * cos(rad);
            double z = r * sin(rad);
            level.addParticle(type, X + x, y, Z + z, 0, 0, 0);
        }
    }

    public static void spawnExaggeratedWaterSplash(Level level, Vec3 position, int count) {
        RandomSource random = level.random;

        int splashCount = count * 2;
        int rainCount = count;

        for (int i = 0; i < splashCount; i++) {
            level.addParticle(
                    ParticleTypes.SPLASH,
                    position.x + (random.nextDouble() - 0.5) * 1.2,
                    position.y + 0.1,
                    position.z + (random.nextDouble() - 0.5) * 1.2,
                    (random.nextDouble() - 0.5) * 2.0,
                    random.nextDouble() * 1.5,
                    (random.nextDouble() - 0.5) * 2.0
            );
        }


        for (int i = 0; i < rainCount; i++) {
            level.addParticle(
                    ParticleTypes.RAIN,
                    position.x + (random.nextDouble() - 0.5) * 1.5,
                    position.y + 0.3,
                    position.z + (random.nextDouble() - 0.5) * 1.5,
                    (random.nextDouble() - 0.5) * 0.8,
                    random.nextDouble() * 1.8,
                    (random.nextDouble() - 0.5) * 0.8
            );
        }

        for (int i = 0; i < count / 2; i++) {
            level.addParticle(
                    ParticleTypes.FALLING_WATER,
                    position.x + (random.nextDouble() - 0.5) * 2.0,
                    position.y + random.nextDouble() * 2.0,
                    position.z + (random.nextDouble() - 0.5) * 2.0,
                    (random.nextDouble() - 0.5) * 1.5,
                    -0.2 - random.nextDouble() * 0.5,
                    (random.nextDouble() - 0.5) * 1.5
            );
        }

        for (int i = 0; i < count / 3; i++) {
            level.addParticle(
                    ParticleTypes.RAIN,
                    position.x + (random.nextDouble() - 0.5) * 1.0,
                    position.y + 0.5,
                    position.z + (random.nextDouble() - 0.5) * 1.0,
                    (random.nextDouble() - 0.5) * 0.3,
                    random.nextDouble() * 0.2,
                    (random.nextDouble() - 0.5) * 0.3
            );
        }
    }

    public static void splashing(ParticleOptions type , double x, double y, double z, Level level) {
        int particleCount = 50;
        for (int i = 0; i < particleCount; i++) {
            double offsetX = (random() - 0.5) * 0.5;
            double offsetY = (random() - 0.5) * 0.5;
            double offsetZ = (random() - 0.5) * 0.5;
            level.addParticle(type, x + offsetX, y + offsetY, z + offsetZ, 0, 0, 0);
        }
    }

    public static Stream<Vec3> Line(Vec3 from, Vec3 to, int seg) {
        return Stream.iterate(0, i -> i <= seg, i -> i + 1).map(i -> {
            double p = i / (double) seg;
            return from.scale(1 - p).add(to.scale(p));
        });
    }
}
