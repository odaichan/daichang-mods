package net.daichang.dcmods.mixins;

import net.daichang.dcmods.DCBaseLib;
import net.daichang.dcmods.inits.DCEnch;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class MixinItem implements DCBaseLib {
    @Unique
    private final Item daichangmod$item = (Item) (Object)this;

    @Inject(method = "getUseDuration", at = @At("RETURN"), cancellable = true)
    private void getUseDuration(ItemStack p_41454_, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() / (p_41454_.getEnchantmentLevel(DCEnch.FAST_BOW.get()) + 1));
    }
}