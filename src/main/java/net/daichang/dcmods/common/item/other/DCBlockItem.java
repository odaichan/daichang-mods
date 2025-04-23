package net.daichang.dcmods.common.item.other;

import net.daichang.dcmods.client.font.DCBlockFont;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class DCBlockItem extends BlockItem {
    public DCBlockItem(Block p_40565_) {
        super(p_40565_, new Properties().fireResistant());
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
