package com.alexvr.bedres.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class Blazium extends FlowerBlock {

    public Blazium(BlockBehaviour.Properties props) {
        super(MobEffects.NIGHT_VISION, 5, props);
    }

    @Override
    public PlantType getPlantType(BlockGetter world, BlockPos pos) {
        return PlantType.NETHER;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader levelIn, BlockPos pos) {
        var down = pos.below();
        return this.mayPlaceOn(levelIn.getBlockState(down), levelIn, down);
    }
    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, Random pRandom) {
        if (new Random().nextInt(500) == 1){
            pLevel.getNearestPlayer(pPos.getX(),pPos.getY(),pPos.getZ(),8,false).setSecondsOnFire(8);
        }
        super.randomTick(pState, pLevel, pPos, pRandom);
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context) {
        var vec3d = state.getOffset(levelIn, pos);
        return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
    }
    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(Blocks.NETHERRACK) || pState.is(Blocks.SOUL_SAND) || pState.is(BlockTags.NYLIUM);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        player.setSecondsOnFire(15);
        return super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

}
