package net.daichang.dcmods.addons.slashblade;

import com.google.common.collect.Multimap;
import mods.flammpfeil.slashblade.capability.slashblade.ISlashBladeState;
import mods.flammpfeil.slashblade.capability.slashblade.SlashBladeState;
import mods.flammpfeil.slashblade.client.renderer.CarryType;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.item.SwordType;
import mods.flammpfeil.slashblade.registry.ComboStateRegistry;
import mods.flammpfeil.slashblade.registry.SlashArtsRegistry;
import mods.flammpfeil.slashblade.registry.combo.ComboState;
import net.daichang.dcmods.DCMod;
import net.daichang.dcmods.client.tool_tip.DCItemTip;
import net.daichang.dcmods.common.item.AttackCountItem;
import net.daichang.dcmods.common.item.DCTier;
import net.daichang.dcmods.common.item.UseCountItem;
import net.daichang.dcmods.inits.DCAttributes;
import net.daichang.dcmods.inits.DCEffects;
import net.daichang.dcmods.inits.DCEnch;
import net.daichang.dcmods.utils.EntityActuallyHurt;
import net.daichang.dcmods.utils.TextUtils;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.daichang.dcmods.utils.helpers.EntityHelper;
import net.daichang.dcmods.utils.helpers.MathHelper;
import net.daichang.dcmods.utils.lists.items.LightItemList;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class DaiChangSB extends ItemSlashBlade implements UseCountItem, AttackCountItem {
    public static final ResourceLocation texture = new ResourceLocation(DCMod.MOD_ID, "models/named/dc_sb.png");
    public static final ResourceLocation model = new ResourceLocation(DCMod.MOD_ID, "models/named/dc_sb.obj");
    public DaiChangSB() {
        super(DCTier.OCEAN_HEART, 45, 1024, new Properties().fireResistant().rarity(Rarity.EPIC));
        LightItemList.addItem(this);
    }

    @Override
    public int getBarColor(@NotNull ItemStack p_150901_) {
        return new Random(Util.getMillis()).nextInt();
    }

    @Override
    public int getBarWidth(@NotNull ItemStack pStack) {
        return 13;
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack pStack) {
        return true;
    }

    @Override
    public @NotNull ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(SBInits.DC_SB.get());
        init(stack);
        return stack;
    }

    @Override
    public @NotNull Item asItem() {
        return SBInits.DC_SB.get();
    }

    public static void init(ItemStack stack) {
        ISlashBladeState state = stack.getCapability(ItemSlashBlade.BLADESTATE).orElse(new SlashBladeState(stack));
        if (!stack.getOrCreateTag().getBoolean("SetDCState")) {
            List<TagKey<Item>> tagKeys = new ArrayList<>(stack.getItem().builtInRegistryHolder().tags().toList());
            stack.getItem().builtInRegistryHolder().bindTags(tagKeys);
            if (state.getKillCount() == 0)
                state.setKillCount(215375832);
            if (state.getProudSoulCount() == 0)
                state.setProudSoulCount(215375832);
            state.setDefaultBewitched(true);
            state.setTexture(texture);
            state.setEffectColor(new Color(186, 85, 211));
            state.addSpecialEffect(SBInits.DC_EDGE.getId());
            state.setModel(model);
            state.setBaseAttackModifier(18.5F);
            state.setMaxDamage(520);
            state.setSealed(false);
            state.setCarryType(CarryType.KATANA);
            stack.getOrCreateTag().put("bladeState", state.serializeNBT());
            stack.getOrCreateTag().putBoolean("SetDCState", true);
            stack.enchant(Enchantments.SHARPNESS, 21);
            stack.enchant(Enchantments.POWER_ARROWS, 35);
            stack.enchant(Enchantments.FIRE_ASPECT, 2);
            stack.enchant(Enchantments.SMITE, 12);
            stack.enchant(DCEnch.SuperSharp.get(), 1);
            UseCountItem.setUseS(stack, 0);
            AttackCountItem.setCountS(stack, 0);
        }
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }

    @Override
    public void onCraftedBy(ItemStack pStack, Level pLevel, Player pPlayer) {
        super.onCraftedBy(pStack, pLevel, pPlayer);
        init(pStack);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        return super.onEntitySwing(stack, entity);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
        init(pStack);
    }

    public static void onDCSBHitEntity(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        ISlashBladeState state = pStack.getCapability(ItemSlashBlade.BLADESTATE).orElse(new SlashBladeState(pStack));
        int count = UseCountItem.getUseS(pStack);
        AttackCountItem.addCountS(pStack, 1);
        UseCountItem.addUseS(pStack, 1);
        if (AttackCountItem.getCountS(pStack) == 10) {
            pTarget.addEffect(EffectHelper.addEffect(DCEffects.Freeze.get(), 3, 1));
            pTarget.addEffect(EffectHelper.addEffect(DCEffects.Bloodshed.get(), 3, 1));
            state.setProudSoulCount(state.getProudSoulCount() + 30);
        }
        float damage = MathHelper.getRandomFloat(4.1F ,16.2F) + pTarget.getMaxHealth() * 0.001F + pTarget.getHealth() * 0.001F;
        if (count < 10) damage = damage + count;
//        if (killCount > 10000) damage = damage + MathHelper.getRandomFloat((float) killCount / 2 / 3 / 1.1F, killCount / 1.5F) * MathHelper.getRandomFloat(0.01F, 0.1F) + MathHelper.getRandomFloat(0.1F, 1.3F);
        else damage = damage + (float) count / 2 * 1.2F;
        if (pAttacker.getAttribute(Attributes.ATTACK_DAMAGE) != null) damage = damage + (float) pAttacker.getAttributeValue(Attributes.ATTACK_DAMAGE);
        if (pAttacker.getAttribute(DCAttributes.DC_SUPER_DAMAGE.get()) != null) damage = damage + (float) pAttacker.getAttributeValue(DCAttributes.DC_SUPER_DAMAGE.get());
        if (pAttacker.getAttribute(DCAttributes.OCEAN_DAMAGE.get()) != null) damage = damage + (float) pAttacker.getAttributeValue(DCAttributes.OCEAN_DAMAGE.get());
        if (pAttacker.getMaxHealth() > 10000) damage = damage + pTarget.getMaxHealth() * 0.005F;
        if (pAttacker.getArmorValue() > 0) damage = damage + MathHelper.getRandomFloat(0.6F, 6.3f) + pTarget.getMaxHealth() * 0.001F;
        DamageSource source;
        if (pAttacker instanceof Player player) source = player.damageSources().playerAttack(player);
        else if (pAttacker instanceof WitherBoss boss) source = boss.damageSources().wither();
        else source = pAttacker.damageSources().mobAttack(pAttacker);
        EntityActuallyHurt.getInstance(pTarget, pAttacker).actuallyHurt(source, damage);
    }

    @Override
    public void appendSlashArt(ItemStack stack, List<Component> tooltip, @NotNull ISlashBladeState s) {
        super.appendSlashArt(stack, tooltip, s);
        s.setSlashArtsKey(SlashArtsRegistry.JUDGEMENT_CUT.getId());
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
    public boolean isDamaged(ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
        DCItemTip.addAttackCount(list, pStack);
        list.add(Component.translatable("tool_tip.dc_m.ocean_tip_2"));
        list.add(Component.translatable("tool_tip.dc_m.ocean_tip_3"));
        list.add(Component.translatable("tool_tip.iaxe_item"));
        super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
    }

    @Override
    public void onUseTick(Level level, LivingEntity player, ItemStack stack, int count) {
        super.onUseTick(level, player, stack, count);
        EntityHelper.forceHeal(player, MathHelper.getRandomFloat(1.2F, 7.2F));
        if (!player.isDeadOrDying()) player.deathTime = 0;
        stack.getCapability(BLADESTATE).ifPresent((state) -> {
            (((IForgeRegistry) ComboStateRegistry.REGISTRY.get()).getValue(state.getComboSeq()) != null ? (ComboState) ((IForgeRegistry) ComboStateRegistry.REGISTRY.get()).getValue(state.getComboSeq()) : ComboStateRegistry.NONE.get()).holdAction(player);
            EnumSet<SwordType> swordType = SwordType.from(stack);
            if (!state.isBroken() && !state.isSealed() && swordType.contains(SwordType.ENCHANTED)) {
                if (!player.level().isClientSide()) {
                    int ticks = player.getTicksUsingItem();
                    int fullChargeTicks = state.getFullChargeTicks(player);
                    if (0 < ticks && ticks == fullChargeTicks / 2) {
                        Vec3 pos = player.getEyePosition(1.0F).add(player.getLookAngle());
                        ((ServerLevel) player.level()).sendParticles(ParticleTypes.PORTAL, pos.x, pos.y, pos.z, 7, 0.7, 0.7, 0.7, 0.02);
                    }
                }
            }
        });
    }

    @Override
    public Component getName(ItemStack pStack) {
        return TextUtils.rainbow(super.getName(pStack));
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        onDCSBHitEntity(pStack, pTarget, pAttacker);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack itemstack, Player playerIn, Entity entity) {
        if (entity instanceof LivingEntity living) EntityHelper.noHurtDuration(living);
        addUse(itemstack, 1);
        return super.onLeftClickEntity(itemstack, playerIn, entity);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        return super.getAttributeModifiers(slot, stack);
    }


//    @Override
//    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
//        Multimap<Attribute, AttributeModifier> modifierMulti = super.getDefaultAttributeModifiers(slot);
//        if (slot == EquipmentSlot.MAINHAND) {
//            modifierMulti.put(DCAttributes.DC_SUPER_DAMAGE.get(), new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon Modifier", 42F, AttributeModifier.Operation.ADDITION));
//            modifierMulti.put(DCAttributes.OCEAN_DAMAGE.get(), new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon Modifier", 42F, AttributeModifier.Operation.ADDITION));
//        }
//        return super.getDefaultAttributeModifiers(slot);
//    }
}
