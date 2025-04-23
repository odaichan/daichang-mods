package net.daichang.dcmods.utils.helpers;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModFileInfo;

import java.util.ArrayList;
import java.util.List;

public class ModHelper {
    public static boolean isModLoading(String mod_id) {
        return ModList.get().isLoaded(mod_id);
    }

    public static String getModFileName(String mod_id) {
        return ModList.get().getModFileById(mod_id).getFile().getFileName();
    }

    public static List<String> getAllModFileName() {
        List<String> s = new ArrayList<>();
        for (IModFileInfo info : ModList.get().getModFiles()) s.add(info.getFile().getFileName());
        return s;
    }
}
