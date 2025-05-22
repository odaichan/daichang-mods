package net.daichang.dcmods.commands;

import net.daichang.dcmods.inits.DCEntities;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.commands.arguments.selector.EntitySelectorParser;
import net.minecraft.network.chat.Component;
import net.minecraftforge.common.command.IEntitySelectorType;

public class DCArgument implements IEntitySelectorType {

    @Override
    public EntitySelector build(EntitySelectorParser parser) {
        parser.limitToType(DCEntities.ELAINA.get());
        parser.setIncludesEntities(true);
        parser.setOrder(EntitySelectorParser.ORDER_NEAREST);
        return parser.getSelector();
    }

    @Override
    public Component getSuggestionTooltip() {
        return null;
    }
}
