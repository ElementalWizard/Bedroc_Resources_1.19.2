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
        //pedestal item must be first in index
        ritualAltarRecipes.add(new RitualAltarRecipes(new ItemStack(Registration.STAFF_ITEM.get()),
                new ItemStack(Registration.VOID_TEAR_ITEM.get(), 1),
                new ItemStack(Registration.BLAZIUM_ITEM.get(), 1),
                new ItemStack(Registration.ENDERIAN_INGOT_ITEM.get(), 4)
        ));

        eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.WATER_BUCKET),"rain",
                genPattern(
                        "wiwiw",
                                "iwwwi",
                                "iw wi",
                                "iwwwi",
                                "wiwiw"),
                new ItemStack(Registration.ENDERIAN_INGOT_ITEM.get(), 10)
        ));

        eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.BUCKET),"rain",
                genPattern(
                        "wiwiw",
                                "iwwwi",
                                "iw wi",
                                "iwwwi",
                                "wiwiw"),
                new ItemStack(Registration.DF_OAK_LEAVE_BLOCK.get(), 10)
        ));

        setUpEventRitualToolRecipes();



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
                    new ItemStack(Items.WOODEN_PICKAXE, 10)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.IRON_PICKAXE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.IRON_PICKAXE, 10)
            ));
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.GOLDEN_PICKAXE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.GOLDEN_PICKAXE, 10)
            ));
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.DIAMOND_PICKAXE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.DIAMOND_PICKAXE, 10)
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
                    new ItemStack(Items.WOODEN_AXE, 10)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.IRON_AXE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.IRON_AXE, 10)
            ));
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.GOLDEN_AXE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.GOLDEN_AXE, 10)
            ));
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.DIAMOND_AXE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.DIAMOND_AXE, 10)
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
                    new ItemStack(Items.WOODEN_SWORD, 10)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.IRON_SWORD),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.IRON_SWORD, 10)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.DIAMOND_SWORD),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.DIAMOND_SWORD, 10)
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
                    new ItemStack(Items.WOODEN_HOE, 10)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.IRON_HOE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.IRON_HOE, 10)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.DIAMOND_HOE),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.DIAMOND_HOE, 10)
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
                    new ItemStack(Items.WOODEN_SHOVEL, 10)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.IRON_SHOVEL),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.IRON_SHOVEL, 10)
            ));
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.GOLDEN_SHOVEL),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.GOLDEN_SHOVEL, 10)
            ));
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.DIAMOND_SHOVEL),"tool",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.DIAMOND_SHOVEL, 10)
            ));
        }

        //SPEED
        {
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.REDSTONE),"player_upgrade",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.REDSTONE, 10)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Registration.BEDROCK_WIRE_ITEM.get()),"player_upgrade",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Registration.BEDROCK_WIRE_ITEM.get(), 10)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Registration.ROPE_ITEM.get()),"player_upgrade",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Registration.ROPE_ITEM.get(), 10)
            ));

        }

        //JUMP
        {
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.RABBIT_FOOT),"player_upgrade",
                genPattern(
                        "wiwiw",
                        "iwwwi",
                        "iw wi",
                        "iwwwi",
                        "wiwiw"),
                new ItemStack(Items.RABBIT_FOOT, 10)
        ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.RABBIT_HIDE),"player_upgrade",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.RABBIT_HIDE, 10)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.CHAIN),"player_upgrade",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.CHAIN, 10)
            ));}

        //FALL_DAMAGE
        {
            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.SLIME_BALL),"player_upgrade",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.SLIME_BALL, 10)
            ));

            eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Items.HONEY_BLOCK),"player_upgrade",
                    genPattern(
                            "wiwiw",
                            "iwwwi",
                            "iw wi",
                            "iwwwi",
                            "wiwiw"),
                    new ItemStack(Items.HONEY_BLOCK, 10)
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
