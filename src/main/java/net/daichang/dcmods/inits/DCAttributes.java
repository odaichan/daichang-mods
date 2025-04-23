package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DCAttributes {
    public static final DeferredRegister<Attribute> attribute = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, DCMod.MOD_ID);

    public static final RegistryObject<Attribute> DC_SUPER_DAMAGE;
    public static final RegistryObject<Attribute> DC_DEFENSE;

    static {
        DC_SUPER_DAMAGE = attribute.register("super_damage",  () -> new RangedAttribute("attribute.dc_mods.super_damage", 0, 0, Double.POSITIVE_INFINITY).setSyncable(true));
        DC_DEFENSE = attribute.register("dc_defense",  () -> new RangedAttribute("attribute.dc_mods.dc_defense", 0, 0, Double.POSITIVE_INFINITY).setSyncable(true));
    }
}
