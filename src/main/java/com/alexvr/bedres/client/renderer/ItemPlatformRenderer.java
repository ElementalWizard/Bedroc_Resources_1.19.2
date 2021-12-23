package com.alexvr.bedres.client.renderer;

import com.alexvr.bedres.blocks.itemPlatform.ItemPlatformTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraftforge.items.CapabilityItemHandler;

public class ItemPlatformRenderer implements BlockEntityRenderer<ItemPlatformTile> {

    public ItemPlatformRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(ItemPlatformTile tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (!tile.item.isEmpty()){
            poseStack.pushPose();
            switch (tile.getBlockState().getValue(FaceAttachedHorizontalDirectionalBlock.FACE)) {
                case FLOOR:
                    poseStack.translate(.5, .2, .5);
                    break;
                case WALL:
                    switch (tile.getBlockState().getValue(FaceAttachedHorizontalDirectionalBlock.FACING)) {
                        case EAST -> poseStack.translate(.2, .5, .5);
                        case WEST -> poseStack.translate(.8, .5, .5);
                        case SOUTH -> poseStack.translate(.5, .5, .2);
                        case NORTH -> poseStack.translate(.5, .5, .8);
                    }
                    break;
                case CEILING:
                    poseStack.translate(.5, .8, .5);
                    break;
            }

            Quaternion rotations = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
            poseStack.mulPose(rotations);
            poseStack.scale(0.25F, 0.25F, 0.25F);
            Minecraft.getInstance().getItemRenderer().renderStatic(tile.item, ItemTransforms.TransformType.FIXED, combinedLight, combinedOverlay, poseStack, bufferSource, (int) tile.getBlockPos().asLong());
            poseStack.popPose();
        }
        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            if (h.getStackInSlot(0).getCount()>0) {

            }
        });

    }



}
