package com.alexvr.bedres.setup;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.CapabilityHandler;
import com.alexvr.bedres.world.ModWorldgen;
import com.alexvr.bedres.world.dimension.ModDimensions;
import com.alexvr.bedres.world.features.ModStructures;
import com.alexvr.bedres.world.ores.ModOres;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.alexvr.bedres.BedrockResources.MODID;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {
    public static final CreativeModeTab GROUP = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.BEDROCK_WIRE_BLOCK.get());
        }
    };

    public static void setup() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener(ModOres::onBiomeLoadingEvent);
        bus.addListener(EventPriority.NORMAL, ModStructures::addDimensionalSpacing);
        bus.addListener(EventPriority.NORMAL, ModStructures::setupStructureSpawns);
    }

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {

        event.enqueueWork(() -> {
//            DecayingFluxedBiome.init();
            ModWorldgen.onCommonSetup();
            ModOres.registerConfiguredFeatures();
            ModStructures.setupStructures();
            ModStructures.registerConfiguredStructures();
            ModDimensions.register();
            MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
            SpawnPlacements.register(Registration.TRECKING_CREEPER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
            SpawnPlacements.register(Registration.BABY_GHAST.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        });
    }

    @SubscribeEvent
    public static void serverLoad(RegisterCommandsEvent event) {
        com.alexvr.bedres.commands.ModCommands.register(event.getDispatcher());
    }
}
