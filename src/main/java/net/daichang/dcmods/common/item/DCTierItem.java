package net.daichang.dcmods.common.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;

public class DCTierItem extends TieredItem {
    public DCTierItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_41395_, LivingEntity target, LivingEntity me) {
        DamageSource damageSource = new DamageSource(me.level().registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FELL_OUT_OF_WORLD), me);
        target.hurt(damageSource, getDamageValue());
        return super.hurtEnemy(p_41395_, target, me);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        return super.onLeftClickEntity(stack, player, entity);
    }

    public float getDamageValue() {
        return this.getDamage(this.getDefaultInstance()) * 9 / 1.5F;
    }
}
