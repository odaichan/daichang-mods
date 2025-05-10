package net.daichang.dcmods.utils;

import net.daichang.dcmods.utils.helpers.ModHelper;

public class ModUtil extends ModHelper {

    //等价交换
    public static boolean isProjecteLoad() {
        return isModLoading("projecte");
    }

    //匠魂
    public static boolean isTconstructLoad() {
        return isModLoading("tconstruct");
    }

    //无尽贪婪
    public static boolean isAvaritiaLoad() {
        return isModLoading("avaritia");
    }

    //Etst前置库(若装了匠魂则必装此Mod)
    public static boolean isEtstLoad() {
        return isModLoading("etstlib");
    }

    //bzd
    public static boolean isDCLoad() {
        return isTconstructLoad() && isEtstLoad();
    }

    //饰品
    public static boolean isCuriosLoad() {
        return isModLoading("curios");
    }

    //铁魔法
    public static boolean isIronSpellbokksLoad() {
        return isModLoading("irons_spellbooks");
    }

    public static boolean isFELoad() {
        return isModLoading("fantasy_ending");
    }

    //帕秋莉手册(用于做教程)
    public static boolean isPatchouliLoad() {
        return isModLoading("patchouli");
    }
}
