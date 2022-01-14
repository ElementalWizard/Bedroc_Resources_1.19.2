package com.alexvr.bedres.blocks.eventAltar;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.blocks.enderianRitualPedestal.EnderianRitualPedestalTile;
import com.alexvr.bedres.recipes.eventRituals.EventRitualsRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestal.CRAFTING;
import static com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestal.VALIDRECIPE;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class EventAltar extends Block implements EntityBlock {

    private final static VoxelShape SHAPE = Block.box(.1D, 0D, .1D, 16D, 3D, 16D);
    private final static VoxelShape LEFTSHAPE = Block.box(-6D, 0D, 4D, 1D, 28D, 13D);
    private final static VoxelShape RIGHTSHAPE = Block.box(15D, 0D, 4D, 24D, 28D, 13D);
    String particlDirection = "up";
    int height = 2;
    double yIncrement = 0.01;
    double radius = 1.5;
    double a = 0;
    double x = 0;
    double y = 0;
    double z = 0;

    public EventAltar(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(CRAFTING, Boolean.valueOf(false)).setValue(VALIDRECIPE, Boolean.valueOf(false)));

    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof Player player && pLevel.getBlockEntity(pPos) instanceof EventAltarTile tile){
            List<EnderianRitualPedestalTile> extensions = EventRitualsRecipes.getTilesForRecipeFromWorld(pLevel,pPos,EventRitualsRecipes.patternRadius,EventRitualsRecipes.patternRadius);
            if(!tile.getBlockState().getValue(CRAFTING)) {
                if (!tile.getBlockState().getValue(VALIDRECIPE)){
                    List<EventRitualsRecipes> recipe = EventRitualsRecipes.findRecipeFromPattern(EventRitualsRecipes.getPatterForRecipeFromWorld(player.level,player.blockPosition(),EventRitualsRecipes.patternRadius,EventRitualsRecipes.patternRadius));
                    EventRitualsRecipes recipe2 = EventRitualsRecipes.findRecipeFromIngrent(EventRitualsRecipes.getItemsFromTiles(extensions));
                    if (!recipe.isEmpty()){
                        BedrockResources.LOGGER.info("Patterns found: " + recipe.get(0).getPattern() + " Amount: " + recipe.size());
                    }
                    if (recipe2 != null){
                        BedrockResources.LOGGER.info("Items found: " + recipe2.getIngredientList());
                    }
                    if (!recipe.isEmpty() && recipe2 != null && recipe.contains(recipe2)){
                        tile.tiles = extensions;
                        tile.recipe = recipe2;
                        tile.target = 0;
                        tile.updateValidRecipe(true);
                        BedrockResources.LOGGER.info("Valid Recipe Found");
                    }else{
                        BedrockResources.LOGGER.info("Recipes dont match: ");
                    }
                }else if (tile.getBlockState().getValue(VALIDRECIPE) && player.isShiftKeyDown()){
                    tile.updateCrafting(true);
                }

            }
        }
        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
        if (pState.getValue(CRAFTING)){
            summonParticles(pLevel,pPos.above(),ParticleTypes.LARGE_SMOKE);
        }else if (pState.getValue(VALIDRECIPE)){
            summonParticles(pLevel,pPos.above(),ParticleTypes.REVERSE_PORTAL);
        }
        super.animateTick(pState, pLevel, pPos, pRandom);
    }

    private void summonParticles(Level pLevel, BlockPos player, SimpleParticleType particleType) {

        x = (cos(a) * radius);
        z = sin(a) * radius;
        pLevel.addParticle(particleType,player.getX() + x,player.getY() + y,player.getZ() + z,0,0,0);
        a++;
        if(particlDirection.equals("up"))
        {
            if(y >= height)
            {
                particlDirection = "down";
                y -= yIncrement;
            }
            else
            {
                y += yIncrement;
            }
        }
        else
        {
            if(y <= 0)
            {
                particlDirection = "up";
                y += yIncrement;
            }
            else
            {
                y -= yIncrement;
            }
        }

        if(a >= 360){a = 0;} //reset a to stop it getting too large

    }

    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (level1, blockPos, blockState, t) -> {
            if (t instanceof EventAltarTile tile) {
                tile.tickServer();
            }
        };
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(CRAFTING).add(VALIDRECIPE);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.or(SHAPE, LEFTSHAPE,RIGHTSHAPE);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EventAltarTile(pPos, pState);
    }
}
