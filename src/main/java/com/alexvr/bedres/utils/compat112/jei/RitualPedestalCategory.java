package com.alexvr.bedres.utils.compat112.jei;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipes;
import com.alexvr.bedres.setup.Registration;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.alexvr.bedres.utils.compat112.jei.JEIHelper.RITUAL_ALTAR;

public class RitualPedestalCategory implements IRecipeCategory<RitualAltarRecipes> {

    public static final int width = 167;
    public static final int height = 107;

    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;

    public static final ResourceLocation RECIPE_GUI_PEDESTAL = new ResourceLocation(BedrockResources.MODID, "textures/gui/jei/category/pedestal.png");

    public RitualPedestalCategory(IGuiHelper guiHelper) {
        ResourceLocation location = RECIPE_GUI_PEDESTAL;
        background = guiHelper.createDrawable(location, 0, 0, width, height);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(Registration.PEDESTAL_BLOCK.get()));
        localizedName = new TranslatableComponent("jei.category.bedres.pedestal");
    }

    @Override
    public ResourceLocation getUid() {
        return RITUAL_ALTAR;
    }

    @Override
    public Class<? extends RitualAltarRecipes> getRecipeClass() {
        return RitualAltarRecipes.class;
    }

    @Override
    public Component getTitle() {
        return localizedName;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setIngredients(RitualAltarRecipes recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, RitualAltarRecipes recipe, IIngredients ingredients) {
        var stacks = recipeLayout.getItemStacks();
        var inputs = ingredients.getInputs(VanillaTypes.ITEM);
        var outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        List<ItemStack> inputStacks = new ArrayList<>();
        for (int i =0; i<inputs.size();i++){
            for (int k =0; k<inputs.get(i).get(0).getCount();k++){
                inputStacks.add(new ItemStack(inputs.get(i).get(0).getItem(),1));
            }
        }

        //inputs
        {
            int x = 0;
            int y= 0;

            for (int i =0; i<inputStacks.size();i++){
                switch (i) {
                    case 0 -> {
                        x = 46;
                        y = 43;
                    }
                    case 1 -> {
                        x = 36;
                        y = 0;
                    }
                    case 2 -> {
                        x = 55;
                        y = 0;
                    }
                    case 3 -> {
                        x = 36;
                        y = 18;
                    }
                    case 4 -> {
                        x = 55;
                        y = 18;
                    }
                    case 5 -> {
                        x = 72;
                        y = 35;
                    }
                    case 6 -> {
                        x = 91;
                        y = 35;
                    }
                    case 7 -> {
                        x = 72;
                        y = 41;
                    }
                    case 8 -> {
                        x = 91;
                        y = 41;
                    }
                    case 9 -> {
                        x = 0;
                        y = 35;
                    }
                    case 10 -> {
                        x = 19;
                        y = 35;
                    }
                    case 11 -> {
                        x = 0;
                        y = 53;
                    }
                    case 12 -> {
                        x = 19;
                        y = 53;
                    }
                    case 13 -> {
                        x = 36;
                        y = 70;
                    }
                    case 14 -> {
                        x = 55;
                        y = 70;
                    }
                    case 15 -> {
                        x = 36;
                        y = 88;
                    }
                    case 16 -> {
                        x = 55;
                        y = 88;
                    }
                }
                stacks.init(i, true, x  , y);
                stacks.set(i, inputStacks.get(i));
            }

        }

        stacks.init(inputStacks.size(), false, 146, 46);
        stacks.set(inputStacks.size(), outputs.get(0));
    }
    @Override
    public boolean isHandled(RitualAltarRecipes recipe) {
        return !recipe.isSpecial();
    }
}
