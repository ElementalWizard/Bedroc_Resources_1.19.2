package com.alexvr.bedres.client.renderer;

import com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestalTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jetbrains.annotations.NotNull;

import static com.alexvr.bedres.utils.RenderHelper.RenderItemstack;

public class BedrociumPedestalRenderer implements BlockEntityRenderer<BedrociumPedestalTile> {

    public BedrociumPedestalRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(BedrociumPedestalTile tile, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (!tile.item.isEmpty()){
            RenderItemstack(poseStack,tile.item,bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),0,.5f, .6f, .5f,0.75F, 0.75F, 0.75F,true );
        }

    }



}
