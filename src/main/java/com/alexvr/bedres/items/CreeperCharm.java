package com.alexvr.bedres.items;

import com.alexvr.bedres.entities.treckingcreeper.TreckingCreeperEntity;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.NBTHelper;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
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
            creeperEntity.setTypeAssignedDir(true);
            String name = NBTHelper.getStirng(stack,"name");
            if(!name.equals("Not Given")){
                creeperEntity.setCustomName(new TextComponent(name));
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
        pTooltipComponents.add(new TextComponent("A char to re-summon your Creeper friend"));
        if (NBTHelper.getBoolean(pStack,"generated")){
            pTooltipComponents.add(new TextComponent("Custom Name: " + NBTHelper.getStirng(pStack,"name")));
            int colorID=Integer.parseInt(NBTHelper.getStirng(pStack,"color"));
            DyeColor color = DyeColor.byId(colorID);
            pTooltipComponents.add(new TextComponent("Backpack Color: " + color.name()).withStyle(Style.EMPTY.withColor(color.getTextColor())));
            pTooltipComponents.add(new TextComponent("Owner: " + pLevel.getPlayerByUUID(NBTHelper.getUUID(pStack,"uuid")).getName().getString()));
            pTooltipComponents.add(new TextComponent("Type: " + getCreeperType(NBTHelper.getInt(pStack,"type"))));
        }
    }
//    private ChatFormatting getBackpackColor(int colorID) {
//        ChatFormatting formatting = ChatFormatting.GREEN;
//        switch (DyeColor.byId(colorID)) {
//            case WHITE-> formatting = ChatFormatting.WHITE;
//            case GRAY-> formatting = ChatFormatting.WHITE;
//            case MAGENTA-> formatting = ChatFormatting.WHITE;
//            case BLACK-> formatting = ChatFormatting.WHITE;
//            case BLUE-> formatting = ChatFormatting.WHITE;
//            case CYAN-> formatting = ChatFormatting.WHITE;
//            case GREEN-> formatting = ChatFormatting.WHITE;
//            case LIGHT_BLUE-> formatting = ChatFormatting.WHITE;
//            case LIGHT_GRAY-> formatting = ChatFormatting.WHITE;
//            case LIME-> formatting = ChatFormatting.WHITE;
//            case ORANGE-> formatting = ChatFormatting.RED;
//            case PINK-> formatting = ChatFormatting.RED;
//            case PURPLE-> formatting = ChatFormatting.DARK_PURPLE;
//            case RED-> formatting = ChatFormatting.DARK_RED;
//            default -> formatting = ChatFormatting.YELLOW;
//        }
//        return formatting;
//    }

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

    public int getColor(ItemStack stack) {
        int colorID=DyeColor.GREEN.getMaterialColor().col;
        if (NBTHelper.getBoolean(stack,"generated")){
            colorID=DyeColor.byId(Integer.parseInt(NBTHelper.getStirng(stack,"color"))).getMaterialColor().col;
        }
        return colorID;
    }
}
