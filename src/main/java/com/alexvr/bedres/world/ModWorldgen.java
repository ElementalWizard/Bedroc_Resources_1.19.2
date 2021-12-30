package com.alexvr.bedres.world;

import com.alexvr.bedres.world.features.ModFeatures;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

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
