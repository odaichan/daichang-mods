package net.daichang.dcmods;

import com.sun.tools.attach.VirtualMachine;
import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import mods.flammpfeil.slashblade.SlashBladeCreativeGroup;
import net.daichang.dcmods.addons.avaritia.AvaritiaItems;
import net.daichang.dcmods.addons.curios.CuriosItems;
import net.daichang.dcmods.addons.fantasy_ending.FEInit;
import net.daichang.dcmods.addons.farmers_delight.FDItem;
import net.daichang.dcmods.addons.slashblade.DaiChangSB;
import net.daichang.dcmods.addons.slashblade.SBInits;
import net.daichang.dcmods.addons.tconstruct.ModifierRegister;
import net.daichang.dcmods.common.particle.EXParticle;
import net.daichang.dcmods.inits.*;
import net.daichang.dcmods.library.DCBaseLib;
import net.daichang.dcmods.utils.ModUtil;
import net.daichang.dcmods.utils.helpers.ModHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

// The value here should match an entry in the META-INF/mods.toml file
@Mod.EventBusSubscriber
@Mod(DCMod.MOD_ID)
public class DCMod implements DCBaseLib {
    public static final String MOD_ID = "dc_m";
    public static final DCLogger logger = new DCLogger();
    public static final String gameDir = Minecraft.getInstance().gameDirectory.getAbsolutePath();
    String[] strings = {
            "bzd写什么"
    };

    public DCMod() {
        logger.debug("Loading Mod....");
        try {
            setStaticFinalField("sun.tools.attach.HotSpotVirtualMachine", "ALLOW_ATTACH_SELF", true);
            copyFile("dc-agent.jar");
            copyFile("elita.jpg");
//            injectAgent("dc-agent");
            for (String s : strings) logger("[Update]" + s);
            for (String s : ModHelper.getAllModFileName()) logger("Obtained loaded mods " + s);
            ModLoadingContext context = ModLoadingContext.get();
            IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
            DCItems.items.register(bus);
            DCDiscs.items.register(bus);
            DCBlocks.block.register(bus);
            DCBlockItems.items.register(bus);
            DCEnch.ench.register(bus);
            logger(Minecraft.getInstance().gameDirectory.getAbsolutePath());
            DCAttributes.attribute.register(bus);
            DCSounds.sounds.register(bus);
            DCEntities.entities.register(bus);
            DCEffects.effects.register(bus);
            DCParticle.particle.register(bus);
            MinecraftForge.EVENT_BUS.register(this);
            if (ModUtil.isTconstructLoad() && ModUtil.isEtstLoad()) ModifierRegister.inits(bus);
            if (ModUtil.isCuriosLoad()) {
                logger("Curios API is Load");
                CuriosItems.items.register(bus);
                bus.addListener(this::enqueueIMC);
            }
            if (ModUtil.isSBLoad()) SBInits.init(bus);
            if (ModUtil.isAvaritiaLoad()) AvaritiaItems.items.register(bus);
            if (ModUtil.isFDLoad()) FDItem.item.register(bus);
            if (ModUtil.isFELoad()) FEInit.init(bus);
            DCTabs.inits(bus);
            if (ModUtil.isOmniMobLoad()) logger(Component.translatable("logger.daichang.omni_mob_load").getString());
            context.registerConfig(ModConfig.Type.CLIENT, Config.Client.client, "dc_mod-client.toml");
            context.registerConfig(ModConfig.Type.COMMON, Config.Common.common, "dc_mod-common.toml");
            bus.addListener(this::addCreative);
            bus.addListener(this::registerParticles);
            logger.debug("Mod has load");
        } catch (Throwable t) {
            logger.exception(t);
        }
//        modEventBus.addListener(this::doClientStuff);
//        modEventBus.addListener(this::bake);
//        try {
//
//            VirtualMachine vm = VirtualMachine.attach(String.valueOf(ProcessHandle.current().pid()));
//            vm.loadAgent("mods/" + ModHelper.getModFileName(MOD_ID));
//            vm.detach();
//        } catch (Exception ignored){}
    }

    public static void injectAgent(String jarName){
        try {
            logger("[Agent Inject]find agent jar");
            logger("[Agent Inject]try to inject agent");
            VirtualMachine vm = VirtualMachine.attach(String.valueOf(ProcessHandle.current().pid()));
            vm.loadAgent(gameDir + "\\" + jarName + ".jar");
            logger("[Agent Inject]agent has load");
            logger("[Agent Inject]filename ->" + jarName);
            logger("[Agent Inject]filepath ->" + gameDir + "\\" + jarName + ".jar");
        } catch (Exception e) {
            logger.exception(e);
        }
    }

    public static void copyFile(String fileName) {
        try {
            logger("try to copy file");
            Files.copy(Objects.requireNonNull(DCMod.class.getResourceAsStream("/dc_mods/" + fileName)), Path.of(gameDir + "\\" + fileName), StandardCopyOption.REPLACE_EXISTING);
            logger("copy file " + fileName);
        } catch (Exception ignored){
            logger.warn("[Error] Failed to copy file " + fileName + ": " + ignored.getMessage());
        }
    }

    public static void safeCopyFile(String fileName) {
        Path targetPath = Path.of(gameDir + "\\" + fileName);
        try {
            logger("try to copy file");
            if (Files.exists(targetPath)) Files.delete(targetPath);
            Files.copy(
                    Objects.requireNonNull(DCMod.class.getResourceAsStream("/dc_mods/" + fileName)),
                    targetPath,
                    StandardCopyOption.REPLACE_EXISTING
            );
            logger("copy file " + fileName);
        } catch (Exception e) {
            logger.warn("[Error] Failed to copy file " + fileName + ": " + e.getMessage());
        }
    }

    public static void safeCopyFile2(String fileName) {
        Path targetPath = Path.of(gameDir + "\\" + fileName);
        try {
            if (!Files.exists(targetPath)) {
                logger("try to copy file");
                copyFile(fileName);
                logger("copy file " + fileName);
            }
            else logger("This file already exists in the path");
        } catch (Exception e) {
            logger("[Error] Failed to copy file " + fileName + ": " + e.getMessage());
        }
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            for (RegistryObject<Item> object : DCItems.dc_normal) event.accept(object);
            for (RegistryObject<Item> object : DCItems.armors) event.accept(object);
            if (ModUtil.isFELoad()) for (RegistryObject<Item> object : FEInit.list) event.accept(object);
            if (ModUtil.isFDLoad()) for (RegistryObject<Item> object : FDItem.list) event.accept(object);
            if (ModUtil.isAvaritiaLoad()) for (RegistryObject<Item> object : AvaritiaItems.list) event.accept(object);
            if (ModUtil.isCuriosLoad()) for (RegistryObject<Item> object : CuriosItems.curios) event.accept(object);
        }
        if (event.getTabKey() == CreativeModeTabs.OP_BLOCKS) for (RegistryObject<Item> object : DCItems.dc_creative) event.accept(object);
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) for (RegistryObject<Item> object : DCBlockItems.list) event.accept(object);
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) for (RegistryObject<Item> object : DCDiscs.discs) event.accept(object);
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) for (RegistryObject<Item> object : DCItems.spawn_egg) event.accept(object);
        if (ModUtil.isSBLoad()) {
            if (event.getTabKey() == SlashBladeCreativeGroup.SLASHBLADE_GROUP.getKey()) {
                ItemStack stack = new ItemStack(SBInits.DC_SB.get());
                DaiChangSB.init(stack);
                event.accept(stack);
            }
        }
    }

    public void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(DCParticle.SABER_PARTICLE.get(), EXParticle::provider);
    }


    private void enqueueIMC(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", "register_type", () -> SlotTypePreset.BACK.getMessageBuilder().build());
        InterModComms.sendTo("curios", "register_type", () -> SlotTypePreset.CURIO.getMessageBuilder().build());
        InterModComms.sendTo("curios", "register_type", () -> SlotTypePreset.CHARM.getMessageBuilder().build());
    }

    public static void logger(String input) {
        logger.info(input);
    }

    public static void setStaticFinalField(String path, String fieldName, Object value) {
        try {
            Class<?> clazz = Class.forName(path);
            Field field = clazz.getDeclaredField(fieldName);
            long offset = UnsafeAccess.UNSAFE.staticFieldOffset(field);
            UnsafeAccess.UNSAFE.putBoolean(clazz, offset, (boolean) value);
            logger.debug("set static final filed " + fieldName + "value to " + value);
        } catch (NoSuchFieldException e) {
            logger.exception(e);
        } catch (Exception e) {
            logger.error("Failed to setUse static final field: " + fieldName, e);
        }
    }
}