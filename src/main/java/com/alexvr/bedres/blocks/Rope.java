package com.alexvr.bedres.blocks;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class Rope extends RotatedPillarBlock implements SimpleWaterloggedBlock  {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty CHAIN = BlockStateProperties.ENABLED;
    public static final IntegerProperty PLACING = IntegerProperty.create("placing", 0, 128);

    protected static final VoxelShape Y_AXIS_AABB = Block.box(6.5D, 0.0D, 6.5D, 9.5D, 16.0D, 9.5D);
    public Rope(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(AXIS, Direction.Axis.Y).setValue(CHAIN, Boolean.valueOf(false)).setValue(PLACING, 0));

    }


    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @org.jetbrains.annotations.Nullable LivingEntity pPlacer, ItemStack pStack) {
        if (pPlacer instanceof Player player){
            if (pLevel.getBlockState(pPos.below()).is(Blocks.AIR) && player.isCreative()){
                pLevel.setBlock(pPos.below(),Registration.ROPE_BLOCK.get().stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(AXIS, Direction.Axis.Y).setValue(CHAIN, Boolean.valueOf(true)).setValue(PLACING, 128),3);

            }
            else if (pLevel.getBlockState(pPos.below()).is(Blocks.AIR) && player.getInventory().contains(new ItemStack(Registration.ROPE_ITEM.get()))){
                int count = 0;
                BlockPos looking = new BlockPos(pPos);
                while(pLevel.getBlockState(looking.below()).is(Blocks.AIR)){
                    count++;
                    looking = looking.below();
                }
                pLevel.setBlock(pPos.below(),Registration.ROPE_BLOCK.get().stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(AXIS, Direction.Axis.Y).setValue(CHAIN, Boolean.valueOf(true)).setValue(PLACING, count),3);

                player.getInventory().removeItem(player.getInventory().findSlotMatchingItem(new ItemStack(Registration.ROPE_ITEM.get())),count);
            }
        }

        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (pLevel.getBlockState(pPos.below()).is(Blocks.AIR) && pState.getValue(CHAIN) && pState.getValue(PLACING) > 0){
            pLevel.setBlock(pPos.below(),Registration.ROPE_BLOCK.get().stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(AXIS, Direction.Axis.Y).setValue(CHAIN, Boolean.valueOf(true)).setValue(PLACING, pState.getValue(PLACING) - 1),3);

        }
        super.onPlace(pState, pLevel, pPos, pOldState, pIsMoving);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Y_AXIS_AABB;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WATERLOGGED).add(AXIS).add(CHAIN).add(PLACING);
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if ((pFromPos.equals(pPos.below()) || pFromPos.equals(pPos.above())) && pLevel.getBlockState(pFromPos).is(Blocks.AIR)){
            pLevel.destroyBlock(pPos,true);
        }
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
    }

    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
        return false;
    }
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        return super.getStateForPlacement(pContext).setValue(WATERLOGGED, Boolean.valueOf(flag));
    }
    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }

}
