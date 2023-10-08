package com.alexvr.bedres.client.screen;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.utils.BedrockReferences;
import com.alexvr.bedres.utils.IDisplayFlux;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.common.util.LazyOptional;


public class FluxOverlay  {

    private static final Minecraft minecraft = Minecraft.getInstance();
    public static final IGuiOverlay OVERLAY = FluxOverlay::drawHUD;
    public static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation(BedrockResources.MODID, BedrockReferences.FLUX_OVERLAY_TEXTURE_RESOURCE);


    public static boolean shouldDisplayBar(){
        if (minecraft.player == null){
            return false;
        }
        ItemStack mainHand = minecraft.player.getMainHandItem();
        ItemStack offHand = minecraft.player.getOffhandItem();
        return (mainHand.getItem() instanceof IDisplayFlux && ((IDisplayFlux) mainHand.getItem()).shouldDisplay(mainHand))
                || (offHand.getItem() instanceof IDisplayFlux && ((IDisplayFlux) offHand.getItem()).shouldDisplay(offHand));
    }


    public static void drawHUD(ForgeGui gui, GuiGraphics guiGraphics, float pt, int width,
                        int height) {
        if(!shouldDisplayBar())
            return;
        Minecraft.getInstance().player.reviveCaps();
        LazyOptional<IPlayerAbility> playerflux = minecraft.player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        playerflux.ifPresent(h -> {
            guiGraphics.blit(CONTAINER_BACKGROUND, 0, minecraft.getWindow().getGuiScaledHeight() - 74 , 211, 14, 32, 64);
            guiGraphics.blit(CONTAINER_BACKGROUND, 4, minecraft.getWindow().getGuiScaledHeight() - 70 , 179, 14, 24, (int) (56 * (h.getFlux()/h.getMaxFlux())));
            guiGraphics.blit(CONTAINER_BACKGROUND, 2, minecraft.getWindow().getGuiScaledHeight() - 65 , 213, 23, 10, 45);
        });
        Minecraft.getInstance().player.invalidateCaps();

    }


}
