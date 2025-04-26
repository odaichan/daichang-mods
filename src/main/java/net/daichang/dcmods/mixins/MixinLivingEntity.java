package net.daichang.dcmods.mixins;

import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.asm.MethodUtil;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity extends Entity {
    @Shadow public abstract float getMaxHealth();

    @Unique
    private final LivingEntity dc_mod$living = (LivingEntity) (Object) this;

    public MixinLivingEntity(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(method = "heal", at = @At("HEAD"), cancellable = true)
    private void heal(float p_21116_, CallbackInfo ci) {
        if (EffectHelper.hasEffect(dc_mod$living, DCEffects.Bloodshed.get()) || dc_mod$living.getPersistentData().contains("toDCMark")) ci.cancel();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        CompoundTag tag = dc_mod$living.getPersistentData();
        if (tag.contains("dc_death")) {
            tag.putInt("dc_death", tag.getInt("dc_death")+1);
            if (tag.getInt("dc_death") > 20) Utils.removeEntity(dc_mod$living);
            dc_mod$living.deathTime= dc_mod$living.deathTime+1;
        }
    }

    @Inject(method = "isDeadOrDying", at = @At("RETURN"), cancellable = true)
    private void isDeadOrDying(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(MethodUtil.isDeadOrDying(dc_mod$living, cir.getReturnValue()));
    }

    @Inject(method = "createLivingAttributes", at = @At("RETURN"), cancellable = true)
    private static void createLivingAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.setReturnValue(cir.getReturnValue().add(DCAttributes.DC_SUPER_DAMAGE.get(), 0.0D).add(DCAttributes.DC_DEFENSE.get(), 0.0D));
    }

    @Inject(method = "getHealth" ,at = @At("RETURN"), cancellable = true)
    private void getHealth(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(MethodUtil.getHealth(dc_mod$living, cir.getReturnValue()));
    }

    @Inject(method = "isAlive", at = @At("RETURN"), cancellable = true)
    private void isAlive(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(MethodUtil.isAlive(dc_mod$living, cir.getReturnValue()));
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void clinit(CallbackInfo ci) {
        DataHelper.DC_GET_HEALTH_DATA = SynchedEntityData.defineId(LivingEntity.class, EntityDataSerializers.FLOAT);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        DataHelper.setHealthDelta(dc_mod$living, tag.getInt(DataHelper.DC_GET_HEALTH));
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        tag.putFloat(DataHelper.DC_GET_HEALTH, DataHelper.getHealthDelta(dc_mod$living));
    }

    @Inject(method = "defineSynchedData", at = @At("HEAD"))
    private void defineSynchedData(CallbackInfo ci) {
        this.entityData.define(DataHelper.DC_GET_HEALTH_DATA, 0F);
    }
}
