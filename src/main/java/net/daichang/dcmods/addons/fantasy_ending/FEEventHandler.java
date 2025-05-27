package net.daichang.dcmods.addons.fantasy_ending;

import com.mega.uom.event.screen.MergeAnvilChangeEvent;
import net.daichang.dcmods.inits.DCItems;
import net.daichang.dcmods.utils.AnviUtil;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FEEventHandler {
    @SubscribeEvent
    public static void feEvent(MergeAnvilChangeEvent event) {
        AnviUtil.addAnviUpdate(event, Items.DIRT, Items.PLAYER_HEAD, DCItems.HEAL.get());
    }
}
