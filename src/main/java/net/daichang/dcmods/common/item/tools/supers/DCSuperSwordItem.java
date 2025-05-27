package net.daichang.dcmods.common.item.tools.supers;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.daichang.dcmods.client.tool_tip.DCItemTip;
import net.daichang.dcmods.common.item.UseCountItem;
import net.daichang.dcmods.common.item.tools.ISwordItem;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.helpers.MathHelper;
import net.daichang.dcmods.utils.lists.items.CanSwordBlockItem;
import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class DCSuperSwordItem extends ISwordItem implements UseCountItem {
    public Multimap<Attribute, AttributeModifier> mainHandModifiers;
    public Multimap<Attribute, AttributeModifier> offHandModifiers;

    public DCSuperSwordItem(Tier p_43269_, int pAttackDamageModifier, float pAttackSpeedModifier,final float super_damage, Properties p_43272_) {
        super(p_43269_, pAttackDamageModifier, pAttackSpeedModifier, p_43272_);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> mainHand = ImmutableMultimap.builder();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> offHand = ImmutableMultimap.builder();
        mainHand.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", pAttackSpeedModifier, AttributeModifier.Operation.ADDITION));
        mainHand.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Item modifier", super_damage, AttributeModifier.Operation.ADDITION));
        mainHand.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 0.52D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        mainHand.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 7, AttributeModifier.Operation.ADDITION));
        mainHandModifiers = mainHand.build();
        offHand.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 8.7D, AttributeModifier.Operation.ADDITION));
        offHand.put(DCAttributes.DC_DEFENSE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 3.2D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "Weapon modifier", 0.5, AttributeModifier.Operation.ADDITION));
        offHandModifiers = offHand.build();
        SuperItemList.addItem(this);
        CanSwordBlockItem.addItem(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        CompoundTag comTag = stack.getTag();
        if (comTag != null) {
            if (!comTag.contains("dc_attking")) setUse(stack, 0);
        }
        return super.getAttributeModifiers(equipmentSlot, stack);
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
        if (pEquipmentSlot == EquipmentSlot.MAINHAND) return this.mainHandModifiers;
        if (pEquipmentSlot == EquipmentSlot.OFFHAND) return this.offHandModifiers;
        return super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public void onUseTick(Level p_41428_, LivingEntity living, ItemStack p_41430_, int p_41431_) {
        living.addEffect(EffectHelper.addEffect(MobEffects.REGENERATION, 20, true));
        living.addEffect(EffectHelper.addEffect(MobEffects.DAMAGE_RESISTANCE, 20, 3,true));
        living.heal(5.5F);
        EntityHelper.forceHeal(living, 5.5F);
        if (DataHelper.getHealthDelta(living) <= 0) DataHelper.addHealthDelta(living, 10.5F);
        living.invulnerableTime = 1;
        super.onUseTick(p_41428_, living, p_41430_, p_41431_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, @NotNull List<Component> list, @NotNull TooltipFlag p_41424_) {
        if (Screen.hasShiftDown()) {
            DCItemTip.addAttackCount(list, stack);
            list.add(Component.translatable("tooltip.dc_mods.tips"));
            list.add(Component.translatable("tooltip.dc_mods.tips_1"));
            list.add(Component.translatable("tooltip.dc_mods.tips_2"));
            list.add(Component.translatable("tooltip.dc_mods.attacking_entity"));
            list.add(Component.translatable("tooltip.dc_mods.sword_boxing"));
            list.add(Component.translatable("tooltip.dc_mods.tip_3"));
            list.add(Component.translatable("tooltip.dc_mods.kill_entity"));
            list.add(Component.translatable("tooltip.dc_mods.minecraft"));
            list.add(Component.translatable("tooltip.dc_mods.drop_loot"));
            list.add(Component.translatable("tooltip.dc_mods.health_get"));
            list.add(Component.translatable("tooltip.dc_mods.kill"));
            list.add(Component.translatable("tooltip.dc_mods.attacking_entity_cooldown"));
            list.add(Component.translatable("tooltip.dc_mods.bleed"));
            list.add(Component.translatable("tooltip.dc_mods.render_death_1"));
            list.add(Component.translatable("tooltip.dc_mods.render_death_2"));
            list.add(Component.translatable("tooltip.dc_mods.setting_max_health"));
            if (getUse(stack) >= 10000) list.add(Component.translatable("tooltip.dc_mods.is_strong"));
            else list.add(Component.translatable("tooltip.dc_mods.strong"));
        }
        else {
            list.add(Component.translatable("tool_tip.dc_mods.7meter"));
            list.add(Component.literal("Subscribe to DaiChang on Bilibili"));
            list.add(Component.literal("< Press Shift to more >"));
        }
        super.appendHoverText(stack, p_41422_, list, p_41424_);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity living) {
        if (living instanceof Player player) Utils.attackEntity(stack, target, player);
        return super.hurtEnemy(stack, target, living);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof LivingEntity living && player.level instanceof ServerLevel) hurtEnemy(stack, living, player);
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public int getBarWidth(ItemStack p_150900_) {
        return 13;
    }

    @Override
    public int getBarColor(ItemStack p_150901_) {
        return new Random(Util.getMillis()).nextInt();
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getUse(stack) >= 10000;
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @SubscribeEvent
    public static void onHitEntity(LivingAttackEvent event) {
        LivingEntity target = event.getEntity();
        if (event.getSource().getEntity() instanceof Player player && player.getMainHandItem().getItem() instanceof DCSuperSwordItem item) {
            ItemStack stack = player.getMainHandItem();
            item.hurtEnemy(stack, target, player);
            target.hurt(target.damageSources().magic(),  21.0F + (float)(MathHelper.getRandomDouble(1, 6) * 4 - 3) + 2);
        }
    }
}
