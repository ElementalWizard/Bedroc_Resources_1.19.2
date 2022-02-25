package com.alexvr.bedres.recipes;

import com.alexvr.bedres.recipes.eventRituals.EventRitualsRecipeSerializer;
import com.alexvr.bedres.recipes.eventRituals.EventRitualsRecipes;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipeSerializer;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipes;
import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.alexvr.bedres.BedrockResources.MODID;

public class ModRecipeRegistry {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

    public static void register() {
        RECIPE_SERIALIZERS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<RecipeSerializer<RitualAltarRecipes>> ALTAR_RECIPES = RECIPE_SERIALIZERS.register("ritual_altar",
            RitualAltarRecipeSerializer::new);

    public static final ResourceLocation RECIPE_ALTAR = new ResourceLocation(MODID, "ritual_altar");
    public static RecipeType<RitualAltarRecipes> ALTAR = Registry.register(Registry.RECIPE_TYPE, RECIPE_ALTAR,
            new RecipeType<RitualAltarRecipes>() {
                @Override
                public String toString() {
                    return RECIPE_ALTAR.toString();
                }
            });

    private static List<RitualAltarRecipes> ritualAltarRecipes = new ArrayList<>();

    public static final RegistryObject<RecipeSerializer<EventRitualsRecipes>> EVENT_RITUAL_RECIPES = RECIPE_SERIALIZERS.register("event_ritual",
            EventRitualsRecipeSerializer::new);

    public static final ResourceLocation RECIPE_EVENT_RITUAL = new ResourceLocation(MODID, "event_ritual");
    public static RecipeType<EventRitualsRecipes> EVENT_RITUAL = Registry.register(Registry.RECIPE_TYPE, RECIPE_EVENT_RITUAL,
            new RecipeType<EventRitualsRecipes>() {
                @Override
                public String toString() {
                    return RECIPE_EVENT_RITUAL.toString();
                }
            });

    private static List<EventRitualsRecipes> eventRitualsRecipes = new ArrayList<>();

    private static boolean initialized = false;

    private static void init() {
        if (initialized) {
            return;
        }
        initialized = true;


        setUpAltarRitualRecipes();

        setUpEventRitualToolRecipes();
        setUpEventRitualPlayerRecipes();
        setUpEventRitualWorldRecipes();

    }

    public static void setUpAltarRitualRecipes(){

        //pedestal item must be first in index
        ritualAltarRecipes.add(new RitualAltarRecipes(new ItemStack(Registration.STAFF_ITEM.get()),
                new ItemStack(Items.CREEPER_HEAD, 1),
                new ItemStack(Registration.VOID_TEAR_ITEM.get(), 1),
                new ItemStack(Registration.BLAZIUM_ITEM.get(), 1),
                new ItemStack(Items.CUT_COPPER_SLAB, 6),
                new ItemStack(Registration.BEDROCK_COMPRESSED_WIRE_BLOCK.get(), 2),
                new ItemStack(Items.GOLD_BLOCK, 2),
                new ItemStack(Registration.ENDERIAN_INGOT_ITEM.get(), 4)
        ));
        ritualAltarRecipes.add(new RitualAltarRecipes(new ItemStack(Registration.FLUXED_CUPCAKE_ITEM.get()),
                new ItemStack(Items.BOWL, 1),
                new ItemStack(Items.MILK_BUCKET, 4),
                new ItemStack(Items.SUGAR, 1),
                new ItemStack(Items.EGG, 1),
                new ItemStack(Items.NETHER_WART, 2),
                new ItemStack(Registration.FLUXED_SPORES_BLOCK.get(), 4),
                new ItemStack(Items.WHEAT, 2)
        ));
        ritualAltarRecipes.add(new RitualAltarRecipes(new ItemStack(Registration.FLUXED_GRAVITY_BUBBLE_ITEM.get()),
                new ItemStack(Registration.VOID_TEAR_ITEM.get(), 1),
                new ItemStack(Items.OBSIDIAN, 4),
                new ItemStack(Items.PURPLE_STAINED_GLASS, 4),
                new ItemStack(Items.PURPLE_STAINED_GLASS_PANE, 4),
                new ItemStack(Registration.BEDROCK_WIRE_ITEM.get(), 4),
                new ItemStack(Registration.ENDER_HUSH_ITEM.get(), 2)
        ));

        ritualAltarRecipes.add(new RitualAltarRecipes(new ItemStack(Registration.MAGE_STAFF_ITEM.get()),
                new ItemStack(Registration.NEBULA_HEART_ITEM.get(), 1),
                new ItemStack(Items.OBSIDIAN, 2),
                new ItemStack(Registration.ENDERIAN_INGOT_ITEM.get(), 4),
                new ItemStack(Registration.BEDROCK_WIRE_ITEM.get(), 4),
                new ItemStack(Registration.FLUXED_SPORES_ITEM.get(), 2),
                new ItemStack(Registration.DF_OAK_LOG_BLOCK.get(), 4),
                new ItemStack(Registration.DF_OAK_PLANKS_BLOCK.get(), 2),
                new ItemStack(Registration.ENDER_HUSH_ITEM.get(), 2)
        ));

        ritualAltarRecipes.add(new RitualAltarRecipes(new ItemStack(Registration.EVENT_ALTAR_ITEM.get()),
                new ItemStack(Registration.ENDERIAN_BLOCK_BLOCK.get(), 1),
                new ItemStack(Items.OBSIDIAN, 2),
                new ItemStack(Registration.BEDROCK_WIRE_ITEM.get(), 4),
                new ItemStack(Registration.ENDERIAN_STAIRS_BLOCK.get(), 4),
                new ItemStack(Registration.ENDERIAN_INGOT_ITEM.get(), 6),
                new ItemStack(Registration.ENDERIAN_BRICK_BLOCK.get(), 8)
        ));

    }

    public static void setUpEventRitualWorldRecipes(){
       //RAIN
        {
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.WATER_BUCKET),"world",
                genPattern(
                        "wwiww",
                        "wiwiw",
                        "iw wi",
                        "wiwiw",
                        "wwiww"),
                new ItemStack(Items.WATER_BUCKET, 4),
                new ItemStack(Items.SUGAR_CANE, 2),
                new ItemStack(Items.SEAGRASS, 2)
        ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.BUCKET),"world",
                    genPattern(
                            "wwiww",
                                    "wiwiw",
                                    "iw wi",
                                    "wiwiw",
                                    "wwiww"),
                    new ItemStack(Items.BUCKET, 4),
                    new ItemStack(Items.DRIED_KELP, 2),
                    new ItemStack(Items.DEAD_BUSH, 2)
            ));
        }

        //TIME_SKIP
        {
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.SUNFLOWER),"world",
                    genPattern(
                            "wiiiw",
                            "iiwii",
                            "iw wi",
                            "iiwii",
                            "wiiiw"),
                    new ItemStack(Items.SUNFLOWER, 12),
                    new ItemStack(Items.CLOCK, 4)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.CLOCK),"world",
                    genPattern(
                            "wiiiw",
                            "iiwii",
                            "iw wi",
                            "iiwii",
                            "wiiiw"),
                    new ItemStack(Items.LAPIS_LAZULI, 12),
                    new ItemStack(Items.CLOCK, 4)
            ));
        }
    }

    public static void setUpEventRitualToolRecipes(){

        //PICKAXE
        {
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.WOODEN_PICKAXE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.OAK_LOG, 2),
                    new ItemStack(Items.WOODEN_PICKAXE, 2),
                    new ItemStack(Items.BIRCH_LOG, 2),
                    new ItemStack(Items.WOODEN_PICKAXE, 2),
                    new ItemStack(Items.JUNGLE_LOG, 2)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.IRON_PICKAXE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.CHAIN, 4),
                    new ItemStack(Items.IRON_PICKAXE, 4),
                    new ItemStack(Items.HEAVY_WEIGHTED_PRESSURE_PLATE, 2)
            ));
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.GOLDEN_PICKAXE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.GOLD_NUGGET, 4),
                    new ItemStack(Items.GOLDEN_PICKAXE, 4),
                    new ItemStack(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, 2)
            ));
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.DIAMOND_PICKAXE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.DIAMOND, 4),
                    new ItemStack(Items.DIAMOND_PICKAXE, 4),
                    new ItemStack(Registration.ENDERIAN_INGOT_ITEM.get(), 2)
            ));
        }

        //AXE
        {
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.WOODEN_AXE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.OAK_LOG, 2),
                    new ItemStack(Items.WOODEN_AXE, 4),
                    new ItemStack(Items.BIRCH_LOG, 2),
                    new ItemStack(Items.JUNGLE_LOG, 2)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.IRON_AXE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.CHAIN, 4),
                    new ItemStack(Items.IRON_AXE, 4),
                    new ItemStack(Items.HEAVY_WEIGHTED_PRESSURE_PLATE, 2)
            ));
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.GOLDEN_AXE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.GOLD_NUGGET, 4),
                    new ItemStack(Items.GOLDEN_AXE, 4),
                    new ItemStack(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, 2)
            ));
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.DIAMOND_AXE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.DIAMOND, 4),
                    new ItemStack(Items.DIAMOND_AXE, 4),
                    new ItemStack(Registration.ENDERIAN_INGOT_ITEM.get(), 2)
            ));
        }

        //SWORD
        {
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.WOODEN_SWORD),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.OAK_LOG, 2),
                    new ItemStack(Items.WOODEN_SWORD, 4),
                    new ItemStack(Items.BIRCH_LOG, 2),
                    new ItemStack(Items.JUNGLE_LOG, 2)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.IRON_SWORD),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.CHAIN, 4),
                    new ItemStack(Items.IRON_SWORD, 4),
                    new ItemStack(Items.HEAVY_WEIGHTED_PRESSURE_PLATE, 2)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.DIAMOND_SWORD),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.DIAMOND, 4),
                    new ItemStack(Items.DIAMOND_SWORD, 4),
                    new ItemStack(Registration.ENDERIAN_INGOT_ITEM.get(), 2)
            ));
        }

        //HOE
        {
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.WOODEN_HOE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.OAK_LOG, 2),
                    new ItemStack(Items.WOODEN_HOE, 4),
                    new ItemStack(Items.BIRCH_LOG, 2),
                    new ItemStack(Items.JUNGLE_LOG, 2)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.IRON_HOE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.CHAIN, 4),
                    new ItemStack(Items.IRON_HOE, 4),
                    new ItemStack(Items.HEAVY_WEIGHTED_PRESSURE_PLATE, 2)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.DIAMOND_HOE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.DIAMOND, 4),
                    new ItemStack(Items.DIAMOND_HOE, 4),
                    new ItemStack(Registration.ENDERIAN_INGOT_ITEM.get(), 2)
            ));
        }

        //SHOVEL
        {
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.WOODEN_SHOVEL),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.OAK_LOG, 2),
                    new ItemStack(Items.WOODEN_SHOVEL, 4),
                    new ItemStack(Items.BIRCH_LOG, 2),
                    new ItemStack(Items.JUNGLE_LOG, 2)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.IRON_SHOVEL),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.CHAIN, 4),
                    new ItemStack(Items.IRON_SHOVEL, 4),
                    new ItemStack(Items.HEAVY_WEIGHTED_PRESSURE_PLATE, 2)
            ));
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.GOLDEN_SHOVEL),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.GOLD_NUGGET, 4),
                    new ItemStack(Items.GOLDEN_SHOVEL, 4),
                    new ItemStack(Items.LIGHT_WEIGHTED_PRESSURE_PLATE, 2)
            ));
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.DIAMOND_SHOVEL),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.DIAMOND, 4),
                    new ItemStack(Items.DIAMOND_SHOVEL, 4),
                    new ItemStack(Registration.ENDERIAN_INGOT_ITEM.get(), 2)
            ));
        }

    }

    public static void setUpEventRitualPlayerRecipes(){
        eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Registration.BEDROCK_COMPRESSED_WIRE_ITEM.get()),"player_upgrade",
                genPattern(
                        "wiiiw",
                        "iwwwi",
                        "iw wi",
                        "iwwwi",
                        "wiiiw"),
                new ItemStack(Items.DIAMOND_BLOCK, 1),
                new ItemStack(Items.END_CRYSTAL, 2),
                new ItemStack(Registration.ENDER_HUSH_ITEM.get(), 2),
                new ItemStack(Registration.VOID_TEAR_ITEM.get(), 1),
                new ItemStack(Items.ENDER_CHEST, 1),
                new ItemStack(Registration.BEDROCK_COMPRESSED_WIRE_ITEM.get(), 2),
                new ItemStack(Items.ENDER_PEARL, 3)
        ));

        //MINING SPEED
        {
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.REDSTONE),"player_upgrade",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "ww ww",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.REDSTONE_BLOCK, 2),
                    new ItemStack(Items.SUGAR, 2),
                    new ItemStack(Items.REDSTONE, 4)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Registration.BEDROCK_WIRE_ITEM.get()),"player_upgrade",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "ww ww",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.SLIME_BALL, 2),
                    new ItemStack(Items.SOUL_SAND, 4),
                    new ItemStack(Items.HONEY_BLOCK, 2)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Registration.ROPE_ITEM.get()),"player_upgrade",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.SLIME_BALL, 2),
                    new ItemStack(Items.REDSTONE, 4),
                    new ItemStack(Items.HONEY_BLOCK, 2)
            ));

        }

        //JUMP
        {
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.RABBIT_FOOT),"player_upgrade",
                    genPattern(
                            "wiiiw",
                            "wwwww",
                            "iw wi",
                            "wwwww",
                            "wiiiw"),
                    new ItemStack(Items.PISTON, 6),
                    new ItemStack(Items.RABBIT_FOOT, 2)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.RABBIT_HIDE),"player_upgrade",
                    genPattern(
                            "wiiiw",
                            "wwwww",
                            "iw wi",
                            "wwwww",
                            "wiiiw"),
                    new ItemStack(Items.STICKY_PISTON, 6),
                    new ItemStack(Items.HAY_BLOCK, 2)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.CHAIN),"player_upgrade",
                    genPattern(
                            "wiiiw",
                            "wwwww",
                            "iw wi",
                            "wwwww",
                            "wiiiw"),
                    new ItemStack(Items.PISTON, 3),
                    new ItemStack(Items.HAY_BLOCK, 1),
                    new ItemStack(Items.RABBIT_FOOT, 1),
                    new ItemStack(Items.STICKY_PISTON, 3)
            ));}

        //FALL_DAMAGE
        {
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.SLIME_BALL),"player_upgrade",
                    genPattern(
                            "wiiiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiiiw"),
                    new ItemStack(Items.FEATHER, 6),
                    new ItemStack(Items.SLIME_BALL, 2),
                    new ItemStack(Items.LAPIS_BLOCK, 2),
                    new ItemStack(Items.HONEY_BLOCK, 2)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.HONEY_BLOCK),"player_upgrade",
                    genPattern(
                            "wiiiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiiiw"),
                    new ItemStack(Items.CHAIN, 6),
                    new ItemStack(Registration.ENDERIAN_INGOT_ITEM.get(), 4),
                    new ItemStack(Items.IRON_BLOCK, 2)
            ));
        }
    }

    public static List<String> genPattern(String... patten){
        ArrayList<String> pattern = new ArrayList<>();
        Collections.addAll(pattern, patten);
        return pattern;
    }

    public static List<RitualAltarRecipes> getRitualAltarRecipes() {
        init();
        return ritualAltarRecipes;
    }

    public static List<EventRitualsRecipes> getEventRitualRecipes() {
        init();
        return eventRitualsRecipes;
    }

}
