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
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

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
            case NETHER -> generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.Placed.BLAZIUM);
            case THEEND -> generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.Placed.ENDER_HUSH);
            default -> {
                generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.Placed.SUN_DAIZE);
                generation.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModFeatures.Placed.ENDER_HUSH);
            }
        }
    }

    public static void onCommonSetup() {
        int size, rate, minY, maxY;
        List<OreConfiguration.TargetBlockState> targets;
        ConfiguredFeature<OreConfiguration, ?> featureOre;
        size = 3;
        minY = -60;
        maxY = 20;
        rate = 10;
        targets = List.of(
                OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, Registration.ENDERIAN_ORE_OVERWORLD.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Registration.ENDERIAN_ORE_DEEPSLATE.get().defaultBlockState()),
                OreConfiguration.target(OreFeatures.NETHER_ORE_REPLACEABLES, Registration.ENDERIAN_ORE_NETHER.get().defaultBlockState())
        );
        featureOre = Feature.ORE.configured(new OreConfiguration(targets, size));

        placedEnderianOreFeature = featureOre.placed(List.of(
                CountPlacement.of(rate),
                InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(minY), VerticalAnchor.absolute(maxY)),
                BiomeFilter.biome()
        ));

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(BedrockResources.MODID, BedrockReferences.ENDERIAN_ORE_REGNAME), featureOre);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BedrockResources.MODID,  BedrockReferences.ENDERIAN_ORE_REGNAME), placedEnderianOreFeature);

        for (var entry : ModFeatures.Configured.class.getFields()) {
            try {
                var feature = (ConfiguredFeature<?, ?>) entry.get(null);
                Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, Objects.requireNonNull(feature.feature.getRegistryName()), feature);
            } catch (IllegalAccessException e) {
                //LOGGER.error(e);
            }
        }
        for (var entry : ModFeatures.Placed.class.getFields()) {
            try {
                var placed = (PlacedFeature) entry.get(null);
                // why are you making this so difficult for me
                Supplier<ConfiguredFeature<?, ?>> feature = ObfuscationReflectionHelper.getPrivateValue(PlacedFeature.class, placed, "f_191775_");
                assert feature != null;
                Registry.register(BuiltinRegistries.PLACED_FEATURE, Objects.requireNonNull(feature.get().feature.getRegistryName()), placed);
            } catch (IllegalAccessException e) {
                //LOGGER.error(e);
            }
        }
    }
}
