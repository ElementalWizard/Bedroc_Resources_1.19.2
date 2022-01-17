package com.alexvr.bedres.utils;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID)
public class WorldEventHandler {

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event){

    }

    @SubscribeEvent
    public static void onPlayerLogIn(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getPlayer();
        LazyOptional<IPlayerAbility> playerAbility = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        playerAbility.ifPresent(h -> {
            player.sendMessage(new TextComponent("Pick Level: " + h.getPick()),player.getUUID());
        });
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        Player player = event.getPlayer();
        if (event.isWasDeath()){
            System.out.println("clonning");
            event.getOriginal().reviveCaps();
            LazyOptional<IPlayerAbility> playerAbility = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            LazyOptional<IPlayerAbility> oldplayerAbility =  event.getOriginal().getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            playerAbility.ifPresent(h -> oldplayerAbility.ifPresent(o -> {
                h.setJumpBoost(o.getJumpBoost());
                h.setGRavityMultiplier(o.getGravityMultiplier());
                h.setMiningSpeedBoost(o.getMiningSpeedBoost());
                h.setHoe(o.getHoe());
                h.setSword(o.getSword());
                h.setShovel(o.getShovel());
                h.setAxe(o.getAxe());
                h.setPick(o.getPick());
                h.setRitualTimer(0);
                h.setRitualTotalTimer(0);
                h.setRitualPedestals(new ArrayList<>());
                h.setFOV(o.getFOV());
                h.setLookPos(o.getlookPos());
                h.setname(o.getNAme());
            }));
            event.getOriginal().invalidateCaps();
        }
    }

}
