package net.daichang.dcmods.bytes.mixins.omni_mob;

import flashfur.omnimobs.entities.anticheat.HealthManager;
import flashfur.omnimobs.entities.base.BossEntity;
import net.daichang.dcmods.utils.ModDependsMixin;
import net.daichang.dcmods.utils.helpers.DataHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//对Omni-mob进行了适当的修改
@Mixin(value = HealthManager.class, remap = false)
@ModDependsMixin("omnimobs")
public class HealthManagerMixin {
    @Inject(method = "_a", at = @At("RETURN"), cancellable = true)
    private static void getHealth(BossEntity bossEntity, CallbackInfoReturnable<Float> cir) {
        if (DataHelper.isDead(bossEntity)) cir.setReturnValue(Float.NEGATIVE_INFINITY);
    }

    @Inject(method = "_b", at = @At("RETURN"), cancellable = true)
    private static void isAlive(BossEntity bossEntity, CallbackInfoReturnable<Boolean> cir) {
        if (DataHelper.isDead(bossEntity)) cir.setReturnValue(false);
    }

    @Inject(method = "_c", at = @At("RETURN"), cancellable = true)
    private static void isDead(BossEntity bossEntity, CallbackInfoReturnable<Boolean> cir) {
        if (DataHelper.isDead(bossEntity)) cir.setReturnValue(true);
    }
}
