package net.daichang.dcmods.common.enchantment;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class BaseEnch extends Enchantment {
    public BaseEnch(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    public double getSnowDamageBounce(int level, MobType type, ItemStack stack) {
        return 0.0D;
    }

    public double getOceanDamageBounce(int level, MobType type, ItemStack stack) {
        return 0.0D;
    }

    @Override
    public @NotNull Component getFullname(int level) {
        MutableComponent name = Component.translatable(getDescriptionId());
        if (this.isCurse()) name.withStyle(ChatFormatting.RED);
        else if (this.isAllowedOnBooks()) name.withStyle(ChatFormatting.GOLD);
        else name.withStyle(ChatFormatting.GRAY);
        if (level != 1) {
            String levelText;
            levelText = String.valueOf(level);
            name.append(" ").append(Component.literal(levelText));
        }
        return name;
    }
}
