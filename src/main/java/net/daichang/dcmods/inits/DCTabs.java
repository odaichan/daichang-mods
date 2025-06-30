package net.daichang.dcmods.inits;

import net.daichang.dcmods.addons.avaritia.AvaritiaInit;
import net.daichang.dcmods.addons.curios.CuriosItems;
import net.daichang.dcmods.addons.fantasy_ending.FEInit;
import net.daichang.dcmods.addons.farmers_delight.FDItem;
import net.daichang.dcmods.addons.slashblade.DaiChangSB;
import net.daichang.dcmods.addons.slashblade.SBInits;
import net.daichang.dcmods.common.creative.DCCreativeModeTab;
import net.daichang.dcmods.common.item.UseCountItem;
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
            () -> DCCreativeModeTab.builder()
                    .title(Component.translatable("tabs.dc_mods.tab"))
                    .icon(() -> new ItemStack(DCItems.SUPER_WOOD_SWORD.get()))
                    .withTabsImage(tabsImage)
                    .withBackgroundLocation(backGround)
                    .withTabsBefore(CreativeModeTabs.COMBAT)
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

                        for (RegistryObject<Item> object : DCItems.dc_normal) tabData.accept(object.get());
                        for (RegistryObject<Item> object : DCItems.armors) tabData.accept(object.get());
                        for (RegistryObject<Item> object : DCDiscs.discs) tabData.accept(object.get());
                        if (ModUtil.isCuriosLoad()) for (RegistryObject<Item> object : CuriosItems.curios) tabData.accept(object.get());
                        if (ModUtil.isAvaritiaLoad()) for (RegistryObject<Item> object : AvaritiaInit.list) tabData.accept(object.get());
                        if (ModUtil.isFDLoad()) for (RegistryObject<Item> object : FDItem.list) tabData.accept(object.get());
                        if (ModUtil.isFELoad()) for (RegistryObject<Item> object : FEInit.list) tabData.accept(object.get());
                        if (ModUtil.isSBLoad()) {
                            ItemStack stack = new ItemStack(SBInits.DC_SB.get());
                            DaiChangSB.init(stack);
                            tabData.accept(stack);
                        }
                    }).build());

    public static final RegistryObject<CreativeModeTab> DC_MOD_CREATIVE_TAB = tab.register("dc_mod_creative_tab",
            () -> DCCreativeModeTab.builder()
                    .title(Component.translatable("tabs.dc_mods.tab_creative"))
                    .icon(() -> new ItemStack(DCItems.DC_CRAFT.get()))
                    .withTabsImage(tabsImage)
                    .withBackgroundLocation(backGround)
                    .withTabsBefore(DC_MOD_TAB.getKey())
                    .displayItems((parameters, tabData) -> {
                        for (RegistryObject<Item> object : DCItems.dc_creative) tabData.accept(object.get());

                        Item[] items = {DCItems.SUPER_WOOD_SWORD.get(), DCItems.OCEAN_SCYTHE.get(), DCItems.SUPER_WOOD_AXE.get()};

                        for (Item item : items) {
                            ItemStack stack = new ItemStack(item);
                            UseCountItem.setUseS(stack, Integer.MAX_VALUE);
                            stack.enchant(Enchantments.SHARPNESS, 12);
                            stack.enchant(Enchantments.MOB_LOOTING, 10);
                            stack.enchant(DCEnch.SuperSharp.get(), 24);
                            stack.getOrCreateTag().putBoolean("Unbreakable", true);
                            tabData.accept(stack);
                        }

                        String[] names = {"DaiHardcore", "Hacker_LX", "MY_Ender" };

                        for (String name : names) {
                            ItemStack head = new ItemStack(Items.PLAYER_HEAD);
                            head.getOrCreateTag().putString("SkullOwner", name);
                            head.enchant(Enchantments.BLOCK_FORTUNE, 52);
                            tabData.accept(head);
                        }

                        for (RegistryObject<Item> object : DCItems.spawn_egg) tabData.accept(object.get());

                        for (RegistryObject<Enchantment> ench : DCEnch.list) {
                            ItemStack ench_book = new ItemStack(Items.ENCHANTED_BOOK);
                            ench_book.enchant(ench.get(), 10);
                            tabData.accept(ench_book);
                        }
                    }).build());


    public static final RegistryObject<CreativeModeTab> DC_MOD_BLOCK_TAB = tab.register("dc_mod_block_tab",
            () -> DCCreativeModeTab.builder()
                    .title(Component.translatable("tabs.dc_mods.tab_block"))
                    .icon(() -> new ItemStack(DCBlockItems.RED_SPIDER_LILY.get()))
                    .withTabsImage(tabsImage)
                    .withBackgroundLocation(backGround)
                    .withTabsBefore(DC_MOD_CREATIVE_TAB.getKey())
                    .displayItems((parameters, tabData) -> {
                        tabData.accept(DCBlocks.RED_SPIDER_LILY.get());
                        tabData.accept(DCBlocks.CurseTheSoil.get());
                    }).build());

    public static final RegistryObject<CreativeModeTab> DC_ITEM_ALL = tab.register("dc_all_item_tab",
            () -> DCCreativeModeTab.builder()
                    .title(Component.translatable("tabs.dc_mods.all_items"))
                    .withTabsImage(tabsImage)
                    .withBackgroundLocation(backGround)
                    .icon(() -> new ItemStack(DCItems.EX_CALIBUR.get()))
                    .withTabsBefore(DC_MOD_BLOCK_TAB.getKey())
                    .displayItems((parameters, tabData) -> {
                        for (RegistryObject<Item> object : DCItems.all_item) tabData.accept(object.get());
                        if (ModUtil.isSBLoad()) {
                            ItemStack stack = new ItemStack(SBInits.DC_SB.get());
                            DaiChangSB.init(stack);
                            tabData.accept(stack);
                        }
                    }).build());

    public static void inits(IEventBus event) {
        tab.register(event);
    }
}
