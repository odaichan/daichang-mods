package net.daichang.dcmods.common.item.armors;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.mega.uom.attribute.ModAttributes;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.daichang.dcmods.client.font.DCOceanItemFont;
import net.daichang.dcmods.common.item.DCTier;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.inits.DCItems;
import net.daichang.dcmods.utils.ModUtil;
import net.daichang.dcmods.utils.TextUtils;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class DCSuperArmor extends ArmorItem {
    public Multimap<Attribute, AttributeModifier> modifiers;
    private static final String NAME = "Armor Modifier";
    private static final ArmorMaterial pMaterial = createArmorMaterial("super_wood", DCTier.OCEAN_HEART.getLevel(), Ingredient.of(DCItems.HEART_OF_THE_OCEAN.get()));

    public DCSuperArmor(Type pType) {
        super(pMaterial, pType, new Properties().fireResistant().rarity(Rarity.EPIC).durability(0));
        ImmutableMultimap.Builder<Attribute, AttributeModifier> mainHand = ImmutableMultimap.builder();
        mainHand.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), NAME, 2.4D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), NAME, 0.1D, AttributeModifier.Operation.ADDITION));
        mainHand.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(UUID.randomUUID(), NAME, 0.1D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.ARMOR, new AttributeModifier(UUID.randomUUID(), NAME, 7.3D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), NAME, 4.3D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), NAME, 7.8D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.MAX_HEALTH, new AttributeModifier(UUID.randomUUID(), NAME, 0.052D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        mainHand.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), NAME, 4.3D, AttributeModifier.Operation.ADDITION));
        mainHand.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), NAME, 0.1D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        mainHand.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.randomUUID(), NAME, 0.23D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        mainHand.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), NAME, 5.2D, AttributeModifier.Operation.ADDITION));
        mainHand.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(UUID.randomUUID(), NAME, 0.23D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        mainHand.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), NAME, 0.52D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        mainHand.put(DCAttributes.DC_DEFENSE.get(), new AttributeModifier(UUID.randomUUID(), NAME, 5.2D, AttributeModifier.Operation.ADDITION));
        mainHand.put(DCAttributes.DC_DEFENSE.get(), new AttributeModifier(UUID.randomUUID(), NAME, 0.52D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        if (ModUtil.isIronSpellbokksLoad()) {
            mainHand.put(AttributeRegistry.MAX_MANA.get(), new AttributeModifier(UUID.randomUUID(), NAME, 520.13D, AttributeModifier.Operation.ADDITION));
            mainHand.put(AttributeRegistry.MAX_MANA.get(), new AttributeModifier(UUID.randomUUID(), NAME, 0.52D, AttributeModifier.Operation.MULTIPLY_TOTAL));
            mainHand.put(AttributeRegistry.MANA_REGEN.get(), new AttributeModifier(UUID.randomUUID(), NAME, 30.4D, AttributeModifier.Operation.ADDITION));
            mainHand.put(AttributeRegistry.MANA_REGEN.get(), new AttributeModifier(UUID.randomUUID(), NAME, 1.14D, AttributeModifier.Operation.MULTIPLY_TOTAL));
            mainHand.put(AttributeRegistry.COOLDOWN_REDUCTION.get(), new AttributeModifier(UUID.randomUUID(), NAME, 0.14D, AttributeModifier.Operation.MULTIPLY_TOTAL));
            mainHand.put(AttributeRegistry.SPELL_POWER.get(), new AttributeModifier(UUID.randomUUID(), NAME, 0.74D, AttributeModifier.Operation.MULTIPLY_TOTAL));
            mainHand.put(AttributeRegistry.SPELL_RESIST.get(), new AttributeModifier(UUID.randomUUID(), NAME, 0.20D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        if (ModUtil.isFELoad()) {
            mainHand.put(ModAttributes.FANTASY_ENDING_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), NAME, 0.52D, AttributeModifier.Operation.MULTIPLY_TOTAL));
            mainHand.put(ModAttributes.FANTASY_ENDING_DAMAGE_RESISTANCE.get(), new AttributeModifier(UUID.randomUUID(), NAME, 0.52D, AttributeModifier.Operation.MULTIPLY_TOTAL));
            mainHand.put(ModAttributes.FANTASY_SPELL_POWER.get(), new AttributeModifier(UUID.randomUUID(), NAME, 0.52D, AttributeModifier.Operation.MULTIPLY_TOTAL));
            mainHand.put(ModAttributes.EVASION.get(), new AttributeModifier(UUID.randomUUID(), NAME, 0.20D, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        modifiers = mainHand.build();
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return false;
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        super.onArmorTick(stack, level, player);
        if (player.tickCount % 40 == 0) DataHelper.addHealthDelta(player, 1.0F);
        if (player.isUnderWater()) {
            player.addEffect(EffectHelper.addEffect(MobEffects.CONDUIT_POWER, 20, 2));
            player.addEffect(EffectHelper.addEffect(MobEffects.DAMAGE_BOOST, 20, 2));
        }
        if (hasAllArmor(player) && player.isAlive()) {
            player.getFoodData().setFoodLevel(20);
            if (player.getHealth() < player.getMaxHealth() * 0.2F) {
                if (player.experienceLevel > 50) {
                    player.experienceLevel = player.experienceLevel - 5;
                    EntityHelper.forceHeal(player, player.getMaxHealth() * 0.2F);
                    DataHelper.restHealthDelta(player);
                    player.displayClientMessage(TextUtils.rainbow(Component.translatable("chat.dc_m.save_player")), true);
                }
            }
            player.addEffect(EffectHelper.addEffect(MobEffects.FIRE_RESISTANCE));
            Utils.addAdvancementToPlayer(player, "dc_m:get_all_ocean_armor");
            if (player.isUnderWater() || level.isRaining()) {
                Utils.addAdvancementToPlayer(player, "dc_m:children_of_the_sea");
                player.clearFire();
                EntityHelper.forceHeal(player, 200.0F);
                DataHelper.restHealthDelta(player);
                player.addEffect(EffectHelper.addEffect(MobEffects.DAMAGE_BOOST, 1, 255, true));
                player.addEffect(EffectHelper.addEffect(MobEffects.MOVEMENT_SPEED, 1, 2, true));
                player.setTicksFrozen(0);
            }
        }
    }

    public static boolean hasAllArmor(Player player) {
        Inventory i = player.getInventory();
        return i.getArmor(3).is(DCItems.WOOD_HELMET.get()) && i.getArmor(2).is(DCItems.WOOD_CHESTPLATE.get()) && i.getArmor(1).is(DCItems.WOOD_LEGGINGS.get()) && i.getArmor(0).is(DCItems.WOOD_BOOTS.get());
    }

    public static int power(Player player) {
        int var = 0;
        Inventory inventory = player.getInventory();
        if (inventory.getArmor(3).is(DCItems.WOOD_HELMET.get())) var = var + 3;
        if (inventory.getArmor(2).is(DCItems.WOOD_CHESTPLATE.get())) var = var + 5;
        if (inventory.getArmor(1).is(DCItems.WOOD_LEGGINGS.get())) var = var + 4;
        if (inventory.getArmor(0).is(DCItems.WOOD_BOOTS.get())) var = var + 2;
        return var;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> list, @NotNull TooltipFlag pIsAdvanced) {
        list.add(Component.translatable("tool_tip.dc_mods.default"));
        list.add(Component.translatable("tool_tip.dc_mods.when_worn"));
        super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
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
                return SoundEvents.ARMOR_EQUIP_NETHERITE;
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

    @Override
    public boolean makesPiglinsNeutral(ItemStack stack, LivingEntity wearer) {
        return true;
    }

    @Override
    public boolean isFireResistant() {
        return true;
    }

    @Override
    public int getDamage(ItemStack stack) {
        return 0;
    }

    @Override
    public void setDamage(ItemStack stack, int damage) {
        super.setDamage(stack, 0);
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
                return DCOceanItemFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }

    public static class Helmet extends DCSuperArmor {
        public Helmet() {
            super(Type.HELMET);
            SuperItemList.addItem(this);
        }

        @Override
        public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
            return pEquipmentSlot == EquipmentSlot.HEAD ? modifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
        }

        @Override
        public void onArmorTick(ItemStack stack, Level level, Player player) {
            super.onArmorTick(stack, level, player);
            player.addEffect(EffectHelper.addEffect(MobEffects.NIGHT_VISION));
            player.addEffect(EffectHelper.addEffect(MobEffects.HERO_OF_THE_VILLAGE));
            player.addEffect(EffectHelper.addEffect(MobEffects.HEAL));
            player.removeEffect(MobEffects.DARKNESS);
            if (player.isUnderWater()) player.setAirSupply(300);
        }

        @Override
        public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity) {
            return true;
        }

        @Override
        public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> list, @NotNull TooltipFlag pIsAdvanced) {
            super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
            list.add(Component.translatable("tool_tip.dc_mods.has_helmet"));
            list.add(Component.translatable("tool_tip.dc_mods.has_helmet_2"));
        }
    }

    public static class Chestplate extends DCSuperArmor {
        public Chestplate() {
            super(Type.CHESTPLATE);
            SuperItemList.addItem(this);
        }

        @Override
        public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(@NotNull EquipmentSlot pEquipmentSlot) {
            return pEquipmentSlot == EquipmentSlot.CHEST ? modifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
        }

        @Override
        public void onArmorTick(ItemStack stack, Level level, Player player) {
            super.onArmorTick(stack, level, player);
            player.addEffect(EffectHelper.addEffect(MobEffects.DAMAGE_BOOST));
            player.addEffect(EffectHelper.addEffect(DCEffects.Heal.get(), 40, 1));
            if (player.isInLava()) player.clearFire();
        }


        @Override
        public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> list, @NotNull TooltipFlag pIsAdvanced) {
            super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
            list.add(Component.translatable("tooltip.dc_m.has_chestplate"));
        }
    }

    public static class Leggings extends DCSuperArmor  {
        public Leggings() {
            super(Type.LEGGINGS);
            SuperItemList.addItem(this);
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
        public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> list, @NotNull TooltipFlag pIsAdvanced) {
            super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
            list.add(Component.translatable("tooltip.dc_m.leggings"));
        }


        @Override
        public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "dc_m:textures/models/armor/super_wood_layer_2.png";
        }
    }

    public static class Boots extends DCSuperArmor {
        public Boots() {
            super(Type.BOOTS);
            SuperItemList.addItem(this);
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
            player.resetFallDistance();
        }

        @Override
        public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
            pTooltipComponents.add(Component.translatable("tooltip.dc_m.has_boots"));
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
