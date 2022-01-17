package com.alexvr.bedres.setup;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.entities.fluxedcreep.FluxedCreepEntity;
import com.alexvr.bedres.entities.sporedeity.SporeDeityEntity;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistrationHelper {

    @SubscribeEvent
    public static void regCaps(RegisterCapabilitiesEvent event)
    {
        event.register(IPlayerAbility.class);
    }

    @SubscribeEvent
    public static void onAttributeCreate(EntityAttributeCreationEvent event) {
        event.put(Registration.SPORE_DEITY.get(), SporeDeityEntity.prepareAttributes().build());
        event.put(Registration.FLUXED_CREEP.get(), FluxedCreepEntity.prepareAttributes().build());
    }
}
