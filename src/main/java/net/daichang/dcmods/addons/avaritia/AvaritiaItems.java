package net.daichang.dcmods.addons.avaritia;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.addons.avaritia.items.DCSingularity;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.daichang.dcmods.inits.DCItems.all_item;

public class AvaritiaItems {
    public static List<RegistryObject<Item>> list = new ArrayList<>();

    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, DCMod.MOD_ID);

    public static RegistryObject<Item> registry(String id, Supplier<? extends Item> target) {
        long startTime = System.currentTimeMillis();
        DCMod.logger("try to register item " + id);
        RegistryObject<Item> object = items.register(id, target);
        list.add(object);
        all_item.add(object);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        DCMod.logger("item " + id + " registered in " + executionTime + " ms");
        return object;
    }

    public static final RegistryObject<Item> WOOD_SWORD_SINGULARITY;

    static {
        WOOD_SWORD_SINGULARITY = registry("super_wood_singularity", DCSingularity::new);
    }
}
