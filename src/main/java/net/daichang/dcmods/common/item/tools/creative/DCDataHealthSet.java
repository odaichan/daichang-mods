package net.daichang.dcmods.common.item.tools.creative;

import net.daichang.dcmods.client.font.DCItemFont;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.daichang.dcmods.utils.lists.items.CreativeItemList;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class DCDataHealthSet extends Item {
    public DCDataHealthSet() {
        super(new Properties().fireResistant().stacksTo(1));
        CreativeItemList.addItem(this);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_41395_, LivingEntity target, LivingEntity p_41397_) {
        DataHelper.setIsDead(target, true);
        return super.hurtEnemy(p_41395_, target, p_41397_);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity target) {
        if (target instanceof LivingEntity living) DataHelper.setIsDead(living, true);
        return super.onLeftClickEntity(stack, player, target);
    }


    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.translatable("tooltip.dc_mods.data_set"));
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
                return DCItemFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }
}
