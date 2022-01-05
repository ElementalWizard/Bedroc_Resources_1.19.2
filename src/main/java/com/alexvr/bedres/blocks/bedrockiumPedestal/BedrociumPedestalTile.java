package com.alexvr.bedres.blocks.bedrockiumPedestal;

import com.alexvr.bedres.blocks.bedrockiumTower.BedrockiumTowerTile;
import com.alexvr.bedres.recipes.ModRecipeRegistry;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipes;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.DirectionalItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.ItemEntity;
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
import java.util.concurrent.atomic.AtomicBoolean;

import static com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestal.CRAFTING;
import static com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestal.TRIGGERED;


public class BedrociumPedestalTile extends BlockEntity {

    public ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public List<BlockPos> extensions = new ArrayList<>();
    public List<BedrockiumTowerTile> towers = new ArrayList<>();

    public ItemStackHandler itemCraftingHandler = createCraftingHandler();
    private final LazyOptional<IItemHandler> craftingHandler = LazyOptional.of(() -> itemCraftingHandler);

    private int craftingItemAmount = 0;
    private int craftingItemConsumed = 0;
    public int MaxItemConsumptionTicks = 100;
    public int itemConsumptionTicks = MaxItemConsumptionTicks;
    public ItemStack outPut = ItemStack.EMPTY;
    public BedrockiumTowerTile target;

    public BedrociumPedestalTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.PEDESTAL_TILE.get(), pWorldPosition, pBlockState);

    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
        craftingHandler.invalidate();
    }
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("inv")) {
            itemHandler.deserializeNBT(tag.getCompound("inv"));
        }
        if (tag.contains("cinv")) {
            itemCraftingHandler.deserializeNBT(tag.getCompound("cinv"));
        }
        if (tag.contains("camount")) {
            craftingItemAmount = tag.getInt("camount");
        }
        if (tag.contains("cprogress")) {
            craftingItemConsumed = tag.getInt("cprogress");
        }
        if (tag.contains("out")) {
            outPut.deserializeNBT(tag.getCompound("out"));
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
        tag.put("cinv", itemCraftingHandler.serializeNBT());
        tag.put("out", outPut.serializeNBT());
        tag.putInt("camount", craftingItemAmount);
        tag.putInt("cprogress", craftingItemConsumed);
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
        ItemStack itemC = itemCraftingHandler.getStackInSlot(0);
        ItemStack out = outPut;
        int amount = craftingItemAmount;
        int consumed = craftingItemConsumed;
        CompoundTag tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(tag);

        // If any of the values was changed we request a refresh of our model data and send a block update (this will cause
        // the baked model to be recreated)
        if (!out.is(outPut.getItem()) || !item.is(itemHandler.getStackInSlot(0).getItem())||
                !itemC.is(itemCraftingHandler.getStackInSlot(0).getItem()) ||
                amount!= craftingItemAmount || consumed != craftingItemConsumed) {
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
                sendUpdates();
            }

        };
    }

    private ItemStackHandler createCraftingHandler() {
        return new ItemStackHandler(18) {

            @Override
            protected void onContentsChanged(int slot) {
                // To make sure the TE persists when the chunk is saved later we need to
                // mark it dirty every time the item handler changes
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
    public <T> LazyOptional<T> getCraftingCapability(@Nonnull Capability<T> cap) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return craftingHandler.cast();
        }
        return super.getCapability(cap);
    }

    private void spawnItemParticles(BlockPos towerPos, ItemStack stack) {
        if (this.getLevel() == null || this.getLevel().isClientSide() || stack.isEmpty())
            return;

        var level = (ServerLevel) this.getLevel();
        var pos = this.getBlockPos();

        double x = towerPos.getX() + (level.getRandom().nextDouble() * 0.2D) + 0.4D;
        double y = towerPos.getY() + (level.getRandom().nextDouble() * 0.2D) + 1.8D;
        double z = towerPos.getZ() + (level.getRandom().nextDouble() * 0.2D) + 0.4D;

        double velX = pos.getX() - towerPos.getX();
        double velY = -0.55D;
        double velZ = pos.getZ() - towerPos.getZ();

        level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), x, y, z, 0, velX, velY, velZ, 0.10D);
    }

    private void spawnItemParticlesWithVelocity(BlockPos blockPos, ItemStack stack, double pXVel, double pYVel, double pZVel,double pXOffset, double pYOffset, double pZOffset) {
        if (this.getLevel() == null || this.getLevel().isClientSide() || stack.isEmpty())
            return;

        var level = (ServerLevel) this.getLevel();

        double x = blockPos.getX() + (level.getRandom().nextDouble() * 0.2D) + pXOffset;
        double y = blockPos.getY() + (level.getRandom().nextDouble() * 0.2D) + pYOffset;
        double z = blockPos.getZ() + (level.getRandom().nextDouble() * 0.2D) + pZOffset;

        level.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, stack), x, y, z, 0, pXVel, pYVel, pZVel, 0.10D);
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
        craftingItemConsumed = 0;
        itemConsumptionTicks = MaxItemConsumptionTicks;
        if (recipe != null){
            recipe.getIngredientList().forEach(ingridient -> craftingItemAmount+= ingridient.getCount());
            target = null;
            outPut = recipe.getResultItem();
            updateTriggered(true);
        }else{
            updateTriggered(false);
            updateCrafting(false);
        }
    }
    public void updateTriggered(boolean state){
       level.setBlock(worldPosition, getBlockState().setValue(TRIGGERED, Boolean.valueOf(state)), 4);
        getBlockState().updateNeighbourShapes(level,worldPosition,32);
        sendUpdates();
    }

    public void updateCrafting(boolean state){
        level.setBlock(worldPosition, getBlockState().setValue(CRAFTING, Boolean.valueOf(state)), 4);
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
                if (level.getBlockEntity(pos) instanceof BedrockiumTowerTile bedrockiumTowerTile){
                    extensions.add(pos);
                    towers.add(bedrockiumTowerTile);
                }
            }
            sendUpdates();
        }
        if (getBlockState().getValue(TRIGGERED) && !getBlockState().getValue(CRAFTING)){
            updateCrafting(true);
        }
        if (getBlockState().getValue(CRAFTING)){
            if (target != null){
                AtomicBoolean end = new AtomicBoolean(false);
                for (Direction dir: Direction.values()){
                    switch (dir){
                        case UP:
                        case DOWN:
                            continue;
                        default:
                            level.getBlockEntity(target.getBlockPos()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,dir).ifPresent(h -> {
                                for (int i = 0; i < h.getSlots();i++){
                                    if (!h.getStackInSlot(i).isEmpty()){
                                        end.set(true);
                                        itemConsumptionTicks--;
                                        spawnItemParticles(target.getBlockPos(), h.getStackInSlot(i));
                                        if (itemConsumptionTicks <= 0){
                                            itemConsumptionTicks = MaxItemConsumptionTicks;
                                            craftingItemConsumed++;
                                            h.extractItem(i,1,false);
                                            sendUpdates();
                                            ((BedrockiumTowerTile)level.getBlockEntity(target.getBlockPos())).sendUpdates();
                                        }
                                        return;
                                    }
                                }
                            });
                            if (end.get()){
                                return;
                            }
                    }
                }
                if (end.get()){
                    return;
                }
                if (craftingItemConsumed+1 == craftingItemAmount){
                    itemConsumptionTicks--;
                    spawnItemParticlesWithVelocity(getBlockPos(), itemHandler.getStackInSlot(0),1,3.35f,0,-0.2f,0.4f,0.4f);
                    spawnItemParticlesWithVelocity(getBlockPos(), itemHandler.getStackInSlot(0),-1,3.35f,0,1.2f,0.4f,0.4f);
                    spawnItemParticlesWithVelocity(getBlockPos(), itemHandler.getStackInSlot(0),0,3.35f,1,.4f,0.4f,-0.2f);
                    spawnItemParticlesWithVelocity(getBlockPos(), itemHandler.getStackInSlot(0),0,3.35f,-1,.4f,0.4f,1.0f);
                    if (itemConsumptionTicks == 0){
                        itemConsumptionTicks = MaxItemConsumptionTicks;
                        craftingItemConsumed = 0;
                        craftingItemAmount = 0;
                        itemHandler.extractItem(0,itemHandler.getStackInSlot(0).getCount(),false);
                        ItemEntity itementity = new ItemEntity(level, worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ(), outPut);
                        itementity.setDeltaMovement(0, level.random.nextGaussian() * (double)0.0075F * (double)4 + (double)0.2F, 0);
                        level.addFreshEntity(itementity);
                        outPut = ItemStack.EMPTY;
                        updateTriggered(false);
                        updateCrafting(false);
                    }
                }else{
                    target = null;
                }
            }else{
                for (BedrockiumTowerTile tower: towers){
                    level.getBlockEntity(tower.getBlockPos()).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                        for (int i = 0; i < h.getSlots();i++){
                            if (!h.getStackInSlot(i).isEmpty()){
                                target = tower;
                                itemConsumptionTicks = MaxItemConsumptionTicks;
                                return;
                            }
                        }
                    });
                }
            }

        }
    }
}
