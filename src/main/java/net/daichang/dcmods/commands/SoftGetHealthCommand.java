package net.daichang.dcmods.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.daichang.dcmods.utils.Utils;
import net.daichang.dcmods.utils.helpers.DataHelper;
import net.daichang.dcmods.utils.lists.GetHealthList;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

public class SoftGetHealthCommand {
    public static ArgumentBuilder register(){
        return Commands.literal("softGetHealth").executes(cs ->{
            Entity entity = cs.getSource().getEntity();
            killed(entity);
            return 0;
        }).then(Commands.argument("entity", EntityArgument.entities()).executes(cs -> {
                    for (Entity entity : EntityArgument.getEntities(cs, "entity")) killed(entity);
            return 0;
        }));
    }

    public static void killed(Entity entity) {
        entity.setPose(Pose.DYING);
        if (entity instanceof LivingEntity living && !(living instanceof Player)) {
            living.setHealth(0.0F);
            Utils.Override_DATA_HEALTH_ID(living, 0.0F);
            GetHealthList.addHealth(living);
        }
        if (entity instanceof Player player) {
            DataHelper.setHealthDelta(player, Float.NEGATIVE_INFINITY);
            Utils.Override_DATA_HEALTH_ID(player, Float.NEGATIVE_INFINITY);
        }
    }
}
