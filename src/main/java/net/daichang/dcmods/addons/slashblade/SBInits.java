package net.daichang.dcmods.addons.slashblade;

import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.registry.specialeffects.SpecialEffect;
import net.daichang.dcmods.DCMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SBInits {
    public static List<RegistryObject<ItemSlashBlade>> list = new ArrayList<>();

    public static final DeferredRegister<Item> item;
    public static final RegistryObject<SpecialEffect> DC_EDGE;

    public static RegistryObject<ItemSlashBlade> registry(String id, Supplier<? extends ItemSlashBlade> target) {
        long startTime = System.currentTimeMillis();
        DCMod.logger("try to register slashblade " + id);
        RegistryObject<ItemSlashBlade> object = item.register(id, target);
        list.add(object);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        DCMod.logger("slashblade " + id + " registered in " + executionTime + " ms");
        return object;
    }
    public static final RegistryObject<ItemSlashBlade> DC_SB;

    public static void init(IEventBus eventBus) {
        DCMod.logger("Slashblade is load");
        effect.register(eventBus);
        item.register(eventBus);
        MinecraftForge.EVENT_BUS.register(DaiChangSpecialEffect.class);
    }

    public static final DeferredRegister<SpecialEffect> effect;

    public static RegistryObject<SpecialEffect> register(String id, Supplier<? extends SpecialEffect> supplier) {
        long startTime = System.currentTimeMillis();
        DCMod.logger("try to register slashblade special effect " + id);
        RegistryObject<SpecialEffect> object = effect.register(id, supplier);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        DCMod.logger("slashblade special effect " + id + " registered in " + executionTime + " ms");
        return object;
    }

    static {
        item = DeferredRegister.create(ForgeRegistries.ITEMS, DCMod.MOD_ID);
        DC_SB = registry("daichang_slash_blade", DaiChangSB::new);
        effect = DeferredRegister.create(SpecialEffect.REGISTRY_KEY, DCMod.MOD_ID);
        DC_EDGE = register("daichang_se", DaiChangSpecialEffect::new);
    }

//    private static ResourceKey<SlashBladeDefinition> init(String id) {
//        return ResourceKey.create(SlashBladeDefinition.REGISTRY_KEY, new ResourceLocation(DCMod.MOD_ID, id));
//    }
//
//
//
//    public static final ResourceKey<SlashBladeDefinition> DC_BLADE = init("daichang_sb");
//    public static SlashBladeDefinition DC_BLADE_DEFINE = new SlashBladeDefinition(
//            SBHandler.DC_BLADE,
//            RenderDefinition.Builder.newInstance()
//                    .effectColor(Color.CYAN.getRGB())
//                    .textureName(new ResourceLocation(DCMod.MOD_ID, "models/named/dc_sb.png"))
//                    .modelName(new ResourceLocation(DCMod.MOD_ID, "models/named/dc_sb.obj")).build(),
//            PropertiesDefinition.Builder.newInstance()
//                    .baseAttackModifier(28.5F)
//                    .maxDamage(40)
//                    .addSpecialEffect(SpecialEffectsRegistry.WITHER_EDGE.getId())
//                    .slashArtsType(SlashArtsRegistry.JUDGEMENT_CUT.getId())
//                    .defaultSwordType(List.of(SwordType.BEWITCHED)).build(),
//            List.of(new EnchantmentDefinition(DCEnch.SuperSharp.getId(), 254))) {
//        @Override
//        public ItemStack getBlade(Item bladeItem) {
//            ItemStack stack = super.getBlade(bladeItem);
//            DaiChangSB.init(stack);
//            return stack;
//        }
//
//        @Override
//        public ItemStack getBlade() {
//            return this.getBlade(SBHandler.getItem(SBHandler.DC_BLADE));
//        }
//    };
}
