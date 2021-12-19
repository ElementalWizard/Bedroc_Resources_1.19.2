package com.alexvr.bedres.items;

import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.world.item.Item;

public class FluxOracle extends Item {
    public FluxOracle(Item.Properties pProperties) {
        super(pProperties);
    }


//    @SuppressWarnings("NullableProblems")
//    @Override
//    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
//        if(!worldIn.isRemote) {
//            if (playerIn.isSneaking()){
//                LazyOptional<IPlayerAbility> abilities = playerIn.getCapability(PlayerAbilityProvider.PLAYER_ABILITY_CAPABILITY, null);
//                abilities.ifPresent(iPlayerAbility -> {
//                    LazyOptional<IBedrockFlux> bedrockFlux = playerIn.getCapability(BedrockFluxProvider.BEDROCK_FLUX_CAPABILITY, null);
//                    bedrockFlux.ifPresent(flux -> {
//                        playerIn.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + "Skills is:"), false);
//                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Sword", iPlayerAbility.getSword())), false);
//                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Axe", iPlayerAbility.getAxe())), false);
//                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Shovel", iPlayerAbility.getShovel())), false);
//                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Hoe", iPlayerAbility.getHoe())), false);
//                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Pickaxe", iPlayerAbility.getPick())), false);
//                        playerIn.sendStatusMessage(new StringTextComponent(TextFormatting.DARK_RED + "Passive: "), false);
//                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Speed", iPlayerAbility.getMiningSpeedBoost())), false);
//                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Jump", iPlayerAbility.getJumpBoost())), false);
//                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Flux", flux.getBedrockFluxString())), false);
//                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Minimum Flux", flux.getMin())), false);
//                        playerIn.sendStatusMessage(new StringTextComponent(String.format(TextFormatting.AQUA + " %s" + TextFormatting.DARK_RED + " Max Flux", flux.getMaxBedrockFlux())), false);
//                    });
//                });
//            }else{
//                NBTHelper.flipBoolean(playerIn.getHeldItemMainhand(),"active");
//                Minecraft.getInstance().displayGuiScreen(WorldEventHandler.fxG);
//
//            }
//        }
//        return super.onItemRightClick(worldIn, playerIn, handIn);
//    }

}
