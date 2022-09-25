package com.alexvr.bedres.items;

import com.alexvr.bedres.utils.IDisplayFlux;
import com.alexvr.bedres.utils.NBTHelper;
import com.alexvr.bedres.utils.WorldEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
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

public class FluxedOracle extends Item implements IDisplayFlux {


    public FluxedOracle(Properties settings) {
        super(settings);
    }

//    public static boolean isOpen() {
//        return Registry.ITEM.getKey(Registration.FLUX_ORACLE_ITEM.get()).equals(PatchouliAPI.get().getOpenBookGui());
//    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, java.util.List<net.minecraft.network.chat.Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Book for this mod"));
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (worldIn.isClientSide) {
            NBTHelper.flipBoolean(playerIn.getMainHandItem(),"active");
            Minecraft.getInstance().setScreen(WorldEventHandler.fxG);
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public boolean shouldDisplay(ItemStack offHand) {
        return true;
    }
}
