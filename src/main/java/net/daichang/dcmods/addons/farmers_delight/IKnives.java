package net.daichang.dcmods.addons.farmers_delight;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mega.uom.attribute.ModAttributes;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.utils.ModUtil;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.lists.items.CanSwordBlockItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.item.KnifeItem;

import java.util.List;
import java.util.UUID;

public class IKnives extends KnifeItem {
    public Multimap<Attribute, AttributeModifier> modifier;
    int s;
    public IKnives(Tier tier, double snow_damage, double damage, int s) {
        super(tier, 0, 0, new Properties().fireResistant());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> hand = ImmutableMultimap.builder();
        this.s = s;
        hand.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", snow_damage, AttributeModifier.Operation.ADDITION));
        hand.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", damage, AttributeModifier.Operation.ADDITION));
        hand.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "Weapon modifier", 2.4D, AttributeModifier.Operation.ADDITION));
        if (ModUtil.isFELoad()) {
            hand.put(ModAttributes.getFeDamage(), new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 0.1D, AttributeModifier.Operation.ADDITION));
            hand.put(ModAttributes.getDsDamage(), new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", 0.2D, AttributeModifier.Operation.ADDITION));
        }
        modifier = hand.build();
        CanSwordBlockItem.addItem(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        pPlayer.startUsingItem(pUsedHand);
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return Utils.getUseAnim();
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? modifier : super.getDefaultAttributeModifiers(pEquipmentSlot);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.addEffect(EffectHelper.addEffect(DCEffects.Bloodshed.get(), s * 20, 1));
        EntityHelper.noHurtDuration(pTarget);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> list, TooltipFlag pIsAdvanced) {
        list.add(Component.translatable("tool_tip.dc_m.hurt_entity"));
        list.add(Component.translatable("tool_tip.dc_m.knives.add_effect_to_entity"));
        super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
    }
}
