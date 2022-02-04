package com.alexvr.bedres.items;

import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.utils.IDisplayFlux;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
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
        pTooltipComponents.add(new TextComponent("Nice Book"));
        if (Minecraft.getInstance().player != null){
            LazyOptional<IPlayerAbility> abilities = Minecraft.getInstance().player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            abilities.ifPresent(flux -> pTooltipComponents.add(new TextComponent("Flux Levels: " + flux.getFlux() + "/" + flux.getMaxFlux())));
        }
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (!worldIn.isClientSide) {
            LazyOptional<IPlayerAbility> abilities = Minecraft.getInstance().player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            abilities.ifPresent(flux -> playerIn.sendMessage(new TextComponent("Flux Level: " + flux.getFlux() + "/" + flux.getMaxFlux()),playerIn.getUUID()));

//            if (playerIn.isShiftKeyDown()){
//                playerIn.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY).ifPresent(h->{
//                    h.setFluxOverlayScreen(new FluxOverlay(new TextComponent("Overlay"),h));
//
//                });
//            }
        }

//        if (playerIn instanceof ServerPlayer player) {
//            PatchouliAPI.get().openBookGUI((ServerPlayer) playerIn, Registry.ITEM.getKey(this));
//        }

        return new InteractionResultHolder<>(InteractionResult.SUCCESS, stack);
    }

    @Override
    public boolean shouldDisplay(ItemStack offHand) {
        return true;
    }
}
