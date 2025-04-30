package net.daichang.dcmods.client.render.entites.layers;

import net.daichang.dcmods.client.models.entites.ElainaModel;
import net.daichang.dcmods.common.entity.DCLoveElaina;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class ElainaArmorLayer extends EnergySwirlLayer<DCLoveElaina, ElainaModel<DCLoveElaina>> {
    private static final ResourceLocation WITHER_ARMOR_LOCATION = new ResourceLocation("textures/entity/wither/wither_armor.png");
    public ElainaModel<DCLoveElaina> model;

    public ElainaArmorLayer(RenderLayerParent<DCLoveElaina, ElainaModel<DCLoveElaina>> pRenderer, EntityModelSet modelSet) {
        super(pRenderer);
        this.model = new ElainaModel<>(modelSet.bakeLayer(ModelLayers.WITHER_ARMOR));
    }

    @Override
    protected float xOffset(float pTickCount) {
        return Mth.cos(pTickCount * 0.02F) * 3.0F;
    }

    @Override
    protected @NotNull ResourceLocation getTextureLocation() {
        return WITHER_ARMOR_LOCATION;
    }

    @Override
    protected @NotNull EntityModel<DCLoveElaina> model() {
        return model;
    }
}
