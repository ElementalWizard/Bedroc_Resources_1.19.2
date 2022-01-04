package com.alexvr.bedres.blocks.gravityBubble;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.client.model.data.ModelProperty;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FluxedGravityBubbleTile extends BlockEntity {

    public ItemStackHandler itemHandler = createHandler();
    private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public static final ModelProperty<Integer> FUEL_AMOUNT = new ModelProperty<>();
    public static final ModelProperty<Boolean> AREA_VISIBLE = new ModelProperty<>();
    private int fuel_amount = 0;
    private boolean area_visible = false;

    private boolean particleSpawn = false;

    private AABB triggerBox = null;
    private int maxPlayers = 2;
    private Map<String, Integer> playersFlying = new HashMap<>();
    List<Player> playersTracked = new ArrayList<>(maxPlayers);

    public FluxedGravityBubbleTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.FLUXED_GRAVITY_BUBBLE_TILE.get(), pWorldPosition, pBlockState);

    }

    public boolean isAreaVisible() {
        return area_visible;
    }

    public void setAreaVisible(boolean area_visible) {
        this.area_visible = area_visible;
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);

    }

    public int getFuelAmount(){
        return fuel_amount;
    }

    public void setFuelAmount(int amount){
        this.fuel_amount = Math.max(amount,itemHandler.getSlotLimit(0));
        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
    }

    public void addFuel(int increment){
        this.setFuelAmount(this.getFuelAmount() + increment);
    }
    public void removeFuel(int decrease){
        this.setFuelAmount(this.getFuelAmount() - decrease);
    }
    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        loadClientData(tag);
        if (tag.contains("inv")) {
            itemHandler.deserializeNBT(tag.getCompound("inv"));
            fuel_amount = itemHandler.getStackInSlot(0).getCount();
        }

    }

    private void saveClientData(CompoundTag tag) {
        CompoundTag infoTag = new CompoundTag();
        tag.put("Info", infoTag);
        infoTag.putBoolean("area",area_visible);
        infoTag.putInt("fuel",fuel_amount);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        saveClientData(tag);
        tag.put("inv", itemHandler.serializeNBT());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveClientData(tag);
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        if (tag != null) {
            loadClientData(tag);
        }
    }

    private void loadClientData(CompoundTag tag) {
        if (tag.contains("Info")) {
            CompoundTag infoTag = tag.getCompound("Info");
            if (infoTag.contains("area")){
                area_visible = infoTag.getBoolean("area");
            }
            if (infoTag.contains("fuel")){
                fuel_amount = infoTag.getInt("fuel");
            }
        }
    }

    public void sendUpdates() {
        level.sendBlockUpdated(getBlockPos(), this.getBlockState(), getBlockState(), 3);
        setChanged();
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {

        ItemStack item = itemHandler.getStackInSlot(0);
        boolean oldArea = area_visible;
        int oldFuel = fuel_amount;

        CompoundTag tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(tag);

        // If any of the values was changed we request a refresh of our model data and send a block update (this will cause
        // the baked model to be recreated)
        if (oldArea != area_visible || oldFuel != fuel_amount || !item.getItem().getRegistryName().equals(itemHandler.getStackInSlot(0).getItem().getRegistryName())) {
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
                setFuelAmount(getStackInSlot(0).getCount());
                sendUpdates();
            }

            @Override
            protected void onLoad() {
                super.onLoad();
                setFuelAmount(getStackInSlot(0).getCount());
            }

            @Override
            public boolean isItemValid(int slot, @NotNull ItemStack stack) {
                return stack.is(Registration.BEDROCK_WIRE_ITEM.get()) || stack.is(Registration.BEDROCK_COMPRESSED_WIRE_ITEM.get());
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
        if (triggerBox == null) {
            triggerBox = new AABB(getBlockPos()).inflate(7).move(0,4,0);
        }
        List<Player> entities = level.getEntitiesOfClass(Player.class, triggerBox);
        List<String> entitiesAccounted = new ArrayList<>();
        for (Player player : entities) {
            entitiesAccounted.add(player.getName().toString());
            if(player.isCreative()){
                continue;
            }

            if (!this.itemHandler.getStackInSlot(0).isEmpty()){
                if (this.playersFlying.containsKey(player.getName().toString())){
                    if (player.getAbilities().flying && this.playersFlying.get(player.getName().toString()) <= 0){
                        if (itemHandler.getStackInSlot(0).is(Registration.BEDROCK_WIRE_ITEM.get().asItem())){
                            this.playersFlying.put(player.getName().toString(),60);
                        }else if (itemHandler.getStackInSlot(0).is(Registration.BEDROCK_COMPRESSED_WIRE_ITEM.get())){
                            this.playersFlying.put(player.getName().toString(),60*9);
                        }
                        itemHandler.getStackInSlot(0).shrink(1);

                    }else if (this.playersFlying.get(player.getName().toString()) > 0){
                        this.playersFlying.put(player.getName().toString(),this.playersFlying.get(player.getName().toString())-1);
                    }
                }else{
                    player.getAbilities().mayfly = true;
                    player.fallDistance = 0;
                    player.getAbilities().flying = true;
                    player.onUpdateAbilities();
                    playersTracked.add(player);
                    if (itemHandler.getStackInSlot(0).is(Registration.BEDROCK_WIRE_ITEM.get().asItem())){
                        this.playersFlying.put(player.getName().toString(),60);
                    }else if (itemHandler.getStackInSlot(0).is(Registration.BEDROCK_COMPRESSED_WIRE_ITEM.get())){
                        this.playersFlying.put(player.getName().toString(),60*9);
                    }
                    itemHandler.getStackInSlot(0).shrink(1);
                }
            }
            if (this.itemHandler.getStackInSlot(0).isEmpty() && (player.getAbilities().mayfly || player.getAbilities().flying)){
                player.getAbilities().mayfly = false;
                player.getAbilities().flying = false;
                player.onUpdateAbilities();
                this.playersFlying.remove(player.getName().toString());
                playersTracked.remove(player);
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 2, false, false));
            }
        }
        List<Player> playersToStopTracking = new ArrayList<>();

        for (Player playerName: playersTracked) {
            if (entitiesAccounted.contains(playerName.getName().toString())){
                continue;
            }else{
                playerName.getAbilities().mayfly = false;
                playerName.getAbilities().flying = false;
                playerName.onUpdateAbilities();
                this.playersFlying.remove(playerName.getName().toString());
                playersToStopTracking.add(playerName);
                playerName.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 60, 2, false, false));

            }
        }
        for (Player player: playersToStopTracking) {
            playersTracked.remove(player);
        }
    }
    public void tickClient() {

    }

    @Nonnull
    @Override
    public IModelData getModelData() {
        return new ModelDataMap.Builder()
                .withInitial(AREA_VISIBLE, area_visible)
                .withInitial(FUEL_AMOUNT, fuel_amount)
                .build();
    }
}
