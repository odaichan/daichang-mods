package net.daichang.dcmods.library;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface DCItemIib {
    default void onKillEntity(int slot, LivingEntity killEntity, Player player) {}
}
