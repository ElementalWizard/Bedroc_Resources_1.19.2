package com.alexvr.bedres.recipes.ritualAltar;

import com.alexvr.bedres.recipes.ModRecipeRegistry;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RitualAltarRecipes implements Recipe<RitualAltarContext> {

    private final ResourceLocation id;
    private final ItemStack destination;
    private final List<ItemStack> ingredients;

    public RitualAltarRecipes(ItemStack destination, ItemStack... ingredients) {
        this.id = destination.getItem().getRegistryName();
        this.destination = destination;
        this.ingredients = new ArrayList<>(ingredients.length);
        Collections.addAll(this.ingredients, ingredients);
    }

    public static RitualAltarRecipes findRecipeFromOutput(ItemStack destination) {
        // @todo optimize
        for (RitualAltarRecipes recipe : ModRecipeRegistry.getRitualAltarRecipes()) {
            if (ItemHandlerHelper.canItemStacksStack(recipe.getDestination(), destination)) {
                return recipe;
            }
        }
        return null;
    }

    public static RitualAltarRecipes findRecipeFromCatalyst(ItemStack stack) {
        // @todo optimize
        for (RitualAltarRecipes recipe : ModRecipeRegistry.getRitualAltarRecipes()) {
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
        for (RitualAltarRecipes recipe : ModRecipeRegistry.getRitualAltarRecipes()) {
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

    public ItemStack getDestination() {
        return destination;
    }

    public List<ItemStack> getIngredientList() {
        return ingredients;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        getIngredientList().forEach(ing -> {
            nonnulllist.add(Ingredient.of(ing));
        });

        return nonnulllist;
    }

    @Override
    public boolean matches(RitualAltarContext inv, Level worldIn) {
        return false;
    }

    @Override
    public ItemStack assemble(RitualAltarContext inv) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return destination;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeRegistry.ALTAR_RECIPES.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeRegistry.ALTAR;
    }

}
