package net.daichang.dcmods.common.item.other;

import net.daichang.dcmods.common.item.DCBaseSpawnEgg;
import net.daichang.dcmods.inits.DCEntities;
import net.daichang.dcmods.utils.lists.items.CreativeItemList;
import net.minecraft.world.item.Item;

import java.awt.*;

public class DCLoliSpawnEgg extends DCBaseSpawnEgg {
    public DCLoliSpawnEgg() {
        super(DCEntities.LOLI , Color.WHITE.getRGB(), Color.LIGHT_GRAY.getRGB(), new Item.Properties().fireResistant());
        CreativeItemList.addItem(this);
    }
}
