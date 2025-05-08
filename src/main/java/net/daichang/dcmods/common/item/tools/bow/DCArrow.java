package net.daichang.dcmods.common.item.tools.bow;

import net.daichang.dcmods.client.font.DCItemFont;
import net.daichang.dcmods.common.entities.projectile.DCSuperArrow;
import net.daichang.dcmods.inits.DCEntities;
import net.daichang.dcmods.utils.lists.items.SuperItemList;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class DCArrow extends ArrowItem {
    public DCArrow() {
        super(new Item.Properties().fireResistant());
        SuperItemList.addItem(this);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Font getFont(ItemStack stack, FontContext context) {
                return DCItemFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }

    @Override
    public @NotNull AbstractArrow createArrow(Level p_40513_, ItemStack p_40514_, LivingEntity p_40515_) {
        return new DCSuperArrow(DCEntities.DC_SUPER_ARROW.get(), p_40513_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.translatable("tooltip.dc_mods.bow_1"));
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }
}
