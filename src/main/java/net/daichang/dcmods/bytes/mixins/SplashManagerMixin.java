package net.daichang.dcmods.bytes.mixins;

import net.daichang.dcmods.Config;
import net.daichang.dcmods.client.render.ISplashRenderer;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.resources.SplashManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SplashManager.class)
public class SplashManagerMixin {
    @Inject(method = "getSplash", at = @At("RETURN"), cancellable = true)
    private void getSplash(CallbackInfoReturnable<SplashRenderer> cir) {
        if (Config.Client.can_change_splash.get()) cir.setReturnValue(new ISplashRenderer("Subscribe to DaiChang !!!"));
    }
}
