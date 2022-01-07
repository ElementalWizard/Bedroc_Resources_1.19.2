package com.alexvr.bedres.world.features;

import com.alexvr.bedres.BedrockResources;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class DungeonStructure extends StructureFeature<JigsawConfiguration> {

    public DungeonStructure(boolean overworld) {
        super(JigsawConfiguration.CODEC, context -> createPiecesGenerator(context, overworld), PostPlacementProcessor.NONE);
    }


    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.UNDERGROUND_STRUCTURES;
    }


    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context,  boolean overworld) {
        // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(50);

        if (overworld) {
            // If we are generating for the overworld we want our portal to spawn underground. Preferably in an open area
            blockpos = findSuitableSpot(context, blockpos);
        }

        var newConfig = new JigsawConfiguration(
                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                        .get(new ResourceLocation(BedrockResources.MODID, "dungeon")),
                32       // In our case our structure is 1 chunk only but by using 5 here it can be replaced with something larger in datapacks
        );

        // Create a new context with the new config that has our json pool. We will pass this into JigsawPlacement.addPieces
        var newContext = ModStructures.createContextWithConfig(context, newConfig);
        var generator = JigsawPlacement.addPieces(newContext,
                PoolElementStructurePiece::new, blockpos, false, false);
        if (generator.isPresent()) {
            // Debugging help to quickly find our structures
            BedrockResources.LOGGER.debug("dungeon at " + blockpos);
        }

        // Return the pieces generator that is now set up so that the game runs it when it needs to create the layout of structure pieces.
        return generator;
    }

    @NotNull
    private static BlockPos findSuitableSpot(PieceGeneratorSupplier.Context<JigsawConfiguration> context, BlockPos blockpos) {
        // Create a randomgenerator that depends on the current chunk location. That way if the world is recreated
        // with the same seed the feature will end up at the same spot
        WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(context.seed()));
        worldgenrandom.setLargeFeatureSeed(context.seed(), context.chunkPos().x, context.chunkPos().z);

        return blockpos.atY(50);
    }
}
