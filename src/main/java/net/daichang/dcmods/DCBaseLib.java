package net.daichang.dcmods;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface DCBaseLib {
    Minecraft mc = Minecraft.getInstance();

    default void strongDCKill(ItemStack stack, LivingEntity me, LivingEntity target) {
        if (stack.getTag().getBoolean("dc_item_strong")) {
            DamageSource damageSource = new DamageSource(target.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC), me);
            target.hurt(damageSource, 3000);
        }
    }
}
