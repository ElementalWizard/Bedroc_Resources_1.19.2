package com.alexvr.bedres.blocks.decayingfluxedblocks;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class DFBase extends Block {


    public DFBase(BlockBehaviour.Properties props) {
        super(props.randomTicks());

    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, Random pRandom) {
        Spread(pLevel, pPos);
        super.animateTick(pState, pLevel, pPos, pRandom);
    }



    static void Spread(Level worldIn, BlockPos pos) {
        if (new Random().nextInt(1200) < 2){
            BlockPos postition = pos;
            switch (new Random().nextInt(6)){
                case 0:
                    postition = pos.below();
                    break;
                case 1:
                    postition = pos.above();
                    break;
                case 2:
                    postition = pos.south();
                    break;
                case 3:
                    postition = pos.north();
                    break;
                case 4:
                    postition = pos.west();
                    break;
                case 5:
                    postition = pos.east();
                    break;
            }

            transform(worldIn,postition);


        }
    }

    public static void transform(Level worldIn, BlockPos postition) {
        if (worldIn.getBlockState(postition).getBlock() == Blocks.GRASS_BLOCK){
            worldIn.setBlockAndUpdate(postition, Registration.DF_GRASS_BLOCK.get().defaultBlockState());
            particle(postition);
        }else if (worldIn.getBlockState(postition).getBlock() == Blocks.DIRT){
            worldIn.setBlockAndUpdate(postition, Registration.DF_DIRT_BLOCK.get().defaultBlockState());
            particle(postition);

        }else if (worldIn.getBlockState(postition).getBlock() == Blocks.COBBLESTONE){
            worldIn.setBlockAndUpdate(postition, Registration.DF_COOBLE_BLOCK.get().defaultBlockState());
            particle(postition);

        }else if (worldIn.getBlockState(postition).getBlock() == Blocks.OAK_LOG){
            worldIn.setBlockAndUpdate(postition, Registration.DF_OAK_LOG_BLOCK.get().defaultBlockState());
            particle(postition);

        }else if (worldIn.getBlockState(postition).getBlock() == Blocks.OAK_PLANKS){
            worldIn.setBlockAndUpdate(postition, Registration.DF_OAK_PLANKS_BLOCK.get().defaultBlockState());
            particle(postition);

        }else if (worldIn.getBlockState(postition).getBlock() == Blocks.OAK_LEAVES){
            worldIn.setBlockAndUpdate(postition, Registration.DF_OAK_LEAVE_BLOCK.get().defaultBlockState());
            particle(postition);

        }else if (worldIn.getBlockState(postition).getBlock() == Blocks.OAK_SLAB){
            worldIn.setBlockAndUpdate(postition, Registration.DF_OAK_SLAB_BLOCK.get().defaultBlockState());
            particle(postition);

        }else if (worldIn.getBlockState(postition).getBlock() == Blocks.STRIPPED_OAK_LOG){
            worldIn.setBlockAndUpdate(postition, Registration.DF_STRIPPED_OAK_LOG_BLOCK.get().defaultBlockState());
            particle(postition);
        }
    }


        static void particle(BlockPos pos){
        int multiplier = 0;
        for (int i =0; i< 16; i++){
            Minecraft.getInstance().levelRenderer.addParticle(ParticleTypes.PORTAL,true,pos.getX()+0.5f-(new Random().nextFloat()-0.5),pos.getY()+1,pos.getZ()+0.5f-(new Random().nextFloat()-0.5),new Random().nextFloat()*multiplier,new Random().nextFloat()*multiplier,new Random().nextFloat()*multiplier);
        }



    }
}
