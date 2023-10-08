package com.alexvr.bedres.setup;

import com.alexvr.bedres.items.MageStaff;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class ClientEvents {

    @SubscribeEvent
    public void onMouseScroll(InputEvent.MouseScrollingEvent event) {
        if (event.getScrollDelta() != 0) {
            Player player = Minecraft.getInstance().player;
            if(player == null || player.level().isClientSide()){
                return;
            }
            if (player.isShiftKeyDown() && player.getMainHandItem().getItem() instanceof MageStaff staff) {

                staff.cycleRune(player.getMainHandItem());
                event.setCanceled(true);
            }
        }
    }
}
