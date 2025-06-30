package net.daichang.dcmods.bytes.mixins.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.daichang.dcmods.event.dcevents.DCRenderToolTipEvent;
import net.daichang.dcmods.utils.DeprecatedMixin;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

//梦幻终焉的一个Mixin
@Mixin(GuiGraphics.class)
@DeprecatedMixin
public abstract class GuiGraphicsMixin {

    @Shadow
    public PoseStack pose;

    @Shadow
    public MultiBufferSource.BufferSource bufferSource;

    @Shadow(remap = false)
    public ItemStack tooltipStack;

    @Shadow
    public abstract int guiWidth();

    @Shadow
    public abstract int guiHeight();

    @Shadow
    public abstract PoseStack pose();

    @Shadow
    @Deprecated
    public abstract void flushIfUnmanaged();


    @Inject(method = "renderTooltipInternal", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ForgeHooksClient;onRenderTooltipPre(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/client/gui/GuiGraphics;IIIILjava/util/List;Lnet/minecraft/client/gui/Font;Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;)Lnet/minecraftforge/client/event/RenderTooltipEvent$Pre;", remap = false), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void renderTooltipInternal(Font p_282675_, List<ClientTooltipComponent> p_282615_, int p_283230_, int p_283417_, ClientTooltipPositioner p_282442_, CallbackInfo ci) {
        DCRenderToolTipEvent.PrePre preEvent = new DCRenderToolTipEvent.PrePre(this.tooltipStack, (GuiGraphics) (Object) this, p_283230_, p_283417_, guiWidth(), guiHeight(), ForgeHooksClient.getTooltipFont(tooltipStack, p_282675_), p_282615_, p_282442_, this.pose());
        MinecraftForge.EVENT_BUS.post(preEvent);
        if (preEvent.isCanceled()) ci.cancel();
    }

    @Inject(method = "renderTooltipInternal", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;popPose()V", shift = At.Shift.AFTER))
    private void renderTooltipInternal_post(Font p_282675_, List<ClientTooltipComponent> p_282615_, int p_283230_, int p_283417_, ClientTooltipPositioner p_282442_, CallbackInfo ci) {
        DCRenderToolTipEvent.Post event = new DCRenderToolTipEvent.Post(this.tooltipStack, (GuiGraphics) (Object) this, p_283230_, p_283417_, p_282675_, p_282615_, p_282442_);
        MinecraftForge.EVENT_BUS.post(event);
    }
}
