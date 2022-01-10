package com.alexvr.bedres.items;

import com.alexvr.bedres.utils.NBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class XPMedallion extends Item {

    public XPMedallion(Properties pProperties) {
        super(pProperties.stacksTo(1).fireResistant().setNoRepair().defaultDurability(256));
    }

    public int levelsStored = 0;

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TextComponent(String.format("Total Levels: %d",levelsStored)).withStyle(ChatFormatting.DARK_PURPLE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player player, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) return super.use(pLevel, player, pUsedHand);
        if (player.isCreative()){
            if (player.isShiftKeyDown()){
                levelsStored--;
            }else{
                levelsStored++;
            }
            NBTHelper.setInteger(player.getItemInHand(pUsedHand),"levels",levelsStored);
            return InteractionResultHolder.success(player.getItemInHand(pUsedHand));
        }else{
            if (player.isShiftKeyDown() && levelsStored >0){
                levelsStored--;
                player.giveExperienceLevels(1);
                NBTHelper.setInteger(player.getItemInHand(pUsedHand),"levels",levelsStored);
                return InteractionResultHolder.success(player.getItemInHand(pUsedHand));
            }else if (!player.isShiftKeyDown() && player.experienceLevel > 1){
                levelsStored++;
                player.giveExperienceLevels(-1);
                NBTHelper.setInteger(player.getItemInHand(pUsedHand),"levels",levelsStored);
                return InteractionResultHolder.success(player.getItemInHand(pUsedHand));
            }
        }
        return super.use(pLevel, player, pUsedHand);
    }
}
