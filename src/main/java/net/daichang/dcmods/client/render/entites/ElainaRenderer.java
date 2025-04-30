package net.daichang.dcmods.client.render.entites;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.client.font.DCEntityFont;
import net.daichang.dcmods.client.models.entites.ElainaModel;
import net.daichang.dcmods.common.entity.DCLoveElaina;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class ElainaRenderer extends MobRenderer<DCLoveElaina, ElainaModel<DCLoveElaina>> {
    public static ResourceLocation Texture;

    private static final float HALF_SQRT_3;

    public ElainaRenderer(EntityRendererProvider.Context context) {
        super(context, new ElainaModel<>(context.bakeLayer(ElainaModel.LAYER_LOCATION)), 0.5f);
    }


    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull DCLoveElaina witherBoss) {
        return Texture;
    }

    @Override
    public @NotNull Font getFont() {
        return DCEntityFont.getFont();
    }

    @Override
    public void render(DCLoveElaina boss, float f1, float f2, PoseStack poseStack, MultiBufferSource bufferSource, int p_115460_) {
        super.render(boss, f1, f2, poseStack, bufferSource, p_115460_);
        if (boss.deathTime > 0 && boss.deathTime < 220) {
            float f = ((float)boss.deathTime + f2 - 1.0F) / 20.0F * 1.6F;
            f = Mth.sqrt(f);
            if (f > 1.0F) f = 1.0F;
            float $$14 = ((float)boss.deathTime + f2) / 200.0F;
            float $$15 = Math.min($$14 > 0.8F ? ($$14 - 0.8F) / 0.2F : 0.0F, 1.0F);
            RandomSource $$16 = RandomSource.create(432L);
            VertexConsumer $$17 = bufferSource.getBuffer(RenderType.lightning());
            poseStack.pushPose();
            poseStack.translate(0.0F, 1.0F, 0.0F);
            for(int $$18 = 0; (float)$$18 < ($$14 + $$14 * $$14) / 2.0F * 60.0F; ++$$18) {
                poseStack.mulPose(Axis.XP.rotationDegrees($$16.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees($$16.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees($$16.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.XP.rotationDegrees($$16.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.YP.rotationDegrees($$16.nextFloat() * 360.0F));
                poseStack.mulPose(Axis.ZP.rotationDegrees($$16.nextFloat() * 360.0F + $$14 * 90.0F));
                float $$19 = $$16.nextFloat() * 20.0F + 5.0F + $$15 * 10.0F;
                float $$20 = $$16.nextFloat() * 2.0F + 1.0F + $$15 * 2.0F;
                Matrix4f $$21 = poseStack.last().pose();
                int $$22 = (int)(255.0F * (1.0F - $$15));
                vertex01($$17, $$21, $$22);
                vertex2($$17, $$21, $$19, $$20);
                vertex3($$17, $$21, $$19, $$20);
                vertex01($$17, $$21, $$22);
                vertex3($$17, $$21, $$19, $$20);
                vertex4($$17, $$21, $$19, $$20);
                vertex01($$17, $$21, $$22);
                vertex4($$17, $$21, $$19, $$20);
                vertex2($$17, $$21, $$19, $$20);
            }
            poseStack.popPose();
        }
    }

    private static void vertex01(VertexConsumer p_254498_, Matrix4f p_253891_, int p_254278_) {
        p_254498_.vertex(p_253891_, 0.0F, 0.0F, 0.0F).color(0, 255, 0, p_254278_).endVertex();
    }

    private static void vertex2(VertexConsumer p_253956_, Matrix4f p_254053_, float p_253704_, float p_253701_) {
        p_253956_.vertex(p_254053_, -HALF_SQRT_3 * p_253701_, p_253704_, -0.5F * p_253701_).color(0, 0, 255, 0).endVertex();
    }

    private static void vertex3(VertexConsumer p_253850_, Matrix4f p_254379_, float p_253729_, float p_254030_) {
        p_253850_.vertex(p_254379_, HALF_SQRT_3 * p_254030_, p_253729_, -0.5F * p_254030_).color(255, 0, 0, 0).endVertex();
    }

    private static void vertex4(VertexConsumer p_254184_, Matrix4f p_254082_, float p_253649_, float p_253694_) {
        p_254184_.vertex(p_254082_, 0.0F, p_253649_, p_253694_).color(125, 125, 125, 0).endVertex();
    }

    @Override
    protected void setupRotations(DCLoveElaina p_115317_, PoseStack p_115318_, float p_115319_, float p_115320_, float p_115321_) {
        if (this.isShaking(p_115317_)) {
            p_115320_ += (float)(Math.cos((double)p_115317_.tickCount * (double)3.25F) * Math.PI * (double)0.4F);
        }

        if (!p_115317_.hasPose(Pose.SLEEPING)) {
            p_115318_.mulPose(Axis.YP.rotationDegrees(180.0F - p_115320_));
        }

        else if (p_115317_.isAutoSpinAttack()) {
            p_115318_.mulPose(Axis.XP.rotationDegrees(-90.0F - p_115317_.getXRot()));
            p_115318_.mulPose(Axis.YP.rotationDegrees(((float)p_115317_.tickCount + p_115321_) * -75.0F));
        }
        else if (isEntityUpsideDown(p_115317_)) {
            p_115318_.translate(0.0F, p_115317_.getBbHeight() + 0.1F, 0.0F);
            p_115318_.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }
    }

    static {
        Texture = new ResourceLocation(DCMod.MOD_ID, "textures/entities/dc_wither.png");
        HALF_SQRT_3 = (float)(Math.sqrt((double)3.0F) / (double)2.0F);
    }
}
