package net.daichang.dcmods.bytes.mixins;

import com.mojang.math.Axis;
import net.daichang.dcmods.Config;
import net.daichang.dcmods.client.font.FuckFont;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashRenderer.class)
public class SplashRendererMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(GuiGraphics pGuiGraphics, int pScreenWidth, Font pFont, int pColor, CallbackInfo ci) {
        if (Config.Client.can_change_splash.get()) {
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate((float)pScreenWidth / 2.0F + 123.0F, 69.0F, 0.0F);
            pGuiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(-20.0F));
            float $$4 = 1.8F - Mth.abs(Mth.sin((float)(Util.getMillis() % 1000L) / 1000.0F * ((float)Math.PI * 2F)) * 0.1F);
            $$4 = $$4 * 100.0F / (float)(FuckFont.getFont().width("Subscribe to DaiChang !!!") + 32);
            pGuiGraphics.pose().scale($$4, $$4, $$4);
            pGuiGraphics.drawCenteredString(FuckFont.getFont(), "Subscribe to DaiChang !!!", 0, -8, 16776960 | pColor);
            pGuiGraphics.pose().popPose();
            ci.cancel();
        }
    }
}
