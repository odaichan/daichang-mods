package net.daichang.dcmods.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.User32;
import net.daichang.dcmods.library.DCBaseLib;

public class RenderUtil implements DCBaseLib {
    private static final User32 user32 = User32.INSTANCE;

    private static final GDI32 gdi32 = GDI32.INSTANCE;

    public static int width = 0;

    public static int height = 0;

    public static void setupRender() {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }

    public static void endRender() {
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }
}