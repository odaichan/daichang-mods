package net.daichang.dcmods.common.item.tools.creative;

import net.daichang.dcmods.client.font.DCItemFont;
import net.daichang.dcmods.utils.lists.items.CreativeItemList;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityInLevelCallback;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class DCCraft extends Item {
    public DCCraft() {
        super(new Properties().fireResistant().stacksTo(1).rarity(Rarity.RARE));
        CreativeItemList.addItem(this);
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
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        Entity.RemovalReason reason = Entity.RemovalReason.KILLED;
        entity.remove(reason);
        entity.setRemoved(reason);
        entity.setLevelCallback(EntityInLevelCallback.NULL);
        entity.onRemovedFromWorld();
        entity.onClientRemoval();
        return super.onLeftClickEntity(stack, player, entity);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_41395_, LivingEntity entity, LivingEntity p_41397_) {
        Entity.RemovalReason reason = Entity.RemovalReason.KILLED;
        entity.remove(reason);
        entity.setRemoved(reason);
        entity.setLevelCallback(EntityInLevelCallback.NULL);
        entity.onRemovedFromWorld();
        entity.onClientRemoval();
        return super.hurtEnemy(p_41395_, entity, p_41397_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.translatable("tooltip.dc_mods.dc_craft"));
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
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
