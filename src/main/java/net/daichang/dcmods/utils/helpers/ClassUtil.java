package net.daichang.dcmods.utils.helpers;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class ClassUtil {
    public static Method[] getAllDeclaredMethods(Class<?> clazz) {
        ArrayList<Method> methods = new ArrayList<>();
        for (Class<?> currentClass = clazz; currentClass != null && currentClass != Object.class; currentClass = currentClass.getSuperclass()) {
            methods.addAll(Arrays.asList(currentClass.getDeclaredMethods()));
        }
        return methods.toArray(new Method[0]);
    }

    public static Field[] getAllDeclaredFields(Class<?> clazz) {
        ArrayList<Field> fields = new ArrayList<>();
        for (Class<?> currentClass = clazz; currentClass != null && currentClass != Object.class; currentClass = currentClass.getSuperclass()) {
            fields.addAll(Arrays.asList(currentClass.getDeclaredFields()));
        }
        return fields.toArray(new Field[0]);
    }

    public static boolean calledFromOtherMod() {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        try {
            for (int i = 2; i < 4; ++i) {
                StackTraceElement element = elements[i];
                if (element.getClassName().startsWith("net.daichang") || element.getClassName().startsWith("net.minecraft")) continue;
                return true;
            }
        } catch (Exception ignored) {}
        return false;
    }
}
