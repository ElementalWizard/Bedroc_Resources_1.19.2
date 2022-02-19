package com.alexvr.bedres.utils.compat112.jei;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.recipes.eventRituals.EventRitualsRecipes;
import com.alexvr.bedres.setup.Registration;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

import static com.alexvr.bedres.recipes.eventRituals.EventRitualsRecipes.getDescription;
import static com.alexvr.bedres.utils.compat112.jei.JEIHelper.EVENT_RITUAL;

public class EventAltarCategory implements IRecipeCategory<EventRitualsRecipes> {

    public static final int width = 167;
    public static final int height = 107;

    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;
    public static final ResourceLocation RECIPE_GUI_EVENT_ALTAR = new ResourceLocation(BedrockResources.MODID, "textures/gui/jei/category/event_ritual.png");

    public EventAltarCategory(IGuiHelper guiHelper) {
        ResourceLocation location = RECIPE_GUI_EVENT_ALTAR;
        background = guiHelper.createDrawable(location, 0, 0, width, height);
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM, new ItemStack(Registration.EVENT_ALTAR_BLOCK.get()));
        localizedName = new TranslatableComponent("jei.category.bedres.event_ritual");
    }

    @Override
    public ResourceLocation getUid() {
        return EVENT_RITUAL;
    }

    @Override
    public Class<? extends EventRitualsRecipes> getRecipeClass() {
        return EventRitualsRecipes.class;
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
    public void setIngredients(EventRitualsRecipes recipe, IIngredients ingredients) {
        ingredients.setInputIngredients(recipe.getIngredients());
        ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
    }

    @Override
    public void draw(EventRitualsRecipes recipe, PoseStack stack, double mouseX, double mouseY) {
        Minecraft minecraft = Minecraft.getInstance();
        int mainColor = 0x1C2E4A;
        drawEventString(minecraft, stack, recipe.getEvent(), mainColor,recipe.getResultItem());
        IRecipeCategory.super.draw(recipe, stack, mouseX, mouseY);
    }

    @Override
    public List<Component> getTooltipStrings(EventRitualsRecipes recipe, double mouseX, double mouseY) {
        List<Component> list = new ArrayList<>();
        if ((mouseX>0 &&mouseX <128) && (mouseY>-22 &&mouseY <6) ){
            String des = getDescription(recipe);
            list.add(new TextComponent(des));
            return list;
        }else if ((mouseX>142 &&mouseX <157) && (mouseY>-87 &&mouseY <99) ){
            list.add(new TextComponent("Wire and item pillars are direction sensitive, this diagram is looking north"));
            return list;
        }

            return IRecipeCategory.super.getTooltipStrings(recipe, mouseX, mouseY);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, EventRitualsRecipes recipe, IIngredients ingredients) {
        var stacks = recipeLayout.getItemStacks();
        var inputs = ingredients.getInputs(VanillaTypes.ITEM);
        var outputs = ingredients.getOutputs(VanillaTypes.ITEM);
        var pattern = recipe.getPattern();
        List<ItemStack> inputStacks = new ArrayList<>();
        for (int i =0; i<inputs.size();i++){
            for (int k =0; k<inputs.get(i).get(0).getCount();k++){
                inputStacks.add(new ItemStack(inputs.get(i).get(0).getItem(),1));
            }
        }
        List<ItemStack> pattenrStacks = new ArrayList<>();
        int count = 0;
        for (String row: pattern){
            for (int c = 0;c<row.length();c++){
                if (row.charAt(c) == 'w'){
                    pattenrStacks.add(new ItemStack(Registration.BEDROCK_WIRE_ITEM.get(),1));
                }else if (row.charAt(c) == 'i'){
                    pattenrStacks.add(inputStacks.get(count++));
                }else{
                    pattenrStacks.add(ItemStack.EMPTY);
                }
            }
        }
        stacks.init(12, true, 45 , 45);
        stacks.set(12, new ItemStack(Registration.EVENT_ALTAR_ITEM.get(),1));
        int x = 6;
        int y= 6;

        for (int i =0; i<pattenrStacks.size();i++){
            if (x==45 && y == 45 || pattenrStacks.get(i).isEmpty()){
                x+=19;
                continue;
            }
            stacks.init(i, true, x , y);
            stacks.set(i, pattenrStacks.get(i));
            if (x==82){
                y+=19;
                x=6;
            }else{
                x+=19;
            }
        }
    }
    @Override
    public boolean isHandled(EventRitualsRecipes recipe) {
        return !recipe.isSpecial();
    }

    private void drawEventString(Minecraft minecraft, PoseStack poseStack, String event, int mainColor, ItemStack output) {

        minecraft.font.draw(poseStack,new TranslatableComponent("container.event." + event + "." + output.getItem().getRegistryName().getPath()), 2, -2, mainColor);
    }

}
