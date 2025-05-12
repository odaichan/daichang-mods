package net.daichang.dcmods;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import net.daichang.dcmods.addons.avaritia.AvaritiaItems;
import net.daichang.dcmods.addons.curios.CuriosItems;
import net.daichang.dcmods.addons.tconstruct.ModifierRegister;
import net.daichang.dcmods.inits.*;
import net.daichang.dcmods.utils.ModUtil;
import net.daichang.dcmods.utils.helpers.ModHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.lang.reflect.Field;
import java.security.ProtectionDomain;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DCMod.MOD_ID)
public class DCMod {
    public static final String MOD_ID = "dc_m";

    String[] strings = {
            "bzd写什么"
    };

    public static ResourceLocation getDCLocation(String path) {
        return new ResourceLocation(MOD_ID, "textures/" + path);
    }

    public static ResourceLocation getDCGUILocation(String path) {
        return getDCLocation("renderer/" + path);
    }

    public static ResourceLocation getDCItemLocation(String path) {
        return getDCLocation("item/" + path);
    }

    public static byte[] defineClassEx(ClassLoader loader, String clsName, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
        return classfileBuffer;
    }

    public DCMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DCEnch.ench.register(modEventBus);
        System.out.println(Minecraft.getInstance().gameDirectory.getAbsolutePath());
        DCItems.items.register(modEventBus);
        DCAttributes.attribute.register(modEventBus);
        DCSounds.sounds.register(modEventBus);
        DCEntities.entities.register(modEventBus);
        DCEffects.effects.register(modEventBus);
        DCBlocks.block.register(modEventBus);
        DCDiscs.items.register(modEventBus);
        DCBlockItems.items.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
//        DaiChangMaginc.INSTANCE.initWindow();
//        DaiChangMaginc.INSTANCE.addClassFileLoadHook("Lnet/daichang/dcmods/DCMod.defineClassEx;");
        if (ModUtil.isTconstructLoad() && ModUtil.isEtstLoad()) ModifierRegister.MODIFIERS.register(modEventBus);
        if (ModUtil.isCuriosLoad()) CuriosItems.items.register(modEventBus);
        if (ModUtil.isAvaritiaLoad()) AvaritiaItems.items.register(modEventBus);
        DCTabs.inits(modEventBus);
        for (String s : strings) logger("[Update]" + s);
        for (String s : ModHelper.getAllModFileName()) logger("Obtained loaded mods " + s);
//        try {
//            setStaticFinalField(Class.forName("sun.tools.attach.HotSpotVirtualMachine"), "ALLOW_ATTACH_SELF", true);
//            VirtualMachine vm = VirtualMachine.attach(String.valueOf(ProcessHandle.current().pid()));
//            vm.loadAgent("mods/" + ModHelper.getModFileName(MOD_ID));
//            vm.detach();
//        } catch (Exception ignored){}
    }

    public static void logger(String input) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = now.format(formatter);
        System.out.println("[" +formattedTime + "][DC Craft]" + input);
    }

    public static void setStaticFinalField(Class<?> clazz, String fieldName, Object value) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            long offset = UnsafeAccess.UNSAFE.staticFieldOffset(field);
            UnsafeAccess.UNSAFE.putBoolean(clazz, offset, (boolean) value);
        } catch (NoSuchFieldException e) {
            System.err.println("[DC MODS]Field " + fieldName + " does not exist in class " + clazz.getName());
        } catch (Exception e) {
            throw new RuntimeException("Failed to set static final field: " + fieldName, e);
        }
    }
}