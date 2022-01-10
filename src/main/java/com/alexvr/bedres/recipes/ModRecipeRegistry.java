package com.alexvr.bedres.recipes;

import com.alexvr.bedres.recipes.eventRituals.EventRitualsRecipeSerializer;
import com.alexvr.bedres.recipes.eventRituals.EventRitualsRecipes;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipeSerializer;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipes;
import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
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

        eventRitualsRecipes.add(new EventRitualsRecipes(new ItemStack(Registration.STAFF_ITEM.get()),
                genPattern(
                        "wiwiw",
                                "iwwwi",
                                "iw wi",
                                "iwwwi",
                                "wiwiw"),
                new ItemStack(Registration.ENDERIAN_INGOT_ITEM.get(), 10)
        ));

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
