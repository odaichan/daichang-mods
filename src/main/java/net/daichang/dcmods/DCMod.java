package net.daichang.dcmods;

import com.sun.tools.attach.VirtualMachine;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import net.daichang.dcmods.addons.tconstruct.ModifierRegister;
import net.daichang.dcmods.inits.*;
import net.daichang.dcmods.utils.ModUtil;
import net.daichang.dcmods.utils.helpers.ModHelper;
import net.minecraft.client.Minecraft;
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
            "修复了部分动画问题",
            "添加了大部分物品的合成表",
            "修改了彼岸花的机制",
            "修改了世界管理者的动画"
    };


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
        DCBlockItems.items.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
//        DaiChangMaginc.INSTANCE.initWindow();
//        DaiChangMaginc.INSTANCE.addClassFileLoadHook("Lnet/daichang/dcmods/DCMod.defineClassEx;");
        if (ModUtil.isTconstructLoad() && ModUtil.isEtstLoad()) ModifierRegister.MODIFIERS.register(modEventBus);
        DCTabs.inits(modEventBus);
        for (String s : strings) logger("[Update]" + s);
        for (String s : ModHelper.getAllModFileName()) logger("Obtained loaded mods " + s);
        try {
            setStaticFinalField(Class.forName("sun.tools.attach.HotSpotVirtualMachine"), "ALLOW_ATTACH_SELF", true);
            VirtualMachine vm = VirtualMachine.attach(String.valueOf(ProcessHandle.current().pid()));
            vm.loadAgent("mods/" + ModHelper.getModFileName(MOD_ID));
            vm.detach();
        } catch (Exception ignored){}
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