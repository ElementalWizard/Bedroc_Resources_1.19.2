package com.alexvr.bedres.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;

public class RenderHelper {

    public static void renderBlock(PoseStack matrix,MultiBufferSource buffer , BlockPos pos, BlockState state, Level level, float xTranslate, float yTranslate, float zTranslate, float xScale, float yScale, float zScale){
        var builder = buffer.getBuffer(ModRenderTypes.GHOST);
        matrix.pushPose();
        matrix.translate(xTranslate, yTranslate, zTranslate);
        matrix.scale(xScale, yScale, zScale);
        Minecraft.getInstance().getBlockRenderer().renderBatched(state, pos, level, matrix, builder, false, level.getRandom(), EmptyModelData.INSTANCE);
        matrix.popPose();
    }

    public static void RenderItem(PoseStack poseStack, ItemStack itemStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BlockPos pos, int slotRendering,
                           float xTranslate, float yTranslate, float zTranslate, float xScale, float yScale, float zScale){
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
        net.minecraftforge.client.RenderProperties.get(itemStack).getItemStackRenderer().renderByItem(itemStack, ItemTransforms.TransformType.FIXED,poseStack, bufferSource, OverlayTexture.NO_OVERLAY, combinedOverlay);

        Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemTransforms.TransformType.FIXED, combinedLight, combinedOverlay, poseStack, bufferSource, (int) pos.asLong() + slotRendering);
        poseStack.popPose();
    }
    public static Float rotationCounter = 0F;

    public static void RenderItemstack(PoseStack poseStack, ItemStack itemStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BlockPos pos, int slotRendering,
                                  float xTranslate, float yTranslate, float zTranslate, float xScale, float yScale, float zScale, boolean followPlayer){
        poseStack.pushPose();
        poseStack.translate(xTranslate, yTranslate, zTranslate);
        poseStack.scale(xScale, yScale, zScale);
        if(followPlayer){
            Quaternion rotations = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
            poseStack.mulPose(rotations);
        }else{
            poseStack.mulPose(Vector3f.YP.rotationDegrees(rotationCounter+=0.5F));
            if (rotationCounter >= 360){
                rotationCounter=0F;
            }
        }
        Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemTransforms.TransformType.FIXED, combinedLight, combinedOverlay, poseStack, bufferSource, (int) pos.asLong() + slotRendering);
        poseStack.popPose();
    }

    public static void RenderBlock(PoseStack poseStack, Block block, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay,
                                   float xTranslate, float yTranslate, float zTranslate, float xScale, float yScale, float zScale, boolean rotate){
        poseStack.pushPose();
        poseStack.translate(xTranslate, yTranslate, zTranslate);
        poseStack.scale(xScale, yScale, zScale);

        if (rotate){
            Quaternion rotations = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
            rotations.set(0,rotations.j(),0,rotations.r());
            poseStack.mulPose(rotations);
        }
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(block.defaultBlockState(), poseStack,bufferSource, combinedLight, combinedOverlay);
        poseStack.popPose();
    }

}
