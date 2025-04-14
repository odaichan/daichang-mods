package net.daichang.dcmods.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.daichang.dcmods.client.font.DCFont;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.utils.EffectUtil;
import net.daichang.dcmods.utils.Utils;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

public class DCSuperSwordItem extends SwordItem {
    private final float dc_super_damage;

    public DCSuperSwordItem(Tier p_43269_, int p_43270_, float p_43271_, float super_damage, Properties p_43272_) {
        super(p_43269_, p_43270_, p_43271_, p_43272_);
        dc_super_damage = super_damage;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return super.use(level, player, hand);
    }

    @Override
    public int getUseDuration(ItemStack p_41454_) {
        return 72000;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack p_41452_) {
        return Utils.getUseAnim();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        CompoundTag comTag = stack.getTag();
        if (stack.getAllEnchantments().containsKey(Enchantments.MENDING)) stack.enchant(Enchantments.MENDING, 1);
        if (stack.getAllEnchantments().containsKey(Enchantments.UNBREAKING)) stack.enchant(Enchantments.UNBREAKING, 5);
        if (stack.getAllEnchantments().containsKey(Enchantments.SHARPNESS)) stack.enchant(Enchantments.SHARPNESS, 10);
        if (comTag != null && !comTag.contains("dc_attking")) comTag.putInt("dc_attking", 0);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        if (equipmentSlot == EquipmentSlot.MAINHAND) {
            builder.putAll(super.getAttributeModifiers(equipmentSlot, stack));
            builder.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", dc_super_damage, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(equipmentSlot, stack);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Font getFont(ItemStack stack, FontContext context) {
                return DCFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }

    @Override
    public void onUseTick(Level p_41428_, LivingEntity living, ItemStack p_41430_, int p_41431_) {
        living.addEffect(EffectUtil.addEffect(MobEffects.REGENERATION, 20, true));
        living.addEffect(EffectUtil.addEffect(MobEffects.DAMAGE_RESISTANCE, 20, 3,true));
        living.heal(0.5F);
        living.invulnerableTime = 1;
        super.onUseTick(p_41428_, living, p_41430_, p_41431_);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        CompoundTag comTag = stack.getTag();
        list.add(Component.literal(Component.translatable("tooltip.dc_mods.hurts").getString() + comTag.getInt("dc_attking")));
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
        if (comTag.getInt("dc_attking") >= 10000) list.add(Component.translatable("tooltip.dc_mods.is_strong"));
        else list.add(Component.translatable("tooltip.dc_mods.strong"));
        super.appendHoverText(stack, p_41422_, list, p_41424_);
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return stack.getTag().getInt("dc_attking") < 10000;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, @NotNull LivingEntity living) {
        if (living instanceof Player player) Utils.attackEntity(stack, target, player, dc_super_damage);
        return super.hurtEnemy(stack, target, living);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        if (entity instanceof LivingEntity living && player.level instanceof ServerLevel) Utils.attackEntity(stack, living, player, dc_super_damage);
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
        return stack.getTag().getInt("dc_attking") >= 10000;
    }
}
