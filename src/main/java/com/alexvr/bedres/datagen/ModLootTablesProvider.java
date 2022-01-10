package com.alexvr.bedres.datagen;

import com.alexvr.bedres.setup.Registration;
import com.alexvr.bedres.utils.BedrockReferences;
import net.minecraft.data.DataGenerator;

public class ModLootTablesProvider extends BaseLootTableProvider {
    public ModLootTablesProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addTables() {
        lootTables.put(Registration.ENDERIAN_ORE_OVERWORLD.get(),createSilktoucheTable(BedrockReferences.ENDERIAN_ORE_REGNAME + "_overworld", Registration.ENDERIAN_ORE_OVERWORLD.get(), Registration.RAW_ENDERIAN_CHUNK.get(),1,3));
        lootTables.put(Registration.ENDERIAN_ORE_NETHER.get(),createSilktoucheTable(BedrockReferences.ENDERIAN_ORE_REGNAME + "_nether", Registration.ENDERIAN_ORE_NETHER.get(), Registration.RAW_ENDERIAN_CHUNK.get(),1,3));
        lootTables.put(Registration.ENDERIAN_ORE_END.get(),createSilktoucheTable(BedrockReferences.ENDERIAN_ORE_REGNAME + "_end", Registration.ENDERIAN_ORE_END.get(), Registration.RAW_ENDERIAN_CHUNK.get(),1,3));
        lootTables.put(Registration.ENDERIAN_ORE_DEEPSLATE.get(),createSilktoucheTable(BedrockReferences.ENDERIAN_ORE_REGNAME + "_deepslate", Registration.ENDERIAN_ORE_DEEPSLATE.get(), Registration.RAW_ENDERIAN_CHUNK.get(),1,3));
        lootTables.put(Registration.ENDERIAN_BLOCK_BLOCK.get(),createSimpleTable(BedrockReferences.ENDERIAN_BLOCK_REGNAME, Registration.ENDERIAN_BLOCK_BLOCK.get()));

        lootTables.put(Registration.ROPE_BLOCK.get(),createSimpleTable(BedrockReferences.ROPE_REGNAME, Registration.ROPE_BLOCK.get()));
        lootTables.put(Registration.ENDERIAN_BRICK_BLOCK.get(),createSimpleTable(BedrockReferences.ENDERIAN_BRICK_REGNAME, Registration.ENDERIAN_BRICK_BLOCK.get()));
        lootTables.put(Registration.ENDERIAN_STAIRS_BLOCK.get(),createSimpleTable(BedrockReferences.ENDERIAN_STAIRS_REGNAME, Registration.ENDERIAN_STAIRS_BLOCK.get()));
        lootTables.put(Registration.ENDERIAN_RITUAL_PEDESTAL_BLOCK.get(),createSimpleTable(BedrockReferences.ENDERIAN_RITUAL_PEDESTAL_REGNAME, Registration.ENDERIAN_RITUAL_PEDESTAL_BLOCK.get()));

        lootTables.put(Registration.SCRAPE_TANK_BLOCK.get(),createStandardTable(BedrockReferences.SCRAPE_TANK_REGNAME, Registration.SCRAPE_TANK_BLOCK.get(),Registration.SCRAPE_TANK_TILE.get()));
        lootTables.put(Registration.SCRAPER_MOTOR_BLOCK.get(),createSimpleTable(BedrockReferences.SCRAPER_MOTOR_REGNAME, Registration.SCRAPER_MOTOR_BLOCK.get()));
        lootTables.put(Registration.EVENT_ALTAR_BLOCK.get(),createSimpleTable(BedrockReferences.EVENT_ALTAR_REGNAME, Registration.EVENT_ALTAR_BLOCK.get()));

        lootTables.put(Registration.SPIKE_BLOCK.get(),createSimpleTable(BedrockReferences.SPIKE_REGNAME, Registration.SPIKE_BLOCK.get()));
        lootTables.put(Registration.BASE_SPIKE_BLOCK.get(),createSimpleTable(BedrockReferences.BASE_SPIKE_REGNAME, Registration.BASE_SPIKE_BLOCK.get()));
        lootTables.put(Registration.PEDESTAL_BLOCK.get(),createSimpleTable(BedrockReferences.PEDESTAL_REGNAME, Registration.PEDESTAL_BLOCK.get()));
        lootTables.put(Registration.ITEM_PLATFORM_BLOCK.get(),createStandardTable(BedrockReferences.ITEM_PLATFORM_REGNAME, Registration.ITEM_PLATFORM_BLOCK.get(),Registration.ITEM_PLATFORM_TILE.get()));

        lootTables.put(Registration.FLUXED_GRAVITY_BUBBLE_BLOCK.get(),createSimpleTable(BedrockReferences.FLUXED_GRAVITY_BUBBLE_REGNAME, Registration.FLUXED_GRAVITY_BUBBLE_BLOCK.get()));
        lootTables.put(Registration.BEDROCK_COMPRESSED_WIRE_BLOCK.get(),createSimpleTable(BedrockReferences.BEDROCK_COMPRESSED_WIRE_REGNAME, Registration.BEDROCK_COMPRESSED_WIRE_BLOCK.get()));
        lootTables.put(Registration.VOID_TEAR_BLOCK.get(),createSimpleTable(BedrockReferences.VOID_TEAR_REGNAME, Registration.VOID_TEAR_BLOCK.get()));
        lootTables.put(Registration.BEDROCK_WIRE_BLOCK.get(),createSimpleTable(BedrockReferences.BEDROCK_WIRE_REGNAME, Registration.BEDROCK_WIRE_BLOCK.get()));

        lootTables.put(Registration.BLAZIUM_BLOCK.get(),createSimpleTable(BedrockReferences.BLAZIUM_REGNAME, Registration.BLAZIUM_BLOCK.get()));
        lootTables.put(Registration.ENDER_HUSH_BLOCK.get(),createSimpleTable(BedrockReferences.ENDER_HUSH_REGNAME, Registration.ENDER_HUSH_BLOCK.get()));
        lootTables.put(Registration.SUN_DAIZE_BLOCK.get(),createSimpleTable(BedrockReferences.SUN_DAIZE_REGNAME, Registration.SUN_DAIZE_BLOCK.get()));
        lootTables.put(Registration.FLUXED_SPORES_BLOCK.get(),createSimpleTable(BedrockReferences.FLUXED_SPORES_REGNAME, Registration.FLUXED_SPORES_BLOCK.get()));

        lootTables.put(Registration.DF_COOBLE_BLOCK.get(),createSimpleTable(BedrockReferences.DF_COBBLE_REGNAME, Registration.DF_COOBLE_BLOCK.get()));
        lootTables.put(Registration.DF_DIRT_BLOCK.get(),createSimpleTable(BedrockReferences.DF_DIRT_REGNAME, Registration.DF_DIRT_BLOCK.get()));
        lootTables.put(Registration.DF_GRASS_BLOCK.get(),createSimpleTable(BedrockReferences.DF_GRASS_REGNAME, Registration.DF_GRASS_BLOCK.get()));
        lootTables.put(Registration.DF_OAK_LEAVE_BLOCK.get(),createShearTable(BedrockReferences.DF_OAK_LEAVES_REGNAME, Registration.DF_OAK_LEAVE_BLOCK.get()));
        lootTables.put(Registration.DF_OAK_LOG_BLOCK.get(),createSimpleTable(BedrockReferences.DF_OAK_LOG_REGNAME, Registration.DF_OAK_LOG_BLOCK.get()));
        lootTables.put(Registration.DF_SAPPLING_BLOCK.get(),createSimpleTable(BedrockReferences.DF_SAPPLING_REGNAME, Registration.DF_SAPPLING_BLOCK.get()));
        lootTables.put(Registration.DF_OAK_PLANKS_BLOCK.get(),createSimpleTable(BedrockReferences.DF_OAK_PLANKS_REGNAME, Registration.DF_OAK_PLANKS_BLOCK.get()));
        lootTables.put(Registration.DF_OAK_SLAB_BLOCK.get(),createSimpleTable(BedrockReferences.DF_OAK_SLAB_REGNAME, Registration.DF_OAK_SLAB_BLOCK.get()));
        lootTables.put(Registration.DF_STRIPPED_OAK_LOG_BLOCK.get(),createSimpleTable(BedrockReferences.DF_STRIPPED_OAK_LOG_REGNAME, Registration.DF_STRIPPED_OAK_LOG_BLOCK.get()));

    }


}
