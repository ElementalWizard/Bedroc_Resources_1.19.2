package com.alexvr.bedres.blocks.itemplatform;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class ItemPlatformTile extends BlockEntity {

    public ItemStackHandler itemHandler = createHandler();
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public ItemStack item = ItemStack.EMPTY;

    public ItemPlatformTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.ITEM_PLATFORM_TILE.get(), pWorldPosition, pBlockState);

    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("inv")) {
            itemHandler.deserializeNBT(tag.getCompound("inv"));
            item = itemHandler.getStackInSlot(0);
        }


    }

    private void saveClientData(CompoundTag tag) {
        CompoundTag infoTag = new CompoundTag();
        tag.put("Info", infoTag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        saveClientData(tag);
        tag.put("inv", itemHandler.serializeNBT());
        CompoundTag infoTag = new CompoundTag();
        tag.put("Info", infoTag);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        CompoundTag infoTag = new CompoundTag();
        tag.put("Info", infoTag);
        return tag;
    }
    public void sendUpdates() {
        level.sendBlockUpdated(getBlockPos(), this.getBlockState(), getBlockState(), 3);
        setChanged();
    }
    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {

        ItemStack item = itemHandler.getStackInSlot(0);

        CompoundTag tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(tag);

        // If any of the values was changed we request a refresh of our model data and send a block update (this will cause
        // the baked model to be recreated)
        if (!item.getItem().getRegistryName().equals(itemHandler.getStackInSlot(0).getItem().getRegistryName())) {
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


    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
                item = getStackInSlot(0);
                sendUpdates();
                setChanged();
            }

            @Override
            protected void onLoad() {
                item = getStackInSlot(0);
                super.onLoad();
            }
        };
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    public void tickServer() {

    }

}
