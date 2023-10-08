package com.alexvr.bedres.setup;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.CapabilityHandler;
import com.alexvr.bedres.commands.ModCommands;
import com.alexvr.bedres.worldgen.world.ModFlowerGen;
import com.alexvr.bedres.worldgen.world.ModOreGen;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {


    public static void setup() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        //bus.addListener(ModOres::onBiomeLoadingEvent);
        //bus.addListener(EventPriority.NORMAL, ModStructures::addDimensionalSpacing);
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
//            DecayingFluxedBiome.init();
            //ModWorldgen.onCommonSetup();
            ModOreGen.registerConfiguredFeatures();
            ModFlowerGen.registerConfiguredFeatures();
            //ModStructures.setupStructures();
            //ModStructures.registerConfiguredStructures();
            //ModDimensions.register();
            MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
            //SpawnPlacements.register(Registration.TRECKING_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
            SpawnPlacements.register(Registration.CHAINED_BLAZE.get(), SpawnPlacements.Type.IN_LAVA, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        });

    }
    @SubscribeEvent
    public static void serverLoad(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }
}
