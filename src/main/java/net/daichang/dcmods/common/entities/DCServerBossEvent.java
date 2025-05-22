package net.daichang.dcmods.common.entities;

import net.minecraft.server.level.ServerBossEvent;

import java.util.UUID;

public class DCServerBossEvent extends ServerBossEvent {
    private UUID uuid;

    public DCServerBossEvent(BossEntity boss, BossBarColor pColor) {
        super(boss.getDisplayName(), pColor, BossBarOverlay.PROGRESS);
        setID(boss.getUUID());
    }

    public void setID(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getID() {
        return uuid;
    }
}
