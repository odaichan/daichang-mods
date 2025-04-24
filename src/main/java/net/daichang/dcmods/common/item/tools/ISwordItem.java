package net.daichang.dcmods.common.item.tools;

import com.google.common.collect.Multimap;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.lists.items.CanSwordBlockItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class ISwordItem extends SwordItem {
    public ISwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        CanSwordBlockItem.addItem(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.startUsingItem(pUsedHand);
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        stack.getOrCreateTag().putInt("onDCSwordBlock", stack.getOrCreateTag().getInt("onDCSwordBlock") + 1);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (!(stack.getOrCreateTag().contains("onDCSwordBlock"))) stack.getOrCreateTag().putInt("onDCSwordBlock", 0);
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        EntityHelper.noHurtDuration(pAttacker);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return Utils.getUseAnim();
    }

    @Override
    public @NotNull AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
        return target.getBoundingBox().inflate(1.2D, 0.32D, 1.2D);
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }
}
