package com.alexvr.bedres.items;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class ScrapeKnife extends SwordItem {

    public ScrapeKnife(Item.Properties pProperties) {
        super(Tiers.STONE,1, -2.4F,pProperties);


    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (!level.isClientSide) {
            if(pContext.getPlayer().isShiftKeyDown()){
                if(level.getBlockState(pContext.getClickedPos()).getBlock().getName().equals(Blocks.BEDROCK.getName())){
                    ItemStack itemstack = new ItemStack(Registration.NEBULA_HEART_ITEM.get(), 1);
                    level.addFreshEntity(new ItemEntity(level,pContext.getClickedPos().getX(),pContext.getClickedPos().getY() + 1,pContext.getClickedPos().getZ(),itemstack));
                }
            }
        }
        return super.useOn(pContext);
    }

}
