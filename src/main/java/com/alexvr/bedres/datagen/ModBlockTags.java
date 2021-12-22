package com.alexvr.bedres.datagen;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockTags extends BlockTagsProvider {

    public ModBlockTags(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BedrockResources.MODID,existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(Registration.ENDERIAN_ORE_OVERWORLD.get())
                .add(Registration.ENDERIAN_ORE_NETHER.get())
                .add(Registration.ENDERIAN_ORE_END.get())
                .add(Registration.ENDERIAN_ORE_DEEPSLATE.get())
                .add(Registration.SPIKE_BLOCK.get())
                .add(Registration.BEDROCK_COMPRESSED_WIRE_BLOCK.get())
                .add(Registration.ENDERIAN_BLOCK_BLOCK.get())
                .add(Registration.ENDERIAN_BRICK_BLOCK.get())
                .add(Registration.FLUXED_GRAVITY_BUBBLE_BLOCK.get())
                .add(Registration.PEDESTAL_BLOCK.get())
                .add(Registration.ENDERIAN_RITUAL_PEDESTAL_BLOCK.get())
                .add(Registration.SCRAPE_TANK_BLOCK.get())
                .add(Registration.SCRAPER_MOTOR_BLOCK.get())
                .add(Registration.VOID_TEAR_BLOCK.get())
                .add(Registration.ENDERIAN_STAIRS_BLOCK.get())
                .add(Registration.DF_COOBLE_BLOCK.get())
                .add(Registration.ITEM_PLATFORM_BLOCK.get());

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(Registration.DF_COOBLE_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(Registration.SPIKE_BLOCK.get())
                .add(Registration.ITEM_PLATFORM_BLOCK.get())
                .add(Registration.BEDROCK_COMPRESSED_WIRE_BLOCK.get())
                .add(Registration.ENDERIAN_BLOCK_BLOCK.get())
                .add(Registration.ENDERIAN_BRICK_BLOCK.get())
                .add(Registration.FLUXED_GRAVITY_BUBBLE_BLOCK.get())
                .add(Registration.PEDESTAL_BLOCK.get())
                .add(Registration.ENDERIAN_RITUAL_PEDESTAL_BLOCK.get())
                .add(Registration.SCRAPE_TANK_BLOCK.get())
                .add(Registration.SCRAPER_MOTOR_BLOCK.get())
                .add(Registration.VOID_TEAR_BLOCK.get())
                .add(Registration.ENDERIAN_STAIRS_BLOCK.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(Registration.ENDERIAN_ORE_OVERWORLD.get())
                .add(Registration.ENDERIAN_ORE_NETHER.get())
                .add(Registration.ENDERIAN_ORE_END.get())
                .add(Registration.ENDERIAN_ORE_DEEPSLATE.get());

        tag(Registration.ENDERIAN_ORE)
                .add(Registration.ENDERIAN_ORE_OVERWORLD.get())
                .add(Registration.ENDERIAN_ORE_NETHER.get())
                .add(Registration.ENDERIAN_ORE_END.get())
                .add(Registration.ENDERIAN_ORE_DEEPSLATE.get());

        tag(Registration.DF_LOG)
                .add(Registration.DF_STRIPPED_OAK_LOG_BLOCK.get())
                .add(Registration.DF_OAK_LOG_BLOCK.get());

        tag(BlockTags.STAIRS)
                .add(Registration.ENDERIAN_STAIRS_BLOCK.get());

        tag(BlockTags.SLABS)
                .add(Registration.DF_OAK_SLAB_BLOCK.get());

        tag(BlockTags.OAK_LOGS)
                .add(Registration.DF_OAK_LOG_BLOCK.get())
                .add(Registration.DF_STRIPPED_OAK_LOG_BLOCK.get());

        tag(BlockTags.PLANKS)
                .add(Registration.DF_OAK_PLANKS_BLOCK.get());

        tag(BlockTags.DIRT)
                .add(Registration.DF_DIRT_BLOCK.get());

        tag(BlockTags.LEAVES)
                .add(Registration.DF_OAK_LEAVE_BLOCK.get());

        tag(Tags.Blocks.COBBLESTONE)
                .add(Registration.DF_COOBLE_BLOCK.get());
        tag(BlockTags.SAPLINGS)
                .add(Registration.DF_SAPPLING_BLOCK.get());

        tag(BlockTags.FLOWERS)
                .add(Registration.BLAZIUM_BLOCK.get())
                .add(Registration.ENDER_HUSH_BLOCK.get())
                .add(Registration.SUN_DAIZE_BLOCK.get());

        tag(Tags.Blocks.ORES)
                .add(Registration.ENDERIAN_ORE_OVERWORLD.get())
                .add(Registration.ENDERIAN_ORE_NETHER.get())
                .add(Registration.ENDERIAN_ORE_END.get())
                .add(Registration.ENDERIAN_ORE_DEEPSLATE.get());
    }

    @Override
    public String getName() {
        return "Bedrock Resources Tags";
    }
}
