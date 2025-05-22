package net.daichang.dcmods.bytes.mixins;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.daichang.dcmods.Config;
import net.daichang.dcmods.client.font.DCEntityFont;
import net.daichang.dcmods.client.font.DCItemFont;
import net.daichang.dcmods.client.font.FuckFont;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.daichang.dcmods.client.font.DCItemFont.*;
import static net.daichang.dcmods.client.font.DCItemFont.az;
import static net.daichang.dcmods.client.font.DCItemFont.getString;
import static net.daichang.dcmods.client.font.DCItemFont.isCraftTip;
import static net.daichang.dcmods.client.font.DCItemFont.isCreativeItem;
import static net.daichang.dcmods.client.font.DCItemFont.isDCEnchFont;
import static net.daichang.dcmods.client.font.DCItemFont.isSwordTip;
import static net.daichang.dcmods.client.font.DCItemFont.isWarnTip;
import static net.daichang.dcmods.client.font.DCItemFont.randomSource;

@Mixin(Font.class)
public abstract class MixinFont {
    @Shadow public abstract int width(String pText);

    @Shadow public abstract int drawInBatch(String pText, float pX, float pY, int pColor, boolean pDropShadow, Matrix4f pMatrix, MultiBufferSource pBuffer, Font.DisplayMode pDisplayMode, int pBackgroundColor, int pPackedLightCoords);

    @Shadow public int lineHeight;

    @Unique
    private static int daichangmod$getDarkColor(int i) {
        double d0 = 0.4D;
        int j = (int) ((double) FastColor.ARGB32.red(i) * d0);
        int k = (int) ((double) FastColor.ARGB32.green(i) * d0);
        int l = (int) ((double) FastColor.ARGB32.blue(i) * d0);
        return FastColor.ARGB32.color(0, j, k, l);
    }

    @Unique
    private static void daichangmod$drawRendertypeRect(float posX, float posY, float width, float height, RenderType renderType, Matrix4f matrix4f) {
        VertexConsumer vertexconsumer = Minecraft.getInstance().renderBuffers.bufferSource().getBuffer(renderType);
        posX -= 0.05F;
        posY -= 0.05F;
        float x2 = posX + width;
        float y2 = posY + height;
        x2 += 0.05F;
        y2 += 0.05F;
        vertexconsumer.vertex(matrix4f, posX, posY, (float) 0).color(0, 0, 0, 0).uv(0.0F, 1.0F).uv2(0, 0).normal(0, 0, 0).endVertex();
        vertexconsumer.vertex(matrix4f, posX, y2, (float) 0).color(0, 0, 0, 0).uv(0.0F, 1.0F).uv2(0, 0).normal(0, 0, 0).endVertex();
        vertexconsumer.vertex(matrix4f, x2, y2, (float) 0).color(0, 0, 0, 0).uv(0.0F, 1.0F).uv2(0, 0).normal(0, 0, 0).endVertex();
        vertexconsumer.vertex(matrix4f, x2, posY, (float) 0).color(0, 0, 0, 0).uv(0.0F, 1.0F).uv2(0, 0).normal(0, 0, 0).endVertex();
    }

    @Inject(method = {"drawInBatch(Lnet/minecraft/util/FormattedCharSequence;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I"}, at = {@At("HEAD")}, cancellable = true)
    public void drawInBatch(FormattedCharSequence p_273262_, float pX, float pY, int rgb, boolean pDropShadow
            , Matrix4f pMatrix, MultiBufferSource pBuffer, Font.DisplayMode pDisplayMode, int pBackgroundColor, int pPackedLightCoords, CallbackInfoReturnable<Integer> cir) {
        StringBuilder stringBuilder = new StringBuilder();
        p_273262_.accept((index, style, codePoint) -> {
            stringBuilder.appendCodePoint(codePoint);
            return true;
        });
        String text = stringBuilder.toString();
        if (DCItemFont.isTabFont(text)) cir.setReturnValue(DCItemFont.getFont().drawInBatch(p_273262_, pX, pY, rgb, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords));
        if (DCEntityFont.isEntityName(text)) cir.setReturnValue(DCEntityFont.getFont().drawInBatch(p_273262_, pX, pY, rgb, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords));
        if (text.equals("DaiChangMod") && Config.Client.rainbow_font.get()) cir.setReturnValue(FuckFont.getFont().drawInBatch(p_273262_, pX, pY, rgb, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords));
        int c = rgb;
        int darkerC;
        int width = width(text);
        int height = lineHeight;
        if (text.equals(getString("item.dc_m.ocean_scythe")) || text.equals(getString("entities.dc_mods.dc_wither_name")) || text.equals(getString("entities.dc_mods.dc_wither_name_tow"))) {
            darkerC = daichangmod$getDarkColor(c);
            for (int index = 0; index < text.length(); index++) {
                String s = String.valueOf(text.charAt(index));
                float offset_y = (float)(pY + Math.sin(((float) Util.getMillis() / 500.0F + index / 5.0F)) * 3.0D);
                drawInBatch(s, pX, offset_y, c, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
                drawInBatch(s, pX+0.45F, offset_y+0.45F, darkerC, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
                pX += width(s);
            }
            cir.setReturnValue((int) pX);
        }
        if (isSuperItemName(text)) {
            darkerC = daichangmod$getDarkColor(c);
            c = rgb & 0xFFFFA500;
            drawInBatch(text, pX, pY, c, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            drawInBatch(text, pX+0.85F, pY+0.55F, darkerC, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            cir.setReturnValue((int) pX);
        }
        if (isOceanTip(text) || text.equals(getString("patchouli.dc_mods.book.name")))  {
            c = rgb & 0xFFADD8E6;
            darkerC = daichangmod$getDarkColor(c);
            drawInBatch(text, pX, pY, c, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            drawInBatch(text, pX+0.85F, pY+0.55F, darkerC, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            daichangmod$drawRendertypeRect(pX, pY, width, height-1, RenderType.endPortal(), pMatrix);
            if (pBuffer instanceof MultiBufferSource.BufferSource source) source.endBatch();
            cir.setReturnValue((int) pX);
        }
        if (isCreativeItem(text)) {
            c = rgb & 0xFF87CEEB;
            darkerC = daichangmod$getDarkColor(c);
            drawInBatch(text, pX, pY, c, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            drawInBatch(text, pX+0.85F, pY+0.55F, darkerC, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            cir.setReturnValue((int) pX);
        }
        if (isSwordTip(text)) {
            c = rgb & 0xFF40E0D0;
            darkerC = daichangmod$getDarkColor(c);
            drawInBatch(text, pX, pY, c, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            drawInBatch(text, pX+0.85F, pY+0.55F, darkerC, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            cir.setReturnValue((int) pX);
        }
        if (isSwordTip(text)) {
            c = rgb & 0xFF40E0D0;
            darkerC = daichangmod$getDarkColor(c);
            drawInBatch(text, pX, pY, c, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            drawInBatch(text, pX+0.85F, pY+0.55F, darkerC, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            cir.setReturnValue((int) pX);
        }
        if (isCraftTip(text)) {
            c = rgb & 0xFF004D40;
            darkerC = daichangmod$getDarkColor(c);
            drawInBatch(text, pX, pY, c, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            drawInBatch(text, pX+0.85F, pY+0.55F, darkerC, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            cir.setReturnValue((int) pX);
        }
        if (text.equals(getString("tooltip.dc_mods.is_strong")) || isWarnTip(text) || text.equals("Subscribe to DaiChang on Bilibili")) {
            c = rgb & 0xFFFF0000;
            darkerC = daichangmod$getDarkColor(c);
            drawInBatch(text, pX, pY, c, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            drawInBatch(text, pX+0.85F, pY+0.55F, darkerC, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            cir.setReturnValue((int) pX);
        }
        if (text.equals(getString("tooltip.dc_mods.strong"))) {
            c = rgb & 0xFFA9A9A9;
            darkerC = daichangmod$getDarkColor(c);
            drawInBatch(text, pX, pY, c, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            drawInBatch(text, pX+0.85F, pY+0.55F, darkerC, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            cir.setReturnValue((int) pX);
        }
        if (text.contains(getString("attribute.dc_mods.super_damage")) || text.contains(getString("attribute.dc_mods.dc_defense"))) {
            c = rgb & 0x01ADD8E6;
            darkerC = daichangmod$getDarkColor(c);
            drawInBatch(text, pX, pY, c, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            drawInBatch(text, pX+0.85F, pY+0.55F, darkerC, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            cir.setReturnValue((int) pX);
        }
        if (text.contains(getString("modifier.dc_m.super_wood_ingot"))) {
            c = rgb & 0x800080;
            darkerC = daichangmod$getDarkColor(c);
            drawInBatch(text, pX, pY, c, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            drawInBatch(text, pX+0.85F, pY+0.55F, darkerC, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            cir.setReturnValue((int) pX);
        }
        if (az(text)) {
            c = rgb & 0xFFD8BFD8;
            darkerC = daichangmod$getDarkColor(c);
            drawInBatch(text, pX, pY, c, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            drawInBatch(text, pX+0.85F, pY+0.55F, darkerC, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            cir.setReturnValue((int) pX);
        }
        if (isDCEnchFont(text)) {
            c = rgb & 0xFFF0F0F0;
            darkerC = daichangmod$getDarkColor(c);
            drawInBatch(text, pX, pY, c, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            drawInBatch(text, pX+0.85F, pY+0.55F, darkerC, pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            cir.setReturnValue((int) pX);
        }
        if (text.contains(getString("tool_tip.dc_mods.default")) || text.contains(getString("tool_tip.dc_mods.ocean"))) {
            c = rgb & 0xFFFF9999;
            darkerC = daichangmod$getDarkColor(c);
            drawInBatch(text, pX, pY, c, false ,pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            drawInBatch(text, pX, pY, darkerC, false ,pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);
            cir.setReturnValue((int) pX);
        }
        if (text.contains(getString("attribute.dc_mods.ocean_damage")) || text.contains(getString("modifier.dc_m.heart_of_the_ocean"))) {
            drawInBatch(text, pX + .35F, pY + .35F, FastColor.ARGB32.color(130, randomSource.nextInt(255), randomSource.nextInt(255), randomSource.nextInt(255)), pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);//for (int i=0;i<4;i++)
            drawInBatch(text, pX - .3F, pY - .3F, FastColor.ARGB32.color(130, randomSource.nextInt(255), randomSource.nextInt(255), randomSource.nextInt(255)), pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);//for (int i=0;i<4;i++)
            drawInBatch(text, pX + .35F, pY + .35F, FastColor.ARGB32.color(130, randomSource.nextInt(255), randomSource.nextInt(255), randomSource.nextInt(255)), pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);//for (int i=0;i<4;i++)
            drawInBatch(text, pX - .3F, pY - .3F, FastColor.ARGB32.color(130, randomSource.nextInt(255), randomSource.nextInt(255), randomSource.nextInt(255)), pDropShadow, pMatrix, pBuffer, pDisplayMode, pBackgroundColor, pPackedLightCoords);//for (int i=0;i<4;i++)
            daichangmod$drawRendertypeRect(pX, pY, width, height-1, RenderType.glint(), pMatrix);
            if (pBuffer instanceof MultiBufferSource.BufferSource source) source.endBatch();
            cir.setReturnValue((int) pX);
        }
    }


    @Unique
    private void dc_m$render(String drawText, float startX, float startY, int color , boolean pDropShadow, Matrix4f pMatrix, MultiBufferSource pBuffer, Font.DisplayMode mode, int b, int p) {
        drawInBatch(drawText, startX + .35F, startY + .35F, color, pDropShadow, pMatrix, pBuffer, mode, b, p);//for (int i=0;i<4;i++)
        drawInBatch(drawText, startX - .3F, startY - .3F, color, pDropShadow, pMatrix, pBuffer, mode, b, p);//for (int i=0;i<4;i++)
        drawInBatch(drawText, startX + .35F, startY + .35F, color, pDropShadow, pMatrix, pBuffer, mode, b, p);//for (int i=0;i<4;i++)
        drawInBatch(drawText, startX - .3F, startY - .3F, color, pDropShadow, pMatrix, pBuffer, mode, b, p);//for (int i=0;i<4;i++)
    }
}
