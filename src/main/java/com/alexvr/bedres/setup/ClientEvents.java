package com.alexvr.bedres.setup;

import com.alexvr.bedres.client.screen.FluxOverlay;
import com.alexvr.bedres.items.MageStaff;
import com.alexvr.bedres.utils.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class ClientEvents {
    private static final FluxOverlay FLUX_OVERLAY = new FluxOverlay();

    @SubscribeEvent
    public static void renderSpellHUD(final RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
        FLUX_OVERLAY.drawHUD(event.getMatrixStack(),event.getPartialTicks());
    }

    @SubscribeEvent
    static void renderWorldLastEvent(RenderLevelLastEvent evt) {
        Player player = Minecraft.getInstance().player;
        if (player.getMainHandItem().getItem() instanceof MageStaff mageStaff && player.isUsingItem()){
            RenderHelper.renderRune(evt.getPoseStack(),player, Minecraft.getInstance().getFrameTime(),mageStaff.getType(player.getMainHandItem()));
        }
    }
}
