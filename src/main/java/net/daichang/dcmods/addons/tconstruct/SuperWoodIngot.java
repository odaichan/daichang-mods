package net.daichang.dcmods.addons.tconstruct;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.register.EtSTLibHooks;
import com.c2h6s.etstlib.tool.hooks.ModifyDamageSourceModifierHook;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolActionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class SuperWoodIngot extends Modifier implements ModifyDamageSourceModifierHook, TooltipModifierHook, ToolActionModifierHook, GeneralInteractionModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.@NotNull Builder hookBuilder) {
        hookBuilder.addHook(this, EtSTLibHooks.MODIFY_DAMAGE_SOURCE, ModifierHooks.TOOLTIP, ModifierHooks.TOOL_ACTION, ModifierHooks.GENERAL_INTERACT);
    }

    @Override
    public LegacyDamageSource modifyDamageSource(IToolStackView tool, ModifierEntry entry, LivingEntity attacker, InteractionHand hand, Entity target, EquipmentSlot sourceSlot, boolean isFullyCharged, boolean isExtraAttack, boolean isCritical, LegacyDamageSource source) {
        return new LegacyDamageSource(EntityHelper.dc_damage(target, attacker));
    }

    @Override
    public void addTooltip(@NotNull IToolStackView iToolStackView, @NotNull ModifierEntry modifierEntry, @Nullable Player player, List<Component> list, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("tconstruct.dc_m.tool_tip"));
    }

    @Override
    public boolean canPerformAction(@NotNull IToolStackView iToolStackView, @NotNull ModifierEntry modifierEntry, ToolAction toolAction) {
        return true;
    }

    @Override
    public InteractionResult onToolUse(IToolStackView iToolStackView, ModifierEntry modifierEntry, Player player, InteractionHand interactionHand, InteractionSource interactionSource) {
        if (interactionSource == InteractionSource.RIGHT_CLICK) {
            GeneralInteractionModifierHook.startUsing(iToolStackView,modifierEntry.getId(), player, interactionHand);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Override
    public void onUsingTick(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, int timeLeft) {
        GeneralInteractionModifierHook.super.onUsingTick(tool, modifier, entity, timeLeft);
        int healcount = ModifierUtil.getModifierLevel(tool.getItem().getDefaultInstance(), ModifierRegister.DC_SUPER_WOOD_INGOT.getId());
        float f = 1.3F * healcount;
        entity.heal(f);
        EntityHelper.forceHeal(entity, f);
    }

    @Override
    public int getUseDuration(IToolStackView tool, ModifierEntry modifier) {
        return 72000;
    }

    @Override
    public @NotNull UseAnim getUseAction(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier) {
        return Utils.getUseAnim();
    }
}
