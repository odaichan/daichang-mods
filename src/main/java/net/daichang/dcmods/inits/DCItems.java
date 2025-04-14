package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.item.DCTier;
import net.daichang.dcmods.item.TimeClock;
import net.daichang.dcmods.item.tools.*;
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
    public static final RegistryObject<Item> TIME_CLOCK;
    public static final RegistryObject<Item> DATA_SET;

    //SUPER ITEMS
    public static final RegistryObject<Item> SUPER_WOOD_SWORD;
    public static final RegistryObject<Item> SUPER_WOOD_PICKAXE;
    public static final RegistryObject<Item> SUPER_WOOD_AXE;
    public static final RegistryObject<Item> SUPER_WOOD_SHOVEL;
    public static final RegistryObject<Item> SUPER_WOOD_HOE;

    static {
        WOOD_INGOT = registry("wood_ingot", ()-> new Item(new Item.Properties().stacksTo(16).rarity(Rarity.COMMON)));
        DC_CRAFT = registry("dc_craft", DCCraft::new);
        DESTROY_BLOCK = registry("destroy_block", DCPickaxe::new);
        TIME_CLOCK = registry("time_clock", TimeClock::new);
        DATA_SET = registry("data_set", DCDataHealthSet::new);

        //NORMAL ITEMS
        NORMAL_WOOD_SWORD = registry("normal_wood_sword", ()-> new SwordItem(DCTier.NORMAL, 3, -2.4F, new Item.Properties()));
        NORMAL_WOOD_PICKAXE = registry("normal_wood_pickaxe", ()-> new PickaxeItem(DCTier.NORMAL, 1, -2.8F, new Item.Properties()));
        NORMAL_WOOD_AXE = registry("normal_wood_axe", ()-> new AxeItem(DCTier.NORMAL, 1, -2.8F, new Item.Properties()));
        NORMAL_WOOD_SHOVEL = registry("normal_wood_shovel", ()-> new ShovelItem(DCTier.NORMAL, 1.5F, -3.0F, new Item.Properties()));
        NORMAL_WOOD_HOE = registry("normal_wood_hoe", ()-> new HoeItem(DCTier.NORMAL, -4, 0.0F, new Item.Properties()));

        //SUPER ITEMS
        SUPER_WOOD_INGOT = registry("super_wood_ingot", ()-> new Item(new Item.Properties().rarity(Rarity.EPIC).stacksTo(8)));
        SUPER_WOOD_SWORD = registry("super_wood_sword", ()-> new DCSuperSwordItem(DCTier.SUPERS, 3, 1.6F,34.7f , new Item.Properties().rarity(Rarity.EPIC)));
        SUPER_WOOD_PICKAXE = registry("super_wood_pickaxe", () -> new DCSuperPickaxeItem(DCTier.SUPERS, 1, -2.8F, new Item.Properties().rarity(Rarity.EPIC)));
        SUPER_WOOD_AXE = registry("super_wood_axe", () -> new DCAxeItem(DCTier.SUPERS, 9.0F, -3.0F, new Item.Properties().rarity(Rarity.EPIC)));
        SUPER_WOOD_SHOVEL = registry("super_wood_shovel", ()-> new DCShovelItem(DCTier.SUPERS, 1.5F, -3.0F, new Item.Properties().rarity(Rarity.EPIC)));
        SUPER_WOOD_HOE = registry("super_wood_hoe", ()-> new DCHoeItem(DCTier.SUPERS, -4, 0.0F, new Item.Properties().rarity(Rarity.EPIC)));
    }
}