package net.daichang.dcmods.inits;

import net.daichang.dcmods.addons.avaritia.AvaritiaItems;
import net.daichang.dcmods.addons.curios.CuriosItems;
import net.daichang.dcmods.utils.ModUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.daichang.dcmods.DCMod.MOD_ID;

public class DCTabs {
    public static final DeferredRegister<CreativeModeTab> tab = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<CreativeModeTab> DC_MOD_TAB = tab.register("dc_mod_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("tabs.dc_mods.tab"))
                    .icon(() -> new ItemStack(DCItems.SUPER_WOOD_SWORD.get()))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .withBackgroundLocation(new ResourceLocation(MOD_ID, "textures/gui/tab_items.png"))
                    .withTabsImage(new ResourceLocation(MOD_ID, "textures/gui/tabs.png"))
                    .displayItems((parameters, tabData) -> {
                        //super
                        tabData.accept(DCItems.SUPER_WOOD_SWORD.get());
                        tabData.accept(DCItems.SUPER_WOOD_PICKAXE.get());
                        tabData.accept(DCItems.SUPER_WOOD_AXE.get());
                        tabData.accept(DCItems.SUPER_WOOD_SHOVEL.get());
                        tabData.accept(DCItems.SUPER_WOOD_HOE.get());

                        //normal
                        tabData.accept(DCItems.NORMAL_WOOD_SWORD.get());
                        tabData.accept(DCItems.NORMAL_WOOD_PICKAXE.get());
                        tabData.accept(DCItems.NORMAL_WOOD_AXE.get());
                        tabData.accept(DCItems.NORMAL_WOOD_SHOVEL.get());
                        tabData.accept(DCItems.NORMAL_WOOD_HOE.get());


                        //items
                        tabData.accept(DCItems.SUPER_WOOD_INGOT.get());
                        tabData.accept(DCItems.WOOD_INGOT.get());

                        //Bow
                        tabData.accept(DCItems.DC_BOW.get());
                        tabData.accept(DCItems.DC_ARROW.get());

                        tabData.accept(DCItems.WOOD_TOTEM.get());
                        for (RegistryObject<Item> object : DCItems.dc_normal) tabData.accept(object.get());
                        if (ModUtil.isCuriosLoad()) {
                            tabData.accept(CuriosItems.WOOD_RING.get());
                        }
                        for (RegistryObject<Item> object : DCItems.armors) {
                            tabData.accept(object.get());
                        }
                        if (ModUtil.isAvaritiaLoad()) for (RegistryObject<Item> object : AvaritiaItems.list) tabData.accept(object.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> DC_MOD_CREATIVE_TAB = tab.register("dc_mod_creative_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("tabs.dc_mods.tab_creative"))
                    .icon(() -> new ItemStack(DCItems.DC_CRAFT.get()))
                    .withTabsBefore(DC_MOD_TAB.getKey())
                    .withBackgroundLocation(new ResourceLocation(MOD_ID, "textures/gui/tab_items.png"))
                    .withTabsImage(new ResourceLocation(MOD_ID, "textures/gui/tabs.png"))
                    .displayItems((parameters, tabData) -> {
                        for (RegistryObject<Item> object : DCItems.dc_creative) tabData.accept(object.get());
                    }).build());


    public static final RegistryObject<CreativeModeTab> DC_MOD_BLOCK_TAB = tab.register("dc_mod_block_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("tabs.dc_mods.tab_block"))
                    .withBackgroundLocation(new ResourceLocation(MOD_ID, "textures/gui/tab_items.png"))
                    .withTabsImage(new ResourceLocation(MOD_ID, "textures/gui/tabs.png"))
                    .icon(() -> new ItemStack(DCBlockItems.RED_SPIDER_LILY.get()))
                    .withTabsBefore(DC_MOD_CREATIVE_TAB.getKey())
                    .displayItems((parameters, tabData) -> {
                        tabData.accept(DCBlocks.RED_SPIDER_LILY.get());
                    }).build());

    public static void inits(IEventBus event) {
        tab.register(event);
    }
}
