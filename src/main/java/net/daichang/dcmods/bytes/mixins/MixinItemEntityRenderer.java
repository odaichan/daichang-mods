package net.daichang.dcmods.bytes.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.daichang.dcmods.client.font.DCItemFont;
import net.daichang.dcmods.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(ItemEntityRenderer.class)
public abstract class MixinItemEntityRenderer extends EntityRenderer<ItemEntity> {
    private static final Random random = new Random();
    @Unique
    private static final float HALF_SQRT_3;
    protected MixinItemEntityRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Unique
    private static int daichangmod$getRenderAmount(ItemStack stack) {
        int count = stack.getCount();
        if (count > 48) return 5;
        if (count > 32) return 4;
        if (count > 16) return 3;
        if (count > 1) return 2;
        return 1;
    }

    @Unique
    private static ItemRenderer daichangmod$getItemRendererFromContext() {
        Minecraft mc = Minecraft.getInstance();

        BlockRenderDispatcher blockRenderer = mc.getBlockRenderer();

        ItemInHandRenderer itemInHandRenderer = mc.gameRenderer.itemInHandRenderer;

        EntityRendererProvider.Context context = new EntityRendererProvider.Context(
                mc.getEntityRenderDispatcher(),
                mc.getItemRenderer(),
                blockRenderer,
                itemInHandRenderer,
                mc.getResourceManager(),
                mc.getEntityModels(),
                mc.font
        );

        return context.getItemRenderer();
    }

    @Unique
    private static void daichangmod$applyPhysicalEffect(ItemEntity entity, PoseStack poseStack) {
        if (!(entity.getItem().getItem() instanceof BlockItem)) {
            poseStack.translate(0.0F, 0.1F, 0.0F);
        } else {
            poseStack.translate(0.0F, 0.2F, 0.0F);
        }
    }

    @Unique
    private static void daichangmod$applyRandomTranslation(boolean is3D, PoseStack poseStack) {
        if (is3D) {
            float randX = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
            float randY = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
            float randZ = (random.nextFloat() * 2.0F - 1.0F) * 0.15F;
            poseStack.translate(randX, randY, (double) randZ);
        } else {
            float randX = (random.nextFloat() * 2.0F - 1.0F) * 0.075F;
            float randY = (random.nextFloat() * 2.0F - 1.0F) * 0.075F;
            poseStack.translate(randX, randY, 0.0D);
        }
    }

    @Inject(method = "render(Lnet/minecraft/world/entity/item/ItemEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at  = @At("HEAD"), cancellable = true)
    private void render(ItemEntity itemE, float p_115037_, float f2, PoseStack poseStack, MultiBufferSource bufferSource, int p_115041_, CallbackInfo ci) {
        ItemStack itemstack = itemE.getItem();
        if (Utils.isCreativeItem(itemstack)) {
            float f = ((float) 100 + f2 - 1.0F) / 20.0F * 1.6F;
            f = Mth.sqrt(f);
            if (f > 1.0F) f = 1.0F;
            float $$14 = ((float)100 / 10000L + f2) / 200.0F;
            float $$15 = Math.min($$14 > 0.8F ? ($$14 - 0.8F) / 0.2F : 0.0F, 1.0F);
            RandomSource $$16 = RandomSource.create(432L);
            VertexConsumer $$17 = bufferSource.getBuffer(RenderType.lightning());
            poseStack.pushPose();
            poseStack.translate(0.0F, 0.4F, 0.0F);
            for(int $$18 = 0; (float)$$18 < ($$14 + $$14 * $$14) / 2.0F * 60.0F; ++$$18) {
                poseStack.mulPose(Axis.XP.rotationDegrees($$16.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees($$16.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees($$16.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees($$16.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees($$16.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees($$16.nextFloat() * 360.0F + $$14 * 90.0F));
                float $$19 = ($$16.nextFloat() * 20.0F + 5.0F + $$15 * 10.0F) * 0.05F;
                float $$20 = ($$16.nextFloat() * 2.0F + 1.0F + $$15 * 2.0F) * 0.05F;
                Matrix4f $$21 = poseStack.last().pose();
                int $$22 = (int)(255.0F);
                daichangmod$vertex01($$17, $$21, $$22);
                daichangmod$vertex2($$17, $$21, $$19, $$20);
                daichangmod$vertex3($$17, $$21, $$19, $$20);
                daichangmod$vertex01($$17, $$21, $$22);
                daichangmod$vertex3($$17, $$21, $$19, $$20);
                daichangmod$vertex4($$17, $$21, $$19, $$20);
                daichangmod$vertex01($$17, $$21, $$22);
                daichangmod$vertex4($$17, $$21, $$19, $$20);
                daichangmod$vertex2($$17, $$21, $$19, $$20);
            }
            poseStack.popPose();
        }
        if (Utils.isSuperTool(itemstack)) {
            poseStack.pushPose();
            int i = itemstack.isEmpty() ? 187 : Item.getId(itemstack.getItem()) + itemstack.getDamageValue();
            random.setSeed(i);
            BakedModel bakedmodel = daichangmod$getItemRendererFromContext().getModel(itemstack, itemE.level(), null, itemE.getId());
            boolean is3D = bakedmodel.isGui3d();
            int renderAmount = daichangmod$getRenderAmount(itemstack);

            daichangmod$applyPhysicalEffect(itemE, poseStack);

            float pitch = itemE.onGround() ? 90 : (itemE.getXRot() + 1) % 360;
            poseStack.mulPose(Axis.XP.rotationDegrees(pitch));
            poseStack.mulPose(Axis.ZP.rotationDegrees(itemE.getYRot()));

            float scaleX = bakedmodel.getTransforms().ground.scale.x();
            float scaleY = bakedmodel.getTransforms().ground.scale.y();
            float scaleZ = bakedmodel.getTransforms().ground.scale.z();

            if (!is3D) {
                float translateX = -0.0F * (renderAmount - 1) * 0.5F * scaleX;
                float translateY = -0.0F * (renderAmount - 1) * 0.5F * scaleY;
                float translateZ = -0.09375F * (renderAmount - 1) * 0.5F * scaleZ;
                poseStack.translate(translateX, translateY, (double) translateZ);
            }

            for (int k = 0; k < renderAmount; ++k) {
                poseStack.pushPose();
                if (k > 0) daichangmod$applyRandomTranslation(is3D, poseStack);

                daichangmod$getItemRendererFromContext().render(itemstack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, p_115041_, OverlayTexture.NO_OVERLAY, bakedmodel);
                poseStack.popPose();
                if (!is3D) poseStack.translate(0.0D, 0.0D, 0.09375F * scaleZ);
            }

            poseStack.popPose();
            ci.cancel();
        }
    }

    @Unique
    private static void daichangmod$vertex01(VertexConsumer p_254498_, Matrix4f p_253891_, int p_254278_) {
        p_254498_.vertex(p_253891_, 0.0F, 0.0F, 0.0F).color(124, 255, 37, p_254278_).endVertex();
    }

    @Unique
    private static void daichangmod$vertex2(VertexConsumer p_253956_, Matrix4f p_254053_, float p_253704_, float p_253701_) {
        p_253956_.vertex(p_254053_, -HALF_SQRT_3 * p_253701_, p_253704_, -0.5F * p_253701_).color(120, 127, 127, 0).endVertex();
    }

    @Unique
    private static void daichangmod$vertex3(VertexConsumer p_253850_, Matrix4f p_254379_, float p_253729_, float p_254030_) {
        p_253850_.vertex(p_254379_, HALF_SQRT_3 * p_254030_, p_253729_, -0.5F * p_254030_).color(200, 34, 125, 0).endVertex();
    }

    @Unique
    private static void daichangmod$vertex4(VertexConsumer p_254184_, Matrix4f p_254082_, float p_253649_, float p_253694_) {
        p_254184_.vertex(p_254082_, 0.0F, p_253649_, p_253694_).color(125, 125, 129, 0).endVertex();
    }

    @Override
    public @NotNull Font getFont() {
        return DCItemFont.getFont();
    }

    static {
        HALF_SQRT_3 = (float)(Math.sqrt(3.0F) / (double)2.0F);
    }
}
