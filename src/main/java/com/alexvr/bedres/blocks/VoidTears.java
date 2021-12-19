package com.alexvr.bedres.blocks;

import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoidTears extends Block {

    private static final VoxelShape SHAPE = Block.box(6D, 5D, 6D, 10D, 9D, 10D);


    public VoidTears(BlockBehaviour.Properties props) {
        super(props);

    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }
}
