package net.daichang.dcmods.addons.tconstruct;

import com.c2h6s.etstlib.entity.specialDamageSources.LegacyDamageSource;
import com.c2h6s.etstlib.register.EtSTLibHooks;
import com.c2h6s.etstlib.tool.hooks.ArrowHitModifierHook;
import com.c2h6s.etstlib.tool.hooks.ModifyDamageSourceModifierHook;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.utils.TextUtils;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.AttributesModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolActionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;

public class OceanHeart extends Modifier implements ModifyDamageSourceModifierHook, TooltipModifierHook, ToolActionModifierHook, AttributesModifierHook, ArrowHitModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.@NotNull Builder hookBuilder) {
        hookBuilder.addHook(this, EtSTLibHooks.MODIFY_DAMAGE_SOURCE, ModifierHooks.TOOLTIP, ModifierHooks.TOOL_ACTION, ModifierHooks.ATTRIBUTES);
    }

    @Override
    public LegacyDamageSource modifyDamageSource(IToolStackView tool, ModifierEntry entry, LivingEntity attacker, InteractionHand hand, Entity target, EquipmentSlot sourceSlot, boolean isFullyCharged, boolean isExtraAttack, boolean isCritical, LegacyDamageSource source) {
        return new LegacyDamageSource(EntityHelper.ocean_damage(attacker));
    }

    @Override
    public LegacyDamageSource modifyArrowDamageSource(ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier, AbstractArrow arrow, @Nullable LivingEntity attacker, @Nullable Entity target, LegacyDamageSource source) {
        return new LegacyDamageSource(EntityHelper.ocean_damage(attacker));
    }

    @Override
    public void addTooltip(@NotNull IToolStackView iToolStackView, @NotNull ModifierEntry modifierEntry, @Nullable Player player, List<Component> list, @NotNull TooltipKey tooltipKey, @NotNull TooltipFlag tooltipFlag) {
        list.add(TextUtils.rainbow(Component.translatable("tconstruct.dc_m.tool_tip.ocean_heart").getString()));
        list.add(TextUtils.rainbow(Component.translatable("tconstruct.dc_m.tool_tip.ocean_heart_1").getString()));
    }

    @Override
    public boolean canPerformAction(@NotNull IToolStackView iToolStackView, @NotNull ModifierEntry modifierEntry, @NotNull ToolAction toolAction) {
        return true;
    }

    @Override
    public void addAttributes(IToolStackView iToolStackView, ModifierEntry modifierEntry, EquipmentSlot equipmentSlot, BiConsumer<Attribute, AttributeModifier> biConsumer) {
        if (equipmentSlot.getType() == EquipmentSlot.Type.HAND) {
            biConsumer.accept(DCAttributes.OCEAN_DAMAGE.get(), new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Item modifer", modifierEntry.getLevel() * 8.94F, AttributeModifier.Operation.ADDITION));
            biConsumer.accept(DCAttributes.OCEAN_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifer", 0.2D, AttributeModifier.Operation.MULTIPLY_TOTAL));
            biConsumer.accept(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(Item.BASE_ATTACK_DAMAGE_UUID, "Item modifer", modifierEntry.getLevel() * 6.67F, AttributeModifier.Operation.ADDITION));
        }
    }
}
