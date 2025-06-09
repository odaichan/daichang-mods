package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class DCAttributes {
    public static final DeferredRegister<Attribute> attribute = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, DCMod.MOD_ID);

    public static final RegistryObject<Attribute> DC_SUPER_DAMAGE;
    public static final RegistryObject<Attribute> DC_DEFENSE;
    public static final RegistryObject<Attribute> OCEAN_DAMAGE;

    public static RegistryObject<Attribute> register(String id, Supplier<? extends Attribute> supplier) {
        long startTime = System.currentTimeMillis();
        DCMod.logger("try to register attribute " + id);
        RegistryObject<Attribute> object = attribute.register(id, supplier);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        DCMod.logger("attribute " + id + " registered in " + executionTime + " ms");
        return object;
    }

    static {
        DC_SUPER_DAMAGE = register("super_damage",  () -> new RangedAttribute("attribute.dc_mods.super_damage", 0, 0, Double.POSITIVE_INFINITY).setSyncable(true));
        OCEAN_DAMAGE = register("ocean_damage",  () -> new RangedAttribute("attribute.dc_mods.ocean_damage", 0, 0, Double.POSITIVE_INFINITY).setSyncable(true));
        DC_DEFENSE = register("dc_defense",  () -> new RangedAttribute("attribute.dc_mods.dc_defense", 0, 0, Double.POSITIVE_INFINITY).setSyncable(true));
    }
}
