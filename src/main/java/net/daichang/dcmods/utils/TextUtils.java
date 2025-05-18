package net.daichang.dcmods.utils;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

import java.awt.*;

//Made By Wzz
public class TextUtils {
    public static MutableComponent rainbow(String text) {
        MutableComponent result = Component.empty();
        long time = System.currentTimeMillis();
        int baseHue = (int) ((time/10) % 1000);
        for (int i = 0; i < text.length(); i++) {
            int hue = (baseHue + i * 15) % 360;
            int rgb = Color.HSBtoRGB(hue / 360.0F, 1.0F, 1.0F);
            Style style = Style.EMPTY.withColor(TextColor.fromRgb(rgb));
            result.append(Component.literal(String.valueOf(text.charAt(i))).withStyle(style));
        }
        return result;
    }

    public static MutableComponent rainbow(Component text) {
        MutableComponent result = Component.empty();
        long time = System.currentTimeMillis();
        int baseHue = (int) ((time/10) % 1000);
        for (int i = 0; i < text.getString().length(); i++) {
            int hue = (baseHue + i * 15) % 360;
            int rgb = Color.HSBtoRGB(hue / 360.0F, 1.0F, 1.0F);
            Style style = Style.EMPTY.withColor(TextColor.fromRgb(rgb));
            result.append(Component.translatable(String.valueOf(text.getString().charAt(i))).withStyle(style));
        }
        return result;
    }

    public static Component color(String text, int rgb) {
        Style style = Style.EMPTY.withColor(TextColor.fromRgb(rgb));
        return Component.literal(text).withStyle(style);
    }

    public static Component orange(String text) {
        return color(text, 0xFFA500);
    }

    public static Component cyan(String text) {
        return color(text, 0x00FFFF);
    }

    public static Component white(String text) {
        return color(text, 0xFFFFFF);
    }
    public static String getString(String text) {
        return Component.translatable(text).getString();
    }
}
