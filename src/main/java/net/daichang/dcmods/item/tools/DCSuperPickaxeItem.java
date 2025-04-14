package net.daichang.dcmods.item.tools;

import com.google.common.collect.Multimap;
import net.daichang.dcmods.client.font.DCFont;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class DCSuperPickaxeItem extends PickaxeItem {
    public DCSuperPickaxeItem(Tier p_42961_, int p_42962_, float p_42963_, Properties p_42964_) {
        super(p_42961_, p_42962_, p_42963_, p_42964_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.translatable("tooltip.dc_mods.minecraft"));
        list.add(Component.translatable("tooltip.dc_mods.pickaxe_1"));
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (stack.getAllEnchantments().containsKey(Enchantments.BLOCK_FORTUNE)) stack.enchant(Enchantments.BLOCK_FORTUNE, 10);
        return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity target, LivingEntity player) {
        target.knockback(3.0,  Mth.sin(player.getYRot() * ((float) Math.PI / 180F)), -Mth.cos(player.getYRot() * ((float) Math.PI / 180F)));
        return super.hurtEnemy(p_40994_, target, player);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Font getFont(ItemStack stack, FontContext context) {
                return DCFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }
}
