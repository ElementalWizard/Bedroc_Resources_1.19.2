package com.alexvr.bedres.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;

public class RenderHelper {


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
        Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemTransforms.TransformType.FIXED, combinedLight, combinedOverlay, poseStack, bufferSource, (int) pos.asLong() + slotRendering);
        poseStack.popPose();
    }

    public static void RenderItemstack(PoseStack poseStack, ItemStack itemStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BlockPos pos, int slotRendering,
                                  float xTranslate, float yTranslate, float zTranslate, float xScale, float yScale, float zScale, boolean followPlayer){
        poseStack.pushPose();
        poseStack.translate(xTranslate, yTranslate, zTranslate);
        poseStack.scale(xScale, yScale, zScale);
        if(followPlayer){
            Quaternion rotations = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
            poseStack.mulPose(rotations);
        }
        Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemTransforms.TransformType.FIXED, combinedLight, combinedOverlay, poseStack, bufferSource, (int) pos.asLong() + slotRendering);
        poseStack.popPose();
    }

}
