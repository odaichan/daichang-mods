package net.daichang.dcmods.common.item.tools;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.daichang.dcmods.client.font.DCItemFont;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.helpers.MathHelper;
import net.daichang.dcmods.utils.lists.items.CanSwordBlockItem;
import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
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
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;

public class DCSuperSwordItem extends ISwordItem {

    public Multimap<Attribute, AttributeModifier> defaultModifiers;

    public DCSuperSwordItem(Tier p_43269_, int pAttackDamageModifier, float pAttackSpeedModifier,final float super_damage, Properties p_43272_) {
        super(p_43269_, pAttackDamageModifier, pAttackSpeedModifier, p_43272_);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", this.attackDamage, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", pAttackSpeedModifier, AttributeModifier.Operation.ADDITION));
        builder.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", super_damage, AttributeModifier.Operation.ADDITION));
        builder.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(UUID.randomUUID(), "Item modifier", 7, AttributeModifier.Operation.ADDITION));
        defaultModifiers = builder.build();
        SuperItemList.addItem(this);
        CanSwordBlockItem.addItem(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        CompoundTag comTag = stack.getTag();
        if (comTag != null) {
            if (!comTag.contains("dc_attking")) comTag.putInt("dc_attking", 0);
        }
        return super.getAttributeModifiers(equipmentSlot, stack);
    }

    @Override
    public @NotNull Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot == EquipmentSlot.MAINHAND ? this.defaultModifiers : super.getDefaultAttributeModifiers(pEquipmentSlot);
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
    public void onUseTick(Level p_41428_, LivingEntity living, ItemStack p_41430_, int p_41431_) {
        living.addEffect(EffectHelper.addEffect(MobEffects.REGENERATION, 20, true));
        living.addEffect(EffectHelper.addEffect(MobEffects.DAMAGE_RESISTANCE, 20, 3,true));
        living.heal(5.5F);
        EntityHelper.forceHeal(living, 5.5F);
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
        list.add(Component.translatable("tooltip.dc_mods.setting_max_health"));
        if (comTag.getInt("dc_attking") >= 10000) list.add(Component.translatable("tooltip.dc_mods.is_strong"));
        else list.add(Component.translatable("tooltip.dc_mods.strong"));
        super.appendHoverText(stack, p_41422_, list, p_41424_);
    }

    @Override
    public boolean isDamaged(ItemStack stack) {
        return stack.getTag().getInt("dc_attking") < 10000 || super.isDamaged(stack);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, @NotNull LivingEntity living) {
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
        return stack.getTag().getInt("dc_attking") >= 10000;
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
