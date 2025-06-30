package net.daichang.dcmods.common.enchantment;

import net.daichang.dcmods.utils.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.jetbrains.annotations.NotNull;

public class BaseEnch extends Enchantment {
    public BaseEnch(Rarity pRarity, EnchantmentCategory pCategory, EquipmentSlot[] pApplicableSlots) {
        super(pRarity, pCategory, pApplicableSlots);
    }

    public double getSnowDamageBounce(int level, ItemStack stack) {
        return 0.0D;
    }

    public double getOceanDamageBounce(int level, ItemStack stack) {
        return 0.0D;
    }

    @Override
    public @NotNull Component getFullname(int level) {
        MutableComponent all = Component.literal("[DaiChang]").withStyle(ChatFormatting.LIGHT_PURPLE);
        MutableComponent name = Component.translatable(getDescriptionId());
        if (this.isCurse())
            all.append(Component.literal("[CURES] ").withStyle(ChatFormatting.RED));
        else if (this.isAllowedOnBooks())
            all.append(Component.literal("[ALLOWED] ").withStyle(ChatFormatting.GOLD));
        else
            all.append(Component.literal("[NORMAL] ").withStyle(ChatFormatting.AQUA));
        all.append(TextUtils.rainbow(name));
        if (level != 1) {
            String levelText;
            levelText = String.valueOf(level);
            all.append(" ").append(Component.literal(levelText).withStyle(ChatFormatting.WHITE));
        }
        return all;
    }
}