package net.daichang.dcmods.bytes.mixins;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowItem.class)
public class MixinBow {
    @Inject(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/BowItem;getUseDuration(Lnet/minecraft/world/item/ItemStack;)I"))
    private void onUseStop(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft, CallbackInfo ci) {

    }

    @Inject(method = "getPowerForTime", at = @At("RETURN"))
    private static void getPowerForTime(int pCharge, CallbackInfoReturnable<Float> cir) {

    }
}
