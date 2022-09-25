package com.alexvr.bedres.blocks.enderianRitualPedestal;



import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class EnderianRitualPedestal extends Block  implements EntityBlock {
    private static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 12.0D, 11.0D);


    public EnderianRitualPedestal(Properties props) {
        super(props);

    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {

        BlockEntity tileEntity = world.getBlockEntity(pos);
        if (tileEntity instanceof EnderianRitualPedestalTile  pedestalTile) {
            pedestalTile.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                ItemStack itemInHand = player.getItemInHand(hand);

                if(itemInHand.isEmpty() || player.isShiftKeyDown()){
                    boolean extracted = player.addItem(h.getStackInSlot(0));
                    if (extracted) {
                        h.insertItem(0,ItemStack.EMPTY,false);
                    }

                }else{
                    ItemStack remainder = ItemHandlerHelper.insertItem(h, itemInHand, false);
                    if (remainder.isEmpty()) {
                        player.setItemInHand(hand,ItemStack.EMPTY);
                    } else {
                        player.setItemInHand(hand,remainder);
                    }
                }

                pedestalTile.sendUpdates();
                state.updateNeighbourShapes(world,pos,32);
            });
        } else {
            throw new IllegalStateException("Our named container provider is missing!");
        }
        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return (level1, blockPos, blockState, t) -> {
            if (t instanceof EnderianRitualPedestalTile tile) {
                tile.tickServer();
            }
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new EnderianRitualPedestalTile(pPos, pState);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

}
