package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.common.item.BaseSuperItem;
import net.daichang.dcmods.common.item.DCBaseSpawnEgg;
import net.daichang.dcmods.common.item.DCTier;
import net.daichang.dcmods.common.item.armors.DCSuperArmor;
import net.daichang.dcmods.common.item.crafts.HeartOfTheOcean;
import net.daichang.dcmods.common.item.other.DCLoliSpawnEgg;
import net.daichang.dcmods.common.item.other.DCSteveSpawnEgg;
import net.daichang.dcmods.common.item.other.DCWitherSpawnEgg;
import net.daichang.dcmods.common.item.other.SteveTokenItem;
import net.daichang.dcmods.common.item.tools.*;
import net.daichang.dcmods.common.item.tools.bow.DCArrow;
import net.daichang.dcmods.common.item.tools.bow.DCBow;
import net.daichang.dcmods.common.item.tools.creative.*;
import net.daichang.dcmods.common.item.tools.normal.IAxeItem;
import net.daichang.dcmods.common.item.tools.normal.IHoeItem;
import net.daichang.dcmods.common.item.tools.normal.IPickaxeItem;
import net.daichang.dcmods.common.item.tools.normal.IShovelItem;
import net.daichang.dcmods.common.item.tools.supers.*;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DCItems {
    public static final List<RegistryObject<Item>> all_item = new ArrayList<>();
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, DCMod.MOD_ID);

    public static RegistryObject<Item> registry(String id, Supplier<? extends Item> target) {
        long startTime = System.currentTimeMillis();
        DCMod.logger("try to register item " + id);
        RegistryObject<Item> item = items.register(id, target);
        all_item.add(item);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        DCMod.logger("item " + id + " registered in " + executionTime + " ms");
        return item;
    }

    public static final List<RegistryObject<Item>> armors = new ArrayList<>();
    public static final List<RegistryObject<Item>> dc_normal = new ArrayList<>();
    public static final List<RegistryObject<Item>> dc_creative = new ArrayList<>();
    public static final List<RegistryObject<Item>> spawn_egg = new ArrayList<>();

    public static RegistryObject<Item> armorRegister(String id, Supplier<? extends ArmorItem> target) {
        RegistryObject<Item> object = registry(id, target);
        armors.add(object);
        return object;
    }

    public static RegistryObject<Item> eggRegister(String id, Supplier<? extends ForgeSpawnEggItem> target) {
        RegistryObject<Item> object = registry(id, target);
        spawn_egg.add(object);
        return object;
    }

    public static RegistryObject<Item> normalItemRegister(String id, Supplier<? extends Item> target) {
        RegistryObject<Item> object = registry(id, target);
        dc_normal.add(object);
        return object;
    }

    public static RegistryObject<Item> creativeItemRegister(String id, Supplier<? extends Item> target) {
        RegistryObject<Item> object = registry(id, target);
        dc_creative.add(object);
        return object;
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
    public static final RegistryObject<Item> DC_STEVE;


    public static final RegistryObject<Item> LoliPickaxe;

    public static final RegistryObject<Item> WOOD_TOTEM;

    public static final RegistryObject<Item> STEVE_TOKEN;


    public static final RegistryObject<Item> WOOD_HELMET;
    public static final RegistryObject<Item> WOOD_CHESTPLATE;
    public static final RegistryObject<Item> WOOD_LEGGINGS;
    public static final RegistryObject<Item> WOOD_BOOTS;
    public static final RegistryObject<Item> HEART_OF_THE_OCEAN;
    public static final RegistryObject<Item> ELIANIA_MODE;
    public static final RegistryObject<Item> OCEAN_SCYTHE;
    public static final RegistryObject<Item> LOLI_SPAWN;
    public static final RegistryObject<Item> EX_CALIBUR;
    public static final RegistryObject<Item> DC_WITHER_SPWAN;

    static {
        WOOD_INGOT = normalItemRegister("wood_ingot", ()-> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON)));
        DC_BOW = normalItemRegister("dc_bow", DCBow::new);

        DC_CRAFT = creativeItemRegister("dc_craft", DCCraft::new);
        DESTROY_BLOCK = creativeItemRegister("destroy_block", DCPickaxe::new);
        DATA_SET = creativeItemRegister("data_set", DCDataHealthSet::new);
        DC_ENTITY_REMOVE = creativeItemRegister("dc_super_remove", DCEntityRemoved::new);
        TO_BAT = creativeItemRegister("to_bat", DCBatItem::new);
        HEAL = creativeItemRegister("heal", DCHeal::new);

        DC_ARROW = normalItemRegister("dc_arrow", DCArrow::new);

        //NORMAL ITEMS
        NORMAL_WOOD_SWORD = normalItemRegister("normal_wood_sword", ()-> new ISwordItem(DCTier.NORMAL, 3, -2.4F, new Item.Properties()));
        NORMAL_WOOD_PICKAXE = normalItemRegister("normal_wood_pickaxe", ()-> new IPickaxeItem(DCTier.NORMAL, 1, -2.8F, new Item.Properties()));
        NORMAL_WOOD_AXE = normalItemRegister("normal_wood_axe", ()-> new IAxeItem(DCTier.NORMAL, 1, -2.8F, new Item.Properties()));
        NORMAL_WOOD_SHOVEL = normalItemRegister("normal_wood_shovel", ()-> new IShovelItem(DCTier.NORMAL, 1.5F, -3.0F, new Item.Properties()));
        NORMAL_WOOD_HOE = normalItemRegister("normal_wood_hoe", ()-> new IHoeItem(DCTier.NORMAL, -4, 0.0F, new Item.Properties()));

        //SUPER ITEMS
        SUPER_WOOD_INGOT = normalItemRegister("super_wood_ingot", ()-> new BaseSuperItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(16)));
        SUPER_WOOD_SWORD = normalItemRegister("super_wood_sword", ()-> new DCSuperSwordItem(DCTier.SUPERS, 3, 1.6F,13.7f , new Item.Properties().rarity(Rarity.EPIC)));
        SUPER_WOOD_PICKAXE = normalItemRegister("super_wood_pickaxe", () -> new DCSuperPickaxeItem(DCTier.SUPERS, 1, -2.8F, new Item.Properties().rarity(Rarity.EPIC)));
        SUPER_WOOD_AXE = normalItemRegister("super_wood_axe", () -> new DCAxeItem(DCTier.SUPERS, 9.0F, -3.0F, new Item.Properties().rarity(Rarity.EPIC)));
        SUPER_WOOD_SHOVEL = normalItemRegister("super_wood_shovel", ()-> new DCShovelItem(DCTier.SUPERS, 1.5F, -3.0F, new Item.Properties().rarity(Rarity.EPIC)));
        SUPER_WOOD_HOE = normalItemRegister("super_wood_hoe", ()-> new DCHoeItem(DCTier.SUPERS, -4, 0.0F, new Item.Properties().rarity(Rarity.EPIC)));

        //SPAWN EGG
        DC_WITHER_SPAWN = eggRegister("dc_wither_spawn_egg", DCWitherSpawnEgg::new);

        STEVE_TOKEN = normalItemRegister("steve_token", SteveTokenItem::new);

        DC_STEVE = eggRegister("dc_steve_spawn_egg", DCSteveSpawnEgg::new);

        LOLI_SPAWN = eggRegister("dc_loli_spawn_egg", DCLoliSpawnEgg::new);

        LoliPickaxe = creativeItemRegister("loli_pickaxe", DCLoliPickaxe::new);

        WOOD_TOTEM = normalItemRegister("super_wood_totem", SuperWoodTotem::new);
        EX_CALIBUR = creativeItemRegister("excalibur", EXCalibur::new);

        //ARMOR
        HEART_OF_THE_OCEAN = normalItemRegister("heart_of_the_ocean", HeartOfTheOcean::new);
        WOOD_HELMET = armorRegister("super_wood_helmet", DCSuperArmor.Helmet::new);
        WOOD_CHESTPLATE = armorRegister("super_wood_chestplate", DCSuperArmor.Chestplate::new);
        WOOD_LEGGINGS = armorRegister("super_wood_leggings", DCSuperArmor.Leggings::new);
        WOOD_BOOTS = armorRegister("super_wood_boots", DCSuperArmor.Boots::new);
        OCEAN_SCYTHE = normalItemRegister("ocean_scythe", OceanScythe::new);
        ELIANIA_MODE = creativeItemRegister("set_dead", DCElainaMode::new);
        DC_WITHER_SPWAN = eggRegister("dc_wither_boss_spawn_egg", ()-> new DCBaseSpawnEgg(DCEntities.DC_WITHER, Color.DARK_GRAY.getRGB(), Color.BLACK.getRGB(), new Item.Properties()));
    }
}