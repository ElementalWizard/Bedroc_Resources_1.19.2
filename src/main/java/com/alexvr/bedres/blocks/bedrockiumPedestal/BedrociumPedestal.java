package com.alexvr.bedres.blocks.bedrockiumPedestal;

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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

public class BedrociumPedestal extends Block implements EntityBlock {

    private static final VoxelShape Base = Block.box(0, 0.0D, 0, 16, .3, 16);


    public BedrociumPedestal(BlockBehaviour.Properties props) {
        super(props);

    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        if (world.isClientSide) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof BedrociumPedestalTile) {
                world.getBlockEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                    ItemStack itemInHand = player.getItemInHand(hand);

                    if(itemInHand.isEmpty() || player.isShiftKeyDown()){
                        ((BedrociumPedestalTile) tileEntity).item = ItemStack.EMPTY;
                    }else{
                        ((BedrociumPedestalTile) tileEntity).item = itemInHand;
                    }
                    tileEntity.setChanged();
                    ((BedrociumPedestalTile) tileEntity).sendUpdates();
                    state.updateNeighbourShapes(world,pos,32);
                });
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }else{
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof BedrociumPedestalTile) {
                world.getBlockEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
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

                    tileEntity.setChanged();
                    ((BedrociumPedestalTile) tileEntity).sendUpdates();
                    state.updateNeighbourShapes(world,pos,32);
                });
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
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
            if (t instanceof BedrociumPedestalTile tile) {
                tile.tickServer();
            }
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BedrociumPedestalTile(pPos, pState);
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Base;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }
}
