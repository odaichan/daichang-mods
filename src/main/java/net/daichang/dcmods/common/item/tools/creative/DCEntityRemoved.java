package net.daichang.dcmods.common.item.tools.creative;

import net.daichang.dcmods.client.font.DCItemFont;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.lists.items.CreativeItemList;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
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
import java.util.Random;
import java.util.function.Consumer;

public class DCEntityRemoved extends Item {
    public DCEntityRemoved() {
        super(new Properties().fireResistant().stacksTo(1));
        CreativeItemList.addItem(this);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level p_41432_, Player p_41433_, InteractionHand p_41434_) {
        Utils.killLevelEntity(p_41432_);
        return super.use(p_41432_, p_41433_, p_41434_);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        Utils.superKillEntity(entity);
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_41395_, LivingEntity target, LivingEntity p_41397_) {
        Utils.superKillEntity(target);
        return super.hurtEnemy(p_41395_, target, p_41397_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, @NotNull List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.translatable("tooltip.dc_mods.dc_warn"));
        list.add(Component.translatable("tooltip.dc_mods.remove_warn"));
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
        return new Random(Util.getMillis()).nextInt(2, 13);
    }

    @Override
    public int getBarColor(@NotNull ItemStack p_150901_) {
        return new Random(Util.getMillis()).nextInt();
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
