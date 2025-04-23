package net.daichang.dcmods.mixins;

import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class MixinEntity {
    @Shadow public abstract void setDeltaMovement(Vec3 pDeltaMovement);

    @Shadow public Vec3 deltaMovement;
    @Unique
    private final Entity daichangmod$entity = (Entity) (Object) this;

    @Inject(method = "getDeltaMovement", at = @At("HEAD"), cancellable = true)
    private void getDeltaMovement(CallbackInfoReturnable<Vec3> cir) {
        if (daichangmod$entity instanceof LivingEntity && EffectHelper.hasEffect((LivingEntity) daichangmod$entity,DCEffects.Freeze.get())) {
            cir.setReturnValue(Vec3.ZERO);
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (daichangmod$entity instanceof LivingEntity && EffectHelper.hasEffect((LivingEntity) daichangmod$entity,DCEffects.Freeze.get())) {
            setDeltaMovement(Vec3.ZERO);
            deltaMovement = Vec3.ZERO;
        }
    }
}
