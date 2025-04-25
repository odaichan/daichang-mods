package net.daichang.dcmods.event;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.client.PacketHandler;
import net.daichang.dcmods.client.models.entites.DCGirlModel;
import net.daichang.dcmods.client.models.entites.ElainaModel;
import net.daichang.dcmods.client.render.entites.DCSuperArrowRenderer;
import net.daichang.dcmods.client.render.entites.DCWitherSkullRenderer;
import net.daichang.dcmods.client.render.entites.ElainaRenderer;
import net.daichang.dcmods.common.entity.DCLoveElaina;
import net.daichang.dcmods.inits.DCEntities;
import net.daichang.dcmods.inits.DCItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DCMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DCModEventHandler {
    @SubscribeEvent
    public static void onClientSetup(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(DCEntities.DC_SUPER_ARROW.get(), DCSuperArrowRenderer::new);
        event.registerEntityRenderer(DCEntities.DC_WITHER.get(), ElainaRenderer::new);
        event.registerEntityRenderer(DCEntities.DC_WITHER_SKULL.get(), DCWitherSkullRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DCGirlModel.LAYER_LOCATION, DCGirlModel::createBodyLayer);
        event.registerLayerDefinition(ElainaModel.LAYER_LOCATION, ElainaModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(DCEntities.DC_WITHER.get(), DCLoveElaina.createAttributes().build());
    }

    @SubscribeEvent
    public static void clientInit(FMLClientSetupEvent event) {
        ItemProperties.register(DCItems.DC_BOW.get(), new ResourceLocation("dc_m", "pull"), (stack, level, entity, i) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return entity.getUseItem() != stack ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks());
            }
        });
        ItemProperties.register(DCItems.DC_BOW.get(), new ResourceLocation("dc_m", "pulling"), (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
        PacketHandler.init();
        event.enqueueWork(PacketHandler::register);
    }
}
