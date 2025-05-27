package net.daichang.dcmods.utils.helpers;

import java.awt.*;

public class ColorHelper {
    public static Color getRGB(int r, int g, int b) {
        if (isRgbValue(r, g, b)) return new Color(r, g, b);
        return Color.BLACK;
    }


    public static Color getRGBA(int r, int g, int b, float a) {
        if (isRgbaValue(r, g, b, a)) return new Color(r, g, b, a);
        return Color.BLACK;
    }

    public static boolean isRgbValue(int r, int g, int b) {
        boolean var1 = true;
        if (r > 255 || r < 0) var1 = false;
        if (b > 255 || b < 0) var1 = false;
        if (g > 255 || g < 0) var1 = false;
        return var1;
    }

    public static boolean isRgbaValue(int r, int g, int b, float a) {
        boolean var1 = true;
        if (r > 255 || r < 0) var1 = false;
        if (b > 255 || b < 0) var1 = false;
        if (g > 255 || g < 0) var1 = false;
        if (a > 1.0F || a < 0.0F) var1 = false;
        return var1;
    }
}
