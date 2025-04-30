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

import java.util.Random;

public class ISwordItem extends SwordItem {
    public ISwordItem(Tier pTier, int pAttackDamageModifier, float pAttackSpeedModifier, Properties pProperties) {
        super(pTier, pAttackDamageModifier, pAttackSpeedModifier, pProperties);
        CanSwordBlockItem.addItem(this);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        if (stack.getTag() != null) stack.getTag().putInt("onDCSwordBlock", new Random().nextInt());
        pPlayer.startUsingItem(pUsedHand);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
        return super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (stack.getTag() != null && !(stack.getTag().contains("onDCSwordBlock"))) stack.getTag().putInt("onDCSwordBlock", 0);
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, @NotNull LivingEntity pTarget, @NotNull LivingEntity pAttacker) {
        EntityHelper.noHurtDuration(pTarget);
        pTarget.hurt(EntityHelper.player_attack_damage(pAttacker), 5);
        if (pStack.getTag() != null) pStack.getTag().putInt("onDCSwordBlock", new Random().nextInt());
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack pStack) {
        return 72000;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack pStack) {
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
