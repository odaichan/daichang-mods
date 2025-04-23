package net.daichang.dcmods.utils;

import net.daichang.dcmods.addons.tconstruct.ModifierRegister;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;

public class FontUtil {
    public static boolean isCanRenderFont(ItemStack item) {
        return ModifierUtil.getModifierLevel(item, ModifierRegister.DC_SUPER_WOOD_INGOT.getId()) > 0;
    }

    public static boolean isCanSwordBlock(ItemStack stack) {
        return isCanRenderFont(stack) && ModUtil.isDCLoad();
    }
}
