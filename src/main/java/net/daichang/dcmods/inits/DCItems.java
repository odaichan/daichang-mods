package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.common.item.BaseSuperItem;
import net.daichang.dcmods.common.item.DCTier;
import net.daichang.dcmods.common.item.other.DCWitherSpawnEgg;
import net.daichang.dcmods.common.item.tools.*;
import net.daichang.dcmods.common.item.tools.creative.*;
import net.daichang.dcmods.common.item.tools.bow.DCArrow;
import net.daichang.dcmods.common.item.tools.bow.DCBow;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class DCItems {
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, DCMod.MOD_ID);

    public static RegistryObject<Item> registry(String id, Supplier<? extends Item> target) {
        return items.register(id, target);
    }

    public static final RegistryObject<Item> WOOD_INGOT;
    public static final RegistryObject<Item> SUPER_WOOD_INGOT;

    //NORMAL ITEMS
    public static final RegistryObject<Item> NORMAL_WOOD_SWORD;
    public static final RegistryObject<Item> NORMAL_WOOD_PICKAXE;
    public static final RegistryObject<Item> NORMAL_WOOD_AXE;
    public static final RegistryObject<Item> NORMAL_WOOD_SHOVEL;
    public static final RegistryObject<Item> NORMAL_WOOD_HOE;

    //CREATIVE ITEM
    public static final RegistryObject<Item> DC_CRAFT;
    public static final RegistryObject<Item> DESTROY_BLOCK;
    public static final RegistryObject<Item> DATA_SET;
    public static final RegistryObject<Item> DC_ENTITY_REMOVE;
    public static final RegistryObject<Item> TO_BAT;
    public static final RegistryObject<Item> HEAL;

    //SUPER ITEMS
    public static final RegistryObject<Item> SUPER_WOOD_SWORD;
    public static final RegistryObject<Item> SUPER_WOOD_PICKAXE;
    public static final RegistryObject<Item> SUPER_WOOD_AXE;
    public static final RegistryObject<Item> SUPER_WOOD_SHOVEL;
    public static final RegistryObject<Item> SUPER_WOOD_HOE;

    //BOW
    public static final RegistryObject<Item> DC_ARROW;
    public static final RegistryObject<Item> DC_BOW;


    public static final RegistryObject<Item> DC_WITHER_SPAWN;

    static {
        WOOD_INGOT = registry("wood_ingot", ()-> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON)));
        DC_BOW = registry("dc_bow", DCBow::new);

        DC_CRAFT = registry("dc_craft", DCCraft::new);
        DESTROY_BLOCK = registry("destroy_block", DCPickaxe::new);
        DATA_SET = registry("data_set", DCDataHealthSet::new);
        DC_ENTITY_REMOVE = registry("dc_super_remove", DCEntityRemoved::new);
        TO_BAT = registry("to_bat", DCBatItem::new);
        HEAL = registry("heal", DCHeal::new);

        DC_ARROW = registry("dc_arrow", DCArrow::new);

        //NORMAL ITEMS
        NORMAL_WOOD_SWORD = registry("normal_wood_sword", ()-> new ISwordItem(DCTier.NORMAL, 3, -2.4F, new Item.Properties()));
        NORMAL_WOOD_PICKAXE = registry("normal_wood_pickaxe", ()-> new PickaxeItem(DCTier.NORMAL, 1, -2.8F, new Item.Properties()));
        NORMAL_WOOD_AXE = registry("normal_wood_axe", ()-> new AxeItem(DCTier.NORMAL, 1, -2.8F, new Item.Properties()));
        NORMAL_WOOD_SHOVEL = registry("normal_wood_shovel", ()-> new ShovelItem(DCTier.NORMAL, 1.5F, -3.0F, new Item.Properties()));
        NORMAL_WOOD_HOE = registry("normal_wood_hoe", ()-> new HoeItem(DCTier.NORMAL, -4, 0.0F, new Item.Properties()));

        //SUPER ITEMS
        SUPER_WOOD_INGOT = registry("super_wood_ingot", ()-> new BaseSuperItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(16)));
        SUPER_WOOD_SWORD = registry("super_wood_sword", ()-> new DCSuperSwordItem(DCTier.SUPERS, 3, 1.6F,34.7f , new Item.Properties().rarity(Rarity.EPIC)));
        SUPER_WOOD_PICKAXE = registry("super_wood_pickaxe", () -> new DCSuperPickaxeItem(DCTier.SUPERS, 1, -2.8F, new Item.Properties().rarity(Rarity.EPIC)));
        SUPER_WOOD_AXE = registry("super_wood_axe", () -> new DCAxeItem(DCTier.SUPERS, 9.0F, -3.0F, new Item.Properties().rarity(Rarity.EPIC)));
        SUPER_WOOD_SHOVEL = registry("super_wood_shovel", ()-> new DCShovelItem(DCTier.SUPERS, 1.5F, -3.0F, new Item.Properties().rarity(Rarity.EPIC)));
        SUPER_WOOD_HOE = registry("super_wood_hoe", ()-> new DCHoeItem(DCTier.SUPERS, -4, 0.0F, new Item.Properties().rarity(Rarity.EPIC)));

        //SPAWN EGG
        DC_WITHER_SPAWN = registry("dc_wither_spawn_egg", DCWitherSpawnEgg::new);
    }
}