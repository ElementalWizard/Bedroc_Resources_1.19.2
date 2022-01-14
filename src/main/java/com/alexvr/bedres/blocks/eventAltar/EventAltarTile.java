package com.alexvr.bedres.blocks.eventAltar;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.blocks.enderianRitualPedestal.EnderianRitualPedestalTile;
import com.alexvr.bedres.blocks.eventAltar.events.RainEvent;
import com.alexvr.bedres.recipes.ModRecipeRegistry;
import com.alexvr.bedres.recipes.eventRituals.EventRitualsRecipes;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.ParticleHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestal.CRAFTING;
import static com.alexvr.bedres.blocks.bedrockiumPedestal.BedrociumPedestal.VALIDRECIPE;


public class EventAltarTile extends BlockEntity {

    public List<EnderianRitualPedestalTile> tiles = new ArrayList<>();
    int target = -1;
    EventRitualsRecipes recipe = null;
    int ticksPerItem = 40;
    int craftingTick = 0;
    public EventAltarTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.EVENT_ALTAR_TILE.get(), pWorldPosition, pBlockState);

    }

    public void tickServer() {

        if (getBlockState().getValue(CRAFTING)) {
            List<ItemStack> removed = new ArrayList<>();
            if (!tiles.isEmpty() && target > -1 && target < tiles.size()) {
                AtomicBoolean found = new AtomicBoolean(false);
                tiles.get(target).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                    ItemStack stack = h.getStackInSlot(0);
                    if (stack.isEmpty()){
                        found.set(true);
                        target++;
                        return;
                    }

                    for (ItemStack stack2 : recipe.getIngredientList()) {
                        if (stack2.is(stack.getItem())) {
                            if (craftingTick == ticksPerItem){
                                craftingTick = 0;
                                removed.add(h.extractItem(0, 1, false));
                                found.set(true);
                                tiles.get(target).sendUpdates();
                                target++;
                                return;
                            }
                            craftingTick++;
                            ParticleHelper.spawnItemParticles(tiles.get(target).getBlockPos(),stack2,level,getBlockPos(),-0.75,.5,1,.5);
                            return;
                        }
                    }

                });

                if (found.get()){
                    return;
                }

            }else if (target >= tiles.size()){
                target = -1;
                for (ItemStack rem:removed){
                    recipe.getIngredientList().forEach( itemStack -> {
                        if (itemStack.is(rem.getItem())){
                            itemStack.shrink(1);
                        }
                    });
                }
                if (!recipe.getIngredientList().isEmpty()){
                    BedrockResources.LOGGER.info("Missing some items to consume: " + recipe.getIngredientList().toString());
                }
                List<String> events = new ArrayList<>();
                for (EventRitualsRecipes recipeCheck : ModRecipeRegistry.getEventRitualRecipes()) {
                    events.add(recipeCheck.getEvent());
                }
                if (events.contains(recipe.getEvent())) {
                    runEvent();
                    updateValidRecipe(false);
                    updateCrafting(false);
                    craftingTick = 0;
                }else{
                    BedrockResources.LOGGER.error("Event: " + recipe.getEvent() + "not found");
                }
            }
        }
    }

    public void updateCrafting(boolean crafting){
        level.setBlock(getBlockPos(), getBlockState().setValue(CRAFTING, Boolean.valueOf(crafting)), 4);
        getBlockState().updateNeighbourShapes(level,getBlockPos(),32);
        sendUpdates();
    }
    public void updateValidRecipe(boolean valid){
        level.setBlock(getBlockPos(), getBlockState().setValue(VALIDRECIPE, Boolean.valueOf(valid)), 4);
        getBlockState().updateNeighbourShapes(level,getBlockPos(),32);
        sendUpdates();
    }
    public void sendUpdates() {
        level.sendBlockUpdated(getBlockPos(), this.getBlockState(), getBlockState(), 3);
        setChanged();
    }

    private void runEvent() {
        switch (recipe.getEvent()){
            case "rain":
                if (recipe.getResultItem().is(Items.WATER_BUCKET)) {
                    RainEvent.start(level,getBlockPos());
                }else if (recipe.getResultItem().is(Items.BUCKET)) {
                    RainEvent.stop(level,getBlockPos());
                }
                break;
        }
    }
}
