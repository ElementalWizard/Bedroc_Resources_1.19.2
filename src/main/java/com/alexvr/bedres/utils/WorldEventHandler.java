package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, value = Dist.CLIENT)
public class WorldEventHandler {

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event){

    }

}
