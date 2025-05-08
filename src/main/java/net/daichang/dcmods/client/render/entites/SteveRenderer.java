package net.daichang.dcmods.client.render.entites;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.client.models.entites.SteveModel;
import net.daichang.dcmods.common.entities.boss.DCSteve;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class SteveRenderer extends MobRenderer<DCSteve, SteveModel<DCSteve>> {
    public static ResourceLocation Texture;
    public static ResourceLocation Herobrine;

    public SteveRenderer(EntityRendererProvider.Context context) {
        super(context, new SteveModel<>(context.bakeLayer(SteveModel.LAYER_LOCATION)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(DCSteve dcSteve) {
        if (dcSteve.isHerobrine()) return Herobrine;
        return Texture;
    }

    static {
        Texture = new ResourceLocation(DCMod.MOD_ID, "textures/entities/steve.png");
        Herobrine = new ResourceLocation(DCMod.MOD_ID, "textures/entities/herobrine.png");
    }
}
