package net.daichang.dcmods.bytes.mixins;


import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ItemStack.class)
public class MixinItemStack {
    @Unique
    private final ItemStack daichangmod$stack = (ItemStack) (Object) this;

//    @Inject(method = "enchant", at = @At("HEAD"), cancellable = true)
//    private void onEnchant(Enchantment pEnchantment, int pLevel, CallbackInfo ci) {
//        if (pEnchantment instanceof BaseEnch baseEnch) {
//            ListTag enchantments = daichangmod$stack.getEnchantmentTags();
//            enchantments.add(EnchantmentHelpers.storeEnchantment(EnchantmentHelpers.getEnchantmentId(baseEnch), pLevel));
//            daichangmod$stack.getOrCreateTag().put("Enchantments", enchantments);
//            ci.cancel();
//        }
//    }
}
