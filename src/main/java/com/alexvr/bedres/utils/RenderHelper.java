package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.data.EmptyModelData;

public class RenderHelper {

    public static final ResourceLocation ZETARUNE = new ResourceLocation(BedrockResources.MODID, "effect/zeta_rune");
    public static final ResourceLocation ALPHARUNE = new ResourceLocation(BedrockResources.MODID, "effect/alpha_rune");
    public static final ResourceLocation BETARUNE = new ResourceLocation(BedrockResources.MODID, "effect/beta_rune");
    public static final ResourceLocation DELTARUNE = new ResourceLocation(BedrockResources.MODID, "effect/delta_rune");
    public static final ResourceLocation EPSILONRUNE = new ResourceLocation(BedrockResources.MODID, "effect/epsilon_rune");
    public static final ResourceLocation ETARUNE = new ResourceLocation(BedrockResources.MODID, "effect/eta_rune");
    public static final ResourceLocation GAMARUNE = new ResourceLocation(BedrockResources.MODID, "effect/gama_rune");
    public static final ResourceLocation THETARUNE = new ResourceLocation(BedrockResources.MODID, "effect/theta_rune");
    public static void renderTransparentBlock(PoseStack matrix, MultiBufferSource buffer , BlockPos pos, BlockState state, Level level, float xTranslate, float yTranslate, float zTranslate, float xScale, float yScale, float zScale){
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

    public static void renderRune(PoseStack poseStack, Player player, float ticks, String runeType) {

        int brightness = LightTexture.FULL_BRIGHT;
        float scale2 = (float)player.getUseItem().getUseDuration() + 1;
        float scale3 = player.getTicksUsingItem() + 1;
        float scale = Math.min(2,(scale3*1500)/scale2);
        TextureAtlasSprite sprite;
        switch (runeType){
            case "zeta":
                sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(ZETARUNE);
                break;
            case "alpha":
                sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(ALPHARUNE);
                break;
            case "beta":
                sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(BETARUNE);
                break;
            case "delta":
                sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(DELTARUNE);
                break;
            case "epsilon":
                sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(EPSILONRUNE);
                break;
            case "eta":
                sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(ETARUNE);
                break;
            case "gama":
                sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(GAMARUNE);
                break;
            default:
                sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(THETARUNE);
                break;
        }
        Vec3 from = player.getViewVector(ticks).multiply(4,4,4);
        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        // Always remember to push the current transformation so that you can restore it later
        poseStack.pushPose();

        poseStack.translate(from.x, from.y, from.z);

        // Use the orientation of the main camera to make sure the single quad that we are going to render always faces the camera
        Quaternion rotation = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        poseStack.mulPose(rotation);

        // Actually render the quad in our own custom render type
        VertexConsumer buffer = bufferSource.getBuffer(RenderType.translucent());
        Matrix4f matrix = poseStack.last().pose();
        // Vertex data has to appear in a specific order:
        buffer.vertex(matrix, -scale, -scale, 0.0f).color(1.0f, 1.0f, 1.0f, 1f).uv(sprite.getU0(), sprite.getV0()).uv2(brightness).normal(0,0,1).endVertex();
        buffer.vertex(matrix, -scale, scale, 0.0f).color(1.0f, 1.0f, 1.0f, 1f).uv(sprite.getU0(), sprite.getV1()).uv2(brightness).normal(0,0,1).endVertex();
        buffer.vertex(matrix, scale, scale, 0.0f).color(1.0f, 1.0f, 1.0f, 1f).uv(sprite.getU1(), sprite.getV1()).uv2(brightness).normal(0,0,1).endVertex();
        buffer.vertex(matrix, scale, -scale, 0.0f).color(1.0f, 1.0f, 1.0f, 1f).uv(sprite.getU1(), sprite.getV0()).uv2(brightness).normal(0,0,1).endVertex();

        poseStack.popPose();
        RenderSystem.disableDepthTest();
    }

}
