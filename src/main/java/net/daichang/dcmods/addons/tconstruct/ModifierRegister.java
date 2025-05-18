package net.daichang.dcmods.addons.tconstruct;

import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static net.daichang.dcmods.DCMod.MOD_ID;

public class ModifierRegister {
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MOD_ID);

    public static final StaticModifier<SuperWoodIngot> DC_SUPER_WOOD_INGOT = MODIFIERS.register("super_wood_ingot", SuperWoodIngot::new);
    public static final StaticModifier<OceanHeart> OCEAN_HEART = MODIFIERS.register("heart_of_the_ocean", OceanHeart::new);
}
