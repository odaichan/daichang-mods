package net.daichang.dcmods.common.item.disc;

import net.daichang.dcmods.client.font.DCItemFont;
import net.daichang.dcmods.inits.DCSounds;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class DCBossFightDisc extends RecordItem {
    public DCBossFightDisc() {
        super(0, DCSounds.BOSS_FIGHT.get(), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), 2440);
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
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> li, TooltipFlag pFlag) {
        li.add(Component.literal(" - KARUT Blue Archive Original Soundtrack Vol.1 ~ Longing for the memorable days~"));
        super.appendHoverText(pStack, pLevel, li, pFlag);
    }
}
