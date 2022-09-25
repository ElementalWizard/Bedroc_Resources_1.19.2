package com.alexvr.bedres.items;

import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.utils.IDisplayFlux;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FluxedCupcake extends Item implements IDisplayFlux {
    public static final FoodProperties CUPCAKE = (new FoodProperties.Builder()).nutrition(4).saturationMod(0.3F).build();

    public FluxedCupcake(Properties pProperties) {
        super(pProperties.food(CUPCAKE));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        Minecraft.getInstance().player.reviveCaps();
        LazyOptional<IPlayerAbility> abilities = Minecraft.getInstance().player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        abilities.ifPresent(flux -> {
            flux.addFlux((flux.getMaxFlux()-flux.getFlux())/10);
        });
        Minecraft.getInstance().player.invalidateCaps();
        return super.finishUsingItem(pStack, pLevel, pLivingEntity);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("Recover 1/10 of the missing Flux"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean shouldDisplay(ItemStack offHand) {
        return true;
    }
}
