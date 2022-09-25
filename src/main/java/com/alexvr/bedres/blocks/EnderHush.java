package com.alexvr.bedres.blocks;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class EnderHush extends FlowerBlock {

    public EnderHush(Properties props) {
        super(MobEffects.NIGHT_VISION, 5, props);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(Registration.ENDERIAN_BRICK_BLOCK.get()) || pState.is(Blocks.FIRE) || super.mayPlaceOn(pState, pLevel, pPos);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (new Random().nextInt(500) == 1){
            pLevel.getNearestPlayer(pPos.getX(),pPos.getY(),pPos.getZ(),8,false).randomTeleport(pPos.getX(),pPos.getY(),pPos.getZ(),true);

        }
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelIn, BlockPos pos) {
        var down = pos.below();
        return this.mayPlaceOn(levelIn.getBlockState(down), levelIn, down);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context) {
        var vec3d = state.getOffset(levelIn, pos);
        return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
    }
}
