package net.daichang.dcmods.inits;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.daichang.dcmods.DCMod.MOD_ID;

public class DCSounds {
    public static final DeferredRegister<SoundEvent> sounds = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);
    public static final RegistryObject<SoundEvent> TIME_STOP = sounds.register("time_stop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "time_stop")));

    public static final RegistryObject<SoundEvent> TIME_RESUME = sounds.register("time_resume", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "time_resume")));

    public static final RegistryObject<SoundEvent> BOSS_FIGHT = sounds.register("boss_fight", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "boss_fight")));

    public static final RegistryObject<SoundEvent> Recollection = sounds.register("recollection", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "recollection")));

    public static final RegistryObject<SoundEvent> BOSS_DEATH = sounds.register("boss_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "boss_death")));

    public static final RegistryObject<SoundEvent> LOLI_SUCCRSS = sounds.register("loli_succrss", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "loli_succrss")));

    public static final RegistryObject<SoundEvent> DC_HIT_ENTITY = sounds.register("dc_hit_entity", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "dc_hit_entity")));

    public static final RegistryObject<SoundEvent> Railway_Guerrilla = sounds.register("railway_guerrilla", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "railway_guerrilla")));

    public static final RegistryObject<SoundEvent> Moog_City_2 = sounds.register("moog_city_2", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MOD_ID, "moog_city_2")));
}
