package com.alexvr.bedres.blocks.bedrockiumPedestal;

import com.alexvr.bedres.blocks.bedrockiumTower.BedrockiumTowerTile;
import com.alexvr.bedres.recipes.ModRecipeRegistry;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipes;
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
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestal.TRIGGERED;


public class BedrociumPedestalTile extends BlockEntity {

    public ItemStackHandler itemHandler = createHandler();
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public ItemStack item = ItemStack.EMPTY;

    public List<BlockPos> extensions = new ArrayList<>();

    public BedrociumPedestalTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.PEDESTAL_TILE.get(), pWorldPosition, pBlockState);

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
        if (!item.getItem().getRegistryName().equals(itemHandler.getStackInSlot(0).getItem().getRegistryName())) {
            this.item = itemHandler.getStackInSlot(0);
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
            }

            @Override
            protected void onLoad() {
                super.onLoad();
                item = getStackInSlot(0);

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


    public List<ItemStack> getItemsForRecipe(){
        List<ItemStack> items = new ArrayList<>();
        if (!extensions.isEmpty()){
            items.add(itemHandler.getStackInSlot(0).copy());
            for (BlockPos pos :extensions) {
                List<ItemStack> temp = new ArrayList<>();
                if (level.getBlockEntity(pos) instanceof BedrockiumTowerTile tower){
                    temp.addAll(getItemsFromCap(tower.westItemHandler));
                    temp.addAll(getItemsFromCap(tower.eastItemHandler));
                    temp.addAll(getItemsFromCap(tower.northItemHandler));
                    temp.addAll(getItemsFromCap(tower.southItemHandler));
                }
                for (ItemStack stack :temp) {
                    boolean grew = false;
                    for (ItemStack stack2: items) {
                        if (stack.is(stack2.getItem())){
                            stack2.grow(stack.getCount());
                            grew = true;
                            break;
                        }
                    }
                    if (!grew){
                       items.add(stack) ;
                    }
                }
            }
        }

        return items;
    }

    public List<ItemStack> getItemsFromCap(DirectionalItemStackHandler itemHandler){
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i<itemHandler.getSlots();i++ ){
            ItemStack stack = itemHandler.getStackInSlot(i).copy();
            if (stack.isEmpty()){
                continue;
            }
            boolean grew = false;
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

        }
        return items;
    }

    public void updateRecipeRender(){
        List<ItemStack> ing = getItemsForRecipe();
        RitualAltarRecipes recipe = ModRecipeRegistry.findRecipeFromIngrent(ing);
        if (recipe != null){
            updateTriggered(true);
        }else{
            updateTriggered(false);
        }
    }
    public void updateTriggered(boolean state){
       level.setBlock(worldPosition, getBlockState().setValue(TRIGGERED, Boolean.valueOf(state)), 4);
        getBlockState().updateNeighbourShapes(level,worldPosition,32);
        sendUpdates();
    }

    public void tickServer() {
        if (extensions.isEmpty()){
            for (Direction dir :Direction.values()) {
                BlockPos pos = getBlockPos();
                switch (dir){
                    case NORTH -> pos = pos.north(2);
                    case SOUTH -> pos = pos.south(2);
                    case EAST -> pos = pos.east(2);
                    case WEST -> pos = pos.west(2);
                }
                if (level.getBlockEntity(pos) instanceof BedrockiumTowerTile){
                    extensions.add(pos);
                }
            }
            sendUpdates();
        }
    }

}
