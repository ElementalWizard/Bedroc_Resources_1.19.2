package com.alexvr.bedres.client.renderer;

import com.alexvr.bedres.blocks.itemPlatform.ItemPlatformTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;

import static com.alexvr.bedres.utils.RenderHelper.RenderItemstack;

public class ItemPlatformRenderer implements BlockEntityRenderer<ItemPlatformTile> {

    public ItemPlatformRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(ItemPlatformTile tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (!tile.item.isEmpty()){//TODO too fast rotaion
            switch (tile.getBlockState().getValue(FaceAttachedHorizontalDirectionalBlock.FACE)) {
                case FLOOR:
                    RenderItemstack(poseStack,tile.item,bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),0,.5f, .2f, .5f,0.25F, 0.25F, 0.25F,false );
                    break;
                case WALL:
                    switch (tile.getBlockState().getValue(FaceAttachedHorizontalDirectionalBlock.FACING)) {
                        case EAST -> RenderItemstack(poseStack,tile.item,bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),0,.2f, .5f, .5f,0.25F, 0.25F, 0.25F,false );
                        case WEST -> RenderItemstack(poseStack,tile.item,bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),0,.8f, .5f, .5f,0.25F, 0.25F, 0.25F,false );
                        case SOUTH -> RenderItemstack(poseStack,tile.item,bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),0,.5f, .5f, .2f,0.25F, 0.25F, 0.25F,false );
                        case NORTH -> RenderItemstack(poseStack,tile.item,bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),0,.5f, .5f, .8f,0.25F, 0.25F, 0.25F,false );
                    }
                    break;
                case CEILING:
                    RenderItemstack(poseStack,tile.item,bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),0,.5f, .8f, .5f,0.25F, 0.25F, 0.25F,false );
                    break;
            }
        }
    }
}
