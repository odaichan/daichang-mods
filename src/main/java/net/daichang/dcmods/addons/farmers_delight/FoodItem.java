package net.daichang.dcmods.addons.farmers_delight;

import net.daichang.dcmods.utils.TextUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FoodItem extends Item {
    public FoodItem(int statckto, FoodProperties properties) {
        super(new Properties().stacksTo(statckto).food(properties));
    }

    @Override
    public Component getName(ItemStack pStack) {
        return TextUtils.rainbow(super.getName(pStack));
    }
}
