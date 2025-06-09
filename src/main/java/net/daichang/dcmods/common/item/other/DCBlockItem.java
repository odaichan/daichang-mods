package net.daichang.dcmods.common.item.other;

import net.daichang.dcmods.client.font.DCBlockFont;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class DCBlockItem extends BlockItem {
    Block block;
    public DCBlockItem(Block p_40565_) {
        super(p_40565_, new Properties().fireResistant());
        block = p_40565_;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);

    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Font getFont(ItemStack stack, FontContext context) {
                return DCBlockFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }
}
