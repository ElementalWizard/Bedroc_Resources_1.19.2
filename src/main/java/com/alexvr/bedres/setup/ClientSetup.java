package com.alexvr.bedres.setup;

import com.alexvr.bedres.BedrockResources;

import com.alexvr.bedres.blocks.HexTile;
import com.alexvr.bedres.client.renderer.*;
import com.alexvr.bedres.client.screen.ScrapeTankScreen;
import com.alexvr.bedres.client.screen.TreckingCreeperScreen;
import com.alexvr.bedres.entities.babyghast.BabyGhastModel;
import com.alexvr.bedres.entities.babyghast.BabyGhastRenderer;
import com.alexvr.bedres.entities.chainedblaze.ChainedBlazeModel;
import com.alexvr.bedres.entities.chainedblaze.ChainedBlazeRenderer;
import com.alexvr.bedres.entities.fluxedcreep.FluxedCreepModel;
import com.alexvr.bedres.entities.fluxedcreep.FluxedCreepRenderer;
import com.alexvr.bedres.entities.sporedeity.SporeDeityModel;
import com.alexvr.bedres.entities.sporedeity.SporeDeityRenderer;
import com.alexvr.bedres.entities.treckingcreeper.TreckingCreeperModel;
import com.alexvr.bedres.entities.treckingcreeper.TreckingCreeperRenderer;
import com.alexvr.bedres.items.CreeperCharm;
import com.alexvr.bedres.items.MageStaff;
import com.alexvr.bedres.utils.KeyBindings;
import com.alexvr.bedres.utils.NBTHelper;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

import static com.alexvr.bedres.items.XPMedallion.MODE;
import static com.alexvr.bedres.utils.KeyBindings.getKey;
import static com.alexvr.bedres.utils.RenderHelper.*;


@Mod.EventBusSubscriber(modid = BedrockResources.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    @SubscribeEvent
    public static void onItemColor(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((stack, i) -> ((MageStaff)stack.getItem()).getColor(stack), Registration.MAGE_STAFF_ITEM.get());
        event.getItemColors().register((stack, i) -> DyeColor.MAGENTA.getMaterialColor().col, Registration.HEXTILE_ITEM.get());
        event.getItemColors().register((stack, i) -> ((CreeperCharm)stack.getItem()).getColor(stack,i), Registration.CREEPER_CHARM_ITEM.get());
    }

    @SubscribeEvent
    public static void onBlockColor(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors().register((p_92646_, p_92647_, p_92648_, p_92649_) -> HexTile.getColor(p_92646_), Registration.HEXTILE_BLOCK.get());
    }

    @SubscribeEvent
    public static void onKeybindMapping(RegisterKeyMappingsEvent event) {
        KeyBindings.toggleMode = new KeyMapping(getKey("toggle_mode"), KeyConflictContext.IN_GAME , InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_K), getKey("category"));
        event.register(KeyBindings.toggleMode);
    }
    public static void init(FMLClientSetupEvent event) {

        event.enqueueWork( () -> {
            MenuScreens.register(Registration.SCRAPE_TANK_CONTAINER.get(), ScrapeTankScreen::new);

            BlockEntityRenderers.register(Registration.ITEM_PLATFORM_TILE.get(), ItemPlatformRenderer::new);
            BlockEntityRenderers.register(Registration.BASE_SPIKE_TILE.get(), BedrockiumTowerRenderer::new);
            BlockEntityRenderers.register(Registration.PEDESTAL_TILE.get(), BedrociumPedestalRenderer::new);
            BlockEntityRenderers.register(Registration.ENDERIAN_RITUAL_PEDESTAL_TILE.get(), EnderianRitualPedestalRenderer::new);
            BlockEntityRenderers.register(Registration.FLUXED_GRAVITY_BUBBLE_TILE.get(), FluxedGravityBubbleRenderer::new);
            ItemProperties.register(Registration.XP_MEDALLION_ITEM.get(), MODE, (itemStack, clientLevel, livingEntity, id) -> livingEntity != null ? NBTHelper.getInt(itemStack, "mode"): 0);
            MenuScreens.register(Registration.TRECKING_CREEPER_MENU.get(), TreckingCreeperScreen::new);
        });
        MinecraftForge.EVENT_BUS.register(ForgeHooksClient.ClientEvents.class);
    }
    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(FluxedCreepModel.LAYER_LOCATION, FluxedCreepModel::createBodyLayer);
        event.registerLayerDefinition(ChainedBlazeModel.LAYER_LOCATION,ChainedBlazeModel::createBodyLayer);
        event.registerLayerDefinition(SporeDeityModel.ARMOR_LAYER_LOCATION,SporeDeityModel::createBodyLayer);
        event.registerLayerDefinition(TreckingCreeperModel.LAYER_LOCATION,TreckingCreeperModel::createBodyLayer);
        event.registerLayerDefinition(TreckingCreeperModel.ARMOR_LAYER_LOCATION,TreckingCreeperModel::createBodyLayer);
        event.registerLayerDefinition(BabyGhastModel.LAYER_LOCATION,BabyGhastModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Registration.SPORE_DEITY.get(), SporeDeityRenderer::new);
        event.registerEntityRenderer(Registration.FLUXED_CREEP.get(), FluxedCreepRenderer::new);
        event.registerEntityRenderer(Registration.CHAINED_BLAZE.get(), ChainedBlazeRenderer::new);
        event.registerEntityRenderer(Registration.TRECKING_CREEPER.get(), TreckingCreeperRenderer::new);
        event.registerEntityRenderer(Registration.BABY_GHAST.get(), BabyGhastRenderer::new);
        event.registerEntityRenderer(Registration.LIGHT_PROJ_ENTITY.get(), RenderStub::new);
    }
    @SubscribeEvent
    public static void onTextureStitch(TextureStitchEvent.Pre event) {
        if (!event.getAtlas().location().equals(TextureAtlas.LOCATION_BLOCKS)) {
            return;
        }
        event.addSprite(ZETARUNE);
        event.addSprite(ALPHARUNE);
        event.addSprite(BETARUNE);
        event.addSprite(DELTARUNE);
        event.addSprite(EPSILONRUNE);
        event.addSprite(ETARUNE);
        event.addSprite(GAMARUNE);
        event.addSprite(THETARUNE);
    }
}
