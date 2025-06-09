package net.daichang.dcmods.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.extensions.IForgeItem;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public interface KeyDown extends IForgeItem {
    long window = Minecraft.getInstance().getWindow().getWindow();

    default boolean isLeftShiftDown() {
        return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT);
    }

    default boolean isRightShiftDown() {
        return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }

    default boolean isLeftCtrlDown() {
        return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_CONTROL);
    }

    default boolean isRightCtrlDown() {
        return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }
}
