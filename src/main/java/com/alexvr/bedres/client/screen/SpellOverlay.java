package com.alexvr.bedres.client.screen;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.items.MageStaff;
import com.alexvr.bedres.setup.Registration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

public class SpellOverlay {

    private static final Minecraft minecraft = Minecraft.getInstance();
    public static final IGuiOverlay OVERLAY = SpellOverlay::drawHUD;

    public static final ResourceLocation ZETARUNE = new ResourceLocation(BedrockResources.MODID, "textures/effect/zeta_rune.png");
    public static final ResourceLocation ALPHARUNE = new ResourceLocation(BedrockResources.MODID, "textures/effect/alpha_rune.png");
    public static final ResourceLocation BETARUNE = new ResourceLocation(BedrockResources.MODID, "textures/effect/beta_rune.png");
    public static final ResourceLocation DELTARUNE = new ResourceLocation(BedrockResources.MODID, "textures/effect/delta_rune.png");
    public static final ResourceLocation EPSILONRUNE = new ResourceLocation(BedrockResources.MODID, "textures/effect/epsilon_rune.png");
    public static final ResourceLocation ETARUNE = new ResourceLocation(BedrockResources.MODID, "textures/effect/eta_rune.png");
    public static final ResourceLocation GAMARUNE = new ResourceLocation(BedrockResources.MODID, "textures/effect/gama_rune.png");
    public static final ResourceLocation THETARUNE = new ResourceLocation(BedrockResources.MODID, "textures/effect/theta_rune.png");

    public static boolean shouldDisplayBar(){
        if (minecraft.player == null){
            return false;
        }
        ItemStack mainHand = minecraft.player.getMainHandItem();
        ItemStack offHand = minecraft.player.getOffhandItem();
        return ((mainHand.getItem() instanceof MageStaff staff && staff.isUsing(mainHand)) || (offHand.getItem() instanceof MageStaff staff2 && staff2.isUsing(offHand)));
    }


    public static void drawHUD(ForgeGui gui, GuiGraphics guiGraphics, float pt, int width,
                        int height) {

        if(!shouldDisplayBar()){
            return;
        }

        ResourceLocation spellUsed = ALPHARUNE;
        ItemStack mainHand = minecraft.player.getMainHandItem().is(Registration.MAGE_STAFF_ITEM.get())? minecraft.player.getMainHandItem() :  minecraft.player.getOffhandItem();
        boolean permaShow = false;
        switch(((MageStaff)mainHand.getItem()).getType(mainHand)){
            case "zeta":
                spellUsed = ZETARUNE;
                permaShow = true;
                break;
            case "beta":
                spellUsed = BETARUNE;
                break;
            case "delta":
                spellUsed = DELTARUNE;
                break;
            case "epsilon":
                spellUsed = EPSILONRUNE;
                break;
            case "eta":
                spellUsed = ETARUNE;
                permaShow = true;
                break;
            case "gama":
                spellUsed = GAMARUNE;
                break;
            case "theta":
                spellUsed = THETARUNE;
                break;

        }
        if(permaShow){
            guiGraphics.blit(spellUsed, 48, minecraft.getWindow().getGuiScaledHeight() - 74, 0, 0, 64, 64, 64, 64);
        }else{
            float scale2 = (float)minecraft.player.getUseItem().getUseDuration() + 1;
            float scale3 = minecraft.player.getTicksUsingItem() + 1;
            float scale = Math.min(1,(scale3*1500)/scale2);
            guiGraphics.blit(spellUsed, (minecraft.getWindow().getGuiScaledWidth()/2) - 128, (minecraft.getWindow().getGuiScaledHeight()/2) - 128 , 0, 0, (int) (256*scale), (int) (256*scale));

        }

    }


}
