package com.alexvr.bedres.recipes.ritualAltar;

import com.alexvr.bedres.recipes.ModRecipeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

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

    public ItemStack getDestination() {
        return destination;
    }

    public List<ItemStack> getIngredientList() {
        return ingredients;
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
