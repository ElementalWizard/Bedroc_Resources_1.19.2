package com.alexvr.bedres.client.renderer;

import com.alexvr.bedres.blocks.enderianRitualPedestal.EnderianRitualPedestalTile;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;

import static com.alexvr.bedres.utils.RenderHelper.RenderItemstack;

public class EnderianRitualPedestalRenderer implements BlockEntityRenderer<EnderianRitualPedestalTile> {

    public EnderianRitualPedestalRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(EnderianRitualPedestalTile tile, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            ItemStack stack = h.getStackInSlot(0);
            RenderItemstack(poseStack,stack,bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),0,.5f, 1f, .5f,0.40F, 0.40F, 0.40F,false );
        });
    }

}
