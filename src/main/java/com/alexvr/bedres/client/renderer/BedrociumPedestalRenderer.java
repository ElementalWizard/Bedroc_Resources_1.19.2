package com.alexvr.bedres.client.renderer;

import com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestalTile;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import org.jetbrains.annotations.NotNull;

import static com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestal.CRAFTING;
import static com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestal.VALIDRECIPE;
import static com.alexvr.bedres.utils.RenderHelper.RenderItemstack;

public class BedrociumPedestalRenderer implements BlockEntityRenderer<BedrociumPedestalTile> {

    public BedrociumPedestalRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(BedrociumPedestalTile tile, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int combinedLight, int combinedOverlay) {
        tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                    ItemStack stack = h.getStackInSlot(0);
            RenderItemstack(poseStack,stack,bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),0,.5f, .6f, .5f,0.75F, 0.75F, 0.75F,false );
            RitualAltarRecipes recipe = RitualAltarRecipes.findRecipeFromCatalyst(stack);
            if (recipe!= null && (tile.getBlockState().getValue(VALIDRECIPE)||tile.getBlockState().getValue(CRAFTING))){
                float ration = tile.getBlockState().getValue(CRAFTING) ? tile.getCraftingProgress() : 1f;
                RenderItemstack(poseStack,recipe.getResultItem(),bufferSource,combinedLight,combinedOverlay,tile.getBlockPos(),0,.5f, 1.3f, .5f,ration, ration, ration,false );
            }
        });
    }

}
