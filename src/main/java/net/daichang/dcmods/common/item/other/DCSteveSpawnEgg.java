package net.daichang.dcmods.common.item.other;

import net.daichang.dcmods.common.item.DCBaseSpawnEgg;
import net.daichang.dcmods.inits.DCEntities;
import net.daichang.dcmods.utils.lists.items.CreativeItemList;
import net.minecraft.world.item.Item;

import java.awt.*;

public class DCSteveSpawnEgg extends DCBaseSpawnEgg {
    public DCSteveSpawnEgg() {
        super(DCEntities.DC_STEVE , Color.WHITE.getRGB(), Color.CYAN.getRGB(), new Item.Properties());
        CreativeItemList.addItem(this);
    }
}
