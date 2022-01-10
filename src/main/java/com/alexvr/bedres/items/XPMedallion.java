package com.alexvr.bedres.items;

import com.alexvr.bedres.utils.NBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
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
    public boolean autoCollect = false;
    public boolean collecting = false;
    public boolean returning = false;
    public int coolDown = 21;


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        ChatFormatting formatting = coolDown == 21 ? ChatFormatting.GREEN: ChatFormatting.RED;
        pTooltipComponents.add(new TextComponent(String.format("Collecting XP: %b ",collecting)).withStyle(formatting));
        pTooltipComponents.add(new TextComponent(String.format("Returning XP: %b",returning)).withStyle(formatting));
        pTooltipComponents.add(new TextComponent(String.format("Total Levels: %d",levelsStored)).withStyle(ChatFormatting.DARK_PURPLE));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player player, InteractionHand pUsedHand) {
        if (coolDown ==21){
            coolDown--;
            if (player.isShiftKeyDown()){
                returning = !returning;
                collecting = false;
            }else{
                collecting = !collecting;
                returning = false;
            }
            NBTHelper.setInteger(player.getMainHandItem(),"levels",levelsStored);
            NBTHelper.setBoolean(player.getMainHandItem(),"collecting",collecting);
            NBTHelper.setBoolean(player.getMainHandItem(),"returning",returning);
            return InteractionResultHolder.success(player.getItemInHand(pUsedHand));
        }
        return super.use(pLevel, player, pUsedHand);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (coolDown <= 20) {
            coolDown--;
            if (coolDown <= 0) {
                coolDown = 21;
            }
            return;
        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }


//    @Override
//    public void readShareTag(ItemStack stack, @Nullable CompoundTag nbt) {
//        if (nbt.contains("collecting")){
//            collecting = NBTHelper.getBoolean(stack,"collecting");
//        }
//        if (nbt.contains("returning")){
//            returning = NBTHelper.getBoolean(stack,"returning");
//        }
//        if (nbt.contains("levels")){
//            levelsStored = NBTHelper.getInteger(stack,"levels");
//        }
//        super.readShareTag(stack, nbt);
//    }
}
