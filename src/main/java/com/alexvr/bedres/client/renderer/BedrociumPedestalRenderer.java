package com.alexvr.bedres.client.renderer;

import com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestalTile;
import com.alexvr.bedres.recipes.ModRecipeRegistry;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;

import static com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestal.TRIGGERED;
import static com.alexvr.bedres.utils.RenderHelper.RenderItemstack;

public class BedrociumPedestalRenderer implements BlockEntityRenderer<BedrociumPedestalTile> {

    public BedrociumPedestalRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(BedrociumPedestalTile tile, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                    ItemStack stack = h.getStackInSlot(0);
            RenderItemstack(poseStack,stack,bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),0,.5f, .6f, .5f,0.75F, 0.75F, 0.75F,false );
            RitualAltarRecipes recipe = ModRecipeRegistry.findRecipeFromCatalyst(stack);
            if (recipe!= null && tile.getBlockState().getValue(TRIGGERED)){
                RenderItemstack(poseStack,recipe.getResultItem(),bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),0,.5f, 1.3f, .5f,1.0F, 1.0F, 1.0F,false );
            }
        });
    }

}
