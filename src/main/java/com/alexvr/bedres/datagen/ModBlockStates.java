package com.alexvr.bedres.datagen;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

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
        simpleBlock(Registration.LIGHT_BLOCK.get(),models().withExistingParent("light_block",mcLoc("block/air")).texture("particle","block/light_block"));

    }

}
