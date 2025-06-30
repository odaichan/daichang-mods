package net.daichang.dcmods.bytes.mixins.slashblade;

import net.daichang.dcmods.addons.slashblade.DaiChangSB;
import net.daichang.dcmods.utils.ModDependsMixin;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
@ModDependsMixin("slashblade")
public abstract class ItemStackMixin {
    @Shadow
    public abstract Item getItem();

    @Inject(method = "<init>(Lnet/minecraft/world/level/ItemLike;ILnet/minecraft/nbt/CompoundTag;)V", at = @At("RETURN"))
    private void init(ItemLike p_41604_, int p_41605_, CompoundTag p_41606_, CallbackInfo ci) {
        ItemStack stack = (ItemStack) (Object) this;
        if (getItem() instanceof DaiChangSB) DaiChangSB.init(stack);
    }
}