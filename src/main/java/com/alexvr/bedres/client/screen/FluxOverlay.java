package com.alexvr.bedres.client.screen;

import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.utils.IDisplayFlux;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import static com.alexvr.bedres.client.screen.ScrapeTankScreen.CONTAINER_BACKGROUND;

public class FluxOverlay extends GuiComponent {

    private static final Minecraft minecraft = Minecraft.getInstance();


    public boolean shouldDisplayBar(){
        if (minecraft.player == null){
            return false;
        }
        ItemStack mainHand = minecraft.player.getMainHandItem();
        ItemStack offHand = minecraft.player.getOffhandItem();
        return (mainHand.getItem() instanceof IDisplayFlux && ((IDisplayFlux) mainHand.getItem()).shouldDisplay(mainHand))
                || (offHand.getItem() instanceof IDisplayFlux && ((IDisplayFlux) offHand.getItem()).shouldDisplay(offHand));
    }


    public void drawHUD(PoseStack ms, float pt) {
        if(!shouldDisplayBar())
            return;
        Minecraft.getInstance().player.reviveCaps();
        LazyOptional<IPlayerAbility> playerflux = minecraft.player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        playerflux.ifPresent(h -> {
            RenderSystem.setShaderTexture(0, CONTAINER_BACKGROUND);
            this.blit(ms, 0, minecraft.getWindow().getGuiScaledHeight() - 74 , 211, 14, 32, 64);
            this.blit(ms, 4, minecraft.getWindow().getGuiScaledHeight() - 70 , 179, 14, 24, (int) (56 * (h.getFlux()/h.getMaxFlux())));
            this.blit(ms, 2, minecraft.getWindow().getGuiScaledHeight() - 65 , 213, 23, 10, 45);
        });
        Minecraft.getInstance().player.invalidateCaps();

    }


}
