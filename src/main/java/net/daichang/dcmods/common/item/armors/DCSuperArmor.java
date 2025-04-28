package net.daichang.dcmods.common.item.armors;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.daichang.dcmods.client.font.DCItemFont;
import net.daichang.dcmods.common.item.DCTier;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.inits.DCItems;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class DCSuperArmor extends ArmorItem {
    public Multimap<Attribute, AttributeModifier> modifiers;
    private static final ArmorMaterial pMaterial = createArmorMaterial("super_wood", DCTier.SUPERS.getLevel(), Ingredient.of(DCItems.SUPER_WOOD_INGOT.get()));

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tool_tip.dc_mods.when_worn"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public static @NotNull ArmorMaterial createArmorMaterial(String name, double level, Ingredient repairIngredient) {
        return new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return (new int[]{8, 11, 14, 7}[type.getSlot().getIndex()] * (int) Math.round(14 * level));
            }

            @Override
            public int getDefenseForType(ArmorItem.Type type) {
                return new int[]{(int) Math.round(1.1f * level), (int) Math.round(2.5 * level), (int) Math.round(3 * level), (int) Math.round(level)}[type.getSlot().getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return (int) Math.round(4 * level);
            }

            @Override
            public @NotNull SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_DIAMOND;
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return repairIngredient;
            }

            @Override
            public @NotNull String getName() {
                return name + "_";
            }

            @Override
            public float getToughness() {
                return level >= 2 ? (float) level : 0f;
            }

            @Override
            public float getKnockbackResistance() {
                return level >= 3 ? (0.1f * (float) level / 3.0f) : 0f;
            }
        };
    }

    public DCSuperArmor(Type pType) {
        super(pMaterial, pType, new Properties().fireResistant().rarity(Rarity.EPIC));
        ImmutableMultimap.Builder<Attribute, AttributeModifier> mainHand = ImmutableMultimap.builder();
        mainHand.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "Weapon modifier", 2.4D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "Weapon modifier", 0.1D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.ARMOR, new AttributeModifier(UUID.randomUUID(), "Weapon modifier", 7.3D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "Weapon modifier", 4.3D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), "Weapon modifier", 5.2D, AttributeModifier.Operation.ADDITION));
        mainHand.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 5.2D, AttributeModifier.Operation.ADDITION));
        mainHand.put(DCAttributes.DC_DEFENSE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 5.2D, AttributeModifier.Operation.ADDITION));
        modifiers = mainHand.build();
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "dc_m:textures/models/armor/super_wood_layer_1.png";
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

    public static class Helmet extends DCSuperArmor {
        public Helmet() {
            super(Type.HELMET);
        }

        @Override
        public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
            return pEquipmentSlot == EquipmentSlot.HEAD ? modifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
        }

        @Override
        public void onArmorTick(ItemStack stack, Level level, Player player) {
            super.onArmorTick(stack, level, player);
            player.addEffect(EffectHelper.addEffect(MobEffects.NIGHT_VISION));
            player.addEffect(EffectHelper.addEffect(MobEffects.HERO_OF_THE_VILLAGE));
            player.addEffect(EffectHelper.addEffect(MobEffects.HEAL));
        }

        @Override
        public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity) {
            return true;
        }

        @Override
        public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
            list.add(Component.translatable("tool_tip.dc_mods.has_helmet"));
            super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
        }
    }

    public static class Chestplate extends DCSuperArmor {
        public Chestplate() {
            super(Type.CHESTPLATE);
        }

        @Override
        public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
            return pEquipmentSlot == EquipmentSlot.CHEST ? modifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
        }

        @Override
        public void onArmorTick(ItemStack stack, Level level, Player player) {
            super.onArmorTick(stack, level, player);
            player.addEffect(EffectHelper.addEffect(MobEffects.DAMAGE_BOOST));
            player.addEffect(EffectHelper.addEffect(DCEffects.Heal.get(), 40, 2));
        }


        @Override
        public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
            list.add(Component.translatable("tooltip.dc_m.has_chestplate"));
            super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
        }
    }

    public static class Leggings extends DCSuperArmor  {
        public Leggings() {
            super(Type.LEGGINGS);
        }

        @Override
        public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
            return pEquipmentSlot == EquipmentSlot.LEGS ? modifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
        }

        @Override
        public void onArmorTick(ItemStack stack, Level level, Player player) {
            super.onArmorTick(stack, level, player);
            player.addEffect(EffectHelper.addEffect(MobEffects.MOVEMENT_SPEED));
            player.addEffect(EffectHelper.addEffect(MobEffects.JUMP));
        }

        @Override
        public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
            list.add(Component.translatable("tooltip.dc_m.leggings"));
            super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
        }


        @Override
        public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "dc_m:textures/models/armor/super_wood_layer_2.png";
        }
    }

    public static class Boots extends DCSuperArmor {
        public Boots() {
            super(Type.BOOTS);
        }

        @Override
        public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
            return pEquipmentSlot == EquipmentSlot.FEET ? modifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
        }

        @Override
        public void onArmorTick(ItemStack stack, Level level, Player player) {
            super.onArmorTick(stack, level, player);
            player.addEffect(EffectHelper.addEffect(MobEffects.LUCK));
            player.addEffect(EffectHelper.addEffect(DCEffects.Speed.get()));
        }

        @Override
        public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("tooltip.dc_m.has_boots"));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    }
    //public static class Helmet extends OlivineArmor {
    //        public Helmet() {
    //            super(Type.HELMET, new Properties());
    //        }
    //
    //        @Override
    //        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
    //            return "trom:textures/models/armor/olivine_layer_1.png";
    //        }
    //    }
    //
    //    public static class Chestplate extends OlivineArmor {
    //        public Chestplate() {
    //            super(Type.CHESTPLATE, new Properties());
    //        }
    //
    //        @Override
    //        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
    //            return "trom:textures/models/armor/olivine_layer_1.png";
    //        }
    //
    //
    //    }
    //
    //    public static class Leggings extends OlivineArmor {
    //        public Leggings() {
    //            super(Type.LEGGINGS, new Properties());
    //        }
    //
    //        @Override
    //        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
    //            return "trom:textures/models/armor/olivine_layer_2.png";
    //        }
    //    }
    //
    //    public static class Boots extends OlivineArmor {
    //        public Boots() {
    //            super(Type.BOOTS, new Properties());
    //        }
    //
    //        @Override
    //        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
    //            return "trom:textures/models/armor/olivine_layer_1.png";
    //        }
    //    }
}
