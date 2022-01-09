package com.alexvr.bedres.datagen;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public ModLanguageProvider(DataGenerator generator, String language) {
        super(generator, BedrockResources.MODID,language);

    }

    @Override
    protected void addTranslations() {
        add(Registration.ENDERIAN_ORE_OVERWORLD.get(),"Enderian Ore" );
        add(Registration.ENDERIAN_ORE_NETHER.get(),"Enderian Ore" );
        add(Registration.ENDERIAN_ORE_END.get(),"Enderian Ore" );
        add(Registration.ENDERIAN_ORE_DEEPSLATE.get(),"Enderian Ore" );

        add(Registration.SCRAPE_TANK_BLOCK.get(),"Scrape Tank" );
        add(Registration.ENDERIAN_BLOCK_BLOCK.get(),"Enderian Block" );
        add(Registration.BLAZIUM_BLOCK.get(),"Blazium" );
        add(Registration.ENDER_HUSH_BLOCK.get(),"Ender Hush" );
        add(Registration.SUN_DAIZE_BLOCK.get(),"Sun Daize" );
        add(Registration.BASE_SPIKE_BLOCK.get(),"Bedrockium Tower" );
        add(Registration.SPIKE_BLOCK.get(),"Bedrockium Spike" );
        add(Registration.PEDESTAL_BLOCK.get(),"Bedrockium Pedestal" );
        add(Registration.ENDERIAN_STAIRS_BLOCK.get(),"Bedrockium Stairs" );
        add(Registration.ENDERIAN_BRICK_BLOCK.get(),"Bedrockium Brick" );
        add(Registration.BEDROCK_COMPRESSED_WIRE_BLOCK.get(),"Bedrock Compressed Wire" );
        add(Registration.ENDERIAN_RITUAL_PEDESTAL_BLOCK.get(),"Enderian Ritual Pedestal" );
        add(Registration.SCRAPER_MOTOR_BLOCK.get(),"Scraper Motor" );
        add(Registration.ROPE_BLOCK.get(),"Rope" );

        add(Registration.DF_OAK_LEAVE_BLOCK.get(),"Decaying Fluxed Leaves" );
        add(Registration.DF_SAPPLING_BLOCK.get(),"Decaying Fluxed Sappling" );
        add(Registration.DF_STRIPPED_OAK_LOG_BLOCK.get(),"Decaying Fluxed Stripped Oak" );
        add(Registration.DF_DIRT_BLOCK.get(),"Decaying Fluxed Dirt" );
        add(Registration.DF_COOBLE_BLOCK.get(),"Decaying Fluxed Cobblestone" );
        add(Registration.DF_OAK_LOG_BLOCK.get(),"Decaying Fluxed oak Log" );
        add(Registration.DF_GRASS_BLOCK.get(),"Decaying Fluxed Grass" );
        add(Registration.DF_OAK_SLAB_BLOCK.get(),"Decaying Fluxed Oak Slabs" );
        add(Registration.DF_OAK_PLANKS_BLOCK.get(),"Decaying Fluxed Oak Planks" );

        add(Registration.FLUXED_SPORES_BLOCK.get(),"Fluxed Spores" );
        add(Registration.FLUXED_GRAVITY_BUBBLE_BLOCK.get(),"Fluxed Gravity Bubble" );
        add(Registration.VOID_TEAR_BLOCK.get(),"Void Tears" );
        add(Registration.ITEM_PLATFORM_BLOCK.get(),"Item Platform" );

        add(Registration.RAW_ENDERIAN_CHUNK.get(), "Raw Enderian Chunk" );
        add(Registration.ENDERIAN_INGOT_ITEM.get(), "Enderian Ingot" );
        add(Registration.BEDROCK_WIRE_BLOCK.get(), "Bedrock Scrapes" );
        add(Registration.SCRAPE_KNIFE_ITEM.get(), "Scrape Knife" );
        add(Registration.STAFF_ITEM.get(), "Gravity Staff" );
        add(Registration.SCRAPER_MESH_ITEM.get(), "Scraper Mesh" );
        add(Registration.NEBULA_HEART_ITEM.get(), "Nebula Heart" );
        add(Registration.FLUX_ORACLE_ITEM.get(), "Fluxed Oracle" );
        add(Registration.FLUXED_CUPCAKE_ITEM.get(), "Fluxed Cupcake" );
        add(Registration.MAGE_STAFF_ITEM.get(), "Mage Staff" );

        add(BedrockReferences.SCRAPE_TANK_TOOLTIP, "Bedrock Scrape Tank, can hold up to 256" );
        add(BedrockReferences.ITEM_PLATFORM_TOOLTIP, "Can hold up to a stack of an item and display it." );

        add(BedrockReferences.CREATIVE_TAB_NAME, "Bedrock Resources Tab" );
        add(BedrockReferences.SCREEN_SCRAPE_TANK, "Scrape Tank" );
        add(Registration.SPORE_DEITY_EGG_ITEM.get(), "Spore Deity Spawn Egg");
        add(Registration.FLUXED_CREEP_EGG_ITEM.get(), "Fluxed Creep Spawn Egg");

        add(Registration.FLUXED_CREEP.get(), "Fluxed Creep");
        add(Registration.SPORE_DEITY.get(), "Spore Deity");
    }


}
