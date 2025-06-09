package net.daichang.dcmods.inits;

import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.common.entities.boss.DCLoveElaina;
import net.daichang.dcmods.common.entities.boss.DCSteve;
import net.daichang.dcmods.common.entities.boss.DCWither;
import net.daichang.dcmods.common.entities.creative.EntityLoli;
import net.daichang.dcmods.common.entities.projectile.DCSuperArrow;
import net.daichang.dcmods.common.entities.projectile.DCWitherSkull;
import net.daichang.dcmods.common.entities.entity.RainbowLightingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DCEntities {
    public static DeferredRegister<EntityType<?>> entities = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DCMod.MOD_ID);

    public static final RegistryObject<EntityType<RainbowLightingEntity>> RAINBOW_LIGHTING = register("rainbow_lighting", EntityType.Builder.<RainbowLightingEntity>of(RainbowLightingEntity::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(RainbowLightingEntity::new).sized(0.6f, 1.8f));

    public static final RegistryObject<EntityType<DCSuperArrow>> DC_SUPER_ARROW = register("dc_super_arrow", EntityType.Builder.of(DCSuperArrow::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(1));

    public static final RegistryObject<EntityType<DCWitherSkull>> DC_WITHER_SKULL = register("dc_wither_skull", EntityType.Builder.of(DCWitherSkull::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(1));

    public static final RegistryObject<EntityType<DCLoveElaina>> ELAINA = register("elaina",EntityType.Builder.<DCLoveElaina>of(DCLoveElaina::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(DCLoveElaina::new).sized(0.5f, 2.1f));

    public static final RegistryObject<EntityType<DCSteve>> DC_STEVE = register("steve",EntityType.Builder.<DCSteve>of(DCSteve::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(DCSteve::new).sized(0.5f, 1.8f));

    public static final RegistryObject<EntityType<EntityLoli>> LOLI = register("loli",EntityType.Builder.<EntityLoli>of(EntityLoli::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(EntityLoli::new).sized(0.5f, 1.8f));

    public static final RegistryObject<EntityType<DCWither>> DC_WITHER = register("wither",EntityType.Builder.<DCWither>of(DCWither::new, MobCategory.MISC).setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(DCWither::new).sized(1.2f, 2.8f));

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String register_id, EntityType.Builder<T> entityTypeBuilder) {
        RegistryObject<EntityType<T>> object = entities.register(register_id, () -> entityTypeBuilder.build(register_id));
        long startTime = System.currentTimeMillis();
        DCMod.logger("try to register entity " + register_id);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        DCMod.logger("entity " + register_id + " registered in " + executionTime + " ms");
        return object;
    }
}
