package net.daichang.dcmods.event.dcevents;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.LogicalSide;

public class RenderEvent extends TickEvent {
    public final float renderTickTime;

    public RenderEvent(Phase phase, float renderTickTime) {
        super(Type.RENDER, LogicalSide.CLIENT, phase);
        this.renderTickTime = renderTickTime;
    }
}
