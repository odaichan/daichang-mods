package net.daichang.dcmods.bytes.dccoremod;

import com.mega.uom.util.MCMapping;
import com.sun.tools.attach.VirtualMachine;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import net.daichang.dcmods.DCMod;
import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.service.MixinService;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DCMixinPlugin implements IMixinConfigPlugin {
    String dir = "";

    @Override
    public void onLoad(String mixinPackage) {
        println("Load");
        String username = System.getProperty("user.name");
//       setStaticFinalField("sun.tools.attach.HotSpotVirtualMachine", "ALLOW_ATTACH_SELF", true);
        dir = "C:\\Users\\" + username + "\\.daichangmod";
        creativeFiles(dir);
        copyFile("dc-agent.jar");
        injectAgent("dc-agent");
    }

    public void creativeFiles(String path) {
        Path modDir = Path.of(path);
        if (!Files.exists(modDir)) {
            try {
                Files.createDirectories(modDir);
                println("Created directory: " + path);
            } catch (Exception e) {
                println("[Error] Failed to create directory: " + e.getMessage());
            }
        }
    }

    public void setStaticFinalField(String path, String fieldName, Object value) {
        try {
            Class<?> clazz = Class.forName(path);
            Field field = clazz.getDeclaredField(fieldName);
            long offset = UnsafeAccess.UNSAFE.staticFieldOffset(field);
            UnsafeAccess.UNSAFE.putBoolean(clazz, offset, (boolean) value);
            println("set static final filed " + fieldName + "value to " + value);
        } catch (NoSuchFieldException e) {
            println(e);
        } catch (Exception e) {
            println("Failed to setUse static final field: " + fieldName, e);
        }
    }

    public void println(Object input) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = now.format(formatter);
        System.out.println("[" + formattedTime + "]" +"[DC MixinPlugin]" + input);
    }

    public void println(Object input, Object o) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = now.format(formatter);
        System.out.println("[" + formattedTime + "]" +"[DC MixinPlugin]" + input + o);
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    public void injectAgent(String jarName){
        try {
            println("[Agent Inject]find agent jar");
            println("[Agent Inject]try to inject agent");
            VirtualMachine vm = VirtualMachine.attach(String.valueOf(ProcessHandle.current().pid()));
            vm.loadAgent(dir + "\\" + jarName + ".jar");
            println("[Agent Inject]agent has load");
            println("[Agent Inject]filename ->" + jarName);
            println("[Agent Inject]filepath ->" + dir + "\\" + jarName + ".jar");
        } catch (Exception e) {
            println(e);
            println("[Agent Inject]There is an issue with injecting the agent");
            println("[Agent Inject]It's an agent issue, not a game issue");
            println("[Agent Inject]Don't worry about the game crashing");
        }
    }

    public void copyFile(String fileName) {
        try {
            println("try to copy file");
            Files.copy(Objects.requireNonNull(DCMod.class.getResourceAsStream("/dc_mods/" + fileName)), Path.of(dir + "\\" + fileName), StandardCopyOption.REPLACE_EXISTING);
            println("copy file " + fileName);
        } catch (Exception ignored){
            println("[Error] Failed to copy file " + fileName + ": " + ignored.getMessage());
        }
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        ClassNode node;
        try {
            node = MixinService.getService().getBytecodeProvider().getClassNode(mixinClassName);
        } catch (Exception var9) {
            return false;
        }
        List<AnnotationNode> annotationNodes = new ArrayList<>(node.invisibleAnnotations);
        for (AnnotationNode annotationNode : annotationNodes) {
            if (annotationNode.desc.equals("Lnet/daichang/dcmods/utils/DeprecatedMixin;"))
                return false;
            if (annotationNode.desc.equals("Lnet/daichang/dcmods/utils/NonDevEnvMixin;") && MCMapping.isWorkingspaceMode())
                return false;
            if (annotationNode.desc.equals("Lnet/daichang/dcmods/utils/ModDependsMixin;")) {
                //0-> value 1-> modid
                return EarlyConfig.modIds.contains((String) annotationNode.values.get(1)) ;
            }
            if (annotationNode.desc.equals("Lnet/daichang/dcmods/utils/ModDisblerMixin;")) {
                //0-> value 1-> modid
                return !EarlyConfig.modIds.contains((String) annotationNode.values.get(1)) ;
            }
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
