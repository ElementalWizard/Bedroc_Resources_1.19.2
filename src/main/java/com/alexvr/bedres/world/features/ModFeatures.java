package com.alexvr.bedres.world.features;


import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.lang.reflect.Modifier;
import java.util.Locale;

import static com.alexvr.bedres.BedrockResources.LOGGER;
import static com.alexvr.bedres.BedrockResources.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeatures {


    public static Feature<TreeConfiguration> DF_TREE;
    public static Feature<NoneFeatureConfiguration> SUN_DAIZE;
    public static Feature<NoneFeatureConfiguration> ENDER_HUSH;
    public static Feature<NoneFeatureConfiguration> BLAZIUM;

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
