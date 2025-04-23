package net.daichang.dcmods.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import net.daichang.dcmods.utils.helpers.FileHelper;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.entity.Entity;

public class DCDefPlayer {
    public static ArgumentBuilder register(){
        return Commands.literal("dc_remove_player").executes(cs ->{
            Entity entity = cs.getSource().getEntity();
            FileHelper.defaultWriteYouItem(entity);
            return 0;
        }).then(Commands.argument("entity", EntityArgument.entities()).executes(cs -> {
            for (Entity entity : EntityArgument.getEntities(cs, "entity")) {
                FileHelper.defaultWriteYouItem(entity);
            }
            return 0;
        }));
    }
}
