package net.daichang.dcmods.common.item.other;

import net.daichang.dcmods.common.item.DCBaseSpawnEgg;
import net.daichang.dcmods.inits.DCEntities;
import net.daichang.dcmods.utils.lists.items.CreativeItemList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

public class DCWitherSpawnEgg extends DCBaseSpawnEgg {
    public DCWitherSpawnEgg() {
        super(DCEntities.ELAINA, Color.WHITE.getRGB(), Color.CYAN.getRGB(), new Properties());
        CreativeItemList.addItem(this);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack p_41421_, @Nullable Level p_41422_, @NotNull List<Component> list, @NotNull TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }
}
