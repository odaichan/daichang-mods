package net.daichang.dcmods.mixins;

import net.daichang.dcmods.common.item.tools.creative.DCLoliPickaxe;
import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.inits.DCEnch;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(Player.class)
public abstract class MixinPlayer {
    @Unique
    private final Player daichangmod$player = (Player) (Object) this;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        Inventory inventory = daichangmod$player.getInventory();
        NonNullList<ItemStack> armor = inventory.armor;
        for (ItemStack stack : armor) {
            Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
            if (enchantments.containsKey(DCEnch.NightVison.get())) {
                daichangmod$player.addEffect(EffectHelper.addEffect(MobEffects.NIGHT_VISION, 80, 1, true));
            }
        }
    }

    @Inject(method = "hurt", at = @At("RETURN"), cancellable = true)
    private void hurt(DamageSource pSource, float pAmount, CallbackInfoReturnable<Boolean> cir) {
        if (Utils.isBlocking(daichangmod$player)) cir.setReturnValue(false);
        if (DCLoliPickaxe.isHasLoliPickaxe(daichangmod$player)) cir.setReturnValue(false);
    }

    @Inject(method = "getSpeed", at = @At("RETURN"), cancellable = true)
    private void getSpeed(CallbackInfoReturnable<Float> cir) {
        if (EffectHelper.hasEffect(daichangmod$player, DCEffects.Speed.get())) cir.setReturnValue(cir.getReturnValue() + (0.1F + EffectHelper.getEffectLevel(daichangmod$player, DCEffects.Speed.get())));
    }
}