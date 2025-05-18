package net.daichang.dcmods.addons.fantasy_ending;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.addons.fantasy_ending.items.LifeAndDeathBook;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FEItem {
    public static List<RegistryObject<Item>> list = new ArrayList<>();

    public static final DeferredRegister<Item> item = DeferredRegister.create(ForgeRegistries.ITEMS, DCMod.MOD_ID);

    public static RegistryObject<Item> registry(String id, Supplier<? extends Item> target) {
        RegistryObject<Item> object = item.register(id, target);
        list.add(object);
        return object;
    }

    public static final RegistryObject<Item> BookOfLifeAndDeath;

    static {
        BookOfLifeAndDeath = registry("book_of_life_and_death", LifeAndDeathBook::new);
    }
}