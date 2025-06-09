package net.daichang.dcmods.library;

import com.mojang.blaze3d.platform.Window;
import com.sun.jna.platform.win32.GDI32;
import com.sun.jna.platform.win32.User32;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.debug.DebugRenderer;
import net.minecraft.world.entity.Entity;

import java.util.UUID;

public interface DCBaseLib {
    Minecraft mc = Minecraft.getInstance();

    LocalPlayer localPlayer = mc.player;

    Entity cameraEntity = mc.getCameraEntity();

    UUID mcPlayerUUID = localPlayer.getUUID();

    String stringUUID = localPlayer.getStringUUID();

    String AAApath = mc.gameDirectory.getPath();

    Window window = mc.getWindow();

    int windowWidth = window.getWidth();

    int windowHeight = window.getHeight();

    DebugRenderer debugRender = mc.debugRenderer;

    User32 user32 = User32.INSTANCE;

    GDI32 gdi32 = GDI32.INSTANCE;

    String DEFAULT_FILE_PATH = AAApath + "/dc_list.txt";
}
