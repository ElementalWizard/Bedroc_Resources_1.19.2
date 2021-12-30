package com.alexvr.bedres.items;

import com.alexvr.bedres.blocks.DungeonDimensionPortalBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class FluxOracle extends Item {
    public FluxOracle(Item.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player entity = context.getPlayer();
        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        ItemStack itemstack = context.getItemInHand();
        Level world = context.getLevel();
        if (!entity.mayUseItemAt(pos, context.getClickedFace(), itemstack)) {
            return InteractionResult.FAIL;
        } else {
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            boolean success = false;
            if (world.isEmptyBlock(pos) && true) {
                DungeonDimensionPortalBlock.portalSpawn(world, pos);
                itemstack.hurtAndBreak(1, entity, c -> c.broadcastBreakEvent(context.getHand()));
                success = true;
            }
            return success ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }
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
