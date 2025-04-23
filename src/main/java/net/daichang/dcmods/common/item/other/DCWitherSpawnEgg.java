package net.daichang.dcmods.common.item.other;

import net.daichang.dcmods.client.font.DCEntityFont;
import net.daichang.dcmods.inits.DCEntities;
import net.daichang.dcmods.utils.lists.items.CreativeItemList;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class DCWitherSpawnEgg extends ForgeSpawnEggItem {
    public DCWitherSpawnEgg() {
        super(DCEntities.DC_WITHER , Color.WHITE.getRGB(), Color.CYAN.getRGB(), new Properties());
        CreativeItemList.addItem(this);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.translatable("tooltip.dc_mods.spawn_mods_egg"));
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Font getFont(ItemStack stack, FontContext context) {
                return DCEntityFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }
}
