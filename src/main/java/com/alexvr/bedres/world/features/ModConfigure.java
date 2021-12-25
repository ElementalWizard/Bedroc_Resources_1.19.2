package com.alexvr.bedres.world.features;

import com.alexvr.bedres.BedrockResources;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

import static com.alexvr.bedres.world.ModFeatures.ALTAR;

public class ModConfigure {

    public static ConfiguredStructureFeature<?, ?> CONFIGURED_ALTAR = ALTAR.get()
            .configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

    public static void registerConfiguredStructures(){

        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(BedrockResources.MODID, "configured_altar"), CONFIGURED_ALTAR);
    }

}
