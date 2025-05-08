package net.daichang.dcmods.addons.avaritia;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.addons.avaritia.items.WoodSingularity;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AvaritiaItems {
    public static List<RegistryObject<Item>> list = new ArrayList<>();

    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, DCMod.MOD_ID);

    public static RegistryObject<Item> registry(String id, Supplier<? extends Item> target) {
        RegistryObject<Item> object = items.register(id, target);
        list.add(object);
        return object;
    }

    public static final RegistryObject<Item> WOOD_SWORD_SINGULARITY;

    static {
        WOOD_SWORD_SINGULARITY = registry("super_wood_singularity", WoodSingularity::new);
    }
}
