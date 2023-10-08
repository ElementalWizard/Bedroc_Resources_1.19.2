package com.alexvr.bedres.datagen;

import com.alexvr.bedres.setup.Registration;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider implements IConditionBuilder {

    public ModRecipes(PackOutput generator) {
        super(generator);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Registration.ENDERIAN_ORE_ITEM), RecipeCategory.MISC,
                Registration.ENDERIAN_INGOT_ITEM.get(),1f,100)
                .unlockedBy("has_ore", has(Registration.ENDERIAN_ORE_ITEM))
                .save(pWriter, "enderian_ingot1");
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Registration.RAW_ENDERIAN_CHUNK.get()), RecipeCategory.MISC,
                        Registration.ENDERIAN_INGOT_ITEM.get(),0f,100)
                .unlockedBy("has_chunk", has(Registration.RAW_ENDERIAN_CHUNK.get()))
                .save(pWriter, "enderian_ingot2");
        planksFromLog(pWriter, Registration.DF_OAK_PLANKS_BLOCK.get(), Registration.DF_LOG_ITEM, 4);
        
        shapeless1x1Recipes(pWriter,Items.RED_DYE, Registration.BLAZIUM_ITEM.get());
        shapeless1x1Recipes(pWriter,Items.YELLOW_DYE, Registration.SUN_DAIZE_ITEM.get());
        shapeless1x1Recipes(pWriter,Items.PURPLE_DYE,Registration.ENDER_HUSH_ITEM.get());

        woodenBoat(pWriter, Items.OAK_BOAT, Registration.DF_OAK_PLANKS_BLOCK.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, Registration.ENDERIAN_INGOT_ITEM.get(), RecipeCategory.BUILDING_BLOCKS, Registration.ENDERIAN_BLOCK_ITEM.get());
        nineBlockStorageRecipes(pWriter, RecipeCategory.MISC, Registration.BEDROCK_WIRE_ITEM.get(), RecipeCategory.BUILDING_BLOCKS, Registration.BEDROCK_COMPRESSED_WIRE_ITEM.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.FLUX_ORACLE_ITEM.get())
                .define('S', Items.ENDER_EYE).define('I', Registration.ENDERIAN_INGOT_ITEM.get()).define('E', Registration.BEDROCK_WIRE_ITEM.get())
                .pattern("IEI").pattern("ESE").pattern("IEI").unlockedBy("has_item", has(Registration.ENDERIAN_INGOT_ITEM.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Registration.ENDERIAN_RITUAL_PEDESTAL_ITEM.get(), 2)
                .define('G', Tags.Items.INGOTS_GOLD).define('B', Registration.ENDERIAN_BRICK_ITEM.get()).define('S', Registration.ENDERIAN_STAIRS_ITEM.get()).define('P', Registration.ITEM_PLATFORM_ITEM.get())
                .pattern(" P ").pattern("SGS").pattern("SBS").unlockedBy("has_item", has(Registration.ITEM_PLATFORM_ITEM.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.SCRAPE_KNIFE_ITEM.get())
                .define('I', Registration.ENDERIAN_INGOT_ITEM.get()).define('E', Tags.Items.INGOTS_GOLD).define('B', Items.BLAZE_ROD).define('R', Items.ENDER_EYE)
                .pattern(" II").pattern("ERI").pattern("BE ").unlockedBy("has_item", has(Registration.ENDERIAN_INGOT_ITEM.get())).save(pWriter);
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, Registration.DF_OAK_SLAB_ITEM.get(), Ingredient.of(Registration.DF_OAK_PLANKS_ITEM.get())).unlockedBy("has_block", has(Registration.DF_OAK_PLANKS_BLOCK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, Registration.ENDERIAN_BRICK_ITEM.get(),16)
                .define('I', Registration.ENDERIAN_INGOT_ITEM.get()).define('B', ItemTags.STONE_BRICKS)
                .pattern("BIB").pattern("IBI").pattern("BIB").unlockedBy("has_brick", has(Items.BRICK)).save(pWriter);
        stairBuilder(Registration.ENDERIAN_STAIRS_ITEM.get(), Ingredient.of(Registration.ENDERIAN_BRICK_ITEM.get())).unlockedBy("has_block", has(Registration.ENDERIAN_BRICK_BLOCK.get())).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, Registration.ITEM_PLATFORM_ITEM.get())
                .define('I', Tags.Items.INGOTS_GOLD).define('L', Tags.Items.STORAGE_BLOCKS_GOLD)
                .pattern(" I ").pattern("ILI").pattern(" I ").unlockedBy("has_block", has(Tags.Items.STORAGE_BLOCKS_GOLD)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.XP_MEDALLION_ITEM.get())
                .define('I', Registration.ENDERIAN_INGOT_ITEM.get()).define('H', Registration.ENDER_HUSH_ITEM.get()).define('E', Items.ENDER_EYE)
                .pattern("IHI").pattern("HEH").pattern("IHI").unlockedBy("has_item", has(Items.ENDER_EYE)).save(pWriter);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Registration.ROPE_ITEM.get())
                .define('B', Registration.SUN_DAIZE_ITEM.get()).define('S', Tags.Items.STRING)
                .pattern("S").pattern("B").pattern("S").unlockedBy("has_item", has(Registration.SUN_DAIZE_ITEM.get())).save(pWriter);
        //ShapelessRecipeBuilder.shapeless(Items.COPPER_INGOT, 9).requires(Blocks.WAXED_COPPER_BLOCK).group(getItemName(Items.COPPER_INGOT)).unlockedBy(getHasName(Blocks.WAXED_COPPER_BLOCK), has(Blocks.WAXED_COPPER_BLOCK)).save(p_176532_, getConversionRecipeName(Items.COPPER_INGOT, Blocks.WAXED_COPPER_BLOCK));
        //ShapedRecipeBuilder.shaped(Blocks.DARK_PRISMARINE).define('S', Items.PRISMARINE_SHARD).define('I', Items.BLACK_DYE).pattern("SSS").pattern("SIS").pattern("SSS").unlockedBy("has_prismarine_shard", has(Items.PRISMARINE_SHARD)).save(p_176532_);
        //nineBlockStorageRecipes(p_176532_, Items.DIAMOND, Items.DIAMOND_BLOCK);
        //slabBuilder(Blocks.QUARTZ_SLAB, Ingredient.of(Blocks.CHISELED_QUARTZ_BLOCK, Blocks.QUARTZ_BLOCK, Blocks.QUARTZ_PILLAR)).unlockedBy("has_chiseled_quartz_block", has(Blocks.CHISELED_QUARTZ_BLOCK)).unlockedBy("has_quartz_block", has(Blocks.QUARTZ_BLOCK)).unlockedBy("has_quartz_pillar", has(Blocks.QUARTZ_PILLAR)).save(p_176532_);

    }





    protected static String getHasName(ItemLike p_176603_) {
        return "has_" + getItemName(p_176603_);
    }


    private static void shapeless1x1Recipes(Consumer<FinishedRecipe> p_176569_, ItemLike p_176570_, ItemLike p_176571_) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, p_176570_, 1).requires(p_176571_).group((String)null).unlockedBy(getHasName(p_176571_), has(p_176571_)).save(p_176569_, new ResourceLocation(getSimpleRecipeName(p_176570_)));
    }


//    protected static void planksFromLog(Consumer<FinishedRecipe> p_125999_, ItemLike p_126000_, Tag<Item> p_126001_) {
//        ShapelessRecipeBuilder.shapeless(p_126000_, 4).requires(p_126001_).group("planks").unlockedBy("has_log", has(p_126001_)).save(p_125999_);
//    }

}
