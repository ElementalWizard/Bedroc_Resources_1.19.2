package com.alexvr.bedres.capability;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapabilityHandler {

    private static final ResourceLocation ABILITY_CAP = new ResourceLocation(BedrockResources.MODID, BedrockReferences.PLAYER_ABILITY_CAP_NAME_RESOURCE);

    @SubscribeEvent
    public void onAttachCaps(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof Player)
        {
            event.addCapability(ABILITY_CAP, new PlayerAbilityProvider());
        }

    }

}
