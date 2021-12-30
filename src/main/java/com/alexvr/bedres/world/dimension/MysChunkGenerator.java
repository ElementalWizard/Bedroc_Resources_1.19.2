package com.alexvr.bedres.world.dimension;

import com.alexvr.bedres.BedrockResources;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MysChunkGenerator extends ChunkGenerator {

    private static final Codec<Settings> SETTINGS_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("base").forGetter(Settings::getBaseHeight)
            ).apply(instance, Settings::new));

    public static final Codec<MysChunkGenerator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter(MysChunkGenerator::getBiomeRegistry),
                    SETTINGS_CODEC.fieldOf("settings").forGetter(MysChunkGenerator::getTutorialSettings)
            ).apply(instance, MysChunkGenerator::new));

    private final Settings settings;

    public MysChunkGenerator(Registry<Biome> registry, Settings settings) {
        super(new MysBiomeProvider(registry), new StructureSettings(false));
        this.settings = settings;
        BedrockResources.LOGGER.info("Chunk generator settings: " + settings.getBaseHeight());
    }

    public Settings getTutorialSettings() {
        return settings;
    }

    public Registry<Biome> getBiomeRegistry() {
        return ((MysBiomeProvider)biomeSource).getBiomeRegistry();
    }

    @Override
    public void buildSurface(@NotNull WorldGenRegion region, @NotNull StructureFeatureManager featureManager, @NotNull ChunkAccess chunk) {
//        BlockState bedrock = Blocks.BEDROCK.defaultBlockState();
//        BlockState stone = Blocks.STONE.defaultBlockState();
//        ChunkPos chunkpos = chunk.getPos();
//
//        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

//        int x;
//        int z;

//        for (x = 0; x < 16; x++) {
//            for (z = 0; z < 16; z++) {
//                chunk.setBlockState(pos.set(x, 0, z), bedrock, false);
//            }
//        }

//        int baseHeight = settings.getBaseHeight();
//        for (x = 0; x < 16; x++) {
//            for (z = 0; z < 16; z++) {
//                int realx = chunkpos.x * 16 + x;
//                int realz = chunkpos.z * 16 + z;
//                int height = (int) (baseHeight + Math.sin(realx / horizontalVariance)*verticalVariance + Math.cos(realz / horizontalVariance)*verticalVariance);
//                for (int y = 1 ; y < height ; y++) {
//                    chunk.setBlockState(pos.set(x, y, z), stone, false);
//                }
//            }
//        }
    }

    @Override
    protected @NotNull Codec<? extends ChunkGenerator> codec() {
        return CODEC;
    }

    @Override
    public @NotNull ChunkGenerator withSeed(long seed) {
        return new MysChunkGenerator(getBiomeRegistry(), settings);
    }

    @Override
    public @NotNull CompletableFuture<ChunkAccess> fillFromNoise(@NotNull Executor executor, @NotNull Blender blender, @NotNull StructureFeatureManager featureManager, @NotNull ChunkAccess chunkAccess) {
        return CompletableFuture.completedFuture(chunkAccess);
    }

    @Override
    public int getBaseHeight(int i, int i1, Heightmap.@NotNull Types types, @NotNull LevelHeightAccessor levelHeightAccessor) {
        return 0;
    }

    @Override
    public @NotNull NoiseColumn getBaseColumn(int i, int i1, @NotNull LevelHeightAccessor levelHeightAccessor) {
        return new NoiseColumn(0, new BlockState[0]);
    }

    @Override
    public Climate.@NotNull Sampler climateSampler() {
        return (x, y, z) -> Climate.target(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
    }

    @Override
    public void applyCarvers(@NotNull WorldGenRegion p_187691_, long p_187692_, @NotNull BiomeManager p_187693_, @NotNull StructureFeatureManager p_187694_, @NotNull ChunkAccess p_187695_, GenerationStep.@NotNull Carving p_187696_) {

    }

    @Override
    public void spawnOriginalMobs(@NotNull WorldGenRegion level) {
        ChunkPos chunkpos = level.getCenter();
        Biome biome = level.getBiome(chunkpos.getWorldPosition().atY(level.getMaxBuildHeight() - 1));
        WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.seedUniquifier()));
        worldgenrandom.setDecorationSeed(level.getSeed(), chunkpos.getMinBlockX(), chunkpos.getMinBlockZ());
        NaturalSpawner.spawnMobsForChunkGeneration(level, biome, chunkpos, worldgenrandom);
    }

    @Override
    public int getMinY() {
        return 0;
    }

    @Override
    public int getGenDepth() {
        return 256;
    }

    @Override
    public int getSeaLevel() {
        return 63;
    }

    private static class Settings {
        private final int baseHeight;

        public Settings(int baseHeight) {
            this.baseHeight = baseHeight;
        }
        public int getBaseHeight() {
            return baseHeight;
        }
    }
}
