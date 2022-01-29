package com.alexvr.bedres.items;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
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
                    ItemStack itemstack = new ItemStack(Registration.BEDROCK_WIRE_ITEM.get(), 1);
                    level.addFreshEntity(new ItemEntity(level,pContext.getClickedPos().getX() + 0.5f,pContext.getClickedPos().getY() + 1,pContext.getClickedPos().getZ()+ 0.5f,itemstack));

                }
            }
        }else{
            if(pContext.getPlayer().isShiftKeyDown()){
                if(level.getBlockState(pContext.getClickedPos()).getBlock().getName().equals(Blocks.BEDROCK.getName())){
                    level.addParticle(Registration.LIGHT_PARTICLE_TYPE.get(),pContext.getClickedPos().getX()+ 0.5f,pContext.getClickedPos().getY() + 0.5,pContext.getClickedPos().getZ()+ 0.5f, 0, 0.0D, 0.0D);

                }
            }
        }
        return super.useOn(pContext);
    }

}
