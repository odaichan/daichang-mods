package net.daichang.dccoremod;

import cpw.mods.modlauncher.LaunchPluginHandler;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.api.ITransformer;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import net.daichang.dcmods.utils.HelperLib;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DCTransformationService implements ITransformationService {

    @Override
    public @NotNull String name() {
        return "DC MOD TransformationService";
    }

    @Override
    public void initialize(IEnvironment environment) {

    }

    @Override
    public void onLoad(IEnvironment env, Set<String> otherServices) {
        System.out.println("[DC ASM]Starting.....");
    }

    @Override
    public @NotNull List<ITransformer> transformers() {
        return List.of();
    }

    static {
        LaunchPluginHandler handler = HelperLib.getFieldValue(Launcher.INSTANCE, "launchPlugins", LaunchPluginHandler.class);
        Map<String, ILaunchPluginService> plugins = (Map<String, ILaunchPluginService>) HelperLib.getFieldValue(handler, "plugins", Map.class);
        Map<String, ILaunchPluginService> newMap = new HashMap<>();
        newMap.put("!DC ASM", new DCLaunchPluginService());
        if (plugins != null) for (String name : plugins.keySet()) newMap.put(name, plugins.get(name));
        HelperLib.setFieldValue(handler, "plugins", newMap);
        HelperLib.coexistenceCoreAndMod();
    }
}
