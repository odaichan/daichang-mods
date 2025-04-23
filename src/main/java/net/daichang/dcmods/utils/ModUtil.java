package net.daichang.dcmods.utils;

import net.daichang.dcmods.utils.helpers.ModHelper;

public class ModUtil extends ModHelper {
    public static boolean isProjecteLoad() {
        return isModLoading("projecte");
    }

    public static boolean isTconstructLoad() {
        return isModLoading("tconstruct");
    }

    public static boolean isAvaritiaLoad() {
        return isModLoading("avaritia");
    }

    public static boolean isEtstLoad() {
        return isModLoading("etstlib");
    }

    public static boolean isDCLoad() {
        return isTconstructLoad() && isEtstLoad();
    }
}
