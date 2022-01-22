package com.alexvr.bedres.setup;

import com.alexvr.bedres.utils.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderLevelLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class ClientEvents {
    @SubscribeEvent
    static void renderWorldLastEvent(RenderLevelLastEvent evt) {
        Player player = Minecraft.getInstance().player;
        if (player.getMainHandItem().is(Registration.MAGE_STAFF_ITEM.get()) && player.isUsingItem()){
            RenderHelper.renderRune(evt.getPoseStack(),player, Minecraft.getInstance().getFrameTime(),"zeta");
        }


    }
}
