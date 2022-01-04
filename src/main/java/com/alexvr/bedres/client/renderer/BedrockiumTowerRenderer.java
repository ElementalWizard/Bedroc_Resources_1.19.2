package com.alexvr.bedres.client.renderer;

import com.alexvr.bedres.blocks.bedrockiumTower.BedrockiumTowerTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

import static com.alexvr.bedres.utils.RenderHelper.RenderItem;

public class BedrockiumTowerRenderer implements BlockEntityRenderer<BedrockiumTowerTile> {

    public BedrockiumTowerRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(BedrockiumTowerTile tile, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        AtomicInteger slot = new AtomicInteger();
        for (Direction dir: Direction.values()) {
            switch (dir){
                case UP:
                case DOWN:
                    break;
                default:
                    tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir).ifPresent(h -> {
                        for (int i = 0; i< h.getSlots();i++){
                            float xTranslate = 0;
                            float zTranslate = 0;
                            if (dir == Direction.NORTH){
                                xTranslate =0.5f;
                                zTranslate = i == 0 ? 0.10f :0.25f;
                            }
                            if (dir == Direction.SOUTH){
                                xTranslate =0.5f;
                                zTranslate = i == 0 ? 0.90f :0.75f;
                            }
                            if (dir == Direction.EAST){
                                zTranslate =0.5f;
                                xTranslate = i == 0 ? 0.90f :0.75f;
                            }
                            if (dir == Direction.WEST){
                                zTranslate =0.5f;
                                xTranslate = i == 0 ? 0.10f :0.25f;
                            }
                            float scale = i == 0 ? 0.45f :0.25f;
                            RenderItem(poseStack,h.getStackInSlot(i),bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(), slot.getAndIncrement(),xTranslate,i == 0 ? 0.25f :0.75f,zTranslate,scale,scale,scale );

                        }
                    });
            }
        }

    }

}
