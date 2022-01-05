package com.alexvr.bedres.blocks.bedrockiumTower;

import com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestalTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

public class BedrociumTower extends Block implements EntityBlock {

    public BedrociumTower(BlockBehaviour.Properties props) {
        super(props);

    }
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        if (world.getBlockEntity(pos) instanceof BedrockiumTowerTile bedrockiumTowerTile) {
            if (trace.getDirection() == Direction.UP || trace.getDirection() == Direction.DOWN){
                return InteractionResult.PASS;
            }
            bedrockiumTowerTile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,trace.getDirection()).ifPresent(h -> {
                int slotToInteract = 0;
                Vec3 hit = trace.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
                if(hit.y >= 0.5){
                    slotToInteract = 1;
                }
                ItemStack itemInHand = player.getItemInHand(hand);
                if((itemInHand.isEmpty() || player.isShiftKeyDown()) && !h.getStackInSlot(slotToInteract).isEmpty()){
                    boolean extracted = player.addItem(h.getStackInSlot(slotToInteract));
                    if (extracted) {
                        h.insertItem(slotToInteract,ItemStack.EMPTY,false);
                    }
                }else{
                    if (!h.getStackInSlot(slotToInteract).isEmpty()){
                        boolean end = h.getStackInSlot(slotToInteract).is(itemInHand.getItem());
                        boolean extracted = player.addItem(h.getStackInSlot(slotToInteract));
                        if (extracted) {
                            h.insertItem(slotToInteract,ItemStack.EMPTY,false);
                            if (end){
                                bedrockiumTowerTile.sendUpdates();
                                state.updateNeighbourShapes(world,pos,32);
                                return;
                            }
                        }

                    }
                    ItemStack remainder = ItemHandlerHelper.insertItem(h, itemInHand, true);
                    h.insertItem(slotToInteract,itemInHand,false);
                    if (remainder.isEmpty()) {
                        player.setItemInHand(hand,ItemStack.EMPTY);
                    } else {
                        player.getItemInHand(hand).shrink(1);
                    }
                }

                bedrockiumTowerTile.sendUpdates();
                state.updateNeighbourShapes(world,pos,32);
                updatePedestal(world, bedrockiumTowerTile);
            });
        } else {
            throw new IllegalStateException("Our named container provider is missing!");
        }
        return InteractionResult.SUCCESS;
    }

    private void updatePedestal(Level world, BedrockiumTowerTile bedrockiumTowerTile) {
        for(Direction dir: Direction.values()){
            BlockPos altarPos = new BlockPos(bedrockiumTowerTile.getBlockPos());
            switch (dir){
                case NORTH -> {
                    altarPos = altarPos.north(2);
                }
                case SOUTH -> {
                    altarPos = altarPos.south(2);
                }
                case EAST -> {
                    altarPos = altarPos.east(2);
                }
                case WEST -> {
                    altarPos = altarPos.west(2);
                }
            }
            if (world.getBlockEntity(altarPos) instanceof BedrociumPedestalTile pedestalTile){
                pedestalTile.updateRecipeRender();
                break;
            }
        }
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level world, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (world.getBlockEntity(pos) instanceof BedrockiumTowerTile bedrockiumTowerTile){
            updatePedestal(world,bedrockiumTowerTile);
        }
        return super.onDestroyedByPlayer(state, world, pos, player, willHarvest, fluid);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }
        return (level1, blockPos, blockState, t) -> {
            if (t instanceof BedrockiumTowerTile tile) {
                tile.tickServer();
            }
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new BedrockiumTowerTile(pPos, pState);
    }
}
