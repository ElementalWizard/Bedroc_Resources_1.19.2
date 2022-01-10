package com.alexvr.bedres.blocks.itemPlatform;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.blocks.enderianRitualPedestal.EnderianRitualPedestalTile;
import com.alexvr.bedres.setup.Registration;
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
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


public class ItemPlatformTile extends BlockEntity {

    public ItemStackHandler itemHandler = createHandler();
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler
    );

    public ItemPlatformTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.ITEM_PLATFORM_TILE.get(), pWorldPosition, pBlockState);

    }

    public List<ItemStack> getItemsForRecipe(BlockPos position, int xRadius, int zRadius){
        List<ItemStack> items = new ArrayList<>();
        items.clear();
        for (int x = position.getX() - xRadius; x <= position.getX() + xRadius; x++){
            for (int z = position.getZ() - zRadius; z <= position.getZ() + zRadius; z++){
                BlockPos newPosition = new BlockPos(x,position.getY(),z);
                BedrockResources.LOGGER.info("Checking at: " + newPosition.toString() + " got " + level.getBlockState(newPosition).getBlock().getName());
                if (level.getBlockEntity(newPosition) instanceof EnderianRitualPedestalTile tower){
                    tower.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).ifPresent(h -> {
                        boolean grew = false;
                        ItemStack stack = h.getStackInSlot(0).copy();
                        for (ItemStack stack2: items) {
                            if (stack.is(stack2.getItem())){
                                stack2.grow(1);
                                grew = true;
                                break;
                            }
                        }
                        if (!grew){
                            items.add(stack) ;
                        }
                    });
                }
            }
        }
        return items;
    }

    public List<String> getPatterRecipe(BlockPos position, int xRadius, int zRadius){
        List<String> patter = new ArrayList<>((zRadius*2)+1);
        for (int z = position.getZ() - zRadius; z <= position.getZ() + zRadius; z++){
            StringBuilder row = new StringBuilder();
            for (int x = position.getX() - xRadius; x <= position.getX() + xRadius; x++){
                BlockPos newPosition = new BlockPos(x,position.getY(),z);
                if (level.getBlockEntity(newPosition) instanceof EnderianRitualPedestalTile){
                    row.append('i');
                }else if (level.getBlockState(newPosition).is(Registration.BEDROCK_WIRE_BLOCK.get())){
                    row.append('w');
                }else{
                    row.append(' ');
                }
            }
            patter.add(row.toString());
        }
        return patter;
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
        this.saveAdditional(tag);
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
        if (!item.is(itemHandler.getStackInSlot(0).getItem())) {
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
                sendUpdates();
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
