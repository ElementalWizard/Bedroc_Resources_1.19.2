package com.alexvr.bedres.setup;

import com.alexvr.bedres.client.screen.FluxOverlay;
import com.alexvr.bedres.items.MageStaff;
import com.alexvr.bedres.utils.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class ClientEvents {
    private static final FluxOverlay FLUX_OVERLAY = new FluxOverlay();

    @SubscribeEvent
    public static void renderSpellHUD(final RenderGuiOverlayEvent.Post event) {
        FLUX_OVERLAY.drawHUD(event.getPoseStack(),event.getPartialTick());
    }

    @SubscribeEvent
    static void renderWorldLastEvent(RenderLevelStageEvent evt) {
        Player player = Minecraft.getInstance().player;
        if (player.getMainHandItem().getItem() instanceof MageStaff mageStaff && player.isUsingItem()){
            RenderHelper.renderRune(evt.getPoseStack(),player, Minecraft.getInstance().getFrameTime(),mageStaff.getType(player.getMainHandItem()));
        }
    }
}
