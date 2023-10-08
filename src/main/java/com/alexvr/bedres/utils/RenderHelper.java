package com.alexvr.bedres.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Quaternionf;

import static com.mojang.math.Axis.YP;

public class RenderHelper {

    public static void renderTransparentBlock(PoseStack matrix, MultiBufferSource buffer , BlockPos pos, BlockState state, Level level, float xTranslate, float yTranslate, float zTranslate, float xScale, float yScale, float zScale){
        var builder = buffer.getBuffer(ModRenderTypes.GHOST);
        matrix.pushPose();
        matrix.translate(xTranslate, yTranslate, zTranslate);
        matrix.scale(xScale, yScale, zScale);
        Minecraft.getInstance().getBlockRenderer().renderBatched(state, pos, level, matrix, builder, false, level.getRandom());
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
        Quaternionf rotations = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        rotations.set(0,rotations.y(),0,rotations.w());
        poseStack.mulPose(rotations);
        net.minecraftforge.client.extensions.common.IClientItemExtensions.of(itemStack).getCustomRenderer().renderByItem(itemStack, ItemDisplayContext.FIXED,poseStack, bufferSource, OverlayTexture.NO_OVERLAY, combinedOverlay);

        Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.FIXED, combinedLight, combinedOverlay, poseStack, bufferSource,Minecraft.getInstance().level , (int) pos.asLong() + slotRendering);
        poseStack.popPose();
    }
    public static Float rotationCounter = 0F;

    public static void RenderItemstack(PoseStack poseStack, ItemStack itemStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, BlockPos pos, int slotRendering,
                                  float xTranslate, float yTranslate, float zTranslate, float xScale, float yScale, float zScale, boolean followPlayer){
        poseStack.pushPose();
        poseStack.translate(xTranslate, yTranslate, zTranslate);
        poseStack.scale(xScale, yScale, zScale);
        if(followPlayer){
            Quaternionf rotations = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
            poseStack.mulPose(rotations);
        }else{
            poseStack.mulPose(YP.rotationDegrees(rotationCounter+=0.5F));
            if (rotationCounter >= 360){
                rotationCounter=0F;
            }
        }
        Minecraft.getInstance().getItemRenderer().renderStatic(itemStack, ItemDisplayContext.FIXED, combinedLight, combinedOverlay, poseStack, bufferSource, Minecraft.getInstance().level, (int) pos.asLong() + slotRendering);
        poseStack.popPose();
    }




}
