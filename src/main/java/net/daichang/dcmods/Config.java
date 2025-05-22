package net.daichang.dcmods;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static class Client {
        public static final ForgeConfigSpec.Builder clientBuild;

        public static final ForgeConfigSpec.BooleanValue rainbow_font;

        public static final ForgeConfigSpec.BooleanValue tool_tip_render;

        public static final ForgeConfigSpec.BooleanValue boss_music;

        public static final ForgeConfigSpec client;

        static {
            clientBuild =  new ForgeConfigSpec.Builder();
            rainbow_font = clientBuild.comment("Mod Name Rainbow Font").define("Mod Name Rainbow Font", true);
            boss_music = clientBuild.comment("Boss Music").define("Boss Music", true);
            tool_tip_render = clientBuild.comment("More tooltip render").define("More tooltip render", true);
            client = clientBuild.build();
        }
    }

    public static class Server {
        public static final ForgeConfigSpec.Builder serverBuild;

        public static final ForgeConfigSpec.BooleanValue boss_super_hurt;

        public static final ForgeConfigSpec.BooleanValue elaina_super_mode;

        public static final ForgeConfigSpec.BooleanValue steve_health_boost;

        public static final ForgeConfigSpec.BooleanValue anti_heal;

        public static final ForgeConfigSpec.IntValue heal_count;

        public static final ForgeConfigSpec.BooleanValue ocean_heart;

        public static final ForgeConfigSpec server;

        static {
            serverBuild =  new ForgeConfigSpec.Builder();
            boss_super_hurt = serverBuild.comment("Boss Super Hurt").define("Boss Super Hurt", true);
            elaina_super_mode = serverBuild.comment("Elaina Super Mode").define("Elaina Super Mode", true);
            ocean_heart = serverBuild.comment("Heart of the Ocean is easy to obtain").define("Ocean Heart Easy Get", false);
            heal_count = serverBuild.comment("DC Health Delta Heal").defineInRange("DC Health Delta Heal", 1, 1, 40);
            anti_heal = serverBuild.comment("Prohibition of treatment").define("Prohibition of treatment", true);
            steve_health_boost = serverBuild.comment("Steve Health Boost").define("Steve Health Boost", false);
            server = serverBuild.build();
        }
    }
}
