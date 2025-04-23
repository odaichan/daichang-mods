package net.daichang.dcagent;


import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class DCAgent {
    private static final String DC_METHOD;

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new DCClassFileTransformer());
        logger("Agent Loader-Premain");
    }

    public static void agentmain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new DCClassFileTransformer());
        logger("Agent Loader-Agentmain");
    }

    public static class DCClassFileTransformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            return ClassFileTransformer.super.transform(loader, className, classBeingRedefined, protectionDomain, classfileBuffer);
        }
    }

    static void logger(String input){
        System.out.println("[DC Agent]: " + input);
    }

    static {
        DC_METHOD = "net/daichang/dcmods/utils/asm/MethodUtil";
    }
}
