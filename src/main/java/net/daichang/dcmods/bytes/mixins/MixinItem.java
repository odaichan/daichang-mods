package net.daichang.dcmods.bytes.mixins;

import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public abstract class MixinItem {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(Item.Properties pProperties, CallbackInfo ci) {

    }
}
