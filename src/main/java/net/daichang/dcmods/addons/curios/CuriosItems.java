package net.daichang.dcmods.addons.curios;

import net.daichang.dcmods.DCMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class CuriosItems {
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, DCMod.MOD_ID);
    public static final List<RegistryObject<Item>> curios = new ArrayList<>();

    public static RegistryObject<Item> registry(String id, Supplier<? extends Item> target) {
        RegistryObject<Item> object = items.register(id, target);
        curios.add(object);
        return object;
    }

    public static final RegistryObject<Item> WOOD_RING;
    public static final RegistryObject<Item> OCEAN_LOVE;

    static {
        WOOD_RING = registry("wood_ring", SuperWoodRing::new);
        OCEAN_LOVE = registry("ocean_love", OceanLoce::new);
    }
}
