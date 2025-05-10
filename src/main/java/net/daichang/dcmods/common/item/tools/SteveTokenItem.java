package net.daichang.dcmods.common.item.tools;

import net.daichang.dcmods.common.entities.boss.DCSteve;
import net.daichang.dcmods.common.item.BaseSuperItem;
import net.daichang.dcmods.inits.DCEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SteveTokenItem extends BaseSuperItem {
    public SteveTokenItem() {
        super(new Properties().rarity(Rarity.EPIC).stacksTo(1).fireResistant());
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("tool_tip.dc_m.spawnd_entity"));
        pTooltipComponents.add(Component.literal("SpawnEntity"));
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return super.isFoil(pStack);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        DCSteve steve = new DCSteve(DCEntities.DC_STEVE.get(), pLevel);
        steve.setPos(pPlayer.getX(), pPlayer.getY(), pPlayer.getZ());
        pLevel.addFreshEntity(steve);
        pPlayer.cooldowns.addCooldown(this, 1200);
        pPlayer.playSound(SoundEvents.TOTEM_USE);
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
