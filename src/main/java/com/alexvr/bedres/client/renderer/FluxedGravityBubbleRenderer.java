package com.alexvr.bedres.client.renderer;

import com.alexvr.bedres.blocks.gravityBubble.FluxedGravityBubbleTile;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.RenderHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import static com.alexvr.bedres.blocks.gravityBubble.FluxedGravityBubble.ENABLED;

public class FluxedGravityBubbleRenderer implements BlockEntityRenderer<FluxedGravityBubbleTile> {

    public FluxedGravityBubbleRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(FluxedGravityBubbleTile tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (tile.getBlockState().getValue(ENABLED) ){
            RenderHelper.renderTransparentBlock(poseStack,bufferSource,tile.getBlockPos(),Registration.RANGE_VIEW_BLOCK.get().defaultBlockState(),tile.getLevel(),-7,-4,-7,15,15,15);
        }
    }
}
