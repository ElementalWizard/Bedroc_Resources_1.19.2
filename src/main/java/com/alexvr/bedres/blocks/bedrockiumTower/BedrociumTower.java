package com.alexvr.bedres.blocks.bedrockiumTower;

import com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestalTile;
import com.alexvr.bedres.recipes.ModRecipeRegistry;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipes;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestal.TRIGGERED;

public class BedrociumTower extends Block implements EntityBlock {

    public BedrociumTower(BlockBehaviour.Properties props) {
        super(props);

    }
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        Direction directionOfBlockClicked = trace.getDirection();
        int slotToInteract = 0;
        Vec3 hit = trace.getLocation().subtract(pos.getX(), pos.getY(), pos.getZ());
        if(hit.y >= 0.5){
            slotToInteract = 1;
        }
        if (world.isClientSide) {
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof BedrockiumTowerTile) {
                ItemStack itemInHand = player.getItemInHand(hand);
                if(itemInHand.isEmpty() || player.isShiftKeyDown()){
                    switch (directionOfBlockClicked){
                        case NORTH -> ((BedrockiumTowerTile) tileEntity).northItems[slotToInteract] = ItemStack.EMPTY;
                        case SOUTH -> ((BedrockiumTowerTile) tileEntity).southItems[slotToInteract] =ItemStack.EMPTY;
                        case WEST -> ((BedrockiumTowerTile) tileEntity).westItems[slotToInteract] = ItemStack.EMPTY;
                        case EAST -> ((BedrockiumTowerTile) tileEntity).eastItems[slotToInteract] = ItemStack.EMPTY;
                    }
                }else{

                    switch (directionOfBlockClicked){
                        case NORTH -> {
                            boolean end = ((BedrockiumTowerTile) tileEntity).northItems[slotToInteract].is(itemInHand.getItem());
                            if(!end) {
                                ((BedrockiumTowerTile) tileEntity).northItems[slotToInteract] = itemInHand;
                            }else{
                                ((BedrockiumTowerTile) tileEntity).northItems[slotToInteract] = ItemStack.EMPTY;
                            }
                        }
                        case SOUTH -> {
                            boolean end = ((BedrockiumTowerTile) tileEntity).southItems[slotToInteract].is(itemInHand.getItem());
                            if(!end) {
                                ((BedrockiumTowerTile) tileEntity).southItems[slotToInteract] = itemInHand;
                            }else{
                                ((BedrockiumTowerTile) tileEntity).southItems[slotToInteract] = ItemStack.EMPTY;
                            }
                        }
                        case WEST -> {
                            boolean end = ((BedrockiumTowerTile) tileEntity).westItems[slotToInteract].is(itemInHand.getItem());
                            if(!end) {
                                ((BedrockiumTowerTile) tileEntity).westItems[slotToInteract] = itemInHand;
                            }else{
                                ((BedrockiumTowerTile) tileEntity).westItems[slotToInteract] = ItemStack.EMPTY;
                            }
                        }
                        case EAST -> {
                            boolean end = ((BedrockiumTowerTile) tileEntity).northItems[slotToInteract].is(itemInHand.getItem());
                            if(!end) {
                                ((BedrockiumTowerTile) tileEntity).eastItems[slotToInteract] = itemInHand;
                            }else{
                                ((BedrockiumTowerTile) tileEntity).eastItems[slotToInteract] = ItemStack.EMPTY;
                            }
                        }
                    }
                }
                tileEntity.setChanged();
                ((BedrockiumTowerTile) tileEntity).sendUpdates();
                state.updateNeighbourShapes(world,pos,32);
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }else{
            BlockEntity tileEntity = world.getBlockEntity(pos);
            if (tileEntity instanceof BedrockiumTowerTile) {
                int finalSlotToInteract = slotToInteract;
                world.getBlockEntity(pos).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,directionOfBlockClicked).ifPresent(h -> {
                    ItemStack itemInHand = player.getItemInHand(hand);
                    if((itemInHand.isEmpty() || player.isShiftKeyDown()) && !h.getStackInSlot(finalSlotToInteract).isEmpty()){
                        boolean extracted = player.addItem(h.getStackInSlot(finalSlotToInteract));
                        if (extracted) {
                            h.insertItem(finalSlotToInteract,ItemStack.EMPTY,false);
                        }
                    }else{
                        if (!h.getStackInSlot(finalSlotToInteract).isEmpty()){
                            boolean end = h.getStackInSlot(finalSlotToInteract).is(itemInHand.getItem());
                            boolean extracted = player.addItem(h.getStackInSlot(finalSlotToInteract));
                            if (extracted) {
                                h.insertItem(finalSlotToInteract,ItemStack.EMPTY,false);
                                if (end){
                                    tileEntity.setChanged();
                                    ((BedrockiumTowerTile) tileEntity).sendUpdates();
                                    state.updateNeighbourShapes(world,pos,32);
                                    return;
                                }
                            }

                        }
                        ItemStack remainder = ItemHandlerHelper.insertItem(h, itemInHand, true);
                        h.insertItem(finalSlotToInteract,itemInHand,false);
                        if (remainder.isEmpty()) {
                            player.setItemInHand(hand,ItemStack.EMPTY);
                        } else {
                            player.getItemInHand(hand).shrink(1);
                        }
                    }

                    tileEntity.setChanged();
                    ((BedrockiumTowerTile) tileEntity).sendUpdates();
                    state.updateNeighbourShapes(world,pos,32);
                    for(Direction dir: Direction.values()){
                        BlockPos altarPos = new BlockPos(tileEntity.getBlockPos());
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
                            List<ItemStack> ing = pedestalTile.getItemsForRecipe();
                            RitualAltarRecipes recipe = ModRecipeRegistry.findRecipeFromIngrent(ing);
                            if (recipe != null){
                                world.setBlock(altarPos, pedestalTile.getBlockState().setValue(TRIGGERED, Boolean.valueOf(true)), 4);

                            }else{
                                world.setBlock(altarPos, pedestalTile.getBlockState().setValue(TRIGGERED, Boolean.valueOf(false)), 4);

                            }
                            pedestalTile.getBlockState().updateNeighbourShapes(world,altarPos,32);
                            pedestalTile.sendUpdates();
                            break;
                        }
                    }
                });
            } else {
                throw new IllegalStateException("Our named container provider is missing!");
            }
        }
        return InteractionResult.SUCCESS;
    }

    private double getYFromHit(Direction facing, Vec3 hit) {
        return switch (facing) {
            case UP -> 1 - hit.x;
            case DOWN -> 1 - hit.x;
            case NORTH -> 1 - hit.x;
            case SOUTH -> hit.x;
            case WEST -> hit.z;
            case EAST -> 1 - hit.z;
        };
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
