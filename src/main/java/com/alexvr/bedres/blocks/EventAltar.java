package com.alexvr.bedres.blocks;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.recipes.eventRituals.EventRitualsRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EventAltar extends Block {

    private static final VoxelShape SHAPE = Block.box(.1D, 0D, .1D, 16D, 3D, 16D);


    public EventAltar(Properties props) {
        super(props);

    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof Player player){
            EventRitualsRecipes recipe = EventRitualsRecipes.findRecipeFromPattern(EventRitualsRecipes.getPatterForRecipeFromWorld(player.level,player.blockPosition(),EventRitualsRecipes.patternRadius,EventRitualsRecipes.patternRadius));
            EventRitualsRecipes recipe2 = EventRitualsRecipes.findRecipeFromIngrent(EventRitualsRecipes.getItemsForRecipeFromWordl(player.level,player.blockPosition(),EventRitualsRecipes.patternRadius,EventRitualsRecipes.patternRadius));
            if (recipe != null){
                BedrockResources.LOGGER.debug("Pattern found: " + recipe.getPattern());
            }
            if (recipe2 != null){
                BedrockResources.LOGGER.debug("Items found: " + recipe2.getIngredientList());
            }
            if (recipe != null && recipe.equals(recipe2)){
                BedrockResources.LOGGER.debug("Ritual Starts Here");
            }
        }
        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
