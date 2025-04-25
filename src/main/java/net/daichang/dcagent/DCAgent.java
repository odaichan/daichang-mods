package net.daichang.dcagent;


import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class DCAgent {
    private static final String DC_METHOD;
    public static String modPath = "";

    public static void premain(String agentArgs, Instrumentation inst) {
        modPath = agentArgs.split(" ")[0] == null ? "" : agentArgs.split(" ")[0];
        inst.addTransformer(new DCClassFileTransformer());
        logger("Agent Loader-Premain");
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        modPath = agentArgs.split(" ")[0] == null ? "" : agentArgs.split(" ")[0];
        inst.addTransformer(new DCClassFileTransformer());
        logger("Agent Loader-Agentmain");
    }

    public static byte[] getClassBytes(String jarPath, String className) throws Exception {
        try (JarFile jarFile = new JarFile(jarPath)) {
            String classPath = className.replace('.', '/') + ".class";
            JarEntry entry = jarFile.getJarEntry(classPath);
            if (entry == null) {
                throw new ClassNotFoundException("Class not found in JAR: " + className);
            }
            try (InputStream is = jarFile.getInputStream(entry)) {
                return is.readAllBytes();
            }
        }
    }

    public static class DCClassFileTransformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            if (className.startsWith("net/daichang")) return transformClass(className, classfileBuffer);
            return ClassFileTransformer.super.transform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
        }
    }


    private static byte[] transformClass(String className, byte[] classfileBuffer) {
        try {
            className = className.replace("/", ".");
            byte[] newBuf = getClassBytes(modPath, className);
            logger(" Fix " + className + " with " + newBuf.length + " bytes");
            return newBuf;
        } catch (Exception ignored) {
            return classfileBuffer;
        }
    }

    static void logger(String input){
        System.out.println("[DC Agent]: " + input);
    }

    static {
        DC_METHOD = "net/daichang/dcmods/utils/asm/MethodUtil";
    }
}
