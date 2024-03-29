package com.alexvr.bedres.blocks.eventAltar.events;

import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.setup.Registration;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerUpgradeEvent implements IEvent{

    public static void jump(Player player, ItemStack stack) {
        player.reviveCaps();
        LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(iPlayerAbility -> {
            if (stack.is(Items.RABBIT_FOOT)){
                if (iPlayerAbility.getJumpBoost()<=0.2) {
                    if (iPlayerAbility.getJumpBoost()==0){
                        iPlayerAbility.setJumpBoost(0.08f);
                    }else if (iPlayerAbility.getJumpBoost()==0.08f){
                        iPlayerAbility.setJumpBoost(0.15f);
                    }else if (iPlayerAbility.getJumpBoost()==0.15f){
                        iPlayerAbility.setJumpBoost(0.25f);
                    }
                }
            }else if (stack.is(Items.RABBIT_HIDE)){
                if (iPlayerAbility.getJumpBoost()>=0.015f) {
                    if (iPlayerAbility.getJumpBoost()==0.25f){
                        iPlayerAbility.setJumpBoost(0.2f);
                    }else if (iPlayerAbility.getJumpBoost()==0.2f){
                        iPlayerAbility.setJumpBoost(0.15);
                    }else if (iPlayerAbility.getJumpBoost()==0.15f){
                        iPlayerAbility.setJumpBoost(0.08f);
                    }else if (iPlayerAbility.getJumpBoost()==0.08){
                        iPlayerAbility.setJumpBoost(0f);
                    }
                }
            }else{
                iPlayerAbility.setJumpBoost(0f);
            }
            player.sendSystemMessage(Component.literal("New jump boost is: "+iPlayerAbility.getJumpBoost()));
        });
        player.invalidateCaps();

    }

    public static void empower(Player player) {
        player.reviveCaps();
        LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(iPlayerAbility -> {
            if (iPlayerAbility.getMaxFlux() < 500){
                iPlayerAbility.setMaxFlux(iPlayerAbility.getMaxFlux() * 1.3);
            }else if (iPlayerAbility.getMaxFlux() < 1000){
                iPlayerAbility.setMaxFlux(iPlayerAbility.getMaxFlux() * 1.2);
            }else if (iPlayerAbility.getMaxFlux() < 1250){
                iPlayerAbility.setMaxFlux(iPlayerAbility.getMaxFlux() * 1.15);
            }else if (iPlayerAbility.getMaxFlux() < 1300){
                iPlayerAbility.setMaxFlux(iPlayerAbility.getMaxFlux() * 1.1);
            }else{
                iPlayerAbility.setMaxFlux(iPlayerAbility.getMaxFlux() * 1.05);
            }
            iPlayerAbility.addFlux(iPlayerAbility.getMaxFlux() - iPlayerAbility.getFlux());
        });
        player.invalidateCaps();
    }



    public static void speed(Player player, ItemStack stack) {
        player.reviveCaps();
        LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(iPlayerAbility -> {
            if (stack.is(Items.REDSTONE)){
                if (iPlayerAbility.getMiningSpeedBoost() < 1.35f){
                    iPlayerAbility.addMiningSpeed(0.3f);
                }
            }else if (stack.is(Registration.BEDROCK_WIRE_ITEM.get())){
                if (iPlayerAbility.getMiningSpeedBoost() > 0.3f){
                    iPlayerAbility.addMiningSpeed(-0.3f);
                }
            }else{
                iPlayerAbility.setMiningSpeedBoost(0f);
            }
            player.sendSystemMessage(Component.literal("New mining speed boost is: "+iPlayerAbility.getMiningSpeedBoost()));

        });
        player.invalidateCaps();

    }

    public static void fallDamage(Player player, ItemStack stack) {
        //1/4 units increase, 2 max
        player.reviveCaps();
        LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(iPlayerAbility -> {
            if (stack.is(Items.SLIME_BALL)){
                iPlayerAbility.setTakesFallDamage(false);
                player.sendSystemMessage(Component.literal("Fall Damage Negated"));

            }else{
                iPlayerAbility.setTakesFallDamage(true);
                player.sendSystemMessage(Component.literal("Fall Damage back to normal"));//TODO translate
            }

        });
        player.invalidateCaps();

    }
    @Override
    public String getName() {
        return  "player_upgrade";
    }

    public static String getDescriptions(ItemStack stack) {
        if (stack.is(Items.REDSTONE)) return "Increase mining speed.";
        if (stack.is(Registration.BEDROCK_WIRE_ITEM.get())) return "Decreases mining speed.";
        if (stack.is(Registration.ROPE_ITEM.get())) return "Returns mining speed to default.";
        if (stack.is(Items.RABBIT_FOOT)) return "Increase player Jump";
        if (stack.is(Items.RABBIT_HIDE)) return "Decreases player Jump";
        if (stack.is(Items.CHAIN)) return "Returns player Jump to default";
        if (stack.is(Items.SLIME_BALL)) return "Removes fall damage.";
        if (stack.is(Items.HONEY_BLOCK)) return "Player will take fall damage again.";
        if (stack.is(Registration.BEDROCK_COMPRESSED_WIRE_ITEM.get())) return "Raise the players max flux";
        return "";
    }
}
