package com.alexvr.bedres.world.dimension;

import com.alexvr.bedres.BedrockResources;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ModDimensions {

    public static final ResourceKey<Level> MYSDIM = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BedrockResources.MODID, "mysdim"));

    public static void register() {
        Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(BedrockResources.MODID, "mystery_dungeon_chunkgen"),
                MysChunkGenerator.CODEC);
        Registry.register(Registry.BIOME_SOURCE, new ResourceLocation(BedrockResources.MODID, "biomes"),
                MysBiomeProvider.CODEC);
    }
}
