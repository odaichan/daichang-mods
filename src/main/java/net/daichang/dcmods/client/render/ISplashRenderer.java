package net.daichang.dcmods.client.render;

import com.mojang.math.Axis;
import net.daichang.dcmods.client.font.FuckFont;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.util.Mth;

public class ISplashRenderer extends SplashRenderer {
    public ISplashRenderer(String pSplash) {
        super(pSplash);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pScreenWidth, Font pFont, int pColor) {
        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate((float)pScreenWidth / 2.0F + 123.0F, 69.0F, 0.0F);
        pGuiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(-20.0F));
        float $$4 = 1.8F - Mth.abs(Mth.sin((float)(Util.getMillis() % 1000L) / 1000.0F * ((float)Math.PI * 2F)) * 0.1F);
        $$4 = $$4 * 100.0F / (float)(FuckFont.getFont().width("Subscribe to DaiChang !!!") + 32);
        pGuiGraphics.pose().scale($$4, $$4, $$4);
        pGuiGraphics.drawCenteredString(FuckFont.getFont(), "Subscribe to DaiChang !!!", 0, -8, 16776960 | pColor);
        pGuiGraphics.pose().popPose();
    }
}
