package com.alexvr.bedres.recipes.eventRituals;

import com.alexvr.bedres.blocks.enderianRitualPedestal.EnderianRitualPedestalTile;
import com.alexvr.bedres.recipes.ModRecipeRegistry;
import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventRitualsRecipes implements Recipe<EventRitualsContext> {

    private final ResourceLocation id;
    private final ItemStack destination;
    private final List<ItemStack> ingredients;
    private final List<String> pattern; // w for wire, i for item
    public static int patternRadius = 2; // pattern size is (patternRadius*2) + 1

    public EventRitualsRecipes(ItemStack destination, List<String> pattern, ItemStack... ingredients) {
        this.id = destination.getItem().getRegistryName();
        this.destination = destination;
        this.ingredients = new ArrayList<>(ingredients.length);
        this.pattern = pattern;
        Collections.addAll(this.ingredients, ingredients);
    }


    public static List<ItemStack> getItemsForRecipeFromWordl(Level level, BlockPos position, int xRadius, int zRadius){
        List<ItemStack> items = new ArrayList<>();
        items.clear();
        for (int x = position.getX() - xRadius; x <= position.getX() + xRadius; x++){
            for (int z = position.getZ() - zRadius; z <= position.getZ() + zRadius; z++){
                BlockPos newPosition = new BlockPos(x,position.getY(),z);
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

    public static List<String> getPatterForRecipeFromWorld(Level level, BlockPos position, int xRadius, int zRadius){
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

    public static EventRitualsRecipes findRecipeFromOutput(ItemStack destination) {
        for (EventRitualsRecipes recipe : ModRecipeRegistry.getEventRitualRecipes()) {
            if (ItemHandlerHelper.canItemStacksStack(recipe.getDestination(), destination)) {
                return recipe;
            }
        }
        return null;
    }
    public static EventRitualsRecipes findRecipeFromPattern(List<String> pat) {
        for (EventRitualsRecipes recipe : ModRecipeRegistry.getEventRitualRecipes()) {
            boolean validRec = true;
            List<String> ingCopy = new ArrayList<>(pat);

            for (String row: recipe.getPattern()){
                boolean valid = false;
                for (String row2: pat){
                    if (row2.equals(row)){
                        valid = true;
                        ingCopy.remove(row2);
                        break;
                    }
                }
                if (!valid){
                    validRec = false;
                    break;
                }
            }
            if (validRec && ingCopy.isEmpty()){
                return recipe;
            }

        }
        return null;
    }

    public static EventRitualsRecipes findRecipeFromIngrent(List<ItemStack> ing) {
        for (EventRitualsRecipes recipe : ModRecipeRegistry.getEventRitualRecipes()) {
            boolean validRec = true;
            List<ItemStack> ingCopy = new ArrayList<>(ing);

            for (ItemStack stack: recipe.getIngredientList()){
                boolean valid = false;
                for (ItemStack stack2: ing){
                    if (stack2.is(stack.getItem()) && stack2.equals(stack,false)){
                        valid = true;
                        ingCopy.remove(stack2);
                        break;
                    }
                }
                if (!valid){
                    validRec = false;
                    break;
                }
            }
            if (validRec && ingCopy.isEmpty()){
                return recipe;
            }

        }
        return null;
    }
    public ItemStack getDestination() {
        return destination;
    }

    public List<ItemStack> getIngredientList() {
        return ingredients;
    }

    @Override
    public boolean matches(EventRitualsContext inv, Level worldIn) {
        return false;
    }

    @Override
    public ItemStack assemble(EventRitualsContext inv) {
        return null;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return destination;
    }

    public List<String> getPattern() {
        return pattern;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeRegistry.EVENT_RITUAL_RECIPES.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeRegistry.EVENT_RITUAL;
    }
}
