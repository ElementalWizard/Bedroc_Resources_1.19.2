package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.recipes.eventRituals.EventRitualsRecipes;
import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, value = Dist.CLIENT)
public class WorldEventHandler {

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event){
        BlockPos playerPos = new BlockPos(event.getEntityLiving().getX(),event.getEntityLiving().getY(),event.getEntityLiving().getZ());
        if (event.getEntityLiving() instanceof Player player && event.getSource() == DamageSource.IN_FIRE && event.getEntityLiving().level.getBlockState(playerPos.below()).getBlock() == Registration.ENDERIAN_BLOCK_BLOCK.get()){
            EventRitualsRecipes recipe = EventRitualsRecipes.findRecipeFromPattern(EventRitualsRecipes.getPatterForRecipeFromWorld(player.level,player.blockPosition(),EventRitualsRecipes.patternRadius,EventRitualsRecipes.patternRadius));
            EventRitualsRecipes recipe2 = EventRitualsRecipes.findRecipeFromIngrent(EventRitualsRecipes.getItemsForRecipeFromWordl(player.level,player.blockPosition(),EventRitualsRecipes.patternRadius,EventRitualsRecipes.patternRadius));
            if (recipe != null){
                BedrockResources.LOGGER.info("Pattern found: " + recipe.getPattern());
            }
            if (recipe2 != null){
                BedrockResources.LOGGER.info("Items found: " + recipe2.getIngredientList());
            }
            if (recipe != null && recipe.equals(recipe2)){
                BedrockResources.LOGGER.info("CRAFTING");
                event.setCanceled(true);
            }
            player.setRemainingFireTicks(0);
        }
    }

}
