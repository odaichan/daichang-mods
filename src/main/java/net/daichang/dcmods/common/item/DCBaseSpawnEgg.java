package net.daichang.dcmods.common.item;

import net.daichang.dcmods.client.font.DCEntityFont;
import net.minecraft.client.gui.Font;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class DCBaseSpawnEgg extends ForgeSpawnEggItem {
    public DCBaseSpawnEgg(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Font getFont(ItemStack stack, FontContext context) {
                return DCEntityFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }
}
