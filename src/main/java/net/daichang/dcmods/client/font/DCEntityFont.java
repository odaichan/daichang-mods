package net.daichang.dcmods.client.font;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.function.Function;

public class DCEntityFont extends Font {
    public DCEntityFont(Function<ResourceLocation, FontSet> p_243253_, boolean p_243245_) {
        super(p_243253_, p_243245_);
    }

    public static boolean isEntityName(String s) {
        return s.equals("entities.dc_mods.dc_wither_name") || s.equals("entities.dc_mods.dc_wither_name_tow");
    }

    public static DCEntityFont getFont() {
        return new DCEntityFont(Minecraft.getInstance().font.fonts, false);
    }

    public int drawInBatch(@NotNull FormattedCharSequence formattedCharSequence, float x, float y, int rgb, boolean b1, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource multiBufferSource, @NotNull DisplayMode mode, int i, int i1) {
        StringBuilder builder = new StringBuilder();
        formattedCharSequence.accept((p_13746_, p_13747_, p_13748_) -> {
            builder.appendCodePoint(p_13748_);
            return true;
        });
        return renderFont(builder.toString(), x, y, rgb, b1, matrix4f, multiBufferSource, mode, i, i1, this.isBidirectional());
    }

    public int drawInBatch(@NotNull String text, float x, float y, int rgb, boolean b, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource source, @NotNull DisplayMode mode, int i, int i1) {
        return renderFont(text, x, y, rgb, b, matrix4f, source, mode, i, i1, this.isBidirectional());
    }

    public int drawInBatch(@NotNull Component component, float x, float y, int rgb, boolean b, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource source, @NotNull DisplayMode mode, int i, int i1) {
        return renderFont(component.getString(), x, y, rgb, b, matrix4f, source, mode, i, i1, this.isBidirectional());
    }

    public int renderFont(@NotNull String text, float x, float y, int rgb, boolean dropShadow, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource multiBufferSource, @NotNull DisplayMode mode, int i, int i1, boolean isText){
        for (int index = 0; index < text.length(); index++) {
            String s = String.valueOf(text.charAt(index));
            float offset_y = (float)(y + Math.sin(((float)Util.getMillis() / 500.0F + index / 5.0F)) * 3.0D);
            int color1 = rgb & 0xFFADD8E6;
            int color2 = rgb & 0xFF0000FF;
            super.drawInBatch(s, x, offset_y, color1, dropShadow, matrix4f, multiBufferSource, mode, i, i1);
            super.drawInBatch(s, x+0.85F, offset_y+0.65F, color2, dropShadow, matrix4f, multiBufferSource, mode, i, i1);
            x += width(s);
        }
        return (int) x;
    }

}
