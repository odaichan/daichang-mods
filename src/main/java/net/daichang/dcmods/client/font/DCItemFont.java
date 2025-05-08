package net.daichang.dcmods.client.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.function.Function;

public class DCItemFont extends Font {
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
        return s.contains(getString("tabs.dc_mods.tab")) || s.contains(getString("tabs.dc_mods.tab_creative")) || s.contains(getString("tabs.dc_mods.tab_block"));
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
                || s.contains(getString("item.dc_m.boss_fight_disc"));
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
                ;
    }

    public static boolean isSuperItemName(String s) {
        return s.contains(getString("item.dc_m.super_wood_ingot"))
                || s.contains(getString("item.dc_m.super_wood_sword"))
                || s.contains(getString("item.dc_m.super_wood_pickaxe"))
                || s.contains(getString("item.dc_m.super_wood_axe"))
                || s.contains(getString("item.dc_m.super_wood_shovel"))
                || s.contains(getString("item.dc_m.super_wood_hoe"))
                || s.contains(getString("item.dc_m.dc_bow"))
                || s.contains(getString("item.dc_m.dc_arrow"))
                || s.contains(getString("item.dc_m.wood_ring"))
                || s.contains(getString("item.dc_m.super_wood_totem"));
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

    public static boolean isMinecraftName(String s) {
        return s.contains(getString("attribute.name.generic.attack_damage"))
                || s.contains(getString("attribute.name.generic.attack_speed"))
                || s.contains(getString("attribute.name.generic.armor"))
                || s.contains(getString("attribute.name.generic.armor_toughness"))
                || s.contains(getString("attribute.name.generic.attack_knockback"))
                || s.contains(getString("attribute.name.generic.flying_speed"))
                || s.contains(getString("attribute.name.generic.knockback_resistance"))
                || s.contains(getString("attribute.name.generic.follow_range"))
                || s.contains(getString("attribute.name.generic.attack_speed"))
                || s.contains(getString("attribute.name.generic.movement_speed"))
                ;
    }

    //不知道应该写什么方法名
    public static boolean az(String s) {
        return s.contains(getString("tooltip.dc_mods.hurts"))
                || s.contains(getString("tool_tip.dc_mods.wood_ring"))
                || s.contains(getString("item.dc_m.super_wood_helmet"))
                || s.contains(getString("item.dc_m.super_wood_chestplate"))
                || s.contains(getString("item.dc_m.super_wood_leggings"))
                || s.contains(getString("item.dc_m.heart_of_the_ocean"))
                || s.contains(getString("item.dc_m.super_wood_boots"));
    }

    public int drawInBatch(@NotNull FormattedCharSequence formattedCharSequence, float x, float y, int rgb, boolean b1, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource multiBufferSource, @NotNull DisplayMode mode, int i, int i1) {
        StringBuilder builder = new StringBuilder();
        formattedCharSequence.accept((p_13746_, p_13747_, p_13748_) -> {
            builder.appendCodePoint(p_13748_);
            return true;
        });
        return renderFont(builder.toString(), x, y, rgb, b1, matrix4f, multiBufferSource, mode, i, i1, this.isBidirectional());
    }

    public int drawInBatch(@NotNull String text, float x, float y, int rgb, boolean b, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource source, @NotNull DisplayMode mode, int i, int i1) {
        return renderFont(text, x, y, rgb, b, matrix4f, source, mode, i, i1, this.isBidirectional());
    }

    public int drawInBatch(@NotNull Component component, float x, float y, int rgb, boolean b, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource source, @NotNull DisplayMode mode, int i, int i1) {
        return renderFont(component.getString(), x, y, rgb, b, matrix4f, source, mode, i, i1, this.isBidirectional());
    }

    public int renderFont(@NotNull String text, float x, float y, int rgb, boolean dropShadow, @NotNull Matrix4f matrix4f, @NotNull MultiBufferSource bufferSource, @NotNull DisplayMode mode, int i, int i1, boolean isText){
        for (int index = 0; index < text.length(); index++) {
            String s = String.valueOf(text.charAt(index));
            int c;
            if (isSuperItemName(text)) c = rgb & 0xFFFFA500;
            else if (isCreativeItem(text)) c = rgb & 0xFF87CEEB;
            else if (isSwordTip(text)) c = rgb &  0xFF40E0D0;
            else if (isMinecraftName(text) || isCraftTip(s)) c = rgb & 0xFF004D40;
            else if (text.equals(getString("tooltip.dc_mods.is_strong")) || isWarnTip(text) || text.equals("Subscribe to DaiChang on Bilibili")) c = rgb & 0xFFFF0000;
            else if (text.equals(getString("tooltip.dc_mods.strong"))) c = rgb & 0xFFA9A9A9;
            else if (text.contains(getString("attribute.dc_mods.super_damage")) || text.contains(getString("attribute.dc_mods.dc_defense"))) c = rgb & 0x01ADD8E6 ;
            else if (text.contains(getString("modifier.dc_m.super_wood_ingot"))) c = rgb & 0x800080;
            else if (az(text)) c = rgb & 0xFFD8BFD8;
            else if (isDCEnchFont(text)) c = rgb & 0xFFF0F0F0;
            else if (isTabFont(text)) c = rgb & 0xFFFFD700;
            else if (isDaiChangTip(text)) c = rgb & 0xFFD700;
            else if (text.contains(getString("curios.slot"))) c = rgb & 0xFFADD8E6;
            else if (text.contains(getString("attribute.name.generic.max_health")) || text.contains(getString("tool_tip.dc_mods.default")) || text.contains(getString("tool_tip.dc_mods.ocean"))) c = rgb & 0xFFFF9999;
            else  c = rgb;
            int darkerC = (c & 0x00FFFFFF) | (0x80000000 & c) >> 1;
            super.drawInBatch(s, x, y, c, dropShadow, matrix4f, bufferSource, mode, i, i1);//前景色
            super.drawInBatch(s, x + 0.55F, y + 0.55F, darkerC, dropShadow, matrix4f, bufferSource, mode, i, i1);
            if (text.startsWith("§o"))  matrix4f.rotate((float) Math.toRadians(10), 0, 1, 0);
            if (text.startsWith("§l"))  matrix4f.scale(1.1F);
            x += width(s);
        }
        return (int) x;
    }
}
