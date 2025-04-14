package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.enchantment.EnchFastBow;
import net.daichang.dcmods.enchantment.EnchLiquidBlock;
import net.daichang.dcmods.enchantment.EnchNightVision;
import net.daichang.dcmods.enchantment.EnchSuperSharp;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class DCEnch {
    public static final DeferredRegister<Enchantment> ench = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, DCMod.MOD_ID);

    public static RegistryObject<Enchantment> registry(String id, Supplier<? extends Enchantment> target) {
        return ench.register(id, target);
    }

    public static final RegistryObject<Enchantment> SuperSharp;
    public static final RegistryObject<Enchantment> NightVison;
    public static final RegistryObject<Enchantment> FAST_BOW;
    public static final RegistryObject<Enchantment> LIQUID_WALK;

    static {
        SuperSharp = registry("super_sharp", EnchSuperSharp::new);
        NightVison = registry("night_vison", EnchNightVision::new);
        FAST_BOW = registry("fast_bow", EnchFastBow::new);
        LIQUID_WALK = registry("liquid_walk", EnchLiquidBlock::new);
    }
}
