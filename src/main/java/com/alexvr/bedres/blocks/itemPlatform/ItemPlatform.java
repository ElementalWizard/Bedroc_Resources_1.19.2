package com.alexvr.bedres.blocks.itemPlatform;

import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.level.block.state.properties.AttachFace.WALL;

public class ItemPlatform extends FaceAttachedHorizontalDirectionalBlock implements EntityBlock {

    private static final VoxelShape ITEM_PLATFORM_EAST_AABB = Block.box(15.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);
    private static final VoxelShape ITEM_PLATFORM_WEST_AABB = Block.box(0.0D, 6.0D, 6.0D, 1.0D, 10.0D, 10.0D);
    private static final VoxelShape ITEM_PLATFORM_SOUTH_AABB = Block.box(6.0D, 6.0D, 15.0D, 10.0D, 10.0D, 16.0D);
    private static final VoxelShape ITEM_PLATFORM_NORTH_AABB = Block.box(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 1.0D);
    private static final VoxelShape ITEM_PLATFORM_UP_AABB = Block.box(6.0D, 15.0D, 6.0D, 10, 16.0D, 10.0D);
    private static final VoxelShape ITEM_PLATFORM_DOWN_AABB = Block.box(6.0D, 0.0D, 6.0D, 10, 1.0D, 10.0D);

    public ItemPlatform(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, WALL));

    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        if (world.getBlockEntity(pos) instanceof ItemPlatformTile itemPlatformTile) {
            itemPlatformTile.getCapability( ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
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
                itemPlatformTile.sendUpdates();
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
            if (t instanceof ItemPlatformTile tile) {
                tile.tickServer();
            }
        };
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ItemPlatformTile(pPos, pState);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter reader, List<Component> list, TooltipFlag flags) {
        list.add(Component.translatable(BedrockReferences.ITEM_PLATFORM_TOOLTIP));
    }

    @Override
    public boolean propagatesSkylightDown(BlockState p_49928_, BlockGetter p_49929_, BlockPos p_49930_) {
        return true;
    }

    public VoxelShape getShape(BlockState p_54665_, BlockGetter p_54666_, BlockPos p_54667_, CollisionContext p_54668_) {
        return switch (p_54665_.getValue(FACE)) {
            case FLOOR -> ITEM_PLATFORM_DOWN_AABB;
            case WALL -> switch (p_54665_.getValue(FACING)) {
                case EAST -> ITEM_PLATFORM_WEST_AABB;
                case WEST -> ITEM_PLATFORM_EAST_AABB;
                case SOUTH -> ITEM_PLATFORM_NORTH_AABB;
                default -> ITEM_PLATFORM_SOUTH_AABB;
            }; case CEILING -> ITEM_PLATFORM_UP_AABB;
        };
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_54663_) {
        p_54663_.add(FACE, FACING);
    }

}
