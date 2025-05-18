package net.daichang.dcmods.bytes.mixins;

import net.daichang.dcmods.utils.Utils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer {
    @Unique
    private final ServerPlayer daichangmod$player = (ServerPlayer) (Object) this;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (Utils.isBlocking(daichangmod$player)) daichangmod$player.isAlive();
    }

    @Inject(method = "hurt", at = @At("RETURN"), cancellable = true)
    private void hurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        if (Utils.isBlocking(daichangmod$player)) cir.setReturnValue(false);
    }
}
