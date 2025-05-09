package net.daichang.dccoremod;

import cpw.mods.cl.ModuleClassLoader;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;
import sun.misc.Unsafe;

import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.VarHandle;
import java.lang.module.ModuleReader;
import java.lang.module.ModuleReference;
import java.lang.module.ResolvedModule;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class DCLaunchPluginService implements ILaunchPluginService {

    public static void logger(String msg){
        System.out.println("[DC ASM]：" + msg);
    }
    private static final String DC_METHOD_OWER;
    private static final VarHandle packageLookup;
    private static final VarHandle parentLoaders;
    private static final MethodHandle getClassBytes;
    private static final MethodHandle classNameToModuleName;
    private static final MethodHandle loadFromModule;
    private static final ModuleClassLoader targetClassLoader;
    private static final Map<String, byte[]> byteCache = new HashMap<>();
    @Override
    public String name() {
        return "DC Mod LaunchPluginService";
    }

    public boolean processClass(Phase phase, ClassNode classNode, Type classType) {
        return this.transform(classNode);
    }

    private  boolean transform(ClassNode classNode) {
        boolean writer = false;
        for (MethodNode methodNode : classNode.methods) {
            for (AbstractInsnNode abstractInsnNode : methodNode.instructions) {
                if (!classNode.name.contains("net/daichang/")
                        && !classNode.name.contains("net/mehvahdjukaar/dummmmmmy/")
                        && !classNode.name.contains("net/arna/jcraft/")
                        && !classNode.name.contains("io/redspace/ironsspellbooks/")
                        && !classNode.name.contains("com/mega/uom/item/")
                        && !classNode.name.contains("com/obscuria/aquamirae/common/effects/")
                        && !classNode.name.contains("com/jerotes/jerotesvillage/world/inventory/MobInventoryGUIMenu")
                        && !classNode.name.contains("vazkii/neat/")
                        && !classNode.name.contains("com/mega/uom/client/music/")
                        && !classNode.name.contains("net/minecraft/")
                        && !classNode.name.contains("io/redspace/ironsspellbooks/api/util/Utils")
                        && !classNode.name.contains("com/mega/uom/mixin/")
                        && !classNode.name.contains("net/minecraftforge/")) {
                    if (abstractInsnNode instanceof MethodInsnNode call && call.getOpcode() != Opcodes.INVOKESPECIAL) {
                        switch (call.name) {
                            case "m_21223_" -> {
                                rMethod(call, "getHealth", "(Lnet/minecraft/world/entity/LivingEntity;)F");
                                logger("Changed GetHealth Method :"  + classNode.name) ;
                                writer = true;
                            }
                            case "m_6084_" -> {
                                rMethod(call, "isAlive", "(Lnet/minecraft/world/entity/Entity;)Z");
                                logger("Changed IsAlive Method :"  + classNode.name);
                                writer = true;
                            }
                            case "m_213877_" -> {
                                rMethod(call, "isRemoved", "(Lnet/minecraft/world/entity/Entity;)Z");
                                logger("Changed IsRemoved Method :"  + classNode.name);
                                writer = true;
                            }
                        }
                    }
                    if (abstractInsnNode instanceof FieldInsnNode field && field.getOpcode() == Opcodes.GETFIELD) {
                        switch (field.name) {
                            case "f_146795_" -> {
                                rField(methodNode, field, "getRemovalReason", "(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/entity/Entity$RemovalReason;");
                                writer = true;
                            }
                        }
                    }
                }
            }
        }
        return writer;
    }

    private static void rField(MethodNode method, FieldInsnNode field, String name, String desc) {
        method.instructions.set(field, new MethodInsnNode(Opcodes.INVOKESTATIC, DC_METHOD_OWER, name, desc, false));
    }

    private static void rMethod(MethodInsnNode call, String name, String desc) {
        call.setOpcode(Opcodes.INVOKESTATIC);
        call.owner = DC_METHOD_OWER;
        call.name = name;
        call.desc = desc;
    }

    private static void removeMethod(MethodNode methodNode, MethodInsnNode insnNode) {
        methodNode.instructions.remove(insnNode);
    }

    private static boolean isAssignableFrom(String current, String father) {
        try {
            while (true) {
                if (current.equals(father)) {
                    return true;
                } else if (current.equals("java/lang/Object")) {
                    return false;
                } else {
                    current = new ClassReader(getClassBytes(current)).getSuperName();
                }
            }
        } catch (RuntimeException e) {
            if (e.getCause() instanceof ClassNotFoundException) {
                return false;
            } else {
                throw e;
            }
        }
    }

    private static byte[] getClassBytes(String aname) {
        byte[] bytes = byteCache.get(aname);

        if (bytes != null) {
            return bytes;
        }

        Throwable suppressed = null;
        String name = aname.replace('/', '.');

        try {
            String pname = name.substring(0, name.lastIndexOf('.'));
            if (((Map<String, ResolvedModule>) packageLookup.get(targetClassLoader)).containsKey(pname)) {
                bytes = (byte[]) loadFromModule.invoke(targetClassLoader, classNameToModuleName.invoke(targetClassLoader, name), (BiFunction<ModuleReader, ModuleReference, Object>) (reader, ref) -> {
                    try {
                        return getClassBytes.invoke(targetClassLoader, reader, ref, name);
                    } catch (Throwable e) {
                        throw new RuntimeException(e);
                    }
                });
            } else {
                Map<String, ClassLoader> parentLoadersMap = (Map<String, ClassLoader>) parentLoaders.get(targetClassLoader);
                if (parentLoadersMap.containsKey(pname)) {
                    try (InputStream is = parentLoadersMap.get(pname).getResourceAsStream(aname + ".class")) {
                        if (is != null) {
                            bytes = is.readAllBytes();
                        }
                    }
                }
            }
        } catch (Throwable e) {
            suppressed = e;
        }

        if (bytes == null || bytes.length == 0) {
            ClassNotFoundException e = new ClassNotFoundException(name);
            if (suppressed != null) e.addSuppressed(suppressed);
            throw new RuntimeException(e);
        }

        byteCache.put(name, bytes);

        return bytes;
    }

    public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        return EnumSet.of(Phase.BEFORE);
    }

    static {
        Field lookupF;
        Unsafe unsafe;
        DC_METHOD_OWER = "net/daichang/dcmods/utils/asm/MethodUtil";
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            lookupF = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");

            unsafeField.setAccessible(true);
            unsafe = (Unsafe) unsafeField.get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        MethodHandles.Lookup lookup = (MethodHandles.Lookup) unsafe.getObject(unsafe.staticFieldBase(lookupF), unsafe.staticFieldOffset(lookupF));
        try {
            packageLookup = lookup.findVarHandle(ModuleClassLoader.class, "packageLookup", Map.class);
            parentLoaders = lookup.findVarHandle(ModuleClassLoader.class, "parentLoaders", Map.class);
            getClassBytes = lookup.findVirtual(ModuleClassLoader.class, "getClassBytes", MethodType.methodType(byte[].class, ModuleReader.class, ModuleReference.class, String.class));
            classNameToModuleName = lookup.findVirtual(ModuleClassLoader.class, "classNameToModuleName", MethodType.methodType(String.class, String.class));
            loadFromModule = lookup.findVirtual(ModuleClassLoader.class, "loadFromModule", MethodType.methodType(Object.class, String.class, BiFunction.class));
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        targetClassLoader = Thread.currentThread().getContextClassLoader() instanceof ModuleClassLoader moduleClassLoader ? moduleClassLoader : (ModuleClassLoader) Thread.getAllStackTraces().keySet().stream().map(Thread::getContextClassLoader).filter(cl -> cl instanceof ModuleClassLoader).findAny().orElseThrow();
    }
}
