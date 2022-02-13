package com.alexvr.bedres.datagen;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModels extends ItemModelProvider {
    public ModItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, BedrockResources.MODID,existingFileHelper);

    }

    @Override
    protected void registerModels() {
        withExistingParent(Registration.ENDERIAN_ORE_OVERWORLD_ITEM.get().getRegistryName().getPath(),modLoc("block/enderian_ore_overworld"));
        withExistingParent(Registration.ENDERIAN_ORE_NETHER_ITEM.get().getRegistryName().getPath(),modLoc("block/enderian_ore_nether"));
        withExistingParent(Registration.ENDERIAN_ORE_END_ITEM.get().getRegistryName().getPath(),modLoc("block/enderian_ore_end"));
        withExistingParent(Registration.ENDERIAN_ORE_DEEPSLATE_ITEM.get().getRegistryName().getPath(),modLoc("block/enderian_ore_deepslate"));
        withExistingParent(Registration.BEDROCK_COMPRESSED_WIRE_ITEM.get().getRegistryName().getPath(),modLoc("block/bedrock_compressed_wire"));

        singleTexture(Registration.RAW_ENDERIAN_CHUNK.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0",modLoc("item/raw_enderian"));
        singleTexture(Registration.ENDERIAN_INGOT_ITEM.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0",modLoc("item/enderian_ingot"));
        singleTexture(Registration.FLUX_ORACLE_ITEM.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0",modLoc("item/flux_oracle"));
        singleTexture(Registration.FLUXED_CUPCAKE_ITEM.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0",modLoc("item/fluxed_cupcake"));
        singleTexture(Registration.SCRAPE_KNIFE_ITEM.get().getRegistryName().getPath(),
                mcLoc("item/handheld"),
                "layer0",modLoc("item/scrape_knife"));
        singleTexture(Registration.SCRAPER_MESH_ITEM.get().getRegistryName().getPath(),
                mcLoc("item/generated"),
                "layer0",modLoc("block/frame"));

        withExistingParent(Registration.XP_MEDALLION_ITEM.get().getRegistryName().getPath(),mcLoc("item/generated")).texture("layer0",modLoc("item/xp_medallion")).override()
                .predicate(new ResourceLocation(BedrockResources.MODID,"mode"),1).model(singleTexture(Registration.XP_MEDALLION_ITEM.get().getRegistryName().getPath() + "_a",
                        mcLoc("item/generated"),
                        "layer0",modLoc("item/xp_medallion_a")));

        singleTexture(Registration.XP_MEDALLION_ITEM.get().getRegistryName().getPath() + "_a",
                modLoc("item/xp_medallion"),"layer0",modLoc("item/xp_medallion_a"));


        withExistingParent(Registration.SPORE_DEITY_EGG_ITEM.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(Registration.FLUXED_CREEP_EGG_ITEM.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(Registration.CHAINED_BLAZE_EGG_ITEM.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(Registration.TRECKING_CREEPER_EGG_ITEM.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(Registration.BABY_GHAST_EGG_ITEM.get().getRegistryName().getPath(), mcLoc("item/template_spawn_egg"));

    }
}
