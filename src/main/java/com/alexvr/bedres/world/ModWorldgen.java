package com.alexvr.bedres.world;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ModWorldgen {

    private static PlacedFeature placedEnderianOreFeature;

    @SubscribeEvent
    public void onBiomesLoading(BiomeLoadingEvent event) {
        var category = event.getCategory();
        var generation = event.getGeneration();
        var name = event.getName();
        if (name == null)
            return;
        generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, placedEnderianOreFeature);
        switch (category) {
            case NETHER -> {
                //generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedBlaziumFeature);
            }
            case THEEND -> {
                //generation.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, placedEnderHushFeature);
            }
            default -> {
                //generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placedBlaziumFeature);


            }
        }
    }

    public static void onCommonSetup() {
        int size, rate, minY, maxY;
        List<OreConfiguration.TargetBlockState> targets;
        ConfiguredFeature<OreConfiguration, ?> feature;
        ConfiguredFeature<RandomPatchConfiguration, ?> flowerFeature;
        ConfiguredFeature<NoneFeatureConfiguration, ?> altartFeature;
        size = 3;
        minY = -60;
        maxY = 20;
        rate = 10;
        targets = List.of(
                OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, Registration.ENDERIAN_ORE_OVERWORLD.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Registration.ENDERIAN_ORE_DEEPSLATE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, Registration.ENDERIAN_ORE_NETHER.get().defaultBlockState())
        );
        feature = Feature.ORE.configured(new OreConfiguration(targets, size));

        placedEnderianOreFeature = feature.placed(List.of(
                CountPlacement.of(rate),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)),
                BiomeFilter.biome()
        ));

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(BedrockResources.MODID, BedrockReferences.ENDERIAN_ORE_REGNAME), feature);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BedrockResources.MODID,  BedrockReferences.ENDERIAN_ORE_REGNAME), placedEnderianOreFeature);


//        flowerFeature = Feature.FLOWER.configured(new RandomPatchConfiguration(64, 10, 4, () -> Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(Registration.ENDER_HUSH_BLOCK.get()))).onlyWhenEmpty()));
//
//        placedBlaziumFeature = flowerFeature.placed(List.of(
//                CountPlacement.of(20),
//                InSquarePlacement.spread(),
//                BiomeFilter.biome()
//        ));
//
//        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(BedrockResources.MODID, BedrockReferences.ENDER_HUSH_REGNAME), flowerFeature);
//        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BedrockResources.MODID,  BedrockReferences.ENDER_HUSH_REGNAME), placedBlaziumFeature);

    }
}
