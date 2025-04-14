package net.daichang.dcmods.item;

import net.daichang.dcmods.client.font.DCFont;
import net.daichang.dcmods.utils.TimeDataHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class TimeClock extends Item {
    public TimeClock() {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack p_41453_) {
        return true;
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack p_150899_) {
        return true;
    }

    @Override
    public int getBarWidth(@NotNull ItemStack p_150900_) {
        return 13;
    }

    @Override
    public int getBarColor(@NotNull ItemStack p_150901_) {
        return 0xFF87CEEB;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Font getFont(ItemStack stack, FontContext context) {
                return DCFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(level.isClientSide()) {
            TimeDataHandler handler = TimeDataHandler.get();
            if (handler.stopTime > 1) {
                handler.setTimeStopped(false);
                handler.stopTime = -1;
                player.displayClientMessage(Component.translatable("chat.dc_mods.end_time_stop").withStyle(ChatFormatting.GRAY), false);
            } else {
                handler.setTimeStopped(true);
                handler.stopTime = Integer.MAX_VALUE;
                player.displayClientMessage(Component.translatable("chat.dc_mods.setup_time_stop").withStyle(ChatFormatting.GOLD), false);
            }
        }
        return super.use(level, player, hand);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.translatable("tooltip.dc_mods.time_stop"));
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }
}
