package com.alexvr.bedres.client.renderer;

import com.alexvr.bedres.blocks.enderianRitualPedestal.EnderianRitualPedestalTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import static com.alexvr.bedres.utils.RenderHelper.RenderItemstack;

public class EnderianRitualPedestalRenderer implements BlockEntityRenderer<EnderianRitualPedestalTile> {

    public EnderianRitualPedestalRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(EnderianRitualPedestalTile tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (!tile.item.isEmpty()){
            RenderItemstack(poseStack,tile.item,bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),0,.5f, 1f, .5f,0.40F, 0.40F, 0.40F,false );
        }
    }

}
