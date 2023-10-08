package com.alexvr.bedres.setup;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.alexvr.bedres.BedrockResources.MODID;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static  void init(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        TABS.register(bus);
    }
    public static final RegistryObject<CreativeModeTab> ITEMS_TABS = TABS.register(MODID,
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Registration.SCRAPE_KNIFE_ITEM.get()))
                    .title(Component.translatable("creativetab.all_items_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(Registration.SCRAPE_KNIFE_ITEM.get());
                        pOutput.accept(Registration.ENDERIAN_ORE_OVERWORLD_ITEM.get());
                        pOutput.accept(Registration.ENDERIAN_ORE_NETHER_ITEM.get());
                        pOutput.accept(Registration.ENDERIAN_ORE_END_ITEM.get());
                        pOutput.accept(Registration.ENDERIAN_ORE_DEEPSLATE_ITEM.get());
                        pOutput.accept(Registration.ENDERIAN_BLOCK_ITEM.get());
                        pOutput.accept(Registration.EVENT_ALTAR_ITEM.get());
                        pOutput.accept(Registration.ROPE_ITEM.get());
                        pOutput.accept(Registration.HEXTILE_ITEM.get());
                        pOutput.accept(Registration.ENDERIAN_BRICK_ITEM.get());
                        pOutput.accept(Registration.ENDERIAN_STAIRS_ITEM.get());
                        pOutput.accept(Registration.ENDERIAN_RITUAL_PEDESTAL_ITEM.get());
                        pOutput.accept(Registration.SPIKE_ITEM.get());
                        pOutput.accept(Registration.BASE_SPIKE_ITEM.get());
                        pOutput.accept(Registration.PEDESTAL_ITEM.get());
                        pOutput.accept(Registration.ITEM_PLATFORM_ITEM.get());
                        pOutput.accept(Registration.BLAZIUM_ITEM.get());
                        pOutput.accept(Registration.ENDER_HUSH_ITEM.get());
                        pOutput.accept(Registration.SUN_DAIZE_ITEM.get());
                        pOutput.accept(Registration.FLUXED_SPORES_ITEM.get());
                        pOutput.accept(Registration.DF_COOBLE_ITEM.get());
                        pOutput.accept(Registration.DF_DIRT_ITEM.get());
                        pOutput.accept(Registration.DF_GRASS_ITEM.get());
                        pOutput.accept(Registration.DF_OAK_LEAVE_ITEM.get());
                        pOutput.accept(Registration.DF_OAK_LOG_ITEM.get());
                        pOutput.accept(Registration.DF_SAPPLING_ITEM.get());
                        pOutput.accept(Registration.DF_OAK_PLANKS_ITEM.get());
                        pOutput.accept(Registration.DF_OAK_SLAB_ITEM.get());
                        pOutput.accept(Registration.DF_STRIPPED_OAK_LOG_ITEM.get());
                        pOutput.accept(Registration.FLUXED_GRAVITY_BUBBLE_ITEM.get());
                        pOutput.accept(Registration.BEDROCK_COMPRESSED_WIRE_ITEM.get());
                        pOutput.accept(Registration.VOID_TEAR_ITEM.get());
                        pOutput.accept(Registration.BEDROCK_WIRE_ITEM.get());
                        pOutput.accept(Registration.RAW_ENDERIAN_CHUNK.get());
                        pOutput.accept(Registration.STAFF_ITEM.get());
                        pOutput.accept(Registration.ENDERIAN_INGOT_ITEM.get());
                        pOutput.accept(Registration.FLUXED_CUPCAKE_ITEM.get());
                        pOutput.accept(Registration.NEBULA_HEART_ITEM.get());
                        pOutput.accept(Registration.XP_MEDALLION_ITEM.get());
                        pOutput.accept(Registration.MAGE_STAFF_ITEM.get());
                        pOutput.accept(Registration.FLUX_ORACLE_ITEM.get());
                    })
                    .build());
}
