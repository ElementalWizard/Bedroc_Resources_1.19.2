package com.alexvr.bedres.world;


import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.BedrockReferences;
import com.alexvr.bedres.world.features.AltarStructure;
import com.alexvr.bedres.world.features.WorldGenDFTree;
import com.alexvr.bedres.world.features.WorldGenFlower;
import com.google.common.collect.ImmutableMap;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.alexvr.bedres.BedrockResources.LOGGER;
import static com.alexvr.bedres.BedrockResources.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeatures {

    public static Feature<TreeConfiguration> DF_TREE;
    public static Feature<NoneFeatureConfiguration> SUN_DAIZE;
    public static Feature<NoneFeatureConfiguration> ENDER_HUSH;
    public static Feature<NoneFeatureConfiguration> BLAZIUM;

    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, BedrockResources.MODID);


    public static final RegistryObject<StructureFeature<JigsawConfiguration>> ALTAR = STRUCTURES.register("altar", () -> (new AltarStructure(JigsawConfiguration.CODEC)));

    public static final class Configured {

        @SuppressWarnings("ConstantConditions")
        public static final ConfiguredFeature<TreeConfiguration, ?> DF_TREE = ModFeatures.DF_TREE.configured(new TreeConfiguration.TreeConfigurationBuilder(null, null, null, null, null).build());
        public static final ConfiguredFeature<NoneFeatureConfiguration, ?> SUN_DAIZE = ModFeatures.SUN_DAIZE.configured(FeatureConfiguration.NONE);
        public static final ConfiguredFeature<NoneFeatureConfiguration, ?> ENDER_HUSH = ModFeatures.ENDER_HUSH.configured(FeatureConfiguration.NONE);
        public static final ConfiguredFeature<NoneFeatureConfiguration, ?> BLAZIUM = ModFeatures.BLAZIUM.configured(FeatureConfiguration.NONE);


    }

    public static final class Placed {

        public static final PlacedFeature SUN_DAIZE = ModFeatures.Configured.SUN_DAIZE.placed();
        public static final PlacedFeature ENDER_HUSH = ModFeatures.Configured.ENDER_HUSH.placed();
        public static final PlacedFeature BLAZIUM = ModFeatures.Configured.BLAZIUM.placed();

    }

    public static void setupStructures() {

        setupMapSpacingAndLand(
                ALTAR.get(), /* The instance of the structure */
                new StructureFeatureConfiguration(40 /* average distance apart in chunks between spawn attempts */,
                        35 /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE*/,
                        1234567890 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */)
        );
    }

    public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(
            F structure,
            StructureFeatureConfiguration structureFeatureConfiguration)
    {

        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);


        StructureSettings.DEFAULTS =
                ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
                        .putAll(StructureSettings.DEFAULTS)
                        .put(structure, structureFeatureConfiguration)
                        .build();


        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();


            if(structureMap instanceof ImmutableMap){
                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureFeatureConfiguration);
                settings.getValue().structureSettings().structureConfig = tempMap;
            }
            else{
                structureMap.put(structure, structureFeatureConfiguration);
            }
        });
    }
    @SubscribeEvent
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        event.getRegistry().registerAll(
                new WorldGenFlower(Registration.SUN_DAIZE_BLOCK.get(), 15, false).setRegistryName(BedrockReferences.SUN_DAIZE_REGNAME),
                new WorldGenFlower(Registration.ENDER_HUSH_BLOCK.get(), 35, false).setRegistryName(BedrockReferences.ENDER_HUSH_REGNAME),
                new WorldGenFlower(Registration.BLAZIUM_BLOCK.get(), 25, true).setRegistryName(BedrockReferences.BLAZIUM_REGNAME),
                new WorldGenDFTree().setRegistryName("df_tree")
        );
        populateObjectHolders(ModFeatures.class,event.getRegistry());
    }

    public static <T extends IForgeRegistryEntry<T>> void populateObjectHolders(Class<?> clazz, IForgeRegistry<T> registry) {
        for (var entry : clazz.getFields()) {
            if (!Modifier.isStatic(entry.getModifiers()))
                continue;
            var location = new ResourceLocation(MODID, entry.getName().toLowerCase(Locale.ROOT));
            if (!registry.containsKey(location)) {
                LOGGER.fatal("Couldn't find entry named " + location + " in registry " + registry.getRegistryName());
                continue;
            }
            try {
                entry.set(null, registry.getValue(location));
            } catch (IllegalAccessException e) {
                LOGGER.error(e);
            }
        }
    }
}
