package net.daichang.dcmods.library;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.UUID;

public interface DCBaseLib {
    Minecraft mc = Minecraft.getInstance();

    LocalPlayer localPlayer = mc.player;

    Entity cameraEntity = mc.getCameraEntity();

    UUID mcPlayerUUID = localPlayer.getUUID();

    String stringUUID = localPlayer.getStringUUID();

    String path = mc.gameDirectory.getPath();
}
