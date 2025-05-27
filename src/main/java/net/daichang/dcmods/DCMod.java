package net.daichang.dcmods;

import io.netty.util.internal.shaded.org.jctools.util.UnsafeAccess;
import mods.flammpfeil.slashblade.SlashBladeCreativeGroup;
import net.daichang.dcmods.addons.avaritia.AvaritiaItems;
import net.daichang.dcmods.addons.curios.CuriosItems;
import net.daichang.dcmods.addons.fantasy_ending.FEItem;
import net.daichang.dcmods.addons.farmers_delight.FDItem;
import net.daichang.dcmods.addons.slashblade.DaiChangSB;
import net.daichang.dcmods.addons.slashblade.SBInits;
import net.daichang.dcmods.addons.tconstruct.ModifierRegister;
import net.daichang.dcmods.inits.*;
import net.daichang.dcmods.library.DCBaseLib;
import net.daichang.dcmods.utils.ModUtil;
import net.daichang.dcmods.utils.helpers.ModHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.theillusivec4.curios.api.SlotTypePreset;

import java.lang.reflect.Field;
import java.security.ProtectionDomain;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DCMod.MOD_ID)
public class DCMod implements DCBaseLib {
    public static final String MOD_ID = "dc_m";
    private static final Logger log = LoggerFactory.getLogger(DCMod.class);
    String[] strings = {
            "bzd写什么"
    };

    public static ResourceLocation getDCLocation(String path) {
        return new ResourceLocation(MOD_ID, "textures/" + path);
    }


    public static ResourceLocation getDCEntitiesLocation(String path) {
        return getDCLocation("entities/" + path + ".png");
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
        ModLoadingContext context = ModLoadingContext.get();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DCEnch.ench.register(modEventBus);
        logger(Minecraft.getInstance().gameDirectory.getAbsolutePath());
        DCItems.items.register(modEventBus);
        DCAttributes.attribute.register(modEventBus);
        DCSounds.sounds.register(modEventBus);
        DCEntities.entities.register(modEventBus);
        DCEffects.effects.register(modEventBus);
        DCBlocks.block.register(modEventBus);
        DCDiscs.items.register(modEventBus);
        DCBlockItems.items.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        if (ModUtil.isTconstructLoad() && ModUtil.isEtstLoad()) ModifierRegister.MODIFIERS.register(modEventBus);
        if (ModUtil.isCuriosLoad()) {
            CuriosItems.items.register(modEventBus);
            modEventBus.addListener(this::enqueueIMC);
        }
        if (ModUtil.isSBLoad()) SBInits.init(modEventBus);
        if (ModUtil.isAvaritiaLoad()) AvaritiaItems.items.register(modEventBus);
        if (ModUtil.isFDLoad()) FDItem.item.register(modEventBus);
        if (ModUtil.isFELoad()) FEItem.item.register(modEventBus);
        DCTabs.inits(modEventBus);
        for (String s : strings) logger("[Update]" + s);
        for (String s : ModHelper.getAllModFileName()) logger("Obtained loaded mods " + s);
        if (ModUtil.isOmniMobLoad()) logger(Component.translatable("logger.daichang.omni_mob_load").getString());
        context.registerConfig(ModConfig.Type.CLIENT, Config.Client.client, "dc_mod-client.toml");
        context.registerConfig(ModConfig.Type.SERVER, Config.Server.server, "dc_mod-server.toml");
        modEventBus.addListener(this::addCreative);
//        modEventBus.addListener(this::doClientStuff);
//        modEventBus.addListener(this::bake);
//        try {
//            setStaticFinalField(Class.forName("sun.tools.attach.HotSpotVirtualMachine"), "ALLOW_ATTACH_SELF", true);
//            VirtualMachine vm = VirtualMachine.attach(String.valueOf(ProcessHandle.current().pid()));
//            vm.loadAgent("mods/" + ModHelper.getModFileName(MOD_ID));
//            vm.detach();
//        } catch (Exception ignored){}
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            for (RegistryObject<Item> object : DCItems.dc_normal) event.accept(object);
            for (RegistryObject<Item> object : DCItems.armors) event.accept(object);
            if (ModUtil.isFELoad()) for (RegistryObject<Item> object : FEItem.list) event.accept(object);
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

    private void enqueueIMC(InterModEnqueueEvent event) {
        InterModComms.sendTo("curios", "register_type", () -> SlotTypePreset.BACK.getMessageBuilder().build());
        InterModComms.sendTo("curios", "register_type", () -> SlotTypePreset.CURIO.getMessageBuilder().build());
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
            throw new RuntimeException("Failed to setUse static final field: " + fieldName, e);
        }
    }

//    public void doClientStuff(FMLClientSetupEvent event) {
//        if (ModUtil.isSBLoad()) {
//            ItemProperties.init(SBInits.DC_SB.get(), new ResourceLocation("slashblade:user"), new ClampedItemPropertyFunction() {
//                public float unclampedCall(ItemStack p_174564_, @Nullable ClientLevel p_174565_, @Nullable LivingEntity p_174566_, int p_174567_) {
//                    BladeModel.user = p_174566_;
//                    return 0.0F;
//                }
//            });
//        }
//    }
//
//    public void bake(ModelEvent.ModifyBakingResult event) {
//        if (ModUtil.isSBLoad()) {
//            mods.flammpfeil.slashblade.client.ClientHandler.bakeBlade(SBInits.DC_SB.get(), event);
//        }
//    }

}