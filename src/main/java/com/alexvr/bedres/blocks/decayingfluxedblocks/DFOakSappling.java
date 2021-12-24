package com.alexvr.bedres.blocks.decayingfluxedblocks;

import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.world.ModFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraftforge.common.PlantType;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.Random;

public class DFOakSappling  extends BushBlock implements BonemealableBlock{


        public DFOakSappling(BlockBehaviour.Properties props) {
        super(props);

    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random) {
        if (!level.isClientSide) {
            super.randomTick(state, level, pos, random);

            if (level.getLightEmission(pos.above()) >= 9 && random.nextInt(7) == 0)
                this.performBonemeal(level, random, pos, state);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SaplingBlock.STAGE);
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter p_50897_, BlockPos p_50898_, BlockState p_50899_, boolean p_50900_) {
        return true;
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.PLAINS;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return super.mayPlaceOn(pState, pLevel, pPos) || pState.is(Registration.DF_DIRT_BLOCK.get()) || pState.is(Registration.DF_GRASS_BLOCK.get());
    }
    @Override
    public boolean isBonemealSuccess(Level level, Random rand, BlockPos pos, BlockState state) {
        return level.random.nextFloat() < 0.45F;
    }
    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        DFBase.Spread(worldIn, pos);
        super.animateTick(stateIn, worldIn, pos, rand);
    }

    @Override
    public void performBonemeal(ServerLevel level, Random rand, BlockPos pos, BlockState state) {
        if (state.getValue(SaplingBlock.STAGE) == 0) {
            level.setBlock(pos, state.cycle(SaplingBlock.STAGE), 4);
        } else if (ForgeEventFactory.saplingGrowTree(level, rand, pos)) {
            ModFeatures.Configured.DF_TREE.place(level, level.getChunkSource().getGenerator(), rand, pos);
        }
    }

}
