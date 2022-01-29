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
        for (int i =1; i<inputs.size();i++){
            for (int k =0; k<inputs.get(i).get(0).getCount();k++){
                inputStacks.add(new ItemStack(inputs.get(i).get(0).getItem(),1));
            }
        }

        //inputs
        {
            int x = 0;
            int y= 0;
            int moduleIndex= 0;
            int moduleX= 1;
            int moduleY= 0;
            boolean nextModule = false;
            stacks.init(0, true, 48,47);
            stacks.set(0, inputs.get(0).get(0));

            for (int i =0; i<inputStacks.size();i++){
                switch (i % 4) {
                    case 0 -> {
                        x = (moduleX * 36);
                        y =(moduleY * 35);
                        nextModule = false;
                    }
                    case 1 -> {
                        x = 19 + (moduleX * 36);
                        y =(moduleY * 35);
                    }
                    case 2 -> {
                        x =(moduleX * 36);
                        y = 18 + (moduleY * 35);
                    }
                    case 3 -> {
                        x = 19 + (moduleX * 36);
                        y = 18 + (moduleY * 35);
                        nextModule = true;
                    }
                }
                if (nextModule){
                    moduleIndex++;
                    switch (moduleIndex){
                        case 1 -> {moduleX = 0;moduleY = 1;}
                        case 2 -> {moduleX = 2;moduleY = 1;}
                        case 3 -> {moduleX = 1;moduleY = 2;}
                        case 4 -> {moduleX = 0;moduleY = 0;}
                        case 5 -> {moduleX = 2;moduleY = 0;}
                        case 6 -> {moduleX = 0;moduleY = 2;}
                        case 7 -> {moduleX = 2;moduleY = 2;}
                    }
                }

                stacks.init(i+1, true, x  , y);
                stacks.set(i+1, inputStacks.get(i));
            }

        }

        stacks.init(inputStacks.size()+1, false, 146, 46);
        stacks.set(inputStacks.size()+1, outputs.get(0));
    }
    @Override
    public boolean isHandled(RitualAltarRecipes recipe) {
        return !recipe.isSpecial();
    }
}
