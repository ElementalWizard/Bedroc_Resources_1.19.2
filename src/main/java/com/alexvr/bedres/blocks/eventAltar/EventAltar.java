package com.alexvr.bedres.blocks.eventAltar;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class EventAltar extends Block implements EntityBlock {

    private final static VoxelShape SHAPE = Block.box(.1D, 0D, .1D, 16D, 3D, 16D);
    private final static VoxelShape LEFTSHAPE = Block.box(-6D, 0D, 4D, 1D, 28D, 13D);
    private final static VoxelShape RIGHTSHAPE = Block.box(15D, 0D, 4D, 24D, 28D, 13D);


    public EventAltar(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any());

    }


    public boolean useShapeForLightOcclusion(BlockState pState) {
        return true;
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {

        super.animateTick(pState, pLevel, pPos, pRandom);
    }



    @org.jetbrains.annotations.Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return (level1, blockPos, blockState, t) -> {
            if (t instanceof EventAltarTile tile) {
                tile.tick();
            }
        };
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
