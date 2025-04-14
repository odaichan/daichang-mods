package net.daichang.dcmods.library;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.world.entity.Entity;

public class MinecraftLib {
    static Minecraft mc = Minecraft.getInstance();

    static LocalPlayer localPlayer = mc.player;

    static Entity cameraEntity = mc.getCameraEntity();

    static ClientLevel clientLevel = mc.level;

    static LevelRenderer levelRenderer = mc.levelRenderer;

    static String gameDir = mc.gameDirectory.getAbsolutePath();

    static GameRenderer gameRenderer = mc.gameRenderer;

    static Font font = mc.font;

    static Options options = mc.options;

    static TextureManager textureManager = mc.getTextureManager();

    static MultiPlayerGameMode gameMode = mc.gameMode;
}
