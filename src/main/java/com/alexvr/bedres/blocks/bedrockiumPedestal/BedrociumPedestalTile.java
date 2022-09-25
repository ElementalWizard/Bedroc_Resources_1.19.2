package com.alexvr.bedres.blocks.bedrockiumPedestal;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.blocks.bedrockiumTower.BedrockiumTowerTile;
import com.alexvr.bedres.recipes.ritualAltar.RitualAltarRecipes;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.DirectionalItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestal.CRAFTING;
import static com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestal.VALIDRECIPE;
import static com.alexvr.bedres.setup.ModConfig.RITUAL_TICKS_PER_ITEM;
import static com.alexvr.bedres.utils.ParticleHelper.spawnItemParticles;
import static com.alexvr.bedres.utils.ParticleHelper.spawnItemParticlesWithVelocity;


public class BedrociumPedestalTile extends BlockEntity {

    public ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    public List<BlockPos> extensions = new ArrayList<>();
    public List<BedrockiumTowerTile> towers = new ArrayList<>();

    public int craftingItemAmount = 0;
    public int craftingItemConsumed = 0;
    public float craftingProgress = 0f;
    public float MaxItemConsumptionTicks = RITUAL_TICKS_PER_ITEM.get();
    public float itemConsumptionTicks = 0f;
    public ItemStack outPut = ItemStack.EMPTY;
    public BedrockiumTowerTile target;
    public RitualAltarRecipes craftingRecipe;

    public BedrociumPedestalTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.PEDESTAL_TILE.get(), pWorldPosition, pBlockState);

    }

    public float getCraftingProgress(){
        float done = (craftingItemConsumed) + (itemConsumptionTicks/MaxItemConsumptionTicks);
        float toGo = (craftingItemAmount);
        float ratio = done/toGo;
        if (craftingProgress < ratio){
            BedrockResources.LOGGER.info(craftingProgress + ":::" +done + ":::" +toGo + ":::"+craftingItemConsumed + ":::" +craftingItemAmount + ":::" +itemConsumptionTicks);
            craftingProgress = ratio;
            sendUpdates();
        }
        return craftingProgress;
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

        if (tag.contains("camount")) {
            craftingItemAmount = tag.getInt("camount");
        }
        if (tag.contains("cconsumed")) {
            craftingItemConsumed = tag.getInt("cconsumed");
        }
        if (tag.contains("cprogress")) {
            craftingProgress = tag.getFloat("cprogress");
        }
        if (tag.contains("out")) {
            outPut.deserializeNBT(tag.getCompound("out"));
            //craftingRecipe = RitualAltarRecipes.findRecipeFromOutput(outPut);

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
        tag.put("out", outPut.serializeNBT());
        tag.putInt("camount", craftingItemAmount);
        tag.putInt("cconsumed", craftingItemConsumed);
        tag.putFloat("cprogress", craftingProgress);
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
        ItemStack out = outPut;
        int amount = craftingItemAmount;
        int consumed = craftingItemConsumed;
        float progress = craftingProgress;
        CompoundTag tag = pkt.getTag();
        // This will call loadClientData()
        handleUpdateTag(tag);

        // If any of the values was changed we request a refresh of our model data and send a block update (this will cause
        // the baked model to be recreated)
        if (!out.is(outPut.getItem()) || !item.is(itemHandler.getStackInSlot(0).getItem())||
                amount!= craftingItemAmount || consumed != craftingItemConsumed || progress != craftingProgress) {
            //craftingRecipe = RitualAltarRecipes.findRecipeFromOutput(outPut);
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
            public int getSlotLimit(int slot) {
                return 1;
            }

        };
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
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

    public RitualAltarRecipes updateRecipeRender(){
        List<ItemStack> ing = getItemsForRecipe();
        RitualAltarRecipes recipe = RitualAltarRecipes.findRecipeFromIngrent(ing);
        if (recipe != null){
            recipe.getIngredientList().forEach(ingridient -> craftingItemAmount+= ingridient.getCount());
            target = null;
            outPut = recipe.getResultItem();
            craftingRecipe = recipe;
            updateValidRecipe(true);
        }else{
            updateValidRecipe(false);
            updateCrafting(false);
        }

        return recipe;
    }
    public void updateValidRecipe(boolean state){
       level.setBlock(worldPosition, getBlockState().setValue(VALIDRECIPE, Boolean.valueOf(state)), 4);
        getBlockState().updateNeighbourShapes(level,worldPosition,32);
        sendUpdates();
    }

    public void updateCrafting(boolean state){
        craftingItemConsumed = 0;
        itemConsumptionTicks = 0;
        craftingProgress = 0f;
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

        if (getBlockState().getValue(CRAFTING)){
            if (!checkFinishTowers()){
                if (target != null){
                    AtomicBoolean end = new AtomicBoolean(false);
                    for (Direction dir: Direction.values()){
                        switch (dir){
                            case UP:
                            case DOWN:
                                continue;
                            default:
                                level.getBlockEntity(target.getBlockPos()).getCapability( ForgeCapabilities.ITEM_HANDLER,dir).ifPresent(h -> {
                                    for (int i = 0; i < h.getSlots();i++){
                                        if (craftingRecipeContains(h.getStackInSlot(i))){
                                            end.set(true);
                                            itemConsumptionTicks++;
                                            spawnItemParticles(target.getBlockPos(), h.getStackInSlot(i),level,getBlockPos(),-0.55,0.4,1.8,0.4);
                                            if (itemConsumptionTicks >= MaxItemConsumptionTicks){
                                                itemConsumptionTicks = 0;
                                                h.extractItem(i,1,false);
                                                craftingItemConsumed++;
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
                    target = null;
                }else{
                    for (BedrockiumTowerTile tower: towers){
                        level.getBlockEntity(tower.getBlockPos()).getCapability( ForgeCapabilities.ITEM_HANDLER).ifPresent(h -> {
                            for (int i = 0; i < h.getSlots();i++){
                                if (craftingRecipeContains(h.getStackInSlot(i))){
                                    target = tower;
                                    itemConsumptionTicks = 0;
                                    return;
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    private boolean checkFinishTowers() {
        if (craftingItemConsumed+1 == craftingItemAmount){
            itemConsumptionTicks++;
            spawnItemParticlesWithVelocity(getBlockPos(), itemHandler.getStackInSlot(0),1,3.35f,0,-0.2f,0.4f,0.4f,level);
            spawnItemParticlesWithVelocity(getBlockPos(), itemHandler.getStackInSlot(0),-1,3.35f,0,1.2f,0.4f,0.4f,level);
            spawnItemParticlesWithVelocity(getBlockPos(), itemHandler.getStackInSlot(0),0,3.35f,1,.4f,0.4f,-0.2f,level);
            spawnItemParticlesWithVelocity(getBlockPos(), itemHandler.getStackInSlot(0),0,3.35f,-1,.4f,0.4f,1.0f,level);
            if (itemConsumptionTicks >= MaxItemConsumptionTicks){
                itemHandler.extractItem(0,itemHandler.getStackInSlot(0).getCount(),false);
                ItemEntity itementity = new ItemEntity(level, worldPosition.getX(), worldPosition.getY() + 1, worldPosition.getZ(), outPut);
                itementity.setDeltaMovement(0, level.random.nextGaussian() * (double)0.0075F * (double)4 + (double)0.2F, 0);
                level.addFreshEntity(itementity);
                outPut = ItemStack.EMPTY;
                updateValidRecipe(false);
                updateCrafting(false);
                itemConsumptionTicks = 0;
                craftingItemConsumed = 0;
                craftingItemAmount = 0;
                craftingProgress = 0;
            }
            return true;
        }
        return false;

    }

    private boolean craftingRecipeContains(ItemStack itemStack) {
        boolean check = false;
        if (craftingRecipe == null){
            return check;
        }
        for (ItemStack stack: craftingRecipe.getIngredientList()){
            if (!itemStack.isEmpty() && stack.is(itemStack.getItem())){
                check = true;
            }
        }
        return check;
    }
}
