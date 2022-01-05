package com.alexvr.bedres.recipes;

import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipeSerializer;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipes;
import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
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

    private static List<RitualAltarRecipes> recipes = new ArrayList<>();

    private static boolean initialized = false;
    private static void init() {
        if (initialized) {
            return;
        }
        initialized = true;
        //pedestal item must be first in index
        recipes.add(new RitualAltarRecipes(new ItemStack(Registration.STAFF_ITEM.get()),
                new ItemStack(Registration.VOID_TEAR_ITEM.get(), 1),
                new ItemStack(Registration.BLAZIUM_ITEM.get(), 1),
                new ItemStack(Registration.ENDERIAN_INGOT_ITEM.get(), 4)
        ));
    }

    public static List<RitualAltarRecipes> getRecipes() {
        init();
        return recipes;
    }

    public static RitualAltarRecipes findRecipeFromOutput(ItemStack destination) {
        // @todo optimize
        for (RitualAltarRecipes recipe : getRecipes()) {
            if (ItemHandlerHelper.canItemStacksStack(recipe.getDestination(), destination)) {
                return recipe;
            }
        }
        return null;
    }

    public static RitualAltarRecipes findRecipeFromCatalyst(ItemStack stack) {
        // @todo optimize
        for (RitualAltarRecipes recipe : getRecipes()) {
            ItemStack stack2 = recipe.getIngredientList().get(0);
            if (stack2.is(stack.getItem()) && stack2.equals(stack,false)){
                return recipe;
            }
        }
        return null;
    }

    //pedestal item must be first in index
    public static RitualAltarRecipes findRecipeFromIngrent(List<ItemStack> ing) {
        // @todo optimize
        for (RitualAltarRecipes recipe : getRecipes()) {
            boolean validRec = true;
            List<ItemStack> ingCopy = new ArrayList<>(ing);

            for (ItemStack stack: recipe.getIngredientList()){
                boolean valid = false;
                for (ItemStack stack2: ing){
                    if (stack2.is(stack.getItem()) && stack2.equals(stack,false)){
                        valid = true;
                        ingCopy.remove(stack2);
                        break;
                    }
                }
                if (!valid){
                    validRec = false;
                    break;
                }
            }
            if (validRec && ingCopy.isEmpty()){
                return recipe;
            }

        }
        return null;
    }

}
