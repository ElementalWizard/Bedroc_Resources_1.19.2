package com.alexvr.bedres.blocks;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class SunDaize extends FlowerBlock {

    public SunDaize(Properties props) {
        super(MobEffects.NIGHT_VISION, 5, props);
    }

    @Override
    protected boolean mayPlaceOn(BlockState p_51042_, BlockGetter p_51043_, BlockPos p_51044_) {
        return super.mayPlaceOn(p_51042_, p_51043_, p_51044_) || p_51042_.is(BlockTags.SAND);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelIn, BlockPos pos) {
        var down = pos.below();
        return super.mayPlaceOn(levelIn.getBlockState(down), levelIn, down);
    }
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (new Random().nextInt(500) == 1){
            pLevel.getNearestPlayer(pPos.getX(),pPos.getY(),pPos.getZ(),8,false).addEffect(new MobEffectInstance(MobEffects.GLOWING,30));
        }
        super.randomTick(pState, pLevel, pPos, pRandom);
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context) {
        var vec3d = state.getOffset(levelIn, pos);
        return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
    }
}
