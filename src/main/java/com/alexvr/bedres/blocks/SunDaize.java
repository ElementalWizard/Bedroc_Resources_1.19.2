package com.alexvr.bedres.blocks;


import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class SunDaize extends FlowerBlock {

    public SunDaize(BlockBehaviour.Properties props) {
        super(MobEffects.NIGHT_VISION, 5, props);
    }

}
