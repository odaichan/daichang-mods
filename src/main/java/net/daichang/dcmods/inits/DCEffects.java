package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.common.effect.BaseEffect;
import net.daichang.dcmods.common.effect.EffectBloodshed;
import net.daichang.dcmods.common.effect.EffectHeal;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class DCEffects {
    public static final DeferredRegister<MobEffect> effects;

    public static final RegistryObject<MobEffect> Bloodshed;
    public static final RegistryObject<MobEffect> Freeze;
    public static final RegistryObject<MobEffect> Heal;

    public static RegistryObject<MobEffect> register(String id, Supplier<? extends MobEffect> supplier) {
        return effects.register(id, supplier);
    }

    static {
        effects = DeferredRegister.create(Registries.MOB_EFFECT, DCMod.MOD_ID);
        Bloodshed = register("bloodshed", EffectBloodshed::new);
        Freeze = register("freeze", ()-> new BaseEffect(MobEffectCategory.BENEFICIAL, 0xFF55FF));
        Heal = register("super_heal", EffectHeal::new);
    }
}
