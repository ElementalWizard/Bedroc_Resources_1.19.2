package com.alexvr.bedres.setup;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.CapabilityHandler;
import com.alexvr.bedres.world.ModWorldgen;
import com.alexvr.bedres.world.dimension.ModDimensions;
import com.alexvr.bedres.world.features.ModStructures;
import com.alexvr.bedres.world.ores.ModOres;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;

import static com.alexvr.bedres.BedrockResources.MODID;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModSetup {
    public static final CreativeModeTab GROUP = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Registration.BEDROCK_WIRE_BLOCK.get());
        }
    };

    private static final Lazy<List<MobSpawnSettings.SpawnerData>> STRUCTURE_MONSTERS = Lazy.of(() -> ImmutableList.of(
            new MobSpawnSettings.SpawnerData(EntityType.ILLUSIONER, 10, 2, 9),
            new MobSpawnSettings.SpawnerData(EntityType.VINDICATOR, 10, 2, 9),
            new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 10, 2, 9)
    ));

    @SubscribeEvent(priority =  EventPriority.HIGH)
    public static void setupStructureSpawns(final StructureSpawnListGatherEvent event) {
        if (event.getStructure() == Registration.ALTAR_OVERWORLD.get() || event.getStructure() == Registration.DUNGEON.get()) {
            event.addEntitySpawns(MobCategory.MONSTER, STRUCTURE_MONSTERS.get());
        }
    }

    public static void setup() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addListener(ModOres::onBiomeLoadingEvent);
        bus.addListener(EventPriority.NORMAL, ModStructures::addDimensionalSpacing);
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
