package com.alexvr.bedres.world.dimension;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MysBiomeProvider extends BiomeSource {

    public static final Codec<MysBiomeProvider> CODEC = RegistryLookupCodec.create(Registry.BIOME_REGISTRY)
            .xmap(MysBiomeProvider::new, MysBiomeProvider::getBiomeRegistry).codec();

    private final Biome biome;
    private final Registry<Biome> biomeRegistry;
    private static final List<ResourceKey<Biome>> SPAWN = Collections.singletonList(Biomes.PLAINS);

    public MysBiomeProvider(Registry<Biome> biomeRegistry) {
        super(getStartBiomes(biomeRegistry));
        this.biomeRegistry = biomeRegistry;
        biome = biomeRegistry.get(Biomes.PLAINS.location());
    }

    private static List<Biome> getStartBiomes(Registry<Biome> registry) {
        return SPAWN.stream().map(s -> registry.get(s.location())).collect(Collectors.toList());
    }

    public Registry<Biome> getBiomeRegistry() {
        return biomeRegistry;
    }

    @Override
    protected @NotNull Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    public @NotNull BiomeSource withSeed(long seed) {
        return this;
    }

    @Override
    public @NotNull Biome getNoiseBiome(int x, int y, int z, Climate.@NotNull Sampler sampler) {
        return biome;
    }
}
