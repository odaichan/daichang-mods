package net.daichang.dcmods.common.item;

import net.daichang.dcmods.client.font.DCEntityFont;
import net.daichang.dcmods.common.entities.DCBaseMonster;
import net.daichang.dcmods.utils.lists.items.CreativeItemList;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DCBaseSpawnEgg extends ForgeSpawnEggItem {
    Supplier<? extends EntityType<? extends DCBaseMonster>> type;
    public DCBaseSpawnEgg(Supplier<? extends EntityType<? extends DCBaseMonster>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props.stacksTo(16));
        CreativeItemList.addItem(this);
        this.type = type;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> list, @NotNull TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, list, pIsAdvanced);
        list.add(Component.literal(" "));
        list.add(Component.translatable("tool_tip.dc_m.spwan_entity"));
        if (pLevel != null) {
            DCBaseMonster boss = new DCBaseMonster(type.get(), pLevel);
            list.add(boss.getDCName());
        }
        list.add(Component.literal(type.get().getDescriptionId()));
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
