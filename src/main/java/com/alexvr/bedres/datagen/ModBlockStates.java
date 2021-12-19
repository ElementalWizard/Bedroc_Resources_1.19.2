package com.alexvr.bedres.datagen;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class ModBlockStates extends BlockStateProvider {
    public ModBlockStates(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BedrockResources.MODID,existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(Registration.ENDERIAN_ORE_OVERWORLD.get());
        simpleBlock(Registration.ENDERIAN_ORE_NETHER.get());
        simpleBlock(Registration.ENDERIAN_ORE_END.get());
        simpleBlock(Registration.ENDERIAN_ORE_DEEPSLATE.get());
        simpleBlock(Registration.BEDROCK_COMPRESSED_WIRE_BLOCK.get());

    }

}
