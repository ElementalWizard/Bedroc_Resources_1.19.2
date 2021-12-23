package com.alexvr.bedres.world.dimension;

import com.alexvr.bedres.BedrockResources;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ModDimensions {
    public static final ResourceKey<DimensionType> MYSTERY_DIM_TYPE = ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(BedrockResources.MODID, "mysdim"));
    public static final ResourceKey<Level> MYSDIM = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BedrockResources.MODID, "mysdim"));

}
