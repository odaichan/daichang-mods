package net.daichang.dcmods;

import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.inits.DCEnch;
import net.daichang.dcmods.inits.DCItems;
import net.daichang.dcmods.inits.DCTabs;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DCMod.MOD_ID)
public class DCMod {
    public static final String MOD_ID = "dc_m";

    public DCMod() {
        //noinspection removal
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        DCEnch.ench.register(modEventBus);
        System.out.println(Minecraft.getInstance().gameDirectory.getAbsolutePath());
        DCItems.items.register(modEventBus);
        DCTabs.tab.register(modEventBus);
        DCAttributes.attribute.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }
}