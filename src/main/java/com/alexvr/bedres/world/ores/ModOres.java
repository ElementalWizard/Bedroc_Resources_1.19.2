package com.alexvr.bedres.world.ores;

import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.BedrockReferences;
import com.alexvr.bedres.world.dimension.ModDimensions;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class ModOres {
    public static final int OVERWORLD_VEINSIZE = 5;
    public static final int OVERWORLD_AMOUNT = 3;
    public static final int DEEPSLATE_VEINSIZE = 5;
    public static final int DEEPSLATE_AMOUNT = 3;
    public static final int NETHER_VEINSIZE = 5;
    public static final int NETHER_AMOUNT = 3;
    public static final int END_VEINSIZE = 10;
    public static final int END_AMOUNT = 6;

    public static final RuleTest IN_ENDSTONE = new TagMatchTest(Tags.Blocks.END_STONES);

    public static PlacedFeature OVERWORLD_OREGEN;
    public static PlacedFeature DEEPSLATE_OREGEN;
    public static PlacedFeature NETHER_OREGEN;
    public static PlacedFeature END_OREGEN;

    public static void registerConfiguredFeatures() {

        OreConfiguration overworldConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, Registration.ENDERIAN_ORE_OVERWORLD.get().defaultBlockState(), OVERWORLD_VEINSIZE);
        OVERWORLD_OREGEN = registerPlacedFeature(BedrockReferences.ENDERIAN_ORE_REGNAME + "_overworld", Feature.ORE.configured(overworldConfig),
                CountPlacement.of(OVERWORLD_AMOUNT),
                InSquarePlacement.spread(),
                new DimensionBiomeFilter(key -> !ModDimensions.MYSDIM.equals(key)),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(90)));

        OreConfiguration deepslateConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Registration.ENDERIAN_ORE_DEEPSLATE.get().defaultBlockState(), DEEPSLATE_VEINSIZE);
        DEEPSLATE_OREGEN = registerPlacedFeature(BedrockReferences.ENDERIAN_ORE_REGNAME + "_deepslate", Feature.ORE.configured(deepslateConfig),
                CountPlacement.of(DEEPSLATE_AMOUNT),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(64)));

        OreConfiguration netherConfig = new OreConfiguration(OreFeatures.NETHER_ORE_REPLACEABLES, Registration.ENDERIAN_ORE_NETHER.get().defaultBlockState(), NETHER_VEINSIZE);
        NETHER_OREGEN = registerPlacedFeature(BedrockReferences.ENDERIAN_ORE_REGNAME + "_nether", Feature.ORE.configured(netherConfig),
                CountPlacement.of(NETHER_AMOUNT),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(90)));

        OreConfiguration endConfig = new OreConfiguration(IN_ENDSTONE, Registration.ENDERIAN_ORE_END.get().defaultBlockState(), END_VEINSIZE);
        END_OREGEN = registerPlacedFeature(BedrockReferences.ENDERIAN_ORE_REGNAME + "_end", Feature.ORE.configured(endConfig),
                CountPlacement.of(END_AMOUNT),
                InSquarePlacement.spread(),
                BiomeFilter.biome(),
                HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(100)));
    }

    private static <C extends FeatureConfiguration, F extends Feature<C>> PlacedFeature registerPlacedFeature(String registryName, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
        PlacedFeature placed = BuiltinRegistries.register(BuiltinRegistries.CONFIGURED_FEATURE, new ResourceLocation(registryName), feature).placed(placementModifiers);
        return PlacementUtils.register(registryName, placed);
    }

    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.BiomeCategory.NETHER) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, NETHER_OREGEN);
        } else if (event.getCategory() == Biome.BiomeCategory.THEEND) {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, END_OREGEN);
        } else {
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_OREGEN);
            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DEEPSLATE_OREGEN);
        }
    }
}
