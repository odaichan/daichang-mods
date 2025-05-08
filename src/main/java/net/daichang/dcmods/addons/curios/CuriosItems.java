package net.daichang.dcmods.addons.curios;

import net.daichang.dcmods.DCMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class CuriosItems {
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, DCMod.MOD_ID);

    public static RegistryObject<Item> registry(String id, Supplier<? extends Item> target) {
        return items.register(id, target);
    }

    public static final RegistryObject<Item> WOOD_RING;


    static {
        WOOD_RING = registry("wood_ring", SuperWoodRing::new);
    }
}
