package net.daichang.dcmods.common.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mega.uom.attribute.ModAttributes;
import net.daichang.dcmods.client.tool_tip.DCItemTip;
import net.daichang.dcmods.common.item.AttackCountItem;
import net.daichang.dcmods.common.item.DCTier;
import net.daichang.dcmods.common.item.DCTierItem;
import net.daichang.dcmods.common.item.UseCountItem;
import net.daichang.dcmods.common.item.tools.creative.DCLoliPickaxe;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.utils.EntityActuallyHurt;
import net.daichang.dcmods.utils.ModUtil;
import net.daichang.dcmods.utils.TextUtils;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.lists.items.CanSwordBlockItem;
import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class OceanScythe extends DCTierItem implements UseCountItem, AttackCountItem {
    public Multimap<Attribute, AttributeModifier> mainHandModifiers;
    public Multimap<Attribute, AttributeModifier> offHandModifiers;
    public OceanScythe() {
        super(DCTier.OCEAN_HEART, new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> mainHand = ImmutableMultimap.builder();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> offHand = ImmutableMultimap.builder();
        mainHand.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 98.2, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", 1.6, AttributeModifier.Operation.ADDITION));
        mainHand.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 97.2D, AttributeModifier.Operation.ADDITION));
        mainHand.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 0.82D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        mainHand.put(DCAttributes.OCEAN_DAMAGE.get(), new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", 97.2D, AttributeModifier.Operation.ADDITION));
        mainHand.put(DCAttributes.OCEAN_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 0.82D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        mainHand.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 10, AttributeModifier.Operation.ADDITION));
        mainHand.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 2, AttributeModifier.Operation.ADDITION));
        if (ModUtil.isFELoad()) {
            mainHand.put(ModAttributes.getFeDamage(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 59.7D, AttributeModifier.Operation.ADDITION));
            offHand.put(ModAttributes.getFeDamage(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 9.5D, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        mainHandModifiers = mainHand.build();
        offHand.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 88.7D, AttributeModifier.Operation.ADDITION));
        offHand.put(DCAttributes.DC_DEFENSE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 23.2D, AttributeModifier.Operation.ADDITION));
        offHand.put(DCAttributes.OCEAN_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 88.7D, AttributeModifier.Operation.ADDITION));
        offHandModifiers = offHand.build();
        CanSwordBlockItem.addItem(this);
        SuperItemList.addItem(this);
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
        if (pEquipmentSlot == EquipmentSlot.MAINHAND) return this.mainHandModifiers;
        if (pEquipmentSlot == EquipmentSlot.OFFHAND) return this.offHandModifiers;
        return super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (!stack.getTag().contains("dc_attking")) setUse(stack, 0);
        if (!stack.getTag().contains("dcAttackValue")) setCount(stack, 0);
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public Component getName(ItemStack pStack) {
        return TextUtils.rainbow(super.getName(pStack));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        EntityHelper.forceHeal(pPlayer, 20.0F);
        DataHelper.restHealthDelta(pPlayer);
        pPlayer.startUsingItem(pUsedHand);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        EntityHelper.forceHeal(pLivingEntity, 20.0F + pLivingEntity.getMaxHealth() * 0.01F);
        DataHelper.restHealthDelta(pLivingEntity);
        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
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
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return super.initCapabilities(stack, nbt);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
        DCItemTip.addAttackCount(list, pStack);
        list.add(Component.translatable("tool_tip.dc_m.ocean_tip_1").withStyle(ChatFormatting.AQUA));
        list.add(Component.translatable("tool_tip.dc_m.ocean_tip_2").withStyle(ChatFormatting.AQUA));
        list.add(Component.translatable("tool_tip.dc_m.ocean_tip_3").withStyle(ChatFormatting.AQUA));
        super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
    }

    void hurtEntity(LivingEntity target, LivingEntity attcker, ItemStack stack) {
        AttackCountItem.addCountS(stack, 1);
        CompoundTag tag = stack.getTag();
        int useValue = getUse(stack);
        float value = tag.getInt("dc_attking") + (float) (attcker.getAttributeValue(DCAttributes.OCEAN_DAMAGE.get()) + attcker.getAttributeValue(DCAttributes.DC_SUPER_DAMAGE.get()) + attcker.getAttributeValue(Attributes.ATTACK_DAMAGE));
        if (useValue >= 100) value = value + 40 + target.getMaxHealth() * 0.1F;
        if (target.attributes.hasAttribute(Attributes.MAX_HEALTH)) Objects.requireNonNull(target.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(target.getMaxHealth() - 10);
        if (useValue >= 1000) value = value + 50;
        if (useValue >= 10000) value = value + 30;
        if (useValue >= 12000) value = value + 20;
        if (useValue < Integer.MAX_VALUE) addUse(stack, 1);
        if (useValue < 0)  setUse(stack, 0);
        if (AttackCountItem.getCountS(stack) >= 10) {
            target.addEffect(EffectHelper.addEffect(DCEffects.Freeze.get(), 40, 1));
            target.addEffect(EffectHelper.addEffect(DCEffects.Bloodshed.get(), 40, 1));
        }
        if (useValue >= 15000) DCLoliPickaxe.killEntity(target, attcker);
        if (target.getHealth() < 10) DCLoliPickaxe.killEntity(target, attcker);
        EntityActuallyHurt util = EntityActuallyHurt.getInstance(target,attcker);
        util.dcHurt(value);
        try {
            target.dropAllDeathLoot(EntityHelper.dc_damage(target));
        } catch (Exception ignored) {}
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity me) {
        hurtEntity(target,me, stack);
        return super.hurtEnemy(stack, target, me);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof LivingEntity living) hurtEnemy(stack, living, player);
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return true;
    }

    @Override
    public int getBarColor(ItemStack pStack) {
        return Color.CYAN.getRGB();
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        return 13;
    }
}
