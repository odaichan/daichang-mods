package net.daichang.dcmods.addons.farmers_delight;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.common.item.DCTier;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FDItem {
    public static List<RegistryObject<Item>> list = new ArrayList<>();

    public static final DeferredRegister<Item> item = DeferredRegister.create(ForgeRegistries.ITEMS, DCMod.MOD_ID);

    public static RegistryObject<Item> registry(String id, Supplier<? extends Item> target) {
        RegistryObject<Item> object = item.register(id, target);
        list.add(object);
        return object;
    }

    public static final RegistryObject<Item> StewedHeartOfTheOcean;
    public static final RegistryObject<Item> NORMAL_KNIVES;
    public static final RegistryObject<Item> SUPER_KNIVES;

    static {
        StewedHeartOfTheOcean = registry("stewed_heart_of_the_ocean", ()-> new FoodItem(1, FoodValue.stewedOceanHeart));
        NORMAL_KNIVES = registry("normal_wood_knives", ()-> new IKnives(DCTier.NORMAL, 1.2D,2.2D, 1));
        SUPER_KNIVES = registry("super_wood_knives", ()-> new IKnives(DCTier.SUPERS, 4.2D,6.2D, 1));
    }
}
