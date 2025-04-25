package net.daichang.dcmods.common.damge_type;

import net.daichang.dcmods.DCMod;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class SuperDamageTypes {
    public static ResourceKey<DamageType> SUPER_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(DCMod.MOD_ID, "super_snow_damage"));


    public static final RegistrySetBuilder DAMAGE_BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, SuperDamageTypes::bootstrap);

    public static HolderLookup.Provider append(HolderLookup.Provider original) {
        return DAMAGE_BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), original);
    }

    // 注册
    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(SUPER_DAMAGE, new DamageType("super_snow_damage", DamageScaling.ALWAYS, 0.1F));
    }
}
