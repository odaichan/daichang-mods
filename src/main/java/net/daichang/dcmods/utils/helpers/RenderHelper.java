package net.daichang.dcmods.utils.helpers;

import com.mojang.blaze3d.systems.RenderSystem;
import net.daichang.dcmods.library.DCBaseLib;

public class RenderHelper implements DCBaseLib {
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