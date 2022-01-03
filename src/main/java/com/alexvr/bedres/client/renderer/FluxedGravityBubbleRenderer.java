package com.alexvr.bedres.client.renderer;

import com.alexvr.bedres.blocks.gravityBubble.FluxedGravityBubbleTile;
import com.alexvr.bedres.setup.Registration;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;

import static com.alexvr.bedres.utils.RenderHelper.RenderBlock;

public class FluxedGravityBubbleRenderer implements BlockEntityRenderer<FluxedGravityBubbleTile> {

    public FluxedGravityBubbleRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(FluxedGravityBubbleTile tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (tile.isAreaVisible() ){
            RenderBlock(poseStack, Registration.RANGE_VIEW_BLOCK.get(),bufferSource,combinedLight, OverlayTexture.NO_OVERLAY,-7,-4,-7,15,15,15,false);
        }
    }
}
