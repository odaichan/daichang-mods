package net.daichang.dcmods;

import net.daichang.dcmods.event.DCForgeEventHandler;
import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static class Client {
        public static final ForgeConfigSpec.Builder clientBuild;

        public static final ForgeConfigSpec.BooleanValue rainbow_font;

        public static final ForgeConfigSpec.BooleanValue tool_tip_render;

        public static final ForgeConfigSpec.BooleanValue boss_music;

        public static final ForgeConfigSpec.BooleanValue tool_tip_background_color;

        public static final ForgeConfigSpec client;

        public static final ForgeConfigSpec.BooleanValue can_change_splash;

        public static final ForgeConfigSpec.EnumValue<DCForgeEventHandler.ToolTipColor> tool_tip_background_color_get;

        static {
            clientBuild =  new ForgeConfigSpec.Builder();
            rainbow_font = clientBuild.comment("Mod Name Rainbow Font").define("Mod Name Rainbow Font", true);
            boss_music = clientBuild.comment("Boss Music").define("Boss Music", true);
            can_change_splash = clientBuild.comment("Can Change Splash").define("Can Change Splash", false);
            tool_tip_render = clientBuild.comment("More tooltip render").define("More tooltip render", true);
            tool_tip_background_color = clientBuild.comment("Tool Tip Custom Render").define("Tool Tip Custom Render", false);
            tool_tip_background_color_get = clientBuild.comment("Tool Tip Custom Render Color").defineEnum("Tooltip Background Color", DCForgeEventHandler.ToolTipColor.WHITE);
            client = clientBuild.build();
        }
    }

    public static class Common {
        public static final ForgeConfigSpec.Builder commonBuild;

        public static final ForgeConfigSpec.BooleanValue boss_super_hurt;

        public static final ForgeConfigSpec.BooleanValue elaina_super_mode;

        public static final ForgeConfigSpec.BooleanValue steve_health_boost;

        public static final ForgeConfigSpec.BooleanValue anti_heal;

        public static final ForgeConfigSpec.IntValue heal_count;

        public static final ForgeConfigSpec.BooleanValue rest_fe_ban_heal;

        public static final ForgeConfigSpec common;

        static {
            commonBuild =  new ForgeConfigSpec.Builder();
            boss_super_hurt = commonBuild.comment("Boss Super Hurt").define("Boss Super Hurt", true);
            elaina_super_mode = commonBuild.comment("Elaina Super Mode").define("Elaina Super Mode", true);
            heal_count = commonBuild.comment("DC Health Delta Heal Value").defineInRange("DC Health Delta Heal", 1, 1, 40);
            anti_heal = commonBuild.comment("Prohibition of treatment").define("Prohibition of treatment", true);
            steve_health_boost = commonBuild.comment("Steve Health Boost").define("Steve Health Boost", false);
            rest_fe_ban_heal = commonBuild.comment("Reset Fantasy Ending Health Delta").define("Reset FE Health Delta", false);
            common = commonBuild.build();
        }
    }

    public static class Server {
        public static final ForgeConfigSpec.Builder serverBuild;

        public static final ForgeConfigSpec.BooleanValue ocean_heart;

        public static final ForgeConfigSpec server;

        static {
            serverBuild =  new ForgeConfigSpec.Builder();
            ocean_heart = serverBuild.comment("Heart of the Ocean is easy to obtain").define("Ocean Heart Easy Get", false);
            server = serverBuild.build();
        }
    }

}
