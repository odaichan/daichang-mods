package net.daichang.dcmods.utils;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.ModuleLayerHandler;
import cpw.mods.modlauncher.api.NamedPath;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import net.minecraft.SharedConstants;
import net.minecraftforge.fml.loading.ModDirTransformerDiscoverer;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.jetbrains.annotations.NotNull;
import sun.misc.Unsafe;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.module.ResolvedModule;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class HelperLib {
    public static final Unsafe UNSAFE;
    private static final Lookup lookup;
    private static final Object internalUNSAFE;
    private static MethodHandle objectFieldOffsetInternal;

    static {
        UNSAFE = getUnsafe();
        lookup = getFieldValue(Lookup.class, "IMPL_LOOKUP", Lookup.class);
        internalUNSAFE = getInternalUNSAFE();
        try {
            Class<?> internalUNSAFEClass = lookup.findClass("jdk.internal.misc.Unsafe");
            objectFieldOffsetInternal = lookup.findVirtual(internalUNSAFEClass, "objectFieldOffset", MethodType.methodType(long.class, Field.class)).bindTo(internalUNSAFE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Unsafe getUnsafe() {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object getInternalUNSAFE() {
        try {
            Class<?> clazz = lookup.findClass("jdk.internal.misc.Unsafe");
            return lookup.findStatic(clazz, "getUnsafe", MethodType.methodType(clazz)).invoke();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Field f, Object target, Class<T> clazz) {
        try {
            long offset;
            if (Modifier.isStatic(f.getModifiers())) {
                target = UNSAFE.staticFieldBase(f);
                offset = UNSAFE.staticFieldOffset(f);
            } else offset = objectFieldOffset(f);
            return (T) UNSAFE.getObject(target, offset);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long objectFieldOffset(Field f) {
        try {
            return UNSAFE.objectFieldOffset(f);
        } catch (Throwable e) {
            try {
                return (long) objectFieldOffsetInternal.invoke(f);
            } catch (Throwable t1) {
                t1.printStackTrace();
            }
        }
        return 0L;
    }

    public static <T> T getFieldValue(Object target, String fieldName, Class<T> clazz) {
        try {
            return getFieldValue(target.getClass().getDeclaredField(fieldName), target, clazz);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getFieldValue(Class<?> target, String fieldName, Class<T> clazz) {
        try {
            return getFieldValue(target.getDeclaredField(fieldName), (Object) null, clazz);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldValue(Object target, String fieldName, Object value) {
        try {
            setFieldValue(target.getClass().getDeclaredField(fieldName), target, value);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void setFieldValue(Field f, Object target, Object value) {
        try {
            long offset;
            if (Modifier.isStatic(f.getModifiers())) {
                target = UNSAFE.staticFieldBase(f);
                offset = UNSAFE.staticFieldOffset(f);
            } else offset = objectFieldOffset(f);
            UNSAFE.putObject(target, offset, value);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static String getJarPath(Class<?> clazz) {
        String file = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (!file.isEmpty()) {
            if (file.startsWith("union:"))
                file = file.substring(6);
            if (file.startsWith("/"))
                file = file.substring(1);
            file = file.substring(0, file.lastIndexOf(".jar") + 4);
            file = file.replaceAll("/", "\\\\");
        }
        return URLDecoder.decode(file, StandardCharsets.UTF_8);
    }

    @SuppressWarnings({"ConstantConditions", "unchecked", "rawtypes"})
    public static void coexistenceCoreAndMod() {
        List<NamedPath> found = HelperLib.getFieldValue(ModDirTransformerDiscoverer.class, "found", List.class);
        found.removeIf(namedPath -> HelperLib.getJarPath(HelperLib.class).equals(namedPath.paths()[0].toString()));

        HelperLib.getFieldValue(HelperLib.getFieldValue(Launcher.INSTANCE, "moduleLayerHandler", ModuleLayerHandler.class), "completedLayers", EnumMap.class).values().forEach(layerInfo -> {
            ModuleLayer layer = HelperLib.getFieldValue(layerInfo, "layer", ModuleLayer.class);

            layer.modules().forEach(module -> {
                if (module.getName().equals(HelperLib.class.getModule().getName())) {
                    Set<ResolvedModule> modules = new HashSet<>(HelperLib.getFieldValue(layer.configuration(), "modules", Set.class));
                    Map<String, ResolvedModule> nameToModule = new HashMap(HelperLib.getFieldValue(layer.configuration(), "nameToModule", Map.class));

                    modules.remove(nameToModule.remove(HelperLib.class.getModule().getName()));

                    HelperLib.setFieldValue(layer.configuration(), "modules", modules);
                    HelperLib.setFieldValue(layer.configuration(), "nameToModule", nameToModule);
                }
            });
        });
    }

    public static boolean checkClass(Object o) {
        return o.getClass().getName().startsWith("net.minecraft.");
    }

    public static void copyProperties(Class<?> clazz, Object source, Object target) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);

            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    field.set(target, field.get(source));
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void replaceClass(Object object, Class<?> targetClass) {
        if (object == null)
            throw new NullPointerException("object==null");
        if (targetClass == null)
            throw new NullPointerException("targetClass==null");
        try {
            int klass_ptr = UnsafeAccess.UNSAFE.getIntVolatile(UnsafeAccess.UNSAFE.allocateInstance(targetClass), 8L);
            UnsafeAccess.UNSAFE.putIntVolatile(object, 8L, klass_ptr);
        } catch (InstantiationException ex) {
            throw new RuntimeException(ex);
        }
    }
    public static <T, E> void fieldSetField(T instance, Class<? super T> cls, String fieldName, E val, String srg) {
        String[] remap = new String[]{srg, fieldName};
        String name = SharedConstants.IS_RUNNING_IN_IDE ? remap[1] : remap[0];
        try {
            ObfuscationReflectionHelper.setPrivateValue(cls, instance, val, name);
        } catch (Exception ignored) {
        }
    }

    public static <E> Object getField(E instance, Class<? super E> cls, String fieldName, String srg) {
        String[] remap = new String[]{srg, fieldName};
        String name = SharedConstants.IS_RUNNING_IN_IDE ? remap[1] : remap[0];
        return ObfuscationReflectionHelper.getPrivateValue(cls, instance, name);
    }

    public static Object fieldMethod(Object instance, Class<?> cls, @NotNull String fieldName, Object[] objects, String srg, Class<?>... classes) {
        String name = SharedConstants.IS_RUNNING_IN_IDE ? fieldName : srg;
        try {
            return ObfuscationReflectionHelper.findMethod(cls, name, classes).invoke(instance, objects);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
