package net.daichang.dcmods.item.tools;

import net.daichang.dcmods.client.font.DCFont;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class DCShovelItem extends ShovelItem {
    public DCShovelItem(Tier p_43114_, float p_43115_, float p_43116_, Properties p_43117_) {
        super(p_43114_, p_43115_, p_43116_, p_43117_);
    }

    @Override
    public boolean hurtEnemy(ItemStack p_40994_, LivingEntity target, LivingEntity p_40996_) {
        target.setTicksFrozen(300);
        return super.hurtEnemy(p_40994_, target, p_40996_);
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> list, TooltipFlag p_41424_) {
        list.add(Component.translatable("tooltip.dc_mods.minecraft"));
        list.add(Component.translatable("tooltip.dc_mods.shovel"));
        super.appendHoverText(p_41421_, p_41422_, list, p_41424_);
    }

    @Override
    public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull Font getFont(ItemStack stack, FontContext context) {
                return DCFont.getFont();
            }
        });
        super.initializeClient(consumer);
    }
}
