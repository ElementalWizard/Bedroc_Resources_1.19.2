package com.alexvr.bedres.blocks.eventAltar.events;

import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.common.util.LazyOptional;

public class ToolUpgradeEvent implements IEvent{

    public static void start(Player player, ItemStack stack) {
        player.reviveCaps();
        LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(iPlayerAbility -> {
            String message = "Upgraded ";
            if (stack.getItem() instanceof TieredItem tieredItem){
                String tier = getType(tieredItem);
                if (tieredItem instanceof SwordItem ){
                    message += "Sword Skill from: " + iPlayerAbility.getSword() + " to: " + tier;
                    iPlayerAbility.setSword(tier);
                }else if (tieredItem instanceof HoeItem ){
                    message += "Hoe Skill from: " + iPlayerAbility.getHoe() + " to: " + tier;
                    iPlayerAbility.setHoe(tier);
                }else if (tieredItem instanceof AxeItem ){
                    message += "Axe Skill from: " + iPlayerAbility.getAxe() + " to: " + tier;
                    iPlayerAbility.setAxe(tier);
                }else if (tieredItem instanceof ShovelItem ){
                    message += "Shovel Skill from: " + iPlayerAbility.getShovel() + " to: " + tier;
                    iPlayerAbility.setShovel(tier);
                }else if (tieredItem instanceof PickaxeItem ){
                    message += "Pickaxe Skill from: " + iPlayerAbility.getPick() + " to: " + tier;
                    iPlayerAbility.setPick(tier);
                }
                player.sendMessage(new TextComponent(message),player.getUUID());
            }
        });
        player.invalidateCaps();

    }

    public static String getType(TieredItem tieredItem) {
        Tier tier = tieredItem.getTier();
        if (Tiers.WOOD.equals(tier)) {
            return "no"; // reset
        } else if (Tiers.STONE.equals(tier)) {
            return "stone";
        } else if (Tiers.IRON.equals(tier)) {
            return "iron";
        } else if (Tiers.GOLD.equals(tier)) {
            return "gold";
        } else if (Tiers.DIAMOND.equals(tier)) {
            return "diamond";
        }
        return "no";
    }

    @Override
    public String getName() {
        return "tool";
    }

    public static String getDescriptions(ItemStack stack) {
        String item = stack.getDisplayName().getString();
        if (stack.getDisplayName().getString().contains("Wooden")){
            return "Sets the player's empty hand to default"+item.substring(item.indexOf(" "),item.indexOf("]"))+ " ability ";
        }
        return "Upgrades the player to be able to use empty hand as: " + stack.getDisplayName().getString().replace("[","").replace("]","");
    }

}
