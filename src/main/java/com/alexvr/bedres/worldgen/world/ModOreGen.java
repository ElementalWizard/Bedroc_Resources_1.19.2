package com.alexvr.bedres.worldgen.world;

import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.Tags;

public class ModOreGen {

    public static Holder<PlacedFeature> OVERWORLD_RESONATING_ORE;
    public static Holder<PlacedFeature> OVERWORLD_DEEPSLATE_RESONATING_ORE;
    public static Holder<PlacedFeature> NETHER_RESONATING_ORE;
    public static Holder<PlacedFeature> END_RESONATING_ORE;

    public static final RuleTest ENDSTONE_TEST = new TagMatchTest(Tags.Blocks.END_STONES);

    public ModOreGen(){
        //MinecraftForge.EVENT_BUS.addListener(EventPriority.HIGH, ModOreGen::onBiomeLoadingEvent);
    }
    public static void registerConfiguredFeatures() {
//        OreConfiguration overworldConfig = new OreConfiguration(OreFeatures.STONE_ORE_REPLACEABLES, Registration.ENDERIAN_ORE_OVERWORLD.get().defaultBlockState(),
//                ModConfig.OVERWORLD_VEINSIZE.get());
//        OVERWORLD_RESONATING_ORE = registerPlacedFeature(BedrockReferences.ENDERIAN_ORE_REGNAME + "_overworld", new ConfiguredFeature<>(Feature.ORE, overworldConfig),
//                CountPlacement.of(ModConfig.OVERWORLD_AMOUNT.get()),
//                InSquarePlacement.spread(),
//                BiomeFilter.biome(),
//                HeightRangePlacement.uniform(VerticalAnchor.absolute(ModConfig.OVERWORLD_MINY.get()), VerticalAnchor.absolute(ModConfig.OVERWORLD_MAXY.get())));
//        OreConfiguration overworldDeepslateConfig = new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Registration.ENDERIAN_ORE_DEEPSLATE.get().defaultBlockState(),
//                ModConfig.DEEPSLATE_VEINSIZE.get());
//        OVERWORLD_DEEPSLATE_RESONATING_ORE = registerPlacedFeature(BedrockReferences.ENDERIAN_ORE_REGNAME + "_deepslate", new ConfiguredFeature<>(Feature.ORE, overworldDeepslateConfig),
//                CountPlacement.of(ModConfig.DEEPSLATE_AMOUNT.get()),
//                InSquarePlacement.spread(),
//                BiomeFilter.biome(),
//                HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(ModConfig.DEEPSLATE_MAXY.get())));
//        OreConfiguration netherConfig = new OreConfiguration(OreFeatures.NETHER_ORE_REPLACEABLES, Registration.ENDERIAN_ORE_NETHER.get().defaultBlockState(),
//                ModConfig.NETHER_VEINSIZE.get());
//        NETHER_RESONATING_ORE = registerPlacedFeature(BedrockReferences.ENDERIAN_ORE_REGNAME + "_nether", new ConfiguredFeature<>(Feature.ORE, netherConfig),
//                CountPlacement.of(ModConfig.NETHER_AMOUNT.get()),
//                InSquarePlacement.spread(),
//                BiomeFilter.biome(),
//                HeightRangePlacement.uniform(VerticalAnchor.absolute(ModConfig.NETHER_MINY.get()), VerticalAnchor.absolute(ModConfig.NETHER_MAXY.get())));
//
//        OreConfiguration endConfig = new OreConfiguration(ENDSTONE_TEST, Registration.ENDERIAN_ORE_END.get().defaultBlockState(),
//                ModConfig.END_VEINSIZE.get());
//        END_RESONATING_ORE = registerPlacedFeature(BedrockReferences.ENDERIAN_ORE_REGNAME + "_end", new ConfiguredFeature<>(Feature.ORE, endConfig),
//                CountPlacement.of(ModConfig.END_AMOUNT.get()),
//                InSquarePlacement.spread(),
//                BiomeFilter.biome(),
//                HeightRangePlacement.uniform(VerticalAnchor.absolute(ModConfig.END_MINY.get()), VerticalAnchor.absolute(ModConfig.END_MAXY.get())));

    }

/*    private static <C extends FeatureConfiguration, F extends Feature<C>> Holder<PlacedFeature> registerPlacedFeature(String registryName, ConfiguredFeature<C, F> feature, PlacementModifier... placementModifiers) {
        return PlacementUtils.register(registryName, Holder.direct(feature), placementModifiers);
    }*/
//
//    @SubscribeEvent
//    public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {
//        if (event.getCategory() == Biome.BiomeCategory.NETHER) {
//            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, NETHER_RESONATING_ORE);
//        } else if (event.getCategory() == Biome.BiomeCategory.THEEND) {
//            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, END_RESONATING_ORE);
//        } else {
//            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_RESONATING_ORE);
//            event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, OVERWORLD_DEEPSLATE_RESONATING_ORE);
//        }
//    }

}
