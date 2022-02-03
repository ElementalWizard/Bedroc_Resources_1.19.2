package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.mojang.math.Vector3f;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class Staff extends TieredItem {
    private static final UUID REDUCED_GRAVITY_ID = UUID.fromString("DEB06000-7979-4242-8888-00000DEB0600");
    private static final AttributeModifier REDUCED_GRAVITY = (new AttributeModifier(REDUCED_GRAVITY_ID, "Reduced gravity", (double)-0.80, AttributeModifier.Operation.MULTIPLY_TOTAL));

    boolean active = false;
    public Staff(Item.Properties pProperties) {
        super(Tiers.DIAMOND, pProperties);
    }


    @Override
    public boolean onDroppedByPlayer(ItemStack item, Player player) {
        AttributeInstance grav = player.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
        BedrockResources.LOGGER.debug("Removed low gravity from Entity: {}", player);
        grav.removeModifier(REDUCED_GRAVITY);
        active = false;
        return super.onDroppedByPlayer(item, player);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player && (!player.getMainHandItem().is(this) && !player.getOffhandItem().is(this))){
            AttributeInstance grav = player.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
            if (grav.hasModifier(REDUCED_GRAVITY))
            {
                BedrockResources.LOGGER.debug("Removed low gravity from Entity: {}", player);
                grav.removeModifier(REDUCED_GRAVITY);
            }
        }else if (pEntity instanceof Player player && (player.getMainHandItem().is(this) || player.getOffhandItem().is(this))){
            AttributeInstance grav = player.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
            if (!grav.hasModifier(REDUCED_GRAVITY))
            {
                BedrockResources.LOGGER.debug("Granted low gravity to Entity: {}", player);
                grav.addTransientModifier(REDUCED_GRAVITY);
            }
            LazyOptional<IPlayerAbility> playerFlux = player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            playerFlux.ifPresent(k -> {
                if (k.getFlux() > 0.05){
                    k.removeFlux(.05);
                    player.resetFallDistance();
                    player.fallDistance = 0;
                    for (int i = 0; i < 9; i++) {
                        pLevel.addParticle(new DustParticleOptions(new Vector3f(0.416f,0.051f,0.678f),2),pEntity.getOnPos().getX() + 0.8,pEntity.getOnPos().getY() + 0.4,((Player) pEntity).getOnPos().getZ() +0.8f,0,-0.4,0);
                    }
                }
            });

        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        AttributeInstance grav = pPlayer.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
        if (!grav.hasModifier(REDUCED_GRAVITY))
        {
            BedrockResources.LOGGER.info("Granted low gravity to Entity: {}", pPlayer);
            grav.addTransientModifier(REDUCED_GRAVITY);
            pPlayer.resetFallDistance();
            active = true;
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(new TextComponent("While holding gravity will affect you differently"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
