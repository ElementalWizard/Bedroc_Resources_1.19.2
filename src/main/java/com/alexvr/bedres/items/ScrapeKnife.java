package com.alexvr.bedres.items;

import com.alexvr.bedres.capability.abilities.IPlayerAbility;
import com.alexvr.bedres.capability.abilities.PlayerAbilityProvider;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.IDisplayFlux;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.util.LazyOptional;

public class ScrapeKnife extends SwordItem implements IDisplayFlux {

    public ScrapeKnife(Properties pProperties) {
        super(Tiers.STONE,1, -2.4F,pProperties);

    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Minecraft.getInstance().player.reviveCaps();
        LazyOptional<IPlayerAbility> playerFlux = Minecraft.getInstance().player.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
        playerFlux.ifPresent(k -> {
            if (k.getFlux() > 0.25){
                k.removeFlux(.25);
                if(pContext.getPlayer().isShiftKeyDown()){
                    if(level.getBlockState(pContext.getClickedPos()).getBlock().getName().equals(Blocks.BEDROCK.getName())){
                        ItemStack itemstack = new ItemStack(Registration.BEDROCK_WIRE_ITEM.get(), 1);
                        level.addFreshEntity(new ItemEntity(level,pContext.getClickedPos().getX() + 0.5f,pContext.getClickedPos().getY() + 1,pContext.getClickedPos().getZ()+ 0.5f,itemstack));
                    }
                }
            }else{
                if (!level.isClientSide){
                    pContext.getPlayer().sendSystemMessage(Component.literal("Flux Levels too weak, get at least 0.25 flux points"));
                }
            }
        });
        Minecraft.getInstance().player.invalidateCaps();
        return super.useOn(pContext);
    }

    @Override
    public boolean shouldDisplay(ItemStack offHand) {
        return true;
    }
}
