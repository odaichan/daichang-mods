package net.daichang.dcmods.inits;

import net.daichang.dcmods.addons.avaritia.AvaritiaItems;
import net.daichang.dcmods.addons.curios.CuriosItems;
import net.daichang.dcmods.addons.fantasy_ending.FEInit;
import net.daichang.dcmods.addons.farmers_delight.FDItem;
import net.daichang.dcmods.addons.slashblade.DaiChangSB;
import net.daichang.dcmods.addons.slashblade.SBInits;
import net.daichang.dcmods.utils.ModUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static net.daichang.dcmods.DCMod.MOD_ID;

public class DCTabs {
    public static final ResourceLocation backGround = new ResourceLocation(MOD_ID, "textures/gui/tab_items.png");
    public static final ResourceLocation tabsImage = new ResourceLocation(MOD_ID, "textures/gui/tabs.png");

    public static final DeferredRegister<CreativeModeTab> tab = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);

    public static final RegistryObject<CreativeModeTab> DC_MOD_TAB = tab.register("dc_mod_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("tabs.dc_mods.tab"))
                    .icon(() -> new ItemStack(DCItems.SUPER_WOOD_SWORD.get()))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .withBackgroundLocation(backGround)
                    .withTabsImage(tabsImage)
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
                        for (RegistryObject<Item> object : DCItems.armors) tabData.accept(object.get());
                        for (RegistryObject<Item> object : DCDiscs.discs) tabData.accept(object.get());
                        if (ModUtil.isCuriosLoad()) for (RegistryObject<Item> object : CuriosItems.curios) tabData.accept(object.get());
                        if (ModUtil.isAvaritiaLoad()) for (RegistryObject<Item> object : AvaritiaItems.list) tabData.accept(object.get());
                        if (ModUtil.isFDLoad()) for (RegistryObject<Item> object : FDItem.list) tabData.accept(object.get());
                        if (ModUtil.isFELoad()) for (RegistryObject<Item> object : FEInit.list) tabData.accept(object.get());
                        if (ModUtil.isSBLoad()) {
                            ItemStack stack = new ItemStack(SBInits.DC_SB.get());
                            DaiChangSB.init(stack);
                            tabData.accept(stack);
                        }
                    }).build());

    public static final RegistryObject<CreativeModeTab> DC_MOD_CREATIVE_TAB = tab.register("dc_mod_creative_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("tabs.dc_mods.tab_creative"))
                    .icon(() -> new ItemStack(DCItems.DC_CRAFT.get()))
                    .withTabsBefore(DC_MOD_TAB.getKey())
                    .withBackgroundLocation(backGround)
                    .withTabsImage(tabsImage)
                    .displayItems((parameters, tabData) -> {
                        for (RegistryObject<Item> object : DCItems.dc_creative) tabData.accept(object.get());

                        ItemStack sword = new ItemStack(DCItems.SUPER_WOOD_SWORD.get());
                        sword.getTag().putInt("dc_attking", Integer.MAX_VALUE);
                        sword.enchant(Enchantments.SHARPNESS, 255);
                        sword.enchant(Enchantments.MOB_LOOTING, 255);
                        sword.enchant(DCEnch.SuperSharp.get(), 255);
                        tabData.accept(sword);

                        ItemStack scythe = new ItemStack(DCItems.OCEAN_SCYTHE.get());
                        scythe.getTag().putInt("dc_attking", Integer.MAX_VALUE);
                        scythe.enchant(Enchantments.SHARPNESS, 255);
                        scythe.enchant(Enchantments.MOB_LOOTING, 255);
                        scythe.enchant(DCEnch.SuperSharp.get(), 255);
                        tabData.accept(scythe);

                        ItemStack dc_head = new ItemStack(Items.PLAYER_HEAD);
                        dc_head.getOrCreateTag().putString("SkullOwner", "DaiHardcore");
                        tabData.accept(dc_head);
                        for (RegistryObject<Item> object : DCItems.spawn_egg) tabData.accept(object.get());

                        for (RegistryObject<Enchantment> ench : DCEnch.list) {
                            ItemStack ench_book = new ItemStack(Items.ENCHANTED_BOOK);
                            ench_book.enchant(ench.get(), 10);
                            tabData.accept(ench_book);
                        }
                    }).build());


    public static final RegistryObject<CreativeModeTab> DC_MOD_BLOCK_TAB = tab.register("dc_mod_block_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("tabs.dc_mods.tab_block"))
                    .withBackgroundLocation(backGround)
                    .withTabsImage(tabsImage)
                    .icon(() -> new ItemStack(DCBlockItems.RED_SPIDER_LILY.get()))
                    .withTabsBefore(DC_MOD_CREATIVE_TAB.getKey())
                    .displayItems((parameters, tabData) -> {
                        tabData.accept(DCBlocks.RED_SPIDER_LILY.get());
                        tabData.accept(DCBlocks.CurseTheSoil.get());
                    }).build());

    public static void inits(IEventBus event) {
        tab.register(event);
    }
}
