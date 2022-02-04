package com.alexvr.bedres.setup;

import com.alexvr.bedres.BedrockResources;
import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class ModConfig {

    public static final String CATEGORY_GENERAL = "general";
    public static final String GRAVITY_BUBBLE = "gravity_bubble";
    public static final String PEDESTAL_CRAFTING = "pendestal_crafting";
    public static final String RITUAL_CRAFTING = "ritual_crafting";
    public static final String PLAYER_ABILITY = "player_ability";

    public static ForgeConfigSpec.IntValue GRAVITY_BUBBLE_RADIUS;

    public static ForgeConfigSpec.IntValue PEDESTAL_TICKS_PER_ITEM;

    public static ForgeConfigSpec.IntValue RITUAL_TICKS_PER_ITEM;

    public static ForgeConfigSpec.ConfigValue<String> DEF_PICK;
    public static ForgeConfigSpec.ConfigValue<String> DEF_AXE;
    public static ForgeConfigSpec.ConfigValue<String> DEF_SHOVEL;
    public static ForgeConfigSpec.ConfigValue<String> DEF_SWORD;
    public static ForgeConfigSpec.ConfigValue<String> DEF_HOE;
    public static ForgeConfigSpec.DoubleValue DEF_JUMP;
    public static ForgeConfigSpec.DoubleValue DEF_SPEED;
    public static ForgeConfigSpec.DoubleValue DEF_FLUX;
    public static ForgeConfigSpec.DoubleValue DEF_MAXFLUX;
    public static ForgeConfigSpec.DoubleValue DEF_FLUXCOOLDOWN;

    public static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec SERVER_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static void register() {
        SERVER_BUILDER.comment("General settings").push(CATEGORY_GENERAL);

        setupGravBubbleConfig();
        setupPedestalConfig();
        setupRitualConfig();
        setupPlayerAbilityConfig();
        setupFluxConfig();


        SERVER_BUILDER.pop();

        SERVER_CONFIG = SERVER_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();

        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.CLIENT, CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.SERVER, SERVER_CONFIG);
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    private static void setupGravBubbleConfig() {
        SERVER_BUILDER.comment("Gravity Bubble settings").push(GRAVITY_BUBBLE);

        GRAVITY_BUBBLE_RADIUS = SERVER_BUILDER.comment("Maximum blocks to extend the gravity bubble by default.")
                .defineInRange("grav_radius", 7, 3, 32);

        SERVER_BUILDER.pop();
    }

    private static void setupPedestalConfig() {
        SERVER_BUILDER.comment("Pedestal Crafting settings").push(PEDESTAL_CRAFTING);

        PEDESTAL_TICKS_PER_ITEM = SERVER_BUILDER.comment("Ticks per item the pedestal will take crafting. (20 ticks = 1 second)")
                .defineInRange("altar_ticks_per_craft", 30, 1, Integer.MAX_VALUE);


        SERVER_BUILDER.pop();
    }

    private static void setupRitualConfig() {
        SERVER_BUILDER.comment("Ritual Crafting settings").push(RITUAL_CRAFTING);

        RITUAL_TICKS_PER_ITEM = SERVER_BUILDER.comment("Ticks per item the ritual will take crafting. (20 ticks = 1 second)")
                .defineInRange("ritual_ticks_per_craft", 120, 1, Integer.MAX_VALUE);


        SERVER_BUILDER.pop();
    }

    private static void setupPlayerAbilityConfig() {
        SERVER_BUILDER.comment("Player Ability settings").push(PLAYER_ABILITY);

        DEF_PICK = SERVER_BUILDER.comment("Default Pickaxe on player.")
                .define("pick", "no");
        DEF_AXE = SERVER_BUILDER.comment("Default Axe on player.")
                .define("axe", "no");
        DEF_SHOVEL = SERVER_BUILDER.comment("Default Shovel on player.")
                .define("shovel", "no");
        DEF_SWORD = SERVER_BUILDER.comment("Default Sword on player.")
                .define("sword", "no");
        DEF_HOE = SERVER_BUILDER.comment("Default Hoe on player. (no or active)")
                .define("hoe", "no");
        DEF_JUMP = SERVER_BUILDER.comment("Default Jump Height (values used in game [0.2,0.015f,0.08f,0.15f,0.25f])")
                .defineInRange("jump", 0, 0, Double.MAX_VALUE);
        DEF_SPEED = SERVER_BUILDER.comment("Default Mining Speed Boost (in game it goes .3 at a time up to 1.35)")
                .defineInRange("speed", 0, 0, Double.MAX_VALUE);


        SERVER_BUILDER.pop();
    }


    private static void setupFluxConfig() {
        SERVER_BUILDER.comment("Bedrock Flux settings").push(PLAYER_ABILITY);
        DEF_FLUX = SERVER_BUILDER.comment("Default Bedrock Flux value")
                .defineInRange("flux", 250, 0, Double.MAX_VALUE);
        DEF_MAXFLUX = SERVER_BUILDER.comment("Default Max Bedrock Flux value")
                .defineInRange("maxflux", 250, 0, Double.MAX_VALUE);
        DEF_FLUXCOOLDOWN = SERVER_BUILDER.comment("Default Bedrock Flux regen cooldown in ticks")
                .defineInRange("fluxcooldown", 20, 0, Double.MAX_VALUE);
        SERVER_BUILDER.pop();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent.Loading event)
    {
        if (event.getConfig().getModId().equals(BedrockResources.MODID))
        {
            CommentedConfig cfg = event.getConfig().getConfigData();

            if (cfg instanceof CommentedFileConfig)
                ((CommentedFileConfig) cfg).load();
        }

    }

    @SubscribeEvent
    public static void onReload(final ModConfigEvent.Reloading configEvent) {
        if (configEvent.getConfig().getModId().equals(BedrockResources.MODID))
        {

            //reload my stuff
            CommentedConfig cfg = configEvent.getConfig().getConfigData();

            if (cfg instanceof CommentedFileConfig)
                ((CommentedFileConfig) cfg).load();
        }
    }

}
