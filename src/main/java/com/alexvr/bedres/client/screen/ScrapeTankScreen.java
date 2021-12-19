package com.alexvr.bedres.client.screen;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.blocks.scrapeTank.ScrapeTankMenu;
import com.alexvr.bedres.utils.BedrockReferences;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;

@OnlyIn(Dist.CLIENT)
public class ScrapeTankScreen extends AbstractContainerScreen<ScrapeTankMenu> {
    /** The ResourceLocation containing the chest GUI texture. */
    private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(BedrockResources.MODID, BedrockReferences.SCRAPE_TANK_GUI_TEXTURE_RESOURCE);
    /** Window height is calculated with these values" the more rows, the higher */
    private final ScrapeTankMenu container;

    public ScrapeTankScreen(ScrapeTankMenu pChestMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pChestMenu, pPlayerInventory, pTitle);
        this.passEvents = false;
        this.imageHeight = 166 + 18;
        this.inventoryLabelY = this.imageHeight - 115;
        this.container = pChestMenu;
    }

    public void render(PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        this.renderBackground(pMatrixStack);
        super.render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        this.renderTooltip(pMatrixStack, pMouseX, pMouseY);
    }

    protected void renderBg(PoseStack pMatrixStack, float pPartialTicks, int pX, int pY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, CONTAINER_BACKGROUND);
        int relX = (this.width - this.imageWidth)/2;
        int relY = (this.height - this.imageHeight)/2;
        this.blit(pMatrixStack, relX, relY, 0, 0, this.getXSize(), this.getYSize());


        this.blit(pMatrixStack, relX + 142, relY + 14, 179, 14, 16, (int) (52 * (128 / 256)));
        //System.out.println(container.tile.getItem(0).getCount());


    }
}