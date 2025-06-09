package net.daichang.dcmods.utils.helpers;

import net.minecraft.Util;

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

    public static int rainBowColor() {
        float index = 0.5F;
        float hueOffset = (float) Util.getMillis() / 16000.0F;
        float hue = hueOffset + index * index;
        float saturation = 1.0F;
        float brightness = 1.0F;
        return Color.HSBtoRGB((((hue * 720.0F + index) % 720.0F >= 360.0F) ? (720.0F - (hue * 720.0F + index) % 720.0F) : ((hue * 720.0F + index) % 720.0F)) / 256.0F, saturation, brightness);
    }

    public static Color getRainbowColor() {
        float index = 0.5F;
        float hueOffset = (float) Util.getMillis() / 16000.0F;
        float hue = hueOffset + index * index;
        float saturation = 1.0F;
        float brightness = 1.0F;
        int c = Color.HSBtoRGB((((hue * 720.0F + index) % 720.0F >= 360.0F) ? (720.0F - (hue * 720.0F + index) % 720.0F) : ((hue * 720.0F + index) % 720.0F)) / 256.0F, saturation, brightness);
        Color color = new Color(c);
        return new Color(color.getRed(), color.getGreen(), color.getBlue());
    }
}
