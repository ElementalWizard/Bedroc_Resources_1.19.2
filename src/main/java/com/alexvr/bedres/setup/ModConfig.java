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
    public static final String WORLD_GEN = "world_gen";
    public static final String MOB = "mob";

    public static ForgeConfigSpec.IntValue SUNDAIZE_CHANCE;
    public static ForgeConfigSpec.IntValue ENDERHUSH_CHANCE;
    public static ForgeConfigSpec.IntValue BLAIZE_CHANCE;

    public static ForgeConfigSpec.IntValue ALTAR_SPACING;
    public static ForgeConfigSpec.IntValue ALTAR_SEPARATION;

    public static ForgeConfigSpec.IntValue MYSTERYDUNGEON_SPACING;
    public static ForgeConfigSpec.IntValue MYSTERYDUNGEON_SEPARATION;

    public static ForgeConfigSpec.IntValue OVERWORLD_VEINSIZE;
    public static ForgeConfigSpec.IntValue OVERWORLD_AMOUNT;
    public static ForgeConfigSpec.IntValue DEEPSLATE_VEINSIZE;
    public static ForgeConfigSpec.IntValue DEEPSLATE_AMOUNT;
    public static ForgeConfigSpec.IntValue NETHER_VEINSIZE;
    public static ForgeConfigSpec.IntValue NETHER_AMOUNT;
    public static ForgeConfigSpec.IntValue END_VEINSIZE;
    public static ForgeConfigSpec.IntValue END_AMOUNT;
    public static ForgeConfigSpec.IntValue OVERWORLD_MINY;
    public static ForgeConfigSpec.IntValue OVERWORLD_MAXY;
    public static ForgeConfigSpec.IntValue DEEPSLATE_MAXY;
    public static ForgeConfigSpec.IntValue NETHER_MINY;
    public static ForgeConfigSpec.IntValue NETHER_MAXY;
    public static ForgeConfigSpec.IntValue END_MINY;
    public static ForgeConfigSpec.IntValue END_MAXY;

    public static ForgeConfigSpec.IntValue GRAVITY_BUBBLE_RADIUS;

    public static ForgeConfigSpec.IntValue PEDESTAL_TICKS_PER_ITEM;

    public static ForgeConfigSpec.IntValue RITUAL_TICKS_PER_ITEM;

    public static ForgeConfigSpec.IntValue TRECKING_CREEPER_WEIGHT;
    public static ForgeConfigSpec.IntValue TRECKING_CREEPER_MIN_GROUP;
    public static ForgeConfigSpec.IntValue TRECKING_CREEPER_MAX_GROUP;
    public static ForgeConfigSpec.IntValue BABY_GHAST_WEIGHT;
    public static ForgeConfigSpec.IntValue BABY_GHAST_MIN_GROUP;
    public static ForgeConfigSpec.IntValue BABY_GHAST_MAX_GROUP;
    public static ForgeConfigSpec.IntValue FLUXED_CREEP_WEIGHT;
    public static ForgeConfigSpec.IntValue FLUXED_CREEP_MIN_GROUP;
    public static ForgeConfigSpec.IntValue FLUXED_CREEP_MAX_GROUP;
    public static ForgeConfigSpec.IntValue CHAINED_BLAZE_WEIGHT;
    public static ForgeConfigSpec.IntValue CHAINED_BLAZE_MIN_GROUP;
    public static ForgeConfigSpec.IntValue CHAINED_BLAZE_MAX_GROUP;

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

        setupWorldGenConfig();
        setupGravBubbleConfig();
        setupPedestalConfig();
        setupRitualConfig();
        setupPlayerAbilityConfig();
        setupMobInfo();

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

    private static void setupMobInfo() {
        SERVER_BUILDER.comment("Mob Settings").push(MOB);
        TRECKING_CREEPER_WEIGHT = SERVER_BUILDER.comment("Weight for Trecking Creepers to spawn.")
                .defineInRange("trecking_weight", 45, 0, 100);
        TRECKING_CREEPER_MIN_GROUP = SERVER_BUILDER.comment("Min group number for Trecking Creepers spawn.")
                .defineInRange("trecking_min", 0, 0, 30);
        TRECKING_CREEPER_MAX_GROUP = SERVER_BUILDER.comment("Max group number for Trecking Creepers spawn.")
                .defineInRange("trecking_max", 3, 1, 30);

        BABY_GHAST_WEIGHT = SERVER_BUILDER.comment("Weight for Baby Ghast to spawn.")
                .defineInRange("baby_ghast_weight", 75, 0, 100);
        BABY_GHAST_MIN_GROUP = SERVER_BUILDER.comment("Min group number for Baby Ghast spawn.")
                .defineInRange("baby_ghast_min", 0, 0, 30);
        BABY_GHAST_MAX_GROUP = SERVER_BUILDER.comment("Max group number for Baby Ghast spawn.")
                .defineInRange("baby_ghast_max", 1, 1, 30);

        FLUXED_CREEP_WEIGHT = SERVER_BUILDER.comment("Weight for Fluxed Creep to spawn.")
                .defineInRange("fluxed_creep_weight", 75, 0, 100);
        FLUXED_CREEP_MIN_GROUP = SERVER_BUILDER.comment("Min group number for Fluxed Creep spawn.")
                .defineInRange("fluxed_creep_min", 0, 0, 30);
        FLUXED_CREEP_MAX_GROUP = SERVER_BUILDER.comment("Max group number for Fluxed Creep spawn.")
                .defineInRange("fluxed_creep_max", 1, 1, 30);

        CHAINED_BLAZE_WEIGHT = SERVER_BUILDER.comment("Weight for Chained Blaze to spawn.")
                .defineInRange("chained_blaze_weight", 75, 0, 100);
        CHAINED_BLAZE_MIN_GROUP = SERVER_BUILDER.comment("Min group number for Chained Blaze spawn.")
                .defineInRange("chained_blaze_min", 0, 0, 30);
        CHAINED_BLAZE_MAX_GROUP = SERVER_BUILDER.comment("Max group number for Chained Blaze spawn.")
                .defineInRange("chained_blaze_max", 2, 1, 30);

        SERVER_BUILDER.pop();
    }

    private static void setupWorldGenConfig() {
        SERVER_BUILDER.comment("World Gen settings").push(WORLD_GEN);

        SUNDAIZE_CHANCE = SERVER_BUILDER.comment("Chance for Sun Daize to spawn")
                .defineInRange("sun_chance", 35, 0, 100);
        ENDERHUSH_CHANCE = SERVER_BUILDER.comment("Chance for Ender Hush to spawn")
                .defineInRange("ender_chance", 45, 0, 100);
        BLAIZE_CHANCE = SERVER_BUILDER.comment("Chance for Blazium to spawn")
                .defineInRange("blazium_chance", 25, 0, 100);

        ALTAR_SPACING = SERVER_BUILDER.comment("Chunks spacing between Altars")
                .defineInRange("altar_space", 10, 3, 1000);
        ALTAR_SEPARATION = SERVER_BUILDER.comment("Average chunks separation to spawn Altar")
                .defineInRange("altar_sep", 5, 3, 1000);

        MYSTERYDUNGEON_SPACING = SERVER_BUILDER.comment("Chunks spacing between Mystery Dungeons WIP")
                .defineInRange("mysdun_space", 64, 3, 1000);
        MYSTERYDUNGEON_SEPARATION = SERVER_BUILDER.comment("Average chunks separation to spawn Mystery Dungeons WIP")
                .defineInRange("mysdun_sep", 32, 3, 1000);

        OVERWORLD_VEINSIZE = SERVER_BUILDER.comment("Enderian Ore Overworld Vein Size")
                .defineInRange("oeore_size", 3, 0, 1000);
        OVERWORLD_AMOUNT = SERVER_BUILDER.comment("Enderian Ore Overworld Spawn Amount")
                .defineInRange("oeore_amount", 2, 0, 1000);
        DEEPSLATE_VEINSIZE = SERVER_BUILDER.comment("Enderian Ore Deepslate Vein Size")
                .defineInRange("deore_size", 5, 0, 1000);
        DEEPSLATE_AMOUNT = SERVER_BUILDER.comment("Enderian Ore Deepslate Spawn Amount")
                .defineInRange("deore_amount", 3, 0, 1000);
        NETHER_VEINSIZE = SERVER_BUILDER.comment("Enderian Ore Nether Vein Size")
                .defineInRange("neore_size", 2, 0, 1000);
        NETHER_AMOUNT = SERVER_BUILDER.comment("Enderian Ore Nether Spawn Amount")
                .defineInRange("neore_amount", 1, 0, 1000);
        END_VEINSIZE = SERVER_BUILDER.comment("Enderian Ore End Vein Size")
                .defineInRange("eeore_size", 10, 0, 1000);
        END_AMOUNT = SERVER_BUILDER.comment("Enderian Ore End Spawn Amount")
                .defineInRange("eeore_amount", 6, 0, 1000);

        OVERWORLD_MINY = SERVER_BUILDER.comment("Enderian Ore Overworld Lowest Y level to spawn")
                .defineInRange("oeore_miny", 0, 0, 255);
        OVERWORLD_MAXY = SERVER_BUILDER.comment("Enderian Ore Overworld Highest Y level to spawn")
                .defineInRange("oeore_maxy", 90, 0, 255);
        DEEPSLATE_MAXY = SERVER_BUILDER.comment("Enderian Ore Deepslate Highest Y level to spawn")
                .defineInRange("deore_maxy", 63, 0, 255);
        NETHER_MINY = SERVER_BUILDER.comment("Enderian Ore Nether Lowest Y level to spawn")
                .defineInRange("neore_miny", 0, 0, 255);
        NETHER_MAXY = SERVER_BUILDER.comment("Enderian Ore Nether Highest Y level to spawn")
                .defineInRange("neore_maxy", 90, 0, 255);
        END_MINY = SERVER_BUILDER.comment("Enderian Ore End Lowest Y level to spawn")
                .defineInRange("eeore_miny", 0, 0, 255);
        END_MAXY = SERVER_BUILDER.comment("Enderian Ore End Highest Y level to spawn")
                .defineInRange("eeore_maxy", 100, 0, 255);

        SERVER_BUILDER.pop();
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
                .defineInRange("altar_ticks_per_craft", 40, 1, Integer.MAX_VALUE);


        SERVER_BUILDER.pop();
    }

    private static void setupRitualConfig() {
        SERVER_BUILDER.comment("Ritual Crafting settings").push(RITUAL_CRAFTING);

        RITUAL_TICKS_PER_ITEM = SERVER_BUILDER.comment("Ticks per item the ritual will take crafting. (20 ticks = 1 second)")
                .defineInRange("ritual_ticks_per_craft", 100, 1, Integer.MAX_VALUE);


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
        DEF_JUMP = SERVER_BUILDER.comment("Default Jump Height (values used in game [0.2,0.08f,0.15f,0.25f])")
                .defineInRange("jump", 0, 0, Double.MAX_VALUE);
        DEF_SPEED = SERVER_BUILDER.comment("Default Mining Speed Boost (in game it goes .3 at a time up to 1.35)")
                .defineInRange("speed", 0, 0, Double.MAX_VALUE);

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
