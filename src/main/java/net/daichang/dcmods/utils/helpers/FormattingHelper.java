package net.daichang.dcmods.utils.helpers;

import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.Collections;

public class FormattingHelper {
    public static final String[] rainbowColours = new String[]{"§c", "§6", "§e", "§a", "§b", "§5", "§d", "§5", "§b", "§a", "§e", "§6"};
    public static final String[] metaColours = new String[]{"§5", "§d", "§b", "§f", "§b", "§d"};
    public static final String[] pinkColour = new String[]{"§d", "§d", "§d", "§d", "§d", "§f", "§d", "§d", "§d", "§d", "§d"};

    public static String cycleColour(String input, String[] colours, String extraFormatting) {
        ArrayList<Character> characters = new ArrayList<Character>();
        for (int i = 0; i < input.length(); ++i) {
            characters.add(Character.valueOf(input.charAt(i)));
        }
        Collections.reverse(characters);
        String text = "";
        StringBuilder builder = new StringBuilder(input.length() * 3);
        for (int i = 0; i < characters.size(); ++i) {
            int index = (int)(((long)i + Minecraft.getInstance().player.level().getGameTime()) % (long)colours.length);
            builder.insert(0, colours[index] + extraFormatting + characters.get(i));
            text = builder.toString();
        }
        return text;
    }

    public static boolean isHealth(String s) {
        return ((s = s.toLowerCase()).contains("health") || s.contains("hp")) && !s.contains("max");
    }

    public static boolean isRemove(String s) {
        return (s = s.toLowerCase()).contains("remove") || s.contains("end") || s.contains("delete");
    }
}
