package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.IDisplayFlux;
import com.alexvr.bedres.utils.NBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class XPMedallion extends Item implements IDisplayFlux {

    public static final ResourceLocation MODE = new ResourceLocation(BedrockResources.MODID,"mode");


    public XPMedallion(Properties pProperties) {
        super(pProperties.stacksTo(1).fireResistant().setNoRepair());
    }

    @Override
    public void verifyTagAfterLoad(CompoundTag pCompoundTag) {
        if (!pCompoundTag.contains("mode")){
            pCompoundTag.putInt("mode",0);
        }
        super.verifyTagAfterLoad(pCompoundTag);
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("'K' by default changes modes"));
        pTooltipComponents.add(Component.literal(String.format("Mode: %s",getMode(pStack) == 1?"Use":"Auto Absorb")).withStyle(ChatFormatting.DARK_PURPLE));
        pTooltipComponents.add(Component.literal(String.format("Total Levels: %d", Minecraft.getInstance().player.totalExperience)).withStyle(ChatFormatting.DARK_PURPLE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player player, InteractionHand pUsedHand) {
        if (!pLevel.isClientSide()) return super.use(pLevel, player, pUsedHand);
        if (player.isCreative()){
            if (player.isShiftKeyDown() && getXP(player.getItemInHand(pUsedHand)) <=0){
                return InteractionResultHolder.pass(player.getItemInHand(pUsedHand));
            }
            NBTHelper.addInteger(player.getItemInHand(pUsedHand),"levels",player.isShiftKeyDown() ? -1: 1);
            return InteractionResultHolder.success(player.getItemInHand(pUsedHand));
        }else{
           /* int levelsStored = getXP(player.getItemInHand(pUsedHand));
            if (player.isShiftKeyDown() && levelsStored >0){
                levelsStored--;
                player.giveExperienceLevels(1);
                setXP(player.getItemInHand(pUsedHand),levelsStored);
                return InteractionResultHolder.success(player.getItemInHand(pUsedHand));
            }else if (!player.isShiftKeyDown() && player.experienceLevel > 1){
                levelsStored++;
                player.giveExperienceLevels(-1);
                setXP(player.getItemInHand(pUsedHand),levelsStored);
                return InteractionResultHolder.success(player.getItemInHand(pUsedHand));
            }*/
        }
        return super.use(pLevel, player, pUsedHand);
    }

    @Override
    public boolean shouldDisplay(ItemStack offHand) {
        return true;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return NBTHelper.getInt(pStack,"levels") > 0;
    }

    public int getXP(ItemStack stack){
        return NBTHelper.getInt(stack, "levels");
    }
    public void setXP(ItemStack stack, int amount){
        NBTHelper.setInteger(stack, "levels",amount);
    }

    public int getMode(ItemStack stack){
        return NBTHelper.getInt(stack, "mode");
    }

    public void cycleMode(ItemStack stack){
        NBTHelper.setInteger(stack, "mode",(getMode(stack) + 1)%2);
    }
}
