package com.alexvr.bedres.blocks.eventAltar.events;

import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.common.util.LazyOptional;

public class ToolUpgradeEvent implements IEvent{

    String name;

    public ToolUpgradeEvent(){
        name = "tool";
    }

    public static void start(Player player, ItemStack stack) {
        player.reviveCaps();
        LazyOptional<IPlayerAbility> abilities = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(iPlayerAbility -> {
            player.sendMessage(new TextComponent("Old pick: " + iPlayerAbility.getPick()),player.getUUID());
            if (stack.getItem() instanceof TieredItem tieredItem){
                String tier = getType(tieredItem);
                if (tieredItem instanceof SwordItem ){
                    iPlayerAbility.setSword(tier);
                }else if (tieredItem instanceof HoeItem ){
                    iPlayerAbility.setHoe(tier);
                }else if (tieredItem instanceof AxeItem ){
                    iPlayerAbility.setAxe(tier);
                }else if (tieredItem instanceof ShovelItem ){
                    iPlayerAbility.setShovel(tier);
                }else if (tieredItem instanceof PickaxeItem ){
                    iPlayerAbility.setPick(tier);
                }
            }
            player.sendMessage(new TextComponent("New pick: " + iPlayerAbility.getPick()),player.getUUID());

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
        return name;
    }

    @Override
    public void getSetName(String name) {
        this.name = name;
    }
}
