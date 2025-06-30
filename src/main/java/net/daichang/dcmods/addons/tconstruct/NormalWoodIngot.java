package net.daichang.dcmods.addons.tconstruct;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.register.EtSTLibHooks;
import com.c2h6s.etstlib.tool.hooks.ModifyDamageSourceModifierHook;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

public class NormalWoodIngot extends Modifier implements OnAttackedModifierHook, ModifyDamageSourceModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.ON_ATTACKED, EtSTLibHooks.MODIFY_DAMAGE_SOURCE);
    }

    @Override
    public LegacyDamageSource modifyDamageSource(IToolStackView tool, ModifierEntry entry, LivingEntity attacker, InteractionHand hand, Entity target, EquipmentSlot sourceSlot, boolean isFullyCharged, boolean isExtraAttack, boolean isCritical, LegacyDamageSource source) {
        if (target instanceof LivingEntity living) living.addEffect(EffectHelper.addEffect(MobEffects.SLOW_FALLING, 1 + entry.getLevel() / 1.5F * 1.1F, 2));
        return ModifyDamageSourceModifierHook.super.modifyDamageSource(tool, entry, attacker, hand, target, sourceSlot, isFullyCharged, isExtraAttack, isCritical, source);
    }

    @Override
    public LegacyDamageSource modifyArrowDamageSource(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, AbstractArrow arrow, @Nullable LivingEntity attacker, @Nullable Entity target, LegacyDamageSource source) {
        if (target instanceof LivingEntity living) living.addEffect(EffectHelper.addEffect(MobEffects.SLOW_FALLING, 1 + modifier.getLevel() / 1.5F * 1.1F, 2));
        return ModifyDamageSourceModifierHook.super.modifyArrowDamageSource(modifiers, persistentData, modifier, arrow, attacker, target, source);
    }

    @Override
    public void onAttacked(@NotNull IToolStackView iToolStackView, @NotNull ModifierEntry modifierEntry, EquipmentContext equipmentContext, EquipmentSlot equipmentSlot, DamageSource damageSource, float v, boolean b) {}
}
