package net.daichang.dcmods.mixins;

import net.daichang.dcmods.inits.DCEnch;
import net.daichang.dcmods.utils.Heal2ZList;
import net.daichang.dcmods.utils.EffectUtil;
import net.minecraft.core.NonNullList;
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

import java.util.Map;

@Mixin(Player.class)
public class MixinPlayer {
    @Unique
    private final Player daichangmod$player = (Player) (Object) this;

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        Inventory inventory = daichangmod$player.getInventory();
        NonNullList<ItemStack> armor = inventory.armor;
        for (ItemStack stack : armor) {
            Map<Enchantment, Integer> enchantments = stack.getAllEnchantments();
            if (enchantments.containsKey(DCEnch.NightVison.get())) {
                daichangmod$player.addEffect(EffectUtil.addEffect(MobEffects.NIGHT_VISION, 80, 1, true));
            }
        }
    }

    @Inject(method = "respawn", at = @At("HEAD"))
    private void respawn(CallbackInfo ci) {
        Heal2ZList.removeUUID(daichangmod$player);
    }
}
