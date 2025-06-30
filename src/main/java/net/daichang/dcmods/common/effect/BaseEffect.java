package net.daichang.dcmods.common.effect;

import net.daichang.dcmods.utils.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.client.extensions.common.IClientMobEffectExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BaseEffect extends MobEffect {
    public BaseEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void initializeClient(Consumer<IClientMobEffectExtensions> consumer) {
        consumer.accept(new IClientMobEffectExtensions() {
            @Override
            public boolean isVisibleInInventory(MobEffectInstance instance) {
                return IClientMobEffectExtensions.super.isVisibleInInventory(instance);
            }

            @Override
            public boolean renderInventoryText(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
                return IClientMobEffectExtensions.super.renderInventoryText(instance, screen, guiGraphics, x, y, blitOffset);
            }

            @Override
            public boolean renderInventoryIcon(MobEffectInstance instance, EffectRenderingInventoryScreen<?> screen, GuiGraphics guiGraphics, int x, int y, int blitOffset) {
                return false;
            }
        });
        super.initializeClient(consumer);
    }

    @Override
    public @NotNull Component getDisplayName() {
        MutableComponent all = Component.literal("[DC MOD]").withStyle(ChatFormatting.AQUA);
        MutableComponent name = Component.translatable(super.getDescriptionId());
        all.append(TextUtils.rainbow(name));
        return all;
    }

    @Override
    public double getAttributeModifierValue(int pAmplifier, AttributeModifier pModifier) {
        return super.getAttributeModifierValue(pAmplifier, pModifier);
    }
}
