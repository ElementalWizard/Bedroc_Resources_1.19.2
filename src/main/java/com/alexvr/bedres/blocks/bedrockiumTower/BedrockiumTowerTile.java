package com.alexvr.bedres.blocks.bedrockiumTower;

import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.DirectionalItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class BedrockiumTowerTile extends BlockEntity {

    public DirectionalItemStackHandler northItemHandler = createHandler(Direction.NORTH);
    private LazyOptional<IItemHandler> northHandler = LazyOptional.of(() -> northItemHandler);

    public DirectionalItemStackHandler southItemHandler = createHandler(Direction.SOUTH);
    private LazyOptional<IItemHandler> southHandler = LazyOptional.of(() -> southItemHandler);

    public DirectionalItemStackHandler eastItemHandler = createHandler(Direction.EAST);
    private LazyOptional<IItemHandler> eastHandler = LazyOptional.of(() -> eastItemHandler);

    public DirectionalItemStackHandler westItemHandler = createHandler(Direction.WEST);
    private LazyOptional<IItemHandler> westHandler = LazyOptional.of(() ->westItemHandler);

    private final LazyOptional<IItemHandler> combinedItemHandler = LazyOptional.of(this::createCombinedItemHandler);

    public BedrockiumTowerTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.BASE_SPIKE_TILE.get(), pWorldPosition, pBlockState);

    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        northHandler.invalidate();
        southHandler.invalidate();
        westHandler.invalidate();
        eastHandler.invalidate();
    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("ninv")) {
            northItemHandler.deserializeNBT(tag.getCompound("ninv"));
        }
        if (tag.contains("sinv")) {
            southItemHandler.deserializeNBT(tag.getCompound("sinv"));
        }
        if (tag.contains("einv")) {
            eastItemHandler.deserializeNBT(tag.getCompound("einv"));
        }
        if (tag.contains("winv")) {
            westItemHandler.deserializeNBT(tag.getCompound("winv"));
        }


    }

    private void saveClientData(CompoundTag tag) {
        CompoundTag infoTag = new CompoundTag();
        tag.put("Info", infoTag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        saveClientData(tag);
        tag.put("ninv", northItemHandler.serializeNBT());
        tag.put("sinv", southItemHandler.serializeNBT());
        tag.put("einv", eastItemHandler.serializeNBT());
        tag.put("winv", westItemHandler.serializeNBT());
        CompoundTag infoTag = new CompoundTag();
        tag.put("Info", infoTag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        CompoundTag infoTag = new CompoundTag();
        tag.put("Info", infoTag);
        this.saveAdditional(tag);
        return tag;
    }
    public void sendUpdates() {
        level.sendBlockUpdated(getBlockPos(), this.getBlockState(), getBlockState(), 3);
        setChanged();
    }
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {

        ItemStack[] northItems = {ItemStack.EMPTY,ItemStack.EMPTY};
        northItems[0] = northItemHandler.getStackInSlot(0);
        northItems[1] = northItemHandler.getStackInSlot(1);
        ItemStack[] southItems = {ItemStack.EMPTY,ItemStack.EMPTY};
        southItems[0] = southItemHandler.getStackInSlot(0);
        southItems[1] = southItemHandler.getStackInSlot(1);
        ItemStack[] eastItems = {ItemStack.EMPTY,ItemStack.EMPTY};
        eastItems[0] = eastItemHandler.getStackInSlot(0);
        eastItems[1] = eastItemHandler.getStackInSlot(1);
        ItemStack[] westItems = {ItemStack.EMPTY,ItemStack.EMPTY};
        westItems[0] = westItemHandler.getStackInSlot(0);
        westItems[1] = westItemHandler.getStackInSlot(1);

        CompoundTag tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(tag);

        // If any of the values was changed we request a refresh of our model data and send a block update (this will cause
        // the baked model to be recreated)
        if (!northItems[0].getItem().getRegistryName().equals(northItemHandler.getStackInSlot(0).getItem().getRegistryName())||!northItems[1].getItem().getRegistryName().equals(northItemHandler.getStackInSlot(1).getItem().getRegistryName())||
                !southItems[0].getItem().getRegistryName().equals(southItemHandler.getStackInSlot(0).getItem().getRegistryName())||!southItems[1].getItem().getRegistryName().equals(southItemHandler.getStackInSlot(1).getItem().getRegistryName())||
                !eastItems[0].getItem().getRegistryName().equals(eastItemHandler.getStackInSlot(0).getItem().getRegistryName())||!eastItems[1].getItem().getRegistryName().equals(eastItemHandler.getStackInSlot(1).getItem().getRegistryName())||
                !westItems[0].getItem().getRegistryName().equals(westItemHandler.getStackInSlot(0).getItem().getRegistryName())||!westItems[1].getItem().getRegistryName().equals(westItemHandler.getStackInSlot(1).getItem().getRegistryName())) {
            ModelDataManager.requestModelDataRefresh(this);
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);

        }
    }

    // The getUpdatePacket()/onDataPacket() pair is used when a block update happens on the client
    // (a blockstate change or an explicit notificiation of a block update from the server). It's
    // easiest to implement them based on getUpdateTag()/handleUpdateTag()

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }


    private DirectionalItemStackHandler createHandler(Direction direction) {
        return new DirectionalItemStackHandler(2, direction) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                sendUpdates();
            }

            @Override
            public int getSlotLimit(int slot) {
                return 1;
            }

        };
    }

    @Nonnull
    private IItemHandler createCombinedItemHandler() {
        return new CombinedInvWrapper(northItemHandler, southItemHandler,eastItemHandler,westItemHandler) {

            @NotNull
            @Override
            public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
                return stack;
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == null){
                return combinedItemHandler.cast();
            }
            switch (side){
                case NORTH -> {
                    return northHandler.cast();
                }
                case SOUTH -> {
                    return southHandler.cast();
                }
                case WEST -> {
                    return westHandler.cast();
                }
                case EAST -> {
                    return eastHandler.cast();
                }
                default -> {
                    return combinedItemHandler.cast();
                }
            }
        }
        return super.getCapability(cap, side);
    }

    public void tickServer() {

    }

}
