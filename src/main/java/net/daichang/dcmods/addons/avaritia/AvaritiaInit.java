package net.daichang.dcmods.addons.avaritia;

import committee.nova.mods.avaritia.common.item.singularity.SingularityItem;
import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.daichang.dcmods.inits.DCItems.all_item;

public class AvaritiaInit {
    public static List<RegistryObject<Item>> list = new ArrayList<>();

    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, DCMod.MOD_ID);

    public static void inits(IEventBus event) {
        DCMod.logger("Avaritia is load");
        items.register(event);
    }

    public static RegistryObject<Item> registry(String id) {
        long startTime = System.currentTimeMillis();
        DCMod.logger("try to register singularity " + id);
        RegistryObject<Item> object = items.register(id, SingularityItem::new);
        list.add(object);
        all_item.add(object);
        SuperItemList.addItem(object.get());
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        DCMod.logger("singularity " + id + " registered in " + executionTime + " ms");
        return object;
    }

    public static RegistryObject<Item> registry(String id, Supplier<? extends Item> itemclazz) {
        long startTime = System.currentTimeMillis();
        DCMod.logger("try to register item " + id);
        RegistryObject<Item> object = items.register(id, itemclazz);
        list.add(object);
        all_item.add(object);
        SuperItemList.addItem(object.get());
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        DCMod.logger("item " + id + " registered in " + executionTime + " ms");
        return object;
    }

    //bzd叫什么的积点，我也不知道可以拿来干啥
    //廖胜于无，就多写一点了
    public static final RegistryObject<Item> WOOD_SWORD_SINGULARITY;
    public static final RegistryObject<Item> NORMAL_WOOD_SINGULARITY;
    public static final RegistryObject<Item> HEART_OF_OCEAN_SINGULARITY;

    static {
        WOOD_SWORD_SINGULARITY = registry("super_wood_singularity");
        NORMAL_WOOD_SINGULARITY = registry("normal_wood_singularity");
        HEART_OF_OCEAN_SINGULARITY = registry("heart_of_ocean_singularity");
    }
}
