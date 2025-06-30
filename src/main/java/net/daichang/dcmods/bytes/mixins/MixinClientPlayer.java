package net.daichang.dcmods.bytes.mixins;

import net.daichang.dcmods.utils.DeprecatedMixin;
import net.daichang.dcmods.utils.Utils;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
@DeprecatedMixin
public class MixinClientPlayer {
    @Inject(method = "isUsingItem", at = @At("RETURN"), cancellable = true)
    private void useItem(CallbackInfoReturnable<Boolean> cir) {
        if (Utils.isBlocking((LocalPlayer)(Object)this)) {
            cir.setReturnValue(false);
        }
    }
}
