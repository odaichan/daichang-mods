package net.daichang.dcmods.client.render.entites;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.daichang.dcmods.DCMod;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class DCSuperArrowRenderer <T extends AbstractArrow> extends EntityRenderer<T> {
    public DCSuperArrowRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public void render(T p_113839_, float p_113840_, float p_113841_, PoseStack p_113842_, MultiBufferSource p_113843_, int p_113844_) {
        p_113842_.pushPose();
        p_113842_.mulPose(Axis.YP.rotationDegrees(Mth.lerp(p_113841_, p_113839_.yRotO, p_113839_.getYRot()) - 90.0F));
        p_113842_.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(p_113841_, p_113839_.xRotO, p_113839_.getXRot())));
        float $$16 = (float)p_113839_.shakeTime - p_113841_;
        if ($$16 > 0.0F) {
            float $$17 = -Mth.sin($$16 * 3.0F) * $$16;
            p_113842_.mulPose(Axis.ZP.rotationDegrees($$17));
        }

        p_113842_.mulPose(Axis.XP.rotationDegrees(45.0F));
        p_113842_.scale(0.05625F, 0.05625F, 0.05625F);
        p_113842_.translate(-4.0F, 0.0F, 0.0F);
        VertexConsumer $$18 = p_113843_.getBuffer(RenderType.entityCutout(this.getTextureLocation(p_113839_)));
        PoseStack.Pose $$19 = p_113842_.last();
        Matrix4f $$20 = $$19.pose();
        Matrix3f $$21 = $$19.normal();
        this.vertex($$20, $$21, $$18, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, p_113844_);
        this.vertex($$20, $$21, $$18, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, p_113844_);
        this.vertex($$20, $$21, $$18, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, p_113844_);
        this.vertex($$20, $$21, $$18, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, p_113844_);
        this.vertex($$20, $$21, $$18, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, p_113844_);
        this.vertex($$20, $$21, $$18, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, p_113844_);
        this.vertex($$20, $$21, $$18, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, p_113844_);
        this.vertex($$20, $$21, $$18, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, p_113844_);

        for(int $$22 = 0; $$22 < 4; ++$$22) {
            p_113842_.mulPose(Axis.XP.rotationDegrees(90.0F));
            this.vertex($$20, $$21, $$18, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, p_113844_);
            this.vertex($$20, $$21, $$18, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, p_113844_);
            this.vertex($$20, $$21, $$18, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, p_113844_);
            this.vertex($$20, $$21, $$18, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, p_113844_);
        }

        p_113842_.popPose();
        super.render(p_113839_, p_113840_, p_113841_, p_113842_, p_113843_, p_113844_);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull T t) {
        return new ResourceLocation(DCMod.MOD_ID, "textures/entities/arrow.png");
    }

    public void vertex(Matrix4f p_254392_, Matrix3f p_254011_, VertexConsumer p_253902_, int p_254058_, int p_254338_, int p_254196_, float p_254003_, float p_254165_, int p_253982_, int p_254037_, int p_254038_, int p_254271_) {
        p_253902_.vertex(p_254392_, (float)p_254058_, (float)p_254338_, (float)p_254196_).color(255, 255, 255, 255).uv(p_254003_, p_254165_).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(p_254271_).normal(p_254011_, (float)p_253982_, (float)p_254038_, (float)p_254037_).endVertex();
    }
}
