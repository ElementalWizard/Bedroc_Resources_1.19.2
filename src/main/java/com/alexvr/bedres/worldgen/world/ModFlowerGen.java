package com.alexvr.bedres.worldgen.world;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModFlowerGen {

    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FEATURE_PATCH_ENDER_HUSH;
    public static Holder<PlacedFeature> PATCH_ENDER_HUSH;

    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FEATURE_PATCH_SUN_DAIZE;
    public static Holder<PlacedFeature> PATCH_SUN_DAIZE;

    public static Holder<ConfiguredFeature<RandomPatchConfiguration, ?>> FEATURE_PATCH_BLAZIUM;
    public static Holder<PlacedFeature> PATCH_BLAZIUM;
    public static final BlockPos BLOCK_BELOW = new BlockPos(0, -1, 0);
    public ModFlowerGen(){
        //MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ModFlowerGen::onBiomeLoadingEvent);
    }
    public static void registerConfiguredFeatures() {
//        FEATURE_PATCH_ENDER_HUSH = register(new ResourceLocation(BedrockResources.MODID, "patch_" + BedrockReferences.ENDER_HUSH_REGNAME),
//                Feature.RANDOM_PATCH, getFlowerConfiguration(Registration.ENDER_HUSH_BLOCK.get(), 32, 2,  BlockPredicate.matchesBlocks(List.of(Blocks.GRASS_BLOCK, Blocks.END_STONE, Registration.ENDERIAN_BRICK_BLOCK.get()), BLOCK_BELOW)));
//        PATCH_ENDER_HUSH = registerPlacement(new ResourceLocation(BedrockResources.MODID, "patch_" + BedrockReferences.ENDER_HUSH_REGNAME),
//                FEATURE_PATCH_ENDER_HUSH, RarityFilter.onAverageOnceEvery(ModConfig.ENDERHUSH_CHANCE.get()), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
//
//        FEATURE_PATCH_SUN_DAIZE = register(new ResourceLocation(BedrockResources.MODID, "patch_" + BedrockReferences.SUN_DAIZE_REGNAME),
//                Feature.RANDOM_PATCH, getFlowerConfiguration(Registration.SUN_DAIZE_BLOCK.get(), 64, 6, BlockPredicate.matchesBlock(Blocks.GRASS_BLOCK, BLOCK_BELOW)));
//        PATCH_SUN_DAIZE = registerPlacement(new ResourceLocation(BedrockResources.MODID, "patch_" + BedrockReferences.SUN_DAIZE_REGNAME),
//                FEATURE_PATCH_SUN_DAIZE, RarityFilter.onAverageOnceEvery(ModConfig.SUNDAIZE_CHANCE.get()), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
//
//        FEATURE_PATCH_BLAZIUM = register(new ResourceLocation(BedrockResources.MODID, "patch_" + BedrockReferences.BLAZIUM_REGNAME),
//                Feature.RANDOM_PATCH, getFlowerConfiguration(Registration.BLAZIUM_BLOCK.get(), 96, 4, BlockPredicate.matchesBlocks(List.of(Blocks.NETHERRACK,Blocks.GRASS_BLOCK, Registration.ENDERIAN_BRICK_BLOCK.get(), Blocks.FIRE), BLOCK_BELOW)));
//        PATCH_BLAZIUM = registerPlacement(new ResourceLocation(BedrockResources.MODID, "patch_" + BedrockReferences.BLAZIUM_REGNAME),
//                FEATURE_PATCH_BLAZIUM, RarityFilter.onAverageOnceEvery(ModConfig.BLAIZE_CHANCE.get()), InSquarePlacement.spread(), PlacementUtils.FULL_RANGE, BiomeFilter.biome());

    }


/*    static Holder<PlacedFeature> registerPlacement(ResourceLocation id, Holder<? extends ConfiguredFeature<?, ?>> feature, PlacementModifier... modifiers) {
        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, id, new PlacedFeature(Holder.hackyErase(feature), List.of(modifiers)));
    }

    protected static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(ResourceLocation id, F feature, FC featureConfig) {
        return register(BuiltinRegistries.CONFIGURED_FEATURE, id, new ConfiguredFeature<>(feature, featureConfig));
    }
    private static <V extends T, T> Holder<V> register(Registry<T> registry, ResourceLocation id, V value) {
        return (Holder<V>) BuiltinRegistries.<T>register(registry, id, value);
    }
    public static RandomPatchConfiguration getFlowerConfiguration(Block block, int tries, int xzSpread, BlockPredicate plantedOn) {
        return new RandomPatchConfiguration(tries, xzSpread, 3, PlacementUtils.filtered(
                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(block)),
                BlockPredicate.allOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, plantedOn)));
    }*/
//    @SubscribeEvent
//    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
//        BiomeGenerationSettingsBuilder builder = event.getGeneration();
//        Biome.ClimateSettings climate = event.getClimate();
//
//        ResourceLocation biomeName = event.getName();
//        if (event.getCategory() == Biome.BiomeCategory.NETHER) {
//            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, PATCH_BLAZIUM);
//
//        } else if (event.getCategory() == Biome.BiomeCategory.THEEND) {
//            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_ENDER_HUSH);
//            builder.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, PATCH_ENDER_HUSH);
//
//        } else {
//            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_ENDER_HUSH);
//            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_SUN_DAIZE);
//            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PATCH_BLAZIUM);
//
//
//        }
//    }//TODO flowers
}
