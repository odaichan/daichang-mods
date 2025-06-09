package net.daichang.dcmods.client.font;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontManager;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.function.Function;

public class DCItemFont extends Font {
    public static RandomSource randomSource = RandomSource.create(Util.getMillis());
    public FontManager  fontManager = Minecraft.getInstance().fontManager;

    public DCItemFont(Function<ResourceLocation, FontSet> p_243253_, boolean p_243245_) {
        super(p_243253_, p_243245_);
    }

    public static DCItemFont getFont() {
        return new DCItemFont(Minecraft.getInstance().font.fonts, false);
    }

    public static String getString(String s) {
        return Component.translatable(s).getString();
    }

    public static boolean isTabFont(String s) {
        return s.contains(getString("tabs.dc_mods.tab")) ||
                s.contains(getString("tabs.dc_mods.tab_creative")) ||
                s.contains(getString("tabs.dc_mods.all_items")) ||
                s.contains(getString("tabs.dc_mods.tab_block"))
                ;
    }

    public static boolean isWarnTip(String s) {
        return s.contains(getString("tooltip.dc_mods.dc_warn"))
                || s.contains(getString("tooltip.dc_mods.remove_warn"))
                || s.equals(getString(" - KARUT Blue Archive Original Soundtrack Vol.1 ~ Longing for the memorable days~"))
                || s.contains(getString("item.dc_m.dc_super_remove"));
    }

    public static boolean isDaiChangTip(String s) {
        return s.contains(getString("tooltip.dc_mods.minecraft"))
                || s.contains(getString("tooltip.dc_mods.drop_loot"))
                || s.contains(getString("tooltip.dc_mods.health_get"))
                || s.contains(getString("tooltip.dc_mods.kill"))
                || s.contains(getString("tooltip.dc_mods.attacking_entity_cooldown"))
                || s.contains(getString("tooltip.dc_mods.pickaxe_1"))
                || s.contains(getString("tooltip.dc_mods.axe"))
                || s.contains(getString("tooltip.dc_mods.shovel"))
                || s.contains(getString("tooltip.dc_mods.hoe"))
                || s.contains(getString("tooltip.dc_mods.bleed"))
                || s.contains(getString("item.dc_m.boss_fight_disc"))
                || s.contains(getString("item.dc_m.stewed_heart_of_the_ocean"))
                ;
    }

    public static boolean isDCEnchFont(String s) {
        return s.contains(getString("enchantment.dc_m.super_sharp"))
                || s.contains(getString("enchantment.dc_m.night_vison"))
                || s.contains(getString("enchantment.dc_m.liquid_walk"))
                ;
    }

    public static boolean isSwordTip(String s) {
        return s.contains(getString("tooltip.dc_mods.tips"))
                || s.contains(getString("tooltip.dc_mods.tips_1"))
                || s.contains(getString("tooltip.dc_mods.tips_2"))
                || s.contains(getString("tooltip.dc_mods.attacking_entity"))
                || s.contains(getString("tooltip.dc_mods.sword_boxing"))
                ||  s.contains(getString("tooltip.dc_mods.tip_3"))
                || s.contains(getString("tooltip.dc_mods.kill_entity"))
                || s.contains(getString("material.dc_m.heart_of_the_ocean"))
                ;
    }

    public static boolean isSuperItemName(String s) {
        return s.contains(getString("item.dc_m.super_wood_ingot"))
                || s.contains(getString("item.dc_m.super_wood_sword"))
                || s.equals(getString("item.dc_m.daichang_slash_blade"))
                || s.contains(getString("item.dc_m.super_wood_pickaxe"))
                || s.contains(getString("item.dc_m.super_wood_axe"))
                || s.contains(getString("item.dc_m.super_wood_shovel"))
                || s.contains(getString("item.dc_m.super_wood_hoe"))
                || s.contains(getString("item.dc_m.dc_bow"))
                || s.contains(getString("item.dc_m.dc_arrow"))
                || s.contains(getString("item.dc_m.wood_ring"))
                || s.contains(getString("item.dc_m.normal_wood_knives"))
                || s.contains(getString("item.dc_m.super_wood_knives"))
                || s.contains(getString("item.dc_m.super_wood_totem"));
    }

    public static boolean isOceanTip(String s) {
        return s.equals(getString("tool_tip.dc_m.ocean_tip_1"))
                || s.equals(getString("tool_tip.dc_m.ocean_tip_2"))
                || s.contains(getString("modifier.dc_m.heart_of_the_ocean.description"))
                || s.contains(getString("modifier.dc_m.heart_of_the_ocean.flavor"))
                || s.equals(getString("tool_tip.dc_m.ocean_tip_3"));
    }

    public static boolean isCreativeItem(String s) {
        return s.contains(getString("item.dc_m.dc_craft"))
                || s.contains(getString("item.dc_m.data_set"))
                || s.contains(getString("item.dc_m.destroy_block"))
                || s.contains(getString("item.dc_m.time_clock"));
    }

    public static boolean isCraftTip(String s) {
        return s.contains(getString("tooltip.dc_mods.dc_craft")) || s.contains(getString("tool_tip.dc_mods.7meter"));
    }

    public static boolean az(String s) {
        return s.contains(getString("tooltip.dc_mods.hurts"))
                || s.contains(getString("tool_tip.dc_mods.wood_ring"))
                || s.contains(getString("item.dc_m.super_wood_helmet"))
                || s.contains(getString("item.dc_m.super_wood_chestplate"))
                || s.contains(getString("item.dc_m.super_wood_leggings"))
                || s.contains(getString("item.dc_m.heart_of_the_ocean"))
                || s.contains(getString("item.dc_m.super_wood_boots"));
    }

    public int drawInBatch(@NotNull FormattedCharSequence formattedCharSequence, float x, float y, int rgb, boolean dropShadow, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource bufferSource, @NotNull DisplayMode mode, int i, int i1) {
        StringBuilder builder = new StringBuilder();
        formattedCharSequence.accept((p_13746_, p_13747_, p_13748_) -> {
            builder.appendCodePoint(p_13748_);
            return true;
        });
        String s = builder.toString();
        int c = rgb;
        int darkerC = (c & 0x00FFFFFF) | (0x80000000 & c) >> 1;
        if (isSuperItemName(s)) {
            c = rgb & 0xFFFFA500;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            return (int) x;
        }
        if (isCreativeItem(s)) {
            c = rgb & 0xFF87CEEB;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            return (int) x;
        }
        if (isSwordTip(s)) {
            c = rgb & 0xFF40E0D0;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            return (int) x;
        }
        if (isCraftTip(s)) {
            c = rgb & 0xFF004D40;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            return (int) x;
        }
        if (s.equals(getString("tooltip.dc_mods.is_strong")) || isWarnTip(s) || s.equals("Subscribe to DaiChang on Bilibili")) {
            c = rgb & 0xFFFF0000;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            return (int) x;
        }
        if (s.equals(getString("tooltip.dc_mods.strong"))) {
            c = rgb & 0xFFA9A9A9;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            return (int) x;
        }
        if (s.contains(getString("attribute.dc_mods.super_damage")) || s.contains(getString("attribute.dc_mods.dc_defense"))) {
            c = rgb & 0x01ADD8E6;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            return (int) x;
        }
        if (s.contains(getString("modifier.dc_m.super_wood_ingot"))) {
            c = rgb & 0x800080;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            return (int) x;
        }
        if (az(s)) {
            c = rgb & 0xFFD8BFD8;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            return (int) x;
        }
        if (isDCEnchFont(s)) {
            c = rgb & 0xFFF0F0F0;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            return (int) x;
        }
        if (isTabFont(s)) {
            c = rgb & 0xFFFFD700;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            return (int) x;
        }
        if (isDaiChangTip(s)) {
            c = rgb & 0xFFD700;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            return (int) x;
        }
        if (s.contains(getString("curios.slot"))) {
            c = rgb & 0xFFADD8E6;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            return (int) x;
        }
        if (s.contains(getString("tool_tip.dc_mods.default")) || s.contains(getString("tool_tip.dc_mods.ocean"))) {
            c = rgb & 0xFFFF9999;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            return (int) x;
        }
        return super.drawInternal(formattedCharSequence, x, y, rgb, dropShadow, matrix4f, bufferSource, mode, i, i1);
    }

    public int drawInBatch(@NotNull String text, float x, float y, int rgb, boolean b, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource source, @NotNull DisplayMode mode, int i, int i1) {
        return super.drawInBatch(text, x, y, rgb, b, matrix4f, source, mode, i, i1);
    }

    public int drawInBatch(@NotNull Component component, float x, float y, int rgb, boolean b, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource source, @NotNull DisplayMode mode, int i, int i1) {
        return super.drawInBatch(component, x, y, rgb, b, matrix4f, source, mode, i, i1);
    }
}
