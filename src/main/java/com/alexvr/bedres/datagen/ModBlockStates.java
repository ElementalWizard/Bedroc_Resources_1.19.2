package com.alexvr.bedres.datagen;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.BiConsumer;

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
    private BiConsumer<Direction, ModelBuilder<BlockModelBuilder>.ElementBuilder.FaceBuilder> addTexture(String texture) {
        return ($, f) -> f.texture(texture).uvs(0,0,16,16).tintindex(0);
    }
}
