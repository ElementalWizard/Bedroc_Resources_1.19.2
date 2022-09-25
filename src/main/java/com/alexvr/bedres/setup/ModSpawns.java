package com.alexvr.bedres.setup;

import com.alexvr.bedres.BedrockResources;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID)
public class ModSpawns {

    private static final Lazy<List<MobSpawnSettings.SpawnerData>> STRUCTURE_MONSTERS = Lazy.of(() -> ImmutableList.of(
            new MobSpawnSettings.SpawnerData(EntityType.ILLUSIONER, 100, 2, 9),
            new MobSpawnSettings.SpawnerData(EntityType.VINDICATOR, 100, 2, 9),
            new MobSpawnSettings.SpawnerData(EntityType.ZOMBIE, 100, 2, 9)
    ));
//    @SubscribeEvent(priority = EventPriority.HIGH)
//    public static void setupStructureSpawns(StructureSpawnListGatherEvent event) {
//        if (event.getStructure() == Registration.ALTAR_OVERWORLD.get())
//            event.addEntitySpawns(MobCategory.MONSTER, STRUCTURE_MONSTERS.get());
//    }

}
