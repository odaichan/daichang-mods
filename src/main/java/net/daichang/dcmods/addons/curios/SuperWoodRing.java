package net.daichang.dcmods.addons.curios;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.daichang.dcmods.client.font.DCItemFont;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class SuperWoodRing extends Item implements ICurioItem {
    public Multimap<Attribute, AttributeModifier> multimap;

    public SuperWoodRing() {
        super(new Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> mainHand = ImmutableMultimap.builder();
        mainHand.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "Weapon modifier", 5.2D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), "Weapon modifier", 10.0D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.LUCK, new AttributeModifier(UUID.randomUUID(), "Weapon modifier", 10.0D, AttributeModifier.Operation.ADDITION));
        mainHand.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 5.2D, AttributeModifier.Operation.ADDITION));
        mainHand.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 0.52D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        multimap = mainHand.build();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            @Override
            public @NotNull Font getFont(ItemStack stack, FontContext context) {
                return DCItemFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tool_tip.dc_mods.wood_ring"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        return multimap;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ICurioItem.super.curioTick(slotContext, stack);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        ICurioItem.super.curioTick(identifier, index, livingEntity, stack);
        if (livingEntity instanceof Player player) {
            Inventory inventory = player.getInventory();
            for (ItemStack stack1 : inventory.items) stack1.setDamageValue(0);
        }
        if (livingEntity.tickCount % 10 == 0) {
            EntityHelper.forceHeal(livingEntity, 5.1F);
            livingEntity.heal(5.1F);
        }
        livingEntity.addEffect(EffectHelper.addEffect(DCEffects.Heal.get(), 200, 5));
        livingEntity.addEffect(EffectHelper.addEffect(MobEffects.LUCK, 200));
        livingEntity.addEffect(EffectHelper.addEffect(MobEffects.ABSORPTION, 200));
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player) {
            Inventory inventory = player.getInventory();
            for (ItemStack stack1 : inventory.items) {
                stack1.setDamageValue(stack1.getDamageValue() - 1);
            }
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        Inventory inventory = player.getInventory();
        for (ItemStack stack1 : inventory.items) {
            stack1.setDamageValue(stack1.getDamageValue() - 1);
        }
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
    }
}
