package com.alexvr.bedres.utils;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;

public class BlockRenderLayers {
    public static void init(BiConsumer<Block, RenderType> consumer) {
        consumer.accept(Registration.BEDROCK_WIRE_BLOCK.get(), RenderType.cutout());
    }

    private BlockRenderLayers() {}
}
