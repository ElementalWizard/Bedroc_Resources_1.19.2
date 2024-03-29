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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alexvr.bedres.blocks.gravityBubble.FluxedGravityBubble.ENABLED;
import static com.alexvr.bedres.setup.ModConfig.GRAVITY_BUBBLE_RADIUS;


public class FluxedGravityBubbleTile extends BlockEntity {

    public ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    private AABB triggerBox = null;
    private final int maxPlayers = 2;
    private Map<String, Integer> playersFlying = new HashMap<>();
    List<Player> playersTracked = new ArrayList<>(maxPlayers);

    public FluxedGravityBubbleTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.FLUXED_GRAVITY_BUBBLE_TILE.get(), pWorldPosition, pBlockState);

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

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("inv", itemHandler.serializeNBT());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        this.saveAdditional(tag);
        return tag;
    }


    public void sendUpdates() {
        level.sendBlockUpdated(getBlockPos(), this.getBlockState(), getBlockState(), 3);
        setChanged();
    }

    public void updateEnabled(boolean enabled){
        level.setBlock(worldPosition, getBlockState().setValue(ENABLED, Boolean.valueOf(enabled)), 4);
        getBlockState().updateNeighbourShapes(level,worldPosition,32);
        sendUpdates();
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
            requestModelDataUpdate();
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
                sendUpdates();
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
        if (cap ==  ForgeCapabilities.ITEM_HANDLER) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }
    public void tickServer() {
        if (triggerBox == null) {
            triggerBox = new AABB(getBlockPos()).inflate(GRAVITY_BUBBLE_RADIUS.get()).move(0,GRAVITY_BUBBLE_RADIUS.get()%2==0?GRAVITY_BUBBLE_RADIUS.get()/2f:(GRAVITY_BUBBLE_RADIUS.get()+1)/2f,0);
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
            }else{
                updateEnabled(false);
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

}
