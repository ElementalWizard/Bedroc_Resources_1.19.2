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
        add(Registration.EVENT_ALTAR_BLOCK.get(),"Event Ritual Altar" );
        add(Registration.LIGHT_BLOCK.get(),"Light Block" );
        add(Registration.HEXTILE_BLOCK.get(),"HexTile" );

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
        add(Registration.XP_MEDALLION_ITEM.get(), "Xp Medallion" );

        add(BedrockReferences.SCRAPE_TANK_TOOLTIP, "Bedrock Scrape Tank, can hold up to 256" );
        add(BedrockReferences.ITEM_PLATFORM_TOOLTIP, "Can hold up to a stack of an item and display it." );
        add("jei.category.bedres.pedestal", "Item Infusions" );
        add("jei.category.bedres.event_ritual", "Event Rituals" );

        add("container.event.world.water_bucket", "Cloud Shift" );
        add("container.event.world.bucket", "Cloud Shift" );
        add("container.event.world.sunflower", "Celestial Shift" );
        add("container.event.world.clock", "Celestial Shift" );

        add("container.event.tool.wooden_pickaxe", "Wooden Pickaxe Infusion" );
        add("container.event.tool.iron_pickaxe", "Iron Pickaxe Infusion" );
        add("container.event.tool.golden_pickaxe", "Gold Pickaxe Infusion" );
        add("container.event.tool.diamond_pickaxe", "Diamond Pickaxe Infusion" );

        add("container.event.tool.wooden_axe", "Wooden Axe Infusion" );
        add("container.event.tool.iron_axe", "Iron Axe Infusion" );
        add("container.event.tool.golden_axe", "Gold Axe Infusion" );
        add("container.event.tool.diamond_axe", "Diamond Axe Infusion" );

        add("container.event.tool.wooden_sword", "Wooden Sword Infusion" );
        add("container.event.tool.iron_sword", "Iron Sword Infusion" );
        add("container.event.tool.diamond_sword", "Diamond Sword Infusion" );

        add("container.event.tool.wooden_hoe", "Wooden Hoe Infusion" );
        add("container.event.tool.iron_hoe", "Iron Hoe Infusion" );
        add("container.event.tool.diamond_hoe", "Diamond Hoe Infusion" );

        add("container.event.tool.wooden_shovel", "Wooden Shovel Infusion" );
        add("container.event.tool.iron_shovel", "Iron Shovel Infusion" );
        add("container.event.tool.golden_shovel", "Gold Shovel Infusion" );
        add("container.event.tool.diamond_shovel", "Diamond Shovel Infusion" );

        add("container.event.player_upgrade.redstone", "Mining Speed Upgrade" );
        add("container.event.player_upgrade.bedrock_wire", "Mining Speed Decrease" );
        add("container.event.player_upgrade.rope", "Mining Speed Reset" );

        add("container.event.player_upgrade.rabbit_foot", "Jump Upgrade" );
        add("container.event.player_upgrade.rabbit_hide", "Jump Decrease" );
        add("container.event.player_upgrade.chain", "Jump Reset" );

        add("container.event.player_upgrade.slime_ball", "Fall Damage Upgrade" );
        add("container.event.player_upgrade.honey_block", "Fall Damage Reset" );

        add("container.event.player_upgrade.bedrock_compressed_wire", "Flux Storage Upgrade" );

        add("mage_staff.rune.type.alpha", "Slingshot" );
        add("mage_staff.rune.type.beta", "Heaven Smite" );
        add("mage_staff.rune.type.delta", "Clear Effects/Regen" );
        add("mage_staff.rune.type.epsilon", "Life Steal" );
        add("mage_staff.rune.type.eta", "TBD" );
        add("mage_staff.rune.type.gama", "Poison" );
        add("mage_staff.rune.type.theta", "Green Thumb" );
        add("mage_staff.rune.type.zeta", "Light Projectile" );

        add("bedres.key.category", "Bedrock Resources Keybindings" );
        add("bedres.key.toggle_mode", "Toggle Item Mode" );

        add("bedres.mage_staff.green_thumb.fail", "Block cant be bonemealed" );
        add("bedres.mage_staff.lightning.fail", "Target entity or block not air" );

        add("bedres:fluxed_plains", "Desolated Fluxed Plains" );

        add("bedres.page_name.flux", "Fluxed Abilities" );
        add("bedres.page_name.altar", "Item Infusion Altar" );
        add("bedres.page_name.scrape", "Bedrock Scrapes" );
        add("bedres.page_name.knife", "Scraping Knife" );
        add("bedres.page_name.ender_hush", "Ender Hush" );
        add("bedres.page_name.daize", "Sun Daize" );
        add("bedres.page_name.blazium", "Blazium" );
        add("bedres.page_name.e_ore", "Enderian Ore" );
        add("bedres.page_name.e_ingot", "Enderian Ingot" );
        add("bedres.page_name.tank", "Scrape Tank" );
        add("bedres.page_name.item_platform", "Item Platform" );
        add("bedres.page_name.e_item_platform", "Ritual Item Platform" );
        add("bedres.page_name.event_ritual", "Event Ritual Pedestal" );
        add("bedres.page_name.spore_mush", "Fluxed Spores" );
        add("bedres.page_name.cupcake", "Fluxed Cupcake" );
        add("bedres.page_name.g_bubble", "Gravity Bubble" );
        add("bedres.page_name.g_staff", "Gravity Staff" );
        add("bedres.page_name.m_staff", "Mage Staff" );
        add("bedres.page_name.nebula", "Nebula" );

        add(BedrockReferences.CREATIVE_TAB_NAME, "Bedrock Resources Tab" );
        add(BedrockReferences.SCREEN_SCRAPE_TANK, "Scrape Tank" );
        add(Registration.SPORE_DEITY_EGG_ITEM.get(), "Spore Deity Spawn Egg");
        add(Registration.FLUXED_CREEP_EGG_ITEM.get(), "Fluxed Creep Spawn Egg");
        add(Registration.CHAINED_BLAZE_EGG_ITEM.get(), "Chained Blaze Spawn Egg");
        add(Registration.TRECKING_CREEPER_EGG_ITEM.get(), "Trecking Creeper Spawn Egg");
        add(Registration.BABY_GHAST_EGG_ITEM.get(), "Baby Ghast Spawn Egg");

        add(Registration.FLUXED_CREEP.get(), "Fluxed Creep");
        add(Registration.SPORE_DEITY.get(), "Spore Deity");
        add(Registration.CHAINED_BLAZE.get(), "Chained Blaze");
        add(Registration.TRECKING_CREEPER.get(), "Trecking Creeper");
        add(Registration.BABY_GHAST.get(), "Baby Ghast");
        add(Registration.LIGHT_PROJ_ENTITY.get(), "Light Projectile");
    }


}
