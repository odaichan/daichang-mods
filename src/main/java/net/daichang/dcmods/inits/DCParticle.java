package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class DCParticle {
    public static final DeferredRegister<ParticleType<?>> particle = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DCMod.MOD_ID);

    public static RegistryObject<SimpleParticleType> register(String id, Supplier<? extends SimpleParticleType> supplier) {
        long startTime = System.currentTimeMillis();
        DCMod.logger("try to register particle " + id);
        RegistryObject<SimpleParticleType> object = particle.register(id, supplier);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        DCMod.logger("particle " + id + " registered in " + executionTime + " ms");
        return object;

    }

    public static final RegistryObject<SimpleParticleType> SABER_PARTICLE = particle.register("ex", () -> new SimpleParticleType(true));
}
