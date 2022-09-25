package com.alexvr.bedres.client.screen;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.items.FluxedOracle;
import com.alexvr.bedres.utils.BedrockReferences;
import com.alexvr.bedres.utils.NBTHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.ScreenUtils;

import java.util.ArrayList;
import java.util.List;


public class FluxOracleScreenGui extends Screen {

    double xOffset = 0, yOffset =0;
    double mouseX = 0, mouseY =0;
    double scaleX = 0, scaleY =0;
    public ArrayList<FluxedOraclePages> book_pages = new ArrayList<>();
    private ImageButton back;
    private ImageButton next;
    public static final ResourceLocation MAINBACKGROUND = new ResourceLocation(BedrockResources.MODID, "textures/gui/flux_oracle_book_gui.png");
    public static final ResourceLocation MOVINGBACKGROUND = new ResourceLocation(BedrockResources.MODID,BedrockReferences.SCRAPE_TANK_GUI_BACK_TEXTURE_RESOURCE);
    public FluxedOraclePages currentPage = null;

    public FluxOracleScreenGui() {
        super(Component.translatable(BedrockReferences.FLUX_GUI_TITLE_RESOURCE));
    }

    protected void renderBackground(PoseStack pMatrixStack, int xOffset, int yOffset, int pX, int pY, ResourceLocation texture) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);
        this.blit(pMatrixStack,pX, pY, xOffset, yOffset, 256 , 256);
    }

    public void renderTexture(PoseStack pMatrixStack, int pX, int pY, int width, int height, ResourceLocation texture) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, texture);
        ScreenUtils.drawTexturedModalRect(pMatrixStack, pX, pY, 0, 0, width, height,width);
    }

    public void renderString(int x, int y, Component string, PoseStack pPoseStack, float pPartialTick, boolean centered, int color){
        if (centered){
            drawCenteredString(pPoseStack,Minecraft.getInstance().font,string,x,y,color);
        }else{
            drawString(pPoseStack,Minecraft.getInstance().font, string,x,y,color);
        }
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        if (book_pages.isEmpty()){
            book_pages.add(new FluxedOraclePages.AltarPage(this));
            book_pages.add(new FluxedOraclePages.FluxPage(this));
            book_pages.add(new FluxedOraclePages.ScrapePage(this));
            book_pages.add(new FluxedOraclePages.BlaziumPage(this));
            book_pages.add(new FluxedOraclePages.EnderHushPage(this));
            book_pages.add(new FluxedOraclePages.DaizePage(this));
            book_pages.add(new FluxedOraclePages.EnderianOrePage(this));
            book_pages.add(new FluxedOraclePages.EnderianIngotPage(this));
            book_pages.add(new FluxedOraclePages.ScrapeTankPage(this));
            book_pages.add(new FluxedOraclePages.ItemPlatformPage(this));
            book_pages.add(new FluxedOraclePages.EventItemPlatformPage(this));
            book_pages.add(new FluxedOraclePages.SporeMushPage(this));
            book_pages.add(new FluxedOraclePages.CupcakePage(this));
            book_pages.add(new FluxedOraclePages.GavityBubblePage(this));
            book_pages.add(new FluxedOraclePages.GravStaffPage(this));
            book_pages.add(new FluxedOraclePages.NebulaPage(this));
            book_pages.add(new FluxedOraclePages.MageStaffPage(this));
            book_pages.add(new FluxedOraclePages.EventRitualPage(this));
            book_pages.add(new FluxedOraclePages.KnifePage(this));
            book_pages.add(new FluxedOraclePages.CreeperPage(this));
            book_pages.add(new FluxedOraclePages.HextPage(this));
        }
        renderBackground(pPoseStack, (int) (xOffset/2), (int) (yOffset/2),width/4,8,MOVINGBACKGROUND);
        if(isMainPage()) {
            for (AbstractButton b : buttons) {
                if (b.x < width/4 || b.y < 8 ||
                        b.x + b.getWidth() > Minecraft.getInstance().getWindow().getGuiScaledWidth() - (Minecraft.getInstance().getWindow().getGuiScaledWidth()/4) ||
                        b.y + b.getHeight() > Minecraft.getInstance().getWindow().getGuiScaledHeight() - (Minecraft.getInstance().getWindow().getGuiScaledHeight()/9)){
                    continue;
                }
                b.renderButton(pPoseStack, pMouseX, pMouseY,pPartialTick);
            }
        }else{
            currentPage.render(pPoseStack,pMouseX,pMouseY,pPartialTick);

        }
        renderBackground(pPoseStack, 0, 0,width/4,8,MAINBACKGROUND);

        back.renderButton(pPoseStack, pMouseX, pMouseY,pPartialTick);
        if (!isMainPage() && currentPage.pages > 1){
            next.render(pPoseStack,pMouseX,pMouseY,pPartialTick);
        }
    }

    @Override
    protected void init() {

        back = new ImageButton(((Minecraft.getInstance().getWindow().getGuiScaledWidth())/4), ((Minecraft.getInstance().getWindow().getGuiScaledHeight()-40)),32,32,0,0,0,
                new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/back.png"),32,32, (button) -> {

            if(currentPage != null && currentPage.pageNumber > 1) {
                currentPage.prevPage();
            }else{
                if (currentPage == null){
                    xOffset=0;
                    yOffset=5;
                    scaleY=0;
                    scaleX=0;
                }
                changePage(-1);
            }
        });

        genButts();
    }

    public boolean isMainPage(){
        return currentPage==null;
    }

    private void changePage(int page) {
        if (page == -1){
            currentPage = null;
        }
        else if (page<book_pages.size()){
            currentPage = book_pages.get(page);
        }
        if (currentPage instanceof FluxedOraclePages.EnderianOrePage orePage){
            orePage.randomizeOre();
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private List<AbstractButton> buttons = new ArrayList<>();

    public void genButts(){
        next = new ImageButton(((Minecraft.getInstance().getWindow().getGuiScaledWidth()*2)/3)+((Minecraft.getInstance().getWindow().getGuiScaledWidth())/46), ((Minecraft.getInstance().getWindow().getGuiScaledHeight()-40)),32,32,0,0,0,
                new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/next.png"),32,32, (button) -> currentPage.nextPage());
        List<AbstractButton> buttons = new ArrayList<>() {{


            add(new ImageButton((int)xOffset+164+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+46+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/altar.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(0);
                }
            }));

            add(new ImageButton((int)xOffset+32+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+97+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/flux.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(1);
                }
            }));

            add(new ImageButton((int)xOffset+64+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+64+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/item/bedrock_scrapes.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(2);
                }
            }));

            add(new ImageButton((int)xOffset+300+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+21+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/block/blazium.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(3);
                }
            }));

            add(new ImageButton((int)xOffset+106+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+76+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/block/ender_hush_base.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(4);
                }
            }));

            add(new ImageButton((int)xOffset+186+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+66+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/block/sun_daize.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(5);
                }
            }));

            add(new ImageButton((int)xOffset+243+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+83+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/block/enderian_ore_overworld.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(6);
                }
            }));

            add(new ImageButton((int)xOffset+243+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+23+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/item/enderian_ingot.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(7);
                }
            }));

            add(new ImageButton((int)xOffset+106+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+28+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/tank.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(8);
                }
            }));

            add(new ImageButton((int)xOffset+332+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+97+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/item_platform.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(9);
                }
            }));
            add(new ImageButton((int)xOffset+355+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/ritual_platform.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(10);
                }
            }));

            add(new ImageButton((int)xOffset+164+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+126+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/block/fluxed_spores.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(11);
                }
            }));

            add(new ImageButton((int)xOffset+335+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+143+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/item/fluxed_cupcake.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(12);
                }
            }));


            add(new ImageButton((int)xOffset+385+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+143+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/fluxed_gravity_bubble.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(13);
                }
            }));

            add(new ImageButton((int)xOffset+305+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+173+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/fire_staff.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(14);
                }
            }));

            add(new ImageButton((int)xOffset+255+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+203+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/nebula.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(15);
                }
            }));

            add(new ImageButton((int)xOffset+251+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+132+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),16+ (int)scaleX,48+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/mage_staff.png"), 16+ (int)scaleX,48+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(16);
                }
            }));

            add(new ImageButton((int)xOffset+68+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+146+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/gui/widget/event_altar.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(17);
                }
            }));
            add(new ImageButton((int)xOffset+148+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+226+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/item/scrape_knife_base.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(18);
                }
            }));
            add(new ImageButton((int)xOffset+128+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+176+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/item/creeper_charm.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(19);
                }
            }));
            add(new ImageButton((int)xOffset+22+ (int)scaleX+((Minecraft.getInstance().getWindow().getGuiScaledWidth()-64)/8), (int)yOffset+176+ (int)scaleY+((Minecraft.getInstance().getWindow().getGuiScaledHeight()-64)/6),32+ (int)scaleX,32+ (int)scaleY,0,0,0,
                    new ResourceLocation(BedrockResources.MODID,"textures/block/hex_tile.png"),32+ (int)scaleX,32+ (int)scaleY, (button) -> {
                if(isMainPage()) {
                    changePage(20);
                }
            }));

            add(next);
            add(back);


        }};
        this.children().clear();
        this.clearWidgets();
        buttons.forEach(this::addWidget);
        this.buttons = buttons;

    }

    @Override
    public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
        mouseX = p_mouseClicked_1_;
        mouseY = p_mouseClicked_3_;
        genButts();
        return super.mouseClicked(p_mouseClicked_1_,p_mouseClicked_3_,p_mouseClicked_5_);
    }

    @Override
    public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
        if (xOffset + (p_mouseDragged_1_ - mouseX) / 64 < 478 && xOffset+ (p_mouseDragged_1_ - mouseX) / 64>-389) {
            xOffset += (p_mouseDragged_1_ - mouseX) / 64;
        }
        if (yOffset+(p_mouseDragged_3_ - mouseY) / 64< 191 && yOffset+(p_mouseDragged_3_ - mouseY) / 64>-109) {
            yOffset += (p_mouseDragged_3_ - mouseY) / 64;
        }

        genButts();
        return super.mouseDragged(p_mouseDragged_1_,p_mouseDragged_3_,p_mouseDragged_5_,p_mouseDragged_6_,p_mouseDragged_8_);
    }

    @Override
    public boolean mouseScrolled(double p_mouseScrolled_1_, double p_mouseScrolled_3_, double p_mouseScrolled_5_) {

        if (scaleX+ p_mouseScrolled_5_>-23 && scaleY+ p_mouseScrolled_5_<9) {
            scaleX += p_mouseScrolled_5_/2;
            scaleY += p_mouseScrolled_5_/2;
        }
        genButts();
        return super.mouseScrolled(p_mouseScrolled_1_,p_mouseScrolled_3_,p_mouseScrolled_5_);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        changePage(-1);
        if(Minecraft.getInstance().player.getMainHandItem().getItem() instanceof FluxedOracle && NBTHelper.getBoolean( (Minecraft.getInstance().player.getMainHandItem()),"active")) {
            NBTHelper.setBoolean( (Minecraft.getInstance().player.getMainHandItem()),"active",false);
        }
        return true;
    }
}
