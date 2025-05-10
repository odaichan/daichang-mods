package net.daichang.dcmods.common.item.crafts;

import net.daichang.dcmods.client.font.DCOceanItemFont;
import net.daichang.dcmods.utils.helpers.EffectHelper;
import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class HeartOfTheOcean extends Item {
    public HeartOfTheOcean() {
        super(new Properties().stacksTo(64).rarity(Rarity.EPIC).fireResistant().durability(8));
        SuperItemList.addItem(this);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tool_tip.dc_mods.ocean"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack stack = this.getDefaultInstance();
        stack.setDamageValue(stack.getDamageValue() + 1);
        pPlayer.addEffect(EffectHelper.addEffect(MobEffects.FIRE_RESISTANCE, 100, 5));
        pPlayer.addEffect(EffectHelper.addEffect(MobEffects.REGENERATION, 100, 3));
        pPlayer.addEffect(EffectHelper.addEffect(MobEffects.ABSORPTION, 100, 4));
        pPlayer.addEffect(EffectHelper.addEffect(MobEffects.CONDUIT_POWER, 100 ,2));
        Minecraft.getInstance().gameRenderer.displayItemActivation(stack);
        pPlayer.playSound(SoundEvents.TOTEM_USE);
        return super.use(pLevel, pPlayer, pUsedHand);
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
}
