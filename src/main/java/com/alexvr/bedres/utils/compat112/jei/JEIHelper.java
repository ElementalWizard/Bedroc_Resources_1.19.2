package com.alexvr.bedres.utils.compat112.jei;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.recipes.ModRecipeRegistry;
import com.alexvr.bedres.recipes.eventRituals.EventRitualsRecipes;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipes;
import com.alexvr.bedres.setup.Registration;
import mezz.jei.Internal;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.gui.textures.Textures;
import mezz.jei.util.ErrorUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

@JeiPlugin
public class JEIHelper implements IModPlugin {

    public static  ResourceLocation RITUAL_ALTAR = new ResourceLocation(BedrockResources.MODID, "ritual_altar");
    public static  ResourceLocation EVENT_RITUAL = new ResourceLocation(BedrockResources.MODID, "event_ritual");

    @Nullable
    private IRecipeCategory<RitualAltarRecipes> altarRecipesIRecipeCategory;

    @Nullable
    private IRecipeCategory<EventRitualsRecipes> eventRecipesIRecipeCategory;
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(BedrockResources.MODID, "jei_helper");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        Textures textures = Internal.getTextures();
        IJeiHelpers jeiHelpers = registration.getJeiHelpers();
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
        registration.addRecipeCategories(
                altarRecipesIRecipeCategory = new RitualPedestalCategory(guiHelper),
                eventRecipesIRecipeCategory = new EventAltarCategory(guiHelper)
        );

    }
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Registration.PEDESTAL_BLOCK.get()), RITUAL_ALTAR);
        registration.addRecipeCatalyst(new ItemStack(Registration.EVENT_ALTAR_BLOCK.get()), EVENT_RITUAL);
    }
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ErrorUtil.checkNotNull(altarRecipesIRecipeCategory, "altarCategory");
        ErrorUtil.checkNotNull(eventRecipesIRecipeCategory, "eventCategory");
        registration.addRecipes(ModRecipeRegistry.getRitualAltarRecipes(), RITUAL_ALTAR);
        registration.addRecipes(ModRecipeRegistry.getEventRitualRecipes(), EVENT_RITUAL);

    }
}
