package com.alexvr.bedres.blocks.bedrockiumPedestal;

import com.alexvr.bedres.recipes.ModRecipeRegistry;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipes;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
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
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BedrociumPedestal extends Block implements EntityBlock {

    private static final VoxelShape Base = Block.box(0, 0.0D, 0, 16, .3, 16);
    public static final BooleanProperty TRIGGERED = BlockStateProperties.TRIGGERED;

    public BedrociumPedestal(BlockBehaviour.Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(TRIGGERED, Boolean.valueOf(false)));

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
                    world.setBlock(pos, state.setValue(TRIGGERED, Boolean.valueOf(false)), 4);
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

    @Override
    public void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pBlock, BlockPos pFromPos, boolean pIsMoving) {
        if (pLevel.isClientSide()) return;
        if (pLevel.getBlockEntity(pPos) instanceof  BedrociumPedestalTile pedestalTile){
            List<ItemStack> ing = pedestalTile.getItemsForRecipe();
            RitualAltarRecipes recipe = ModRecipeRegistry.findRecipeFromIngrent(ing);
            if (recipe != null){
                pLevel.addParticle(new DustParticleOptions(new Vector3f(0,0,0),3),pPos.getX(),pPos.getY(),pPos.getZ(),0,2,0);
                pLevel.setBlock(pPos, pState.setValue(TRIGGERED, Boolean.valueOf(true)), 4);

            }else{
                pLevel.setBlock(pPos, pState.setValue(TRIGGERED, Boolean.valueOf(false)), 4);

            }
            pState.updateNeighbourShapes(pLevel,pPos,32);
            pedestalTile.sendUpdates();
        }
        super.neighborChanged(pState, pLevel, pPos, pBlock, pFromPos, pIsMoving);
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

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TRIGGERED);
    }
}
