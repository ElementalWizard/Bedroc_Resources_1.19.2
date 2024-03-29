package com.alexvr.bedres.items;

import com.alexvr.bedres.entities.treckingcreeper.TreckingCreeperEntity;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.NBTHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;


public class CreeperCharm extends Item {
    public CreeperCharm(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Player pPlayer = pContext.getPlayer();
        ItemStack stack = pPlayer.getItemInHand(pContext.getHand());
        if (NBTHelper.getBoolean(stack,"generated") && !pContext.getLevel().isClientSide()){
            TreckingCreeperEntity creeperEntity = Registration.TRECKING_CREEPER.get().create(pContext.getLevel());
            creeperEntity.setTamed(true);
            creeperEntity.setBackpackColor(DyeColor.byId(Integer.parseInt(NBTHelper.getStirng(stack,"color"))));
            creeperEntity.setOwnerUUID(NBTHelper.getUUID(stack,"uuid"));
            creeperEntity.setTypeDir(NBTHelper.getInt(stack,"type"));
            creeperEntity.setInventory(NBTHelper.getTag(stack,"InventoryCustom"));
            String name = NBTHelper.getStirng(stack,"name");
            if(!name.equals("Not Given")){
                creeperEntity.setCustomName(Component.literal(name));
            }
            creeperEntity.moveTo(pContext.getClickedPos().getX() + 0.5f,pContext.getClickedPos().getY()+2.0f,pContext.getClickedPos().getZ()+ 0.5f);
            pContext.getLevel().addFreshEntity(creeperEntity);
            pPlayer.setItemInHand(pContext.getHand(),ItemStack.EMPTY);
            return InteractionResult.CONSUME;
        }
        return super.useOn(pContext);
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, java.util.List<net.minecraft.network.chat.Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("A charm to re-summon your Creeper friend"));
        pTooltipComponents.add(Component.literal("You can tame the tiny creepers!").withStyle(ChatFormatting.DARK_GREEN));
        if (NBTHelper.getBoolean(pStack,"generated")){
            pTooltipComponents.add(Component.literal("Custom Name: " + NBTHelper.getStirng(pStack,"name")));
            int colorID=Integer.parseInt(NBTHelper.getStirng(pStack,"color"));
            DyeColor color = DyeColor.byId(colorID);
            pTooltipComponents.add(Component.literal("Backpack Color: " + color.name()).withStyle(Style.EMPTY.withColor(color.getTextColor())));
            pTooltipComponents.add(Component.literal("Owner: " + pLevel.getPlayerByUUID(NBTHelper.getUUID(pStack,"uuid")).getName().getString()));
            pTooltipComponents.add(Component.literal("Type: " + getCreeperType(NBTHelper.getInt(pStack,"type"))));
        }
    }

    private String getCreeperType(int type) {
        return switch (type) {
            case 0 -> "Green";
            case 1 -> "Cyan";
            case 2 -> "GreyScale";
            case 3 -> "Magenta";
            case 4 -> "Purple";
            case 5 -> "Red";
            case 6 -> "Sepia";
            case 7 -> "Void";
            default -> "Yellow";
        };
    }

    public int getColor(ItemStack stack, int layer) {
        int colorID=DyeColor.GREEN.getMaterialColor().col;
        if (layer == 0){
            if (NBTHelper.getBoolean(stack,"generated")){
                colorID=DyeColor.byId(Integer.parseInt(NBTHelper.getStirng(stack,"color"))).getMaterialColor().col;
            }
        }else{
            if (NBTHelper.getBoolean(stack,"generated")){
                colorID=getCreeperTyperColor(stack);
            }
        }

        return colorID;
    }

    private int getCreeperTyperColor(ItemStack stack) {

        int type = NBTHelper.getInt(stack,"type");
        return switch (type) {
            case 0 -> 0x41B736;
            case 1 -> 0x32BFBA;
            case 2 -> 0x515151;
            case 3 -> 0xA156A8;
            case 4 -> 0x5D32BF;
            case 5 -> 0xA85656;
            case 6 -> 0x978574;
            case 7 -> 0xBE48C9;
            default -> 0xBFBD32;
        };
    }
}
