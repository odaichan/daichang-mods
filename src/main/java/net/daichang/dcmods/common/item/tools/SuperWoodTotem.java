package net.daichang.dcmods.common.item.tools;

import net.daichang.dcmods.common.item.BaseSuperItem;
import net.daichang.dcmods.utils.lists.items.SuperItemList;

public class SuperWoodTotem extends BaseSuperItem {
    public SuperWoodTotem() {
        super(new Properties().stacksTo(1).fireResistant().durability(1024));
        SuperItemList.addItem(this);
    }
}