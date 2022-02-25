package com.alexvr.bedres.blocks.eventAltar;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.blocks.enderianRitualPedestal.EnderianRitualPedestalTile;
import com.alexvr.bedres.blocks.eventAltar.events.PlayerUpgradeEvent;
import com.alexvr.bedres.blocks.eventAltar.events.ToolUpgradeEvent;
import com.alexvr.bedres.blocks.eventAltar.events.WorldEvent;
import com.alexvr.bedres.recipes.ModRecipeRegistry;
import com.alexvr.bedres.recipes.eventRituals.EventRitualsRecipes;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.ParticleHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
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
import static com.alexvr.bedres.setup.ModConfig.PEDESTAL_TICKS_PER_ITEM;


public class EventAltarTile extends BlockEntity {

    public List<EnderianRitualPedestalTile> tiles = new ArrayList<>();
    int target = -1;
    EventRitualsRecipes recipe = null;
    int ticksPerItem = PEDESTAL_TICKS_PER_ITEM.get();
    int craftingTick = 0;
    Player playerInside;
    public EventAltarTile(BlockPos pWorldPosition, BlockState pBlockState) {
        super(Registration.EVENT_ALTAR_TILE.get(), pWorldPosition, pBlockState);

    }

    public void tick() {
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
            case "world":
                if (recipe.getResultItem().is(Items.WATER_BUCKET) || recipe.getResultItem().is(Items.BUCKET)) {
                    WorldEvent.rain(level,getBlockPos(),recipe.getResultItem());
                }else if (recipe.getResultItem().is(Items.SUNFLOWER) || recipe.getResultItem().is(Items.CLOCK)) {
                    WorldEvent.timeSkip(level,getBlockPos(),recipe.getResultItem());
                }
                break;
            case "tool":
                ToolUpgradeEvent.start(Minecraft.getInstance().player, recipe.getResultItem());
                break;
            case "player_upgrade":
                if (recipe.getResultItem().is(Items.REDSTONE) || recipe.getResultItem().is(Registration.BEDROCK_WIRE_ITEM.get()) || recipe.getResultItem().is(Registration.ROPE_ITEM.get())) {
                    PlayerUpgradeEvent.speed(Minecraft.getInstance().player,recipe.getResultItem());
                }else if (recipe.getResultItem().is(Items.RABBIT_FOOT) || recipe.getResultItem().is(Items.RABBIT_HIDE) || recipe.getResultItem().is(Items.CHAIN)) {
                    PlayerUpgradeEvent.jump(Minecraft.getInstance().player,recipe.getResultItem());
                }else if (recipe.getResultItem().is(Items.SLIME_BALL) || recipe.getResultItem().is(Items.HONEY_BLOCK)) {
                    PlayerUpgradeEvent.fallDamage(Minecraft.getInstance().player,recipe.getResultItem());
                }else if (recipe.getResultItem().is(Registration.BEDROCK_WIRE_ITEM.get())) {
                    PlayerUpgradeEvent.empower(Minecraft.getInstance().player);
                }
                break;
        }
    }
}
