package com.alexvr.bedres.datagen;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider {

    public ModRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> p_176532_) {

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Registration.ENDERIAN_ORE_ITEM),
                Registration.ENDERIAN_INGOT_ITEM.get(),1f,100)
                .unlockedBy("has_ore", has(Registration.ENDERIAN_ORE_ITEM))
                .save(p_176532_, "enderian_ingot1");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Registration.RAW_ENDERIAN_CHUNK.get()),
                        Registration.ENDERIAN_INGOT_ITEM.get(),0f,100)
                .unlockedBy("has_chunk", has(Registration.RAW_ENDERIAN_CHUNK.get()))
                .save(p_176532_, "enderian_ingot2");
        planksFromLog(p_176532_, Registration.DF_OAK_PLANKS_BLOCK.get(), Registration.DF_LOG_ITEM);
        woodenBoat(p_176532_, Items.OAK_BOAT, Registration.DF_OAK_PLANKS_BLOCK.get());
        nineBlockStorageRecipes(p_176532_, Registration.ENDERIAN_INGOT_ITEM.get(), Registration.ENDERIAN_BLOCK_ITEM.get());
        nineBlockStorageRecipes(p_176532_, Registration.BEDROCK_WIRE_ITEM.get(), Registration.BEDROCK_COMPRESSED_WIRE_ITEM.get());
        ShapedRecipeBuilder.shaped(Registration.FLUX_ORACLE_ITEM.get())
                .define('S', Items.ENDER_EYE).define('I', Registration.ENDERIAN_INGOT_ITEM.get()).define('E', Registration.BEDROCK_WIRE_ITEM.get())
                .pattern("IEI").pattern("ESE").pattern("IEI").unlockedBy("has_item", has(Registration.ENDERIAN_INGOT_ITEM.get())).save(p_176532_);
        ShapedRecipeBuilder.shaped(Registration.ENDERIAN_RITUAL_PEDESTAL_ITEM.get(), 2)
                .define('G', Tags.Items.INGOTS_GOLD).define('B', Registration.ENDERIAN_BRICK_ITEM.get()).define('S', Registration.ENDERIAN_STAIRS_ITEM.get()).define('P', Registration.ITEM_PLATFORM_ITEM.get())
                .pattern(" P ").pattern("SGS").pattern("SBS").unlockedBy("has_item", has(Registration.ITEM_PLATFORM_ITEM.get())).save(p_176532_);
        ShapedRecipeBuilder.shaped(Registration.SCRAPE_KNIFE_ITEM.get())
                .define('I', Registration.ENDERIAN_INGOT_ITEM.get()).define('E', Tags.Items.INGOTS_GOLD).define('B', Items.BLAZE_ROD).define('R', Items.ENDER_EYE)
                .pattern(" II").pattern("ERI").pattern("BE ").unlockedBy("has_item", has(Registration.ENDERIAN_INGOT_ITEM.get())).save(p_176532_);
        ShapedRecipeBuilder.shaped(Registration.SCRAPE_TANK_ITEM.get())
                .define('I', Tags.Items.INGOTS_IRON).define('R', Registration.ENDERIAN_INGOT_ITEM.get()).define('L', Tags.Items.GEMS_LAPIS).define('E', Registration.BEDROCK_WIRE_ITEM.get())
                .pattern("ILI").pattern("ERE").pattern("ILI").unlockedBy("has_item", has(Registration.ENDERIAN_INGOT_ITEM.get())).save(p_176532_);
        ShapedRecipeBuilder.shaped(Registration.SCRAPER_MESH_ITEM.get())
                .define('I', Tags.Items.INGOTS_IRON).define('S', Items.STRING).define('B', Items.IRON_BARS)
                .pattern("ISI").pattern("SBS").pattern("ISI").unlockedBy("has_item", has(Items.IRON_BARS)).save(p_176532_);
        slabBuilder(Registration.DF_OAK_SLAB_ITEM.get(), Ingredient.of(Registration.DF_OAK_PLANKS_ITEM.get())).unlockedBy("has_block", has(Registration.DF_OAK_PLANKS_BLOCK.get())).save(p_176532_);
        ShapedRecipeBuilder.shaped(Registration.ENDERIAN_BRICK_ITEM.get(),16)
                .define('I', Registration.ENDERIAN_INGOT_ITEM.get()).define('B', ItemTags.STONE_BRICKS)
                .pattern("BIB").pattern("IBI").pattern("BIB").unlockedBy("has_brick", has(Items.BRICK)).save(p_176532_);
        stairBuilder(Registration.ENDERIAN_STAIRS_ITEM.get(), Ingredient.of(Registration.ENDERIAN_BRICK_ITEM.get())).unlockedBy("has_block", has(Registration.ENDERIAN_BRICK_BLOCK.get())).save(p_176532_);
        ShapedRecipeBuilder.shaped(Registration.ITEM_PLATFORM_ITEM.get())
                .define('I', Tags.Items.INGOTS_GOLD).define('L', Tags.Items.STORAGE_BLOCKS_GOLD)
                .pattern(" I ").pattern("ILI").pattern(" I ").unlockedBy("has_block", has(Tags.Items.STORAGE_BLOCKS_GOLD)).save(p_176532_);
        ShapedRecipeBuilder.shaped(Registration.XP_MEDALLION_ITEM.get())
                .define('I', Registration.ENDERIAN_INGOT_ITEM.get()).define('H', Registration.ENDER_HUSH_ITEM.get()).define('E', Items.ENDER_EYE)
                .pattern("IHI").pattern("HEH").pattern("IHI").unlockedBy("has_item", has(Items.ENDER_EYE)).save(p_176532_);
        ShapedRecipeBuilder.shaped(Registration.ROPE_ITEM.get())
                .define('B', Registration.SUN_DAIZE_ITEM.get()).define('S', Tags.Items.STRING)
                .pattern("S").pattern("B").pattern("S").unlockedBy("has_item", has(Registration.SUN_DAIZE_ITEM.get())).save(p_176532_);
        //ShapelessRecipeBuilder.shapeless(Items.COPPER_INGOT, 9).requires(Blocks.WAXED_COPPER_BLOCK).group(getItemName(Items.COPPER_INGOT)).unlockedBy(getHasName(Blocks.WAXED_COPPER_BLOCK), has(Blocks.WAXED_COPPER_BLOCK)).save(p_176532_, getConversionRecipeName(Items.COPPER_INGOT, Blocks.WAXED_COPPER_BLOCK));
        //ShapedRecipeBuilder.shaped(Blocks.DARK_PRISMARINE).define('S', Items.PRISMARINE_SHARD).define('I', Items.BLACK_DYE).pattern("SSS").pattern("SIS").pattern("SSS").unlockedBy("has_prismarine_shard", has(Items.PRISMARINE_SHARD)).save(p_176532_);
        //nineBlockStorageRecipes(p_176532_, Items.DIAMOND, Items.DIAMOND_BLOCK);
        //slabBuilder(Blocks.QUARTZ_SLAB, Ingredient.of(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR)).unlockedBy("has_chiseled_quartz_block", has(Blocks.CHISELED_QUARTZ_BLOCK)).unlockedBy("has_quartz_block", has(Blocks.QUARTZ_BLOCK)).unlockedBy("has_quartz_pillar", has(Blocks.QUARTZ_PILLAR)).save(p_176532_);

    }

    private static RecipeBuilder stairBuilder(ItemLike p_176711_, Ingredient p_176712_) {
        return ShapedRecipeBuilder.shaped(p_176711_, 4).define('#', p_176712_).pattern("#  ").pattern("## ").pattern("###");
    }

    private static RecipeBuilder slabBuilder(ItemLike p_176705_, Ingredient p_176706_) {
        return ShapedRecipeBuilder.shaped(p_176705_, 6).define('#', p_176706_).pattern("###");
    }
    private static String getSimpleRecipeName(ItemLike p_176645_) {
        return getItemName(p_176645_);
    }

    private static void nineBlockStorageRecipes(Consumer<FinishedRecipe> p_176744_, ItemLike p_176745_, ItemLike p_176746_) {
        nineBlockStorageRecipes(p_176744_, p_176745_, p_176746_, getSimpleRecipeName(p_176746_), (String)null, getSimpleRecipeName(p_176745_), (String)null);
    }

    private static String getItemName(ItemLike p_176633_) {
        return Registry.ITEM.getKey(p_176633_.asItem()).getPath();
    }

    private static String getHasName(ItemLike p_176603_) {
        return "has_" + getItemName(p_176603_);
    }

    private static void nineBlockStorageRecipes(Consumer<FinishedRecipe> p_176569_, ItemLike p_176570_, ItemLike p_176571_, String p_176572_, @Nullable String p_176573_, String p_176574_, @Nullable String p_176575_) {
        ShapelessRecipeBuilder.shapeless(p_176570_, 9).requires(p_176571_).group(p_176575_).unlockedBy(getHasName(p_176571_), has(p_176571_)).save(p_176569_, new ResourceLocation(p_176574_));
        ShapedRecipeBuilder.shaped(p_176571_).define('#', p_176570_).pattern("###").pattern("###").pattern("###").group(p_176573_).unlockedBy(getHasName(p_176570_), has(p_176570_)).save(p_176569_, new ResourceLocation(p_176572_));
    }
    private static void woodenBoat(Consumer<FinishedRecipe> p_126022_, ItemLike p_126023_, ItemLike p_126024_) {
        ShapedRecipeBuilder.shaped(p_126023_).define('#', p_126024_).pattern("# #").pattern("###").group("boat").unlockedBy("in_water", insideOf(Blocks.WATER)).save(p_126022_);
    }


    private static void planksFromLog(Consumer<FinishedRecipe> p_125999_, ItemLike p_126000_, Tag<Item> p_126001_) {
        ShapelessRecipeBuilder.shapeless(p_126000_, 4).requires(p_126001_).group("planks").unlockedBy("has_log", has(p_126001_)).save(p_125999_);
    }

}
