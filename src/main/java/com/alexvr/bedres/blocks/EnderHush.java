package com.alexvr.bedres.blocks;

import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.PlantType;

public class EnderHush extends FlowerBlock {

    public EnderHush(BlockBehaviour.Properties props) {
        super(MobEffects.NIGHT_VISION, 5, props);
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.PLAINS;
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(Registration.ENDERIAN_BRICK_BLOCK.get()) || pState.is(Blocks.FIRE) || super.mayPlaceOn(pState, pLevel, pPos);
    }
}
