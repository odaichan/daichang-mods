package net.daichang.dcmods.common.item.tools.creative;

import net.daichang.dcmods.client.font.DCEntityFont;
import net.daichang.dcmods.utils.lists.items.CreativeItemList;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class DCBatItem extends Item {
    public DCBatItem() {
        super(new Properties());
        CreativeItemList.addItem(this);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        p_41423_.add(Component.translatable("tooltip.dc_mods.bat"));
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Font getFont(ItemStack stack, FontContext context) {
                return DCEntityFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }
}
