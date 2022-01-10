package com.alexvr.bedres.recipes.eventRituals;

import com.alexvr.bedres.recipes.ModRecipeRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventRitualsRecipes implements Recipe<EventRitualsContext> {

    private final ResourceLocation id;
    private final ItemStack destination;
    private final List<ItemStack> ingredients;
    private final List<String> pattern; // w for wire, i for item

    public EventRitualsRecipes(ItemStack destination, List<String> pattern, ItemStack... ingredients) {
        this.id = destination.getItem().getRegistryName();
        this.destination = destination;
        this.ingredients = new ArrayList<>(ingredients.length);
        this.pattern = pattern;
        Collections.addAll(this.ingredients, ingredients);
    }

    public static EventRitualsRecipes findRecipeFromOutput(ItemStack destination) {
        for (EventRitualsRecipes recipe : ModRecipeRegistry.getEventRitualRecipes()) {
            if (ItemHandlerHelper.canItemStacksStack(recipe.getDestination(), destination)) {
                return recipe;
            }
        }
        return null;
    }
    public static EventRitualsRecipes findRecipeFromPattern(List<String> pat) {
        for (EventRitualsRecipes recipe : ModRecipeRegistry.getEventRitualRecipes()) {
            boolean validRec = true;
            List<String> ingCopy = new ArrayList<>(pat);

            for (String row: recipe.getPattern()){
                boolean valid = false;
                for (String row2: pat){
                    if (row2.equals(row)){
                        valid = true;
                        ingCopy.remove(row2);
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

    public static EventRitualsRecipes findRecipeFromIngrent(List<ItemStack> ing) {
        // @todo optimize
        for (EventRitualsRecipes recipe : ModRecipeRegistry.getEventRitualRecipes()) {
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
    public boolean matches(EventRitualsContext inv, Level worldIn) {
        return false;
    }

    @Override
    public ItemStack assemble(EventRitualsContext inv) {
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

    public List<String> getPattern() {
        return pattern;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeRegistry.EVENT_RITUAL_RECIPES.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeRegistry.EVENT_RITUAL;
    }
}
