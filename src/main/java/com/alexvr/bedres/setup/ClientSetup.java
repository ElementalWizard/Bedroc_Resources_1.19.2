package com.alexvr.bedres.setup;

import com.alexvr.bedres.BedrockResources;
import com.alexvr.bedres.client.renderer.ItemPlatformRenderer;
import com.alexvr.bedres.client.screen.ScrapeTankScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = BedrockResources.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {

    public static void init(FMLClientSetupEvent event){
        event.enqueueWork( () -> {
            MenuScreens.register(Registration.SCRAPE_TANK_CONTAINER.get(), ScrapeTankScreen::new);
            ItemBlockRenderTypes.setRenderLayer(Registration.PEDESTAL_BLOCK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.SPIKE_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.ENDERIAN_RITUAL_PEDESTAL_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.FLUXED_GRAVITY_BUBBLE_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.FLUXED_SPORES_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.SCRAPER_MOTOR_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.SCRAPE_TANK_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.VOID_TEAR_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.DF_OAK_LEAVE_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.DF_SAPPLING_BLOCK.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(Registration.BLAZIUM_BLOCK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.SUN_DAIZE_BLOCK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.ENDER_HUSH_BLOCK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.ITEM_PLATFORM_BLOCK.get(), RenderType.cutout());
            ItemBlockRenderTypes.setRenderLayer(Registration.BEDROCK_WIRE_BLOCK.get(), RenderType.translucent());

            BlockEntityRenderers.register(Registration.ITEM_PLATFORM_TILE.get(), ItemPlatformRenderer::new);
        });

    }

    @SubscribeEvent
    public static void onItemColor(ColorHandlerEvent.Item event) {
        event.getItemColors().register((stack, i) -> 0xff0000, Registration.SPORE_DEITY_EGG_ITEM.get());
    }
}
