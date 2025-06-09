package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.daichang.dcmods.DCMod.MOD_ID;

public class DCSounds {
    public static final DeferredRegister<SoundEvent> sounds = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);

    public static RegistryObject<SoundEvent> registry(String id, Supplier<? extends SoundEvent> supplier) {
        long startTime = System.currentTimeMillis();
        DCMod.logger("try to register sound " + id);
        RegistryObject<SoundEvent> object = sounds.register(id, supplier);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        DCMod.logger("sound " + id + " registered in " + executionTime + " ms");
        return object;
    }

    public static final RegistryObject<SoundEvent> TIME_STOP = registry("time_stop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "time_stop")));

    public static final RegistryObject<SoundEvent> TIME_RESUME = registry("time_resume", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "time_resume")));

    public static final RegistryObject<SoundEvent> BOSS_FIGHT = registry("boss_fight", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "boss_fight")));

    public static final RegistryObject<SoundEvent> Recollection = registry("recollection", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "recollection")));

    public static final RegistryObject<SoundEvent> BOSS_DEATH = registry("boss_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "boss_death")));

    public static final RegistryObject<SoundEvent> LOLI_SUCCRSS = registry("loli_succrss", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "loli_succrss")));

    public static final RegistryObject<SoundEvent> DC_HIT_ENTITY = registry("dc_hit_entity", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "dc_hit_entity")));

    public static final RegistryObject<SoundEvent> Railway_Guerrilla = registry("railway_guerrilla", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "railway_guerrilla")));

    public static final RegistryObject<SoundEvent> Moog_City_2 = registry("moog_city_2", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "moog_city_2")));
}
