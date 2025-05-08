package net.daichang.dcmods.client.render.entites;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.daichang.dcmods.common.entities.projectile.DCWitherSkull;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.WitherSkullRenderer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.projectile.WitherSkull;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;


@OnlyIn(Dist.CLIENT)
public class DCWitherSkullRenderer extends WitherSkullRenderer {
    private static final float HALF_SQRT_3;
    public DCWitherSkullRenderer(EntityRendererProvider.Context p_174449_) {
        super(p_174449_);
    }

    @Override
    public void render(@NotNull WitherSkull itemE, float p_115037_, float f2, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int p_115041_) {
        super.render(itemE, p_115037_, f2, poseStack, bufferSource, p_115041_);
        if (itemE instanceof DCWitherSkull skull) {
            float f = ((float)skull.age + f2 - 1.0F) / 20.0F * 1.6F;
            f = Mth.sqrt(f);
            if (f > 1.0F) f = 1.0F;
            float $$14 = ((float)skull.age + f2) / 200.0F;
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

    static {
        HALF_SQRT_3 = (float)(Math.sqrt((double)3.0F) / (double)2.0F);
    }
}
