package net.daichang.dcmods.common.item.other.flowers;

import net.daichang.dcmods.inits.DCBlocks;
import net.daichang.dcmods.common.item.other.DCBlockItem;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RedSpiderLilyBlockItem extends DCBlockItem {
    public RedSpiderLilyBlockItem() {
        super(DCBlocks.RED_SPIDER_LILY.get());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_40572_, @Nullable Level p_40573_, List<Component> list, @NotNull TooltipFlag p_40575_) {
        list.add(Component.translatable("tooltip.dc_mods.flower_1"));
        list.add(Component.translatable("tooltip.dc_mods.flower_2"));
        list.add(Component.translatable("tooltip.dc_mods.flower_3"));
        list.add(Component.translatable("tooltip.dc_mods.flower_4"));
        super.appendHoverText(p_40572_, p_40573_, list, p_40575_);
    }
}
