package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DCSounds {
    public static final DeferredRegister<SoundEvent> sounds = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DCMod.MOD_ID);
    public static final RegistryObject<SoundEvent> TIME_STOP = sounds.register("time_stop", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DCMod.MOD_ID, "time_stop")));

    public static final RegistryObject<SoundEvent> TIME_RESUME = sounds.register("time_resume", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DCMod.MOD_ID, "time_resume")));

    public static final RegistryObject<SoundEvent> BOSS_FIGHT = sounds.register("boss_fight", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DCMod.MOD_ID, "boss_fight")));

    public static final RegistryObject<SoundEvent> BOSS_DEATH = sounds.register("boss_death", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DCMod.MOD_ID, "boss_death")));
}
