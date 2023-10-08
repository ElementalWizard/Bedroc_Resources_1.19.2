package com.alexvr.bedres.datagen;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.setup.Registration;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModItemTags extends ItemTagsProvider {
    public ModItemTags(PackOutput generator, CompletableFuture<HolderLookup.Provider> p_275729_,
                       CompletableFuture<TagLookup<Block>> p_275322_, ExistingFileHelper existingFileHelper) {
        super(generator,p_275729_, p_275322_, BedrockResources.MODID,existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(Tags.Items.ORES)
                .add(Registration.ENDERIAN_ORE_OVERWORLD_ITEM.get())
                .add(Registration.ENDERIAN_ORE_NETHER_ITEM.get())
                .add(Registration.ENDERIAN_ORE_END_ITEM.get())
                .add(Registration.ENDERIAN_ORE_DEEPSLATE_ITEM.get());

        tag(Registration.ENDERIAN_ORE_ITEM)
                .add(Registration.ENDERIAN_ORE_OVERWORLD_ITEM.get())
                .add(Registration.ENDERIAN_ORE_NETHER_ITEM.get())
                .add(Registration.ENDERIAN_ORE_END_ITEM.get())
                .add(Registration.ENDERIAN_ORE_DEEPSLATE_ITEM.get());

        tag(ItemTags.STAIRS)
                .add(Registration.ENDERIAN_STAIRS_ITEM.get());

        tag(ItemTags.SLABS)
                .add(Registration.DF_OAK_SLAB_ITEM.get());

        tag(ItemTags.OAK_LOGS)
                .add(Registration.DF_OAK_LOG_ITEM.get())
                .add(Registration.DF_STRIPPED_OAK_LOG_ITEM.get());

        tag(ItemTags.PLANKS)
                .add(Registration.DF_OAK_PLANKS_ITEM.get());

        tag(Tags.Items.INGOTS)
                .add(Registration.ENDERIAN_INGOT_ITEM.get());

        tag(ItemTags.DIRT)
                .add(Registration.DF_DIRT_ITEM.get());

        tag(Registration.DF_LOG_ITEM)
                .add(Registration.DF_STRIPPED_OAK_LOG_ITEM.get())
                .add(Registration.DF_OAK_LOG_ITEM.get());

        tag(ItemTags.LEAVES)
                .add(Registration.DF_OAK_LEAVE_ITEM.get());

        tag(Tags.Items.COBBLESTONE)
                .add(Registration.DF_COOBLE_ITEM.get());
        tag(ItemTags.SAPLINGS)
                .add(Registration.DF_SAPPLING_ITEM.get());

        tag(ItemTags.FLOWERS)
                .add(Registration.BLAZIUM_ITEM.get())
                .add(Registration.ENDER_HUSH_ITEM.get())
                .add(Registration.SUN_DAIZE_ITEM.get());

    }

}
