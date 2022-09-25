package com.alexvr.bedres.blocks.decayingfluxedblocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class DFOakStrippedLog extends RotatedPillarBlock {


    public DFOakStrippedLog(Properties props) {
        super(props.randomTicks());

    }
    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        DFBase.Spread(worldIn, pos);
        super.animateTick(stateIn, worldIn, pos, rand);
    }



}
