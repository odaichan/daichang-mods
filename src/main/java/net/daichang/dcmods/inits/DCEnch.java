package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.common.enchantment.dc_enchs.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DCEnch {
    public static final DeferredRegister<Enchantment> ench = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DCMod.MOD_ID);
    public static List<RegistryObject<Enchantment>> list = new ArrayList<>();

    public static RegistryObject<Enchantment> registry(String id, Supplier<? extends Enchantment> target) {
        long startTime = System.currentTimeMillis();
        DCMod.logger("try to register enchantment " + id);
        RegistryObject<Enchantment> object = ench.register(id, target);
        list.add(object);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        DCMod.logger("enchantment " + id + " registered in " + executionTime + " ms");
        return object;
    }

    public static final RegistryObject<Enchantment> SuperSharp;
    public static final RegistryObject<Enchantment> SuperProtect;
    public static final RegistryObject<Enchantment> NightVison;
    public static final RegistryObject<Enchantment> FAST_BOW;
    public static final RegistryObject<Enchantment> LIQUID_WALK;
    public static final RegistryObject<Enchantment> WATER_DEATH;

    static {
        SuperSharp = registry("super_sharp", EnchSuperSharp::new);
        NightVison = registry("night_vison", EnchNightVision::new);
        FAST_BOW = registry("fast_bow", EnchFastBow::new);
        LIQUID_WALK = registry("liquid_walk", EnchLiquidBlock::new);
        SuperProtect = registry("super_protection", EnchSuperProtection::new);
        WATER_DEATH = registry("underwater_curse",EnchWaterDeath::new);
    }
}
