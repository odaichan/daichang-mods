package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.common.item.other.DCBlockItem;
import net.daichang.dcmods.common.item.other.flowers.RedSpiderLilyBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.daichang.dcmods.inits.DCItems.all_item;

public class DCBlockItems {
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, DCMod.MOD_ID);

    public static final List<RegistryObject<Item>> list = new ArrayList<>();

    public static RegistryObject<Item> registry(String id, Supplier<? extends BlockItem> clazz) {
        RegistryObject<Item> object = items.register(id, clazz);
        list.add(object);
        all_item.add(object);
        return object;
    }

    public static final RegistryObject<Item> RED_SPIDER_LILY;
    public static final RegistryObject<Item> CurseTheSoil;

    static {
        RED_SPIDER_LILY = registry("red_spider_lily", RedSpiderLilyBlockItem::new);
        CurseTheSoil = registry("curse_the_soil", ()->new DCBlockItem(DCBlocks.CurseTheSoil.get()));
    }
}
