package net.daichang.dcmods.addons.tconstruct;

import net.daichang.dcmods.DCMod;
import net.minecraftforge.eventbus.api.IEventBus;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import java.util.function.Supplier;

import static net.daichang.dcmods.DCMod.MOD_ID;

public class ModifierRegister {
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MOD_ID);

    public static void inits(IEventBus event) {
        DCMod.logger("Etsb and Tconstruct is load");
        MODIFIERS.register(event);
    }

    public static StaticModifier<Modifier> register(String id, Supplier<? extends Modifier> clazz) {
        long startTime = System.currentTimeMillis();
        DCMod.logger("try to register modifier " + id);
        StaticModifier<Modifier> modifier = MODIFIERS.register(id, clazz);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        DCMod.logger("modifier " + id + " registered in " + executionTime + " ms");
        return modifier;
    }

    public static final StaticModifier<Modifier> DC_SUPER_WOOD_INGOT = register("super_wood_ingot", SuperWoodIngot::new);
    public static final StaticModifier<Modifier> OCEAN_HEART = register("heart_of_the_ocean", OceanHeart::new);
    public static final StaticModifier<Modifier> NORMAL_WOOD_INGOT = register("normal_wood_ingot", NormalWoodIngot::new);
}
