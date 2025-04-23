package net.daichang.dcmods.common.item;

import net.daichang.dcmods.client.font.DCFont;
import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BaseSuperItem extends Item {
    public BaseSuperItem(Properties pProperties) {
        super(pProperties);
        SuperItemList.addItem(this);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Font getFont(ItemStack stack, FontContext context) {
                return DCFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }
}
