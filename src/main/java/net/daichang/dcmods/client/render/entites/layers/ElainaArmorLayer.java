package net.daichang.dcmods.client.render.entites.layers;

import net.daichang.dcmods.client.models.entites.ElainaModel;
import net.daichang.dcmods.common.entity.DCLoveElaina;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class ElainaArmorLayer extends EnergySwirlLayer<DCLoveElaina, ElainaModel<DCLoveElaina>> {

    public ElainaArmorLayer(RenderLayerParent<DCLoveElaina, ElainaModel<DCLoveElaina>> pRenderer) {
        super(pRenderer);
    }

    @Override
    protected float xOffset(float pTickCount) {
        return Mth.cos(pTickCount * 0.02F) * 3.0F;
    }

    @Override
    protected ResourceLocation getTextureLocation() {
        return null;
    }

    @Override
    protected EntityModel<DCLoveElaina> model() {
        return null;
    }
}
