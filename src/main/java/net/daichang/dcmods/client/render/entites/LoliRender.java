package net.daichang.dcmods.client.render.entites;

import com.mojang.blaze3d.vertex.PoseStack;
import net.daichang.dcmods.client.models.entites.ModelLoliEntity;
import net.daichang.dcmods.common.entities.creative.EntityLoli;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class LoliRender extends MobRenderer<EntityLoli, ModelLoliEntity<EntityLoli>> {
    private final ResourceLocation location = new ResourceLocation("dc_m", "textures/entities/loli.png");

    public LoliRender(EntityRendererProvider.Context context) {
        super(context, new ModelLoliEntity(context.bakeLayer(ModelLoliEntity.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityLoli entity) {
        return location;
    }

    @Override
    protected @Nullable RenderType getRenderType(EntityLoli p_115322_, boolean p_115323_, boolean p_115324_, boolean p_115325_) {
        return super.getRenderType(p_115322_, p_115323_, p_115324_, p_115325_);
    }

    @Override
    public ModelLoliEntity<EntityLoli> getModel() {
        return super.getModel();
    }

    @Override
    protected void scale(EntityLoli pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        super.scale(pLivingEntity, pPoseStack, pPartialTickTime);
    }
}
