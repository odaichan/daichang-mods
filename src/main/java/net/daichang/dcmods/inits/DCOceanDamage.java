package net.daichang.dcmods.inits;

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

public class DCOceanDamage {
    public static ResourceKey<DamageType> OCEAN_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(DCMod.MOD_ID, "ocean_damage"));


    public static final RegistrySetBuilder DAMAGE_BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, DCOceanDamage::bootstrap);

    public static HolderLookup.Provider append(HolderLookup.Provider original) {
        return DAMAGE_BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), original);
    }

    // 注册
    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(OCEAN_DAMAGE, new DamageType("ocean_damage", DamageScaling.ALWAYS, 0.1F));
    }
}
