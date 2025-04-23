package net.daichang.dcmods.client.render.item;

import net.daichang.dcmods.DCMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


@OnlyIn(Dist.CLIENT)
public class DCItemRenderer extends ItemRenderer {
    public static final ResourceLocation ENCHANTED_GLINT_ITEM = new ResourceLocation(DCMod.MOD_ID, "textures/model/enchanted_glint_item.png");
    public DCItemRenderer(Minecraft pMinecraft, TextureManager pTextureManager, ModelManager pModelManager, ItemColors pItemColors, BlockEntityWithoutLevelRenderer pBlockEntityRenderer) {
        super(pMinecraft, pTextureManager, pModelManager, pItemColors, pBlockEntityRenderer);
    }
}
