package com.alexvr.bedres.client.renderer;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.blocks.gravityBubble.FluxedGravityBubbleTile;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.RenderHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class FluxedGravityBubbleRenderer implements BlockEntityRenderer<FluxedGravityBubbleTile> {

    public FluxedGravityBubbleRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(FluxedGravityBubbleTile tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (tile.isAreaVisible() ){
            BedrockResources.LOGGER.info(tile.getFuelAmount());
            //RenderBlock(poseStack, Registration.RANGE_VIEW_BLOCK.get(),bufferSource,combinedLight, OverlayTexture.NO_OVERLAY,-7,-4,-7,15,15,15,false);
            RenderHelper.renderTransparentBlock(poseStack,bufferSource,tile.getBlockPos(),Registration.RANGE_VIEW_BLOCK.get().defaultBlockState(),tile.getLevel(),-7,-4,-7,15,15,15);
        }
    }
}
