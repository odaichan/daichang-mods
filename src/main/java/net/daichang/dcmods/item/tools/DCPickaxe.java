package net.daichang.dcmods.item.tools;

import net.daichang.dcmods.client.font.DCFont;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
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

public class DCPickaxe extends Item {
    public DCPickaxe() {
        super(new Properties().stacksTo(1).rarity(Rarity.RARE));
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.translatable("tooltip.dc_mods.destroy_block"));
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
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
}
