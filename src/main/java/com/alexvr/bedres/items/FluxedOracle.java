package com.alexvr.bedres.items;

import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class FluxedOracle extends Item {


    public FluxedOracle(Properties settings) {
        super(settings);
    }

//    public static boolean isOpen() {
//        return Registry.ITEM.getKey(Registration.FLUX_ORACLE_ITEM.get()).equals(PatchouliAPI.get().getOpenBookGui());
//    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, java.util.List<net.minecraft.network.chat.Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TextComponent("Nice Book"));
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);

//        if (playerIn instanceof ServerPlayer player) {
//            PatchouliAPI.get().openBookGUI((ServerPlayer) playerIn, Registry.ITEM.getKey(this));
//        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

}
