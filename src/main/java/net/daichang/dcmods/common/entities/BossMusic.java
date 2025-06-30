package net.daichang.dcmods.common.entities;

import net.daichang.dcmods.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class BossMusic extends AbstractTickableSoundInstance {
    BossEntity boss;

    protected BossMusic(BossEntity boss) {
        super(boss.getBossMusic(), SoundSource.RECORDS, RandomSource.create());
        this.boss = boss;
        this.volume = 1.0f;
        this.pitch = 1.0f;
        this.looping = true;
    }

    @Override
    public void tick() {
        if (Minecraft.getInstance().level == null) return;
        boolean b = false;
        if (Config.Client.boss_music.get()) {
            for (Entity entity : Minecraft.getInstance().level.entitiesForRendering()) {
                if (Minecraft.getInstance().player != null && (!entity.equals(this.boss) || !(this.boss.distanceTo(Minecraft.getInstance().player) < (float) (Minecraft.getInstance().options.renderDistance().get() * 16))))
                    continue;
                b = true;
            }
            if (Minecraft.getInstance().options.getSoundSourceVolume(SoundSource.RECORDS) <= 0.0f) {
                this.volume = 0.0f;
            }
        }
        if (!b || !Config.Client.boss_music.get()) {
            Minecraft.getInstance().getSoundManager().stop(this);
        }
    }

    public boolean canPlayMusic() {
        boolean b = true;
        for (SoundInstance soundInstance : Minecraft.getInstance().getSoundManager().soundEngine.tickingSounds) {
            if (!soundInstance.getLocation().equals(this.getLocation()) || !(soundInstance.getVolume() > 0.0f)) continue;
            b = false;
        }
        return !Minecraft.getInstance().getSoundManager().isActive(this) && Minecraft.getInstance().level.isClientSide() && b;
    }

    public boolean isStopped() {
        return super.isStopped();
    }

    public static void playMusic(BossMusic music, BossEntity bossEntity) {
        if (Minecraft.getInstance().options.getSoundSourceVolume(SoundSource.RECORDS) <= 0.0f || Config.Client.boss_music.get()) {
            music = null;
        }
        if (music != null) {
            music = new BossMusic(bossEntity);
        }
        if (music != null && music.canPlayMusic()) {
            Minecraft.getInstance().getSoundManager().play(music);
        }
    }

    public float getVolume() {
        return this.volume;
    }
}
