package com.alexvr.bedres.client.renderer;

import com.alexvr.bedres.blocks.bedrockiumTower.BedrockiumTowerTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jetbrains.annotations.NotNull;

import static com.alexvr.bedres.utils.RenderHelper.RenderItem;

public class BedrockiumTowerRenderer implements BlockEntityRenderer<BedrockiumTowerTile> {

    public BedrockiumTowerRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(BedrockiumTowerTile tile, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
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

}
