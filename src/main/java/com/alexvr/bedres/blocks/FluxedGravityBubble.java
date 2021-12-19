package com.alexvr.bedres.blocks;

import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FluxedGravityBubble extends Block {

    private static final VoxelShape SHAPE = Block.box(4.5D, 4.0D, 4.5D, 11.5D, 11.5D, 11.5D);

    public FluxedGravityBubble(BlockBehaviour.Properties props) {
        super(props);

    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

}
