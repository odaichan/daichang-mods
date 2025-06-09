package net.daichang.dcmods.client.render.entites;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.daichang.dcmods.client.models.entites.DCWitherModel;
import net.daichang.dcmods.common.entities.boss.DCWither;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DCWitherRender extends MobRenderer<DCWither, DCWitherModel<DCWither>> {
    private static final ResourceLocation WITHER_INVULNERABLE_LOCATION = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
    private static final ResourceLocation WITHER_LOCATION = new ResourceLocation("textures/entity/wither/wither.png");

    public DCWitherRender(EntityRendererProvider.Context p_174445_) {
        super(p_174445_, new DCWitherModel(p_174445_.bakeLayer(ModelLayers.WITHER)), 1.0F);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(DCWither dcWither) {
        int $$1 = dcWither.getInvulnerableTicks();
        return $$1 > 0 && ($$1 > 80 || $$1 / 5 % 2 != 1) ? WITHER_INVULNERABLE_LOCATION : WITHER_LOCATION;
    }

    @Override
    protected int getBlockLightLevel(DCWither pEntity, BlockPos pPos) {
        return 15;
    }

    @Override
    protected void setupRotations(DCWither pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        if (this.isShaking(pEntityLiving)) {
            pRotationYaw += (float)(Math.cos((double)pEntityLiving.tickCount * (double)3.25F) * Math.PI * (double)0.4F);
        }
        if (!pEntityLiving.hasPose(Pose.SLEEPING)) {
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pRotationYaw));
        }else if (pEntityLiving.isAutoSpinAttack()) {
            pPoseStack.mulPose(Axis.XP.rotationDegrees(-90.0F - pEntityLiving.getXRot()));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(((float)pEntityLiving.tickCount + pPartialTicks) * -75.0F));
        }else if (isEntityUpsideDown(pEntityLiving)) {
            pPoseStack.translate(0.0F, pEntityLiving.getBbHeight() + 0.1F, 0.0F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }
    }

    @Override
    protected void scale(DCWither pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        float value = 2.0F;
        if (pLivingEntity.isBaby()) value = 1.0F;
        pPoseStack.scale(value, value, value);
    }
}
