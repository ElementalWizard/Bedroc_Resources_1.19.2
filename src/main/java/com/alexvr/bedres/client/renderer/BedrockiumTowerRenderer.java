package com.alexvr.bedres.client.renderer;

import com.alexvr.bedres.blocks.bedrockiumTower.BedrockiumTowerTile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class BedrockiumTowerRenderer implements BlockEntityRenderer<BedrockiumTowerTile> {

    public BedrockiumTowerRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(BedrockiumTowerTile tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        if (!tile.northItems[0].isEmpty()){
            RenderItem(poseStack,tile.northItems[0],bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),0,.5f,.25f,0.10f,0.45f,0.45f,0.45f );
        }
        if (!tile.northItems[1].isEmpty()){
            RenderItem(poseStack,tile.northItems[1],bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),1,.5f,.75f,0.25f,0.25f,0.25f,0.25f );
        }
        if (!tile.southItems[0].isEmpty()){
            RenderItem(poseStack,tile.southItems[0],bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),2,.5f,.25f,0.9f,0.45f,0.45f,0.45f);
        }
        if (!tile.southItems[1].isEmpty()){
            RenderItem(poseStack,tile.southItems[1],bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),3,.5f,.75f,0.75f,0.25f,0.25f,0.25f );
        }
        if (!tile.westItems[0].isEmpty()){
            RenderItem(poseStack,tile.westItems[0],bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),4,.1f,.25f,0.5f,0.45f,0.45f,0.45f);
        }
        if (!tile.westItems[1].isEmpty()){
            RenderItem(poseStack,tile.westItems[1],bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),4,.25f,.75f,.5f,0.25f,0.25f,0.25f );
        }
        if (!tile.eastItems[0].isEmpty()){
            RenderItem(poseStack,tile.eastItems[0],bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),6,0.9f,.25f,0.5f,0.45f,0.45f,0.45f);
        }
        if (!tile.eastItems[1].isEmpty()){
            RenderItem(poseStack,tile.eastItems[1],bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),7,.75f,.75f,0.5f,0.25f,0.25f,0.25f );
        }

    }

    public void RenderItem(PoseStack poseStack, ItemStack itemStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BlockPos pos, int slotRendering,
                           float xTranslate, float yTranslate, float zTranslate,float xScale, float yScale, float zScale){
        poseStack.pushPose();
        poseStack.translate(xTranslate, yTranslate, zTranslate);
        if (!(itemStack.getItem() instanceof BlockItem)){
            poseStack.scale(xScale *.6f, yScale*.6f, zScale*.6f);
        }else{
            poseStack.scale(xScale, yScale, zScale);
        }
        Quaternion rotations = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        rotations.set(0,rotations.j(),0,rotations.r());
        poseStack.mulPose(rotations);
        Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemTransforms.TransformType.FIXED, combinedLight, combinedOverlay, poseStack, bufferSource, (int) pos.asLong() + slotRendering);
        poseStack.popPose();
    }

}
