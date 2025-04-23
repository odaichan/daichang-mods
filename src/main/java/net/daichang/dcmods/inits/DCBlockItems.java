package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.common.item.other.DCBlockItem;
import net.daichang.dcmods.common.item.other.flowers.RedSpiderLilyBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class DCBlockItems {
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, DCMod.MOD_ID);

    public static RegistryObject<Item> registry(String id, Supplier<? extends BlockItem> clazz) {
        return items.register(id, clazz);
    }

    public static final RegistryObject<Item> RED_SPIDER_LILY;
    public static final RegistryObject<Item> CurseTheSoil;

    static {
        RED_SPIDER_LILY = registry("red_spider_lily", RedSpiderLilyBlockItem::new);
        CurseTheSoil = registry("curse_the_soil", ()->new DCBlockItem(DCBlocks.CurseTheSoil.get()));
    }
}
