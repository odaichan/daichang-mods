package net.daichang.dcmods.mixins;

import net.daichang.dcmods.client.font.DCFont;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Font.class)
public abstract class MixinFont {
    @Inject(method = {"drawInBatch(Lnet/minecraft/util/FormattedCharSequence;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I"}, at = {@At("HEAD")}, cancellable = true)
    public void drawInBatch(FormattedCharSequence p_273262_, float x, float y, int color, boolean p_273674_, Matrix4f p_273525_, MultiBufferSource p_272624_, Font.DisplayMode p_273418_, int p_273330_, int p_272981_, CallbackInfoReturnable<Integer> cir) {
        StringBuilder stringBuilder = new StringBuilder();
        p_273262_.accept((index, style, codePoint) -> {
            stringBuilder.appendCodePoint(codePoint);
            return true;
        });
        if (DCFont.isTabFont(stringBuilder.toString())) cir.setReturnValue(DCFont.getFont().drawInBatch(p_273262_, x, y, color, p_273674_, p_273525_, p_272624_, p_273418_, p_273330_, p_272981_));
    }
}
