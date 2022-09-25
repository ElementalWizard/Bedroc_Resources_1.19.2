package com.alexvr.bedres.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;
import java.util.Random;


public class HexTile extends Block {
    public static final EnumProperty<DyeColor> COLOR = EnumProperty.create("hex_color", DyeColor.class);
    public static final IntegerProperty COLORBRIGHTNESS = IntegerProperty.create("hex_brightness", 0,6);

    public HexTile(Properties p_49795_) {
        super(p_49795_.randomTicks());
        this.registerDefaultState(this.stateDefinition.any().setValue(COLOR, DyeColor.MAGENTA).setValue(COLORBRIGHTNESS, 0));
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pPlayer.getItemInHand(pHand).getItem() instanceof DyeItem dyeItem){
            pLevel.setBlockAndUpdate(pPos,pState.setValue(COLOR,dyeItem.getDyeColor()).setValue(COLORBRIGHTNESS,0));
            return InteractionResult.SUCCESS;
        }
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        pTooltip.add(Component.literal("You can dye this block with right click of a dye.")); //TODO translate
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        Player player = pLevel.getNearestPlayer(pFromPos.getX(),pFromPos.getY(),pFromPos.getZ(),16,false);
        if ( player != null && player.getMainHandItem().getItem() instanceof DyeItem && player.getOffhandItem().is(player.getMainHandItem().getItem()) && pLevel.getBlockState(pFromPos).getBlock() instanceof HexTile){
            pLevel.setBlockAndUpdate(pPos,pState.setValue(COLOR,pLevel.getBlockState(pFromPos).getValue(COLOR)).setValue(COLORBRIGHTNESS,0));
        }
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        int amount = new Random().nextInt(0,3) - 1;
        int newValue =pState.getValue(COLORBRIGHTNESS) + amount;
        if (newValue > 0 && newValue < 6){
            pLevel.setBlockAndUpdate(pPos,pState.setValue(COLORBRIGHTNESS,pState.getValue(COLORBRIGHTNESS) + amount).setValue(COLOR,pState.getValue(COLOR)));
        }
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(COLOR).add(COLORBRIGHTNESS);
    }

    public static int getColor(BlockState state){
        Color color = new Color(state.getValue(COLOR).getMaterialColor().col);
        if (state.getValue(COLORBRIGHTNESS) == 0){
            return color.getRGB();
        }
        int dir =state.getValue(COLORBRIGHTNESS) < 4 ? 1: -1;
        int amount = state.getValue(COLORBRIGHTNESS) <= 3? state.getValue(COLORBRIGHTNESS):state.getValue(COLORBRIGHTNESS) -3;
        for(int i = 0  ; i < amount; i ++){
            if (dir == 1){
                color = color.brighter();
            }else{
                color = color.darker();
            }
        }
        return color.getRGB();
    }

}
