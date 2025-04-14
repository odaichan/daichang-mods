package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DCTabs {
    public static final DeferredRegister<CreativeModeTab> tab = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DCMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> DC_MOD_TAB = tab.register("dc_mod_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("tabs.dc_mods,tab"))
                    .icon(() -> new ItemStack(DCItems.SUPER_WOOD_SWORD.get()))
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
                    }).build());

    public static final RegistryObject<CreativeModeTab> DC_MOD_CREATIVE_TAB = tab.register("dc_mod_creative_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("tabs.dc_mods,tab_creative"))
                    .icon(() -> new ItemStack(DCItems.DC_CRAFT.get()))
                    .displayItems((parameters, tabData) -> {
                        tabData.accept(DCItems.DC_CRAFT.get());
                        tabData.accept(DCItems.DESTROY_BLOCK.get());
                        tabData.accept(DCItems.TIME_CLOCK.get());
                        tabData.accept(DCItems.DATA_SET.get());
                    }).build());
}
