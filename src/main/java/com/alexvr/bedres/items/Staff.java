package com.alexvr.bedres.items;

import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.utils.IDisplayFlux;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class Staff extends TieredItem implements IDisplayFlux {
    private static final UUID REDUCED_GRAVITY_ID = UUID.fromString("DEB06000-7979-4242-8888-00000DEB0600");
    public static final AttributeModifier REDUCED_GRAVITY = (new AttributeModifier(REDUCED_GRAVITY_ID, "Reduced gravity", (double)-0.80, AttributeModifier.Operation.MULTIPLY_TOTAL));

    public Staff(Properties pProperties) {
        super(Tiers.DIAMOND, pProperties);
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pEntity instanceof Player player && (!player.getMainHandItem().is(this) && !player.getOffhandItem().is(this))){
            AttributeInstance grav = player.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
            if (grav.hasModifier(REDUCED_GRAVITY))
            {
                Minecraft.getInstance().player.reviveCaps();
                grav.removeModifier(REDUCED_GRAVITY);
                LazyOptional<IPlayerAbility> playerFlux = Minecraft.getInstance().player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
                playerFlux.ifPresent(k -> k.setGivenGravity(false));
                Minecraft.getInstance().player.invalidateCaps();

            }
        }else if (pEntity instanceof Player player && (player.getMainHandItem().is(this) || player.getOffhandItem().is(this))){
            AttributeInstance grav = player.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
            Minecraft.getInstance().player.reviveCaps();
            LazyOptional<IPlayerAbility> playerFlux = Minecraft.getInstance().player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
            playerFlux.ifPresent(k -> {
                if (!grav.hasModifier(REDUCED_GRAVITY) && k.getFlux() >= 0.01)
                {
                    grav.addTransientModifier(REDUCED_GRAVITY);
                    k.setGivenGravity(true);
                }
                if (k.getFlux() >= 0.01 && !player.isOnGround()){
                    k.removeFlux(.01);
                    player.resetFallDistance();
                    player.fallDistance = 0;
                    for (int i = 0; i < 9; i++) {
                        pLevel.addParticle(new DustParticleOptions(new Vector3f(0.416f,0.051f,0.678f),2),pEntity.getOnPos().getX() + 0.8,pEntity.getOnPos().getY() + 0.4,((Player) pEntity).getOnPos().getZ() +0.8f,0,-0.4,0);
                    }
                }else if (k.getFlux() < 0.01){
                    if (grav.hasModifier(REDUCED_GRAVITY))
                    {
                        grav.removeModifier(REDUCED_GRAVITY);
                        k.setGivenGravity(false);
                    }
                }
            });
            Minecraft.getInstance().player.invalidateCaps();

        }

        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.literal("While holding gravity will affect you differently"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean shouldDisplay(ItemStack offHand) {
        return true;
    }
}
