package net.daichang.dcmods.addons.curios.items;

import net.daichang.dcmods.utils.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class SuperWoodAmulet extends Item implements ICurioItem {
    public SuperWoodAmulet() {
        super(new Properties().rarity(Rarity.EPIC));
    }


    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        ICurioItem.super.curioTick(slotContext, stack);
    }

    @Override
    public void curioTick(String identifier, int index, LivingEntity livingEntity, ItemStack stack) {
        ICurioItem.super.curioTick(identifier, index, livingEntity, stack);
        if (livingEntity instanceof Player player) {
            for (ItemStack isRepair : player.getInventory().items) {
                isRepair.setDamageValue(0);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> list, TooltipFlag pIsAdvanced) {
        list.add(TextUtils.rainbow(Component.translatable("tool_tip.dc_mods.repier_item")));
        super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
    }
}
