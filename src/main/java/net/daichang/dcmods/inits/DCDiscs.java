package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.common.item.DCBaseDisc;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DCDiscs {
    public static final DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, DCMod.MOD_ID);
    public static final List<RegistryObject<Item>> discs = new ArrayList<>();
    public static final RegistryObject<Item> BossFight;
    public static final RegistryObject<Item> Light;
    public static final RegistryObject<Item> Recollection;
    public static final RegistryObject<Item> MoogCity2;

    public static RegistryObject<Item> discRegister(String id, Supplier<? extends Item> target) {
        RegistryObject<Item> object = items.register(id, target);
        discs.add(object);
        return object;
    }

    static {
        BossFight = discRegister("boss_fight_disc", ()-> new DCBaseDisc(DCSounds.BOSS_FIGHT.get(), 2440));
        Light = discRegister("railway_guerrilla", () -> new DCBaseDisc(DCSounds.Railway_Guerrilla.get(), 2480));
        Recollection = discRegister("recollection", () -> new DCBaseDisc(DCSounds.Recollection.get(), 4800));
        MoogCity2 = discRegister("moog_city_2", () -> new DCBaseDisc(DCSounds.Moog_City_2.get(), 3700));
    }
}
