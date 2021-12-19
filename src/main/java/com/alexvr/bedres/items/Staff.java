package com.alexvr.bedres.items;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class Staff extends SwordItem {

    public Staff(Item.Properties pProperties) {
        super(Tiers.DIAMOND,4, 0.5F, pProperties);
    }

//    @Override
//    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
//        NBTHelper.flipBoolean(playerIn.getHeldItemMainhand(),"status");
//        return super.onItemRightClick(worldIn, playerIn, handIn);
//    }
//
//    @Override
//    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
//        if (entityIn instanceof PlayerEntity) {
//            PlayerEntity player = (PlayerEntity) entityIn;
//            if (player.getHeldItemMainhand() != stack || player.onGround || player.isPotionActive(Effects.HUNGER) ){
//                NBTHelper.setBoolean(stack,"status",false);
//            }
//            if (player.getHeldItemMainhand() == stack && !player.onGround && !player.isPotionActive(Effects.HUNGER) && NBTHelper.getBoolean(stack, "status", false)) {
//                NBTHelper.increaseInteger(stack, "counter", 1);
//                if (NBTHelper.getInteger(stack, "counter") % 20 == 0) {
//                    if (new Random().nextBoolean()) {
//                        LazyOptional<IBedrockFlux> bedrockFlux = player.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
//                        bedrockFlux.ifPresent(flux -> {
//                            flux.fill(4);
//                        });
//                    }
//                }
//                if (NBTHelper.getInteger(stack, "counter") % (20 * 5) == 0) {
//                    if (new Random().nextBoolean()) {
//                        player.addPotionEffect(new EffectInstance(Effects.HUNGER, 20 * 5 , 2));
//                        NBTHelper.setBoolean(stack,"status",false);
//                    }
//                }
//                player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 1, 5));
//                player.setMotion(-player.getMotion().x, 0, -player.getMotion().z);
//                player.fallDistance = player.fallDistance/2;
//                player.velocityChanged = true;
//            }
//        }
//        super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
//    }
//
//    @Override
//    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
//        tooltip.add(new StringTextComponent("Gravity cancel: " + NBTHelper.getBoolean(stack,"status",false)));
//        super.addInformation(stack, worldIn, tooltip, flagIn);
//    }

}
